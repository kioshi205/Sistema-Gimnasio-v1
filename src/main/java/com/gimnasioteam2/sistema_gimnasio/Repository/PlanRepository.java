package com.gimnasioteam2.sistema_gimnasio.Repository;

import com.gimnasioteam2.sistema_gimnasio.Model.Plan;
import com.gimnasioteam2.sistema_gimnasio.Model.PlanDetalle;
import com.gimnasioteam2.sistema_gimnasio.Model.Producto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PlanRepository {

    private static final String SQL_PLAN =
            "SELECT id_plan, nombre_plan, tipo_plan, duracion_meses, precio_total, estado, id_usuario, id_cliente "
                    + "FROM PLAN ";

    private static final String SQL_DETALLE =
            "SELECT pd.id_plan_detalle, pd.id_plan, pd.id_producto, pd.estado AS estado_detalle, "
                    + "p.nombre_subproducto, p.id_categoria, p.precio_individual, "
                    + "p.estado AS estado_producto, c.nombre_categoria "
                    + "FROM PLAN_DETALLE pd "
                    + "JOIN PRODUCTO p ON p.id_producto = pd.id_producto "
                    + "JOIN CATEGORIA c ON c.id_categoria = p.id_categoria ";

    private static final String SQL_PRODUCTO =
            "SELECT p.id_producto, p.nombre_subproducto, p.id_categoria, p.precio_individual, "
                    + "p.estado AS estado_producto, c.nombre_categoria "
                    + "FROM PRODUCTO p "
                    + "JOIN CATEGORIA c ON c.id_categoria = p.id_categoria ";

    private final JdbcTemplate jdbcTemplate;

    public PlanRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Producto> productoMapper = (rs, rowNum) -> new Producto(
            rs.getInt("id_producto"),
            rs.getString("nombre_subproducto"),
            rs.getInt("id_categoria"),
            rs.getString("nombre_categoria"),
            rs.getBigDecimal("precio_individual"),
            rs.getString("estado_producto")
    );

    private final RowMapper<Plan> planMapper = (rs, rowNum) -> {
        Plan plan = new Plan();
        plan.setIdPlan(rs.getInt("id_plan"));
        plan.setNombrePlan(rs.getString("nombre_plan"));
        plan.setTipoPlan(rs.getString("tipo_plan"));
        plan.setDuracionMeses(rs.getInt("duracion_meses"));
        plan.setPrecioTotal(rs.getBigDecimal("precio_total"));
        plan.setEstado(rs.getString("estado"));
        plan.setIdUsuario(rs.getInt("id_usuario"));
        int idCliente = rs.getInt("id_cliente");
        plan.setIdCliente(rs.wasNull() ? null : idCliente);
        return plan;
    };

    private final RowMapper<PlanDetalle> detalleMapper = (rs, rowNum) -> {
        PlanDetalle detalle = new PlanDetalle();
        detalle.setIdPlanDetalle(rs.getInt("id_plan_detalle"));
        detalle.setIdPlan(rs.getInt("id_plan"));
        detalle.setIdProducto(rs.getInt("id_producto"));
        detalle.setEstado(rs.getString("estado_detalle"));
        detalle.setProducto(new Producto(
                rs.getInt("id_producto"),
                rs.getString("nombre_subproducto"),
                rs.getInt("id_categoria"),
                rs.getString("nombre_categoria"),
                rs.getBigDecimal("precio_individual"),
                rs.getString("estado_producto")
        ));
        return detalle;
    };

    /**
     * Lista todos los planes (predeterminados y personalizados) con sus detalles.
     */
    public List<Plan> listarTodos() {
        List<Plan> planes = jdbcTemplate.query(SQL_PLAN + "ORDER BY id_plan", planMapper);
        if (planes.isEmpty()) {
            return planes;
        }

        Map<Integer, Plan> planesPorId = new LinkedHashMap<>();
        for (Plan plan : planes) {
            planesPorId.put(plan.getIdPlan(), plan);
        }

        String sqlDetalles = SQL_DETALLE
                + "ORDER BY pd.id_plan, c.nombre_categoria, p.nombre_subproducto";
        List<PlanDetalle> detalles = jdbcTemplate.query(sqlDetalles, detalleMapper);
        for (PlanDetalle detalle : detalles) {
            Plan plan = planesPorId.get(detalle.getIdPlan());
            if (plan != null) {
                plan.getDetalles().add(detalle);
            }
        }
        return planes;
    }

    /**
     * Busca un plan por su id, incluyendo todos sus detalles (activos e inactivos).
     */
    public Plan buscarPorId(int idPlan) {
        List<Plan> planes = jdbcTemplate.query(SQL_PLAN + "WHERE id_plan = ?", planMapper, idPlan);
        if (planes.isEmpty()) {
            return null;
        }

        Plan plan = planes.get(0);
        String sqlDetalles = SQL_DETALLE
                + "WHERE pd.id_plan = ? "
                + "ORDER BY c.nombre_categoria, p.nombre_subproducto";
        plan.setDetalles(jdbcTemplate.query(sqlDetalles, detalleMapper, idPlan));
        return plan;
    }

    /**
     * Subproductos que pueden ofrecerse en un plan (RN02).
     */
    public List<Producto> listarProductosActivos() {
        String sql = SQL_PRODUCTO
                + "WHERE p.estado = 'Activo' "
                + "ORDER BY c.nombre_categoria, p.nombre_subproducto";
        return jdbcTemplate.query(sql, productoMapper);
    }

    /**
     * Trae los productos solicitados sin filtrar por estado, para que el Service
     * pueda validar RN02 y detectar ids inexistentes.
     */
    public List<Producto> buscarProductosPorIds(List<Integer> idsProductos) {
        if (idsProductos == null || idsProductos.isEmpty()) {
            return new ArrayList<>();
        }
        String marcadores = String.join(", ", Collections.nCopies(idsProductos.size(), "?"));
        String sql = SQL_PRODUCTO + "WHERE p.id_producto IN (" + marcadores + ")";
        return jdbcTemplate.query(sql, productoMapper, idsProductos.toArray());
    }

    /**
     * Inserta la cabecera del plan y devuelve su id.
     * data.sql inserta ids explícitos y no adelanta la secuencia IDENTITY de H2,
     * por eso el siguiente id se calcula dentro de la misma transacción.
     */
    public int insertarPlan(Plan plan) {
        int idPlan = siguienteIdPlan();
        String sql = "INSERT INTO PLAN "
                + "(id_plan, nombre_plan, tipo_plan, duracion_meses, precio_total, estado, id_usuario, id_cliente) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(conexion -> {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idPlan);
            ps.setString(2, plan.getNombrePlan());
            ps.setString(3, plan.getTipoPlan());
            ps.setInt(4, plan.getDuracionMeses());
            ps.setBigDecimal(5, plan.getPrecioTotal());
            ps.setString(6, plan.getEstado());
            ps.setInt(7, plan.getIdUsuario());
            if (plan.getIdCliente() == null) {
                ps.setNull(8, Types.INTEGER);
            } else {
                ps.setInt(8, plan.getIdCliente());
            }
            return ps;
        });
        return idPlan;
    }

    public void insertarDetalle(int idPlan, int idProducto) {
        int idPlanDetalle = siguienteIdPlanDetalle();
        String sql = "INSERT INTO PLAN_DETALLE (id_plan_detalle, id_plan, id_producto, estado) "
                + "VALUES (?, ?, ?, 'Activo')";
        jdbcTemplate.update(sql, idPlanDetalle, idPlan, idProducto);
    }

    public void insertarDetalles(int idPlan, List<Integer> idsProductos) {
        if (idsProductos == null || idsProductos.isEmpty()) {
            return;
        }
        int idPlanDetalle = siguienteIdPlanDetalle();
        String sql = "INSERT INTO PLAN_DETALLE (id_plan_detalle, id_plan, id_producto, estado) "
                + "VALUES (?, ?, ?, 'Activo')";
        for (Integer idProducto : idsProductos) {
            jdbcTemplate.update(sql, idPlanDetalle++, idPlan, idProducto);
        }
    }

    private int siguienteIdPlan() {
        Integer siguiente = jdbcTemplate.queryForObject(
                "SELECT COALESCE(MAX(id_plan), 0) + 1 FROM PLAN", Integer.class);
        return siguiente == null ? 1 : siguiente;
    }

    private int siguienteIdPlanDetalle() {
        Integer siguiente = jdbcTemplate.queryForObject(
                "SELECT COALESCE(MAX(id_plan_detalle), 0) + 1 FROM PLAN_DETALLE", Integer.class);
        return siguiente == null ? 1 : siguiente;
    }

    /**
     * Actualiza solo los datos editables. No toca tipo_plan, id_cliente ni id_usuario.
     */
    public int actualizarPlan(int idPlan, String nombrePlan, int duracionMeses, BigDecimal precioTotal) {
        String sql = "UPDATE PLAN SET nombre_plan = ?, duracion_meses = ?, precio_total = ? "
                + "WHERE id_plan = ? AND estado = 'Activo'";
        return jdbcTemplate.update(sql, nombrePlan, duracionMeses, precioTotal, idPlan);
    }

    /**
     * Reactiva un plan dado de baja lógica. No toca detalles ni suscripciones.
     */
    public int activarPlan(int idPlan) {
        String sql = "UPDATE PLAN SET estado = 'Activo' WHERE id_plan = ? AND estado = 'Inactivo'";
        return jdbcTemplate.update(sql, idPlan);
    }

    public int desactivarPlan(int idPlan) {
        String sql = "UPDATE PLAN SET estado = 'Inactivo' WHERE id_plan = ? AND estado = 'Activo'";
        return jdbcTemplate.update(sql, idPlan);
    }

    /**
     * Marca como inactivos los detalles vigentes antes de aplicar la nueva selección.
     * Las filas se conservan (F12).
     */
    public int desactivarDetallesActivos(int idPlan) {
        String sql = "UPDATE PLAN_DETALLE SET estado = 'Inactivo' WHERE id_plan = ? AND estado = 'Activo'";
        return jdbcTemplate.update(sql, idPlan);
    }

    /**
     * Reactiva un detalle que ya existía (aunque estuviera inactivo).
     * Devuelve 0 si el subproducto nunca estuvo asociado al plan.
     */
    public int activarDetalle(int idPlan, int idProducto) {
        String sql = "UPDATE PLAN_DETALLE SET estado = 'Activo' WHERE id_plan = ? AND id_producto = ?";
        return jdbcTemplate.update(sql, idPlan, idProducto);
    }
}