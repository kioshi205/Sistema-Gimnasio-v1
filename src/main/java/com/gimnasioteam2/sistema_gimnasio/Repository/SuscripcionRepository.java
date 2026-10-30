package com.gimnasioteam2.sistema_gimnasio.Repository;

import com.gimnasioteam2.sistema_gimnasio.clases.PlanInfo;
import com.gimnasioteam2.sistema_gimnasio.clases.SuscripcionEstado;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Repository
public class SuscripcionRepository implements SuscripcionDAO {

    private final JdbcTemplate jdbcTemplate;

    public SuscripcionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean clienteActivo(Integer idCliente) {
        String sql = "SELECT COUNT(*) FROM CLIENTE WHERE id_cliente = ? AND estado_cliente = 'ACTIVO'";
        Integer n = jdbcTemplate.queryForObject(sql, Integer.class, idCliente);
        return n != null && n > 0;
    }

    // RN05
    @Override
    public int contarVigentes(Integer idCliente) {
        String sql = "SELECT COUNT(*) FROM SUSCRIPCION " +
                "WHERE id_cliente = ? AND estado_suscripcion = 'ACTIVO' AND fecha_fin >= CURRENT_DATE";
        Integer n = jdbcTemplate.queryForObject(sql, Integer.class, idCliente);
        return n == null ? 0 : n;
    }

    @Override
    public Optional<PlanInfo> buscarPlan(Integer idPlan) {
        String sql = "SELECT id_plan, duracion_meses, precio_total, estado, id_cliente " +
                "FROM PLAN WHERE id_plan = ?";
        List<PlanInfo> lista = jdbcTemplate.query(sql, (rs, i) -> new PlanInfo(
                rs.getInt("id_plan"),
                rs.getInt("duracion_meses"),
                rs.getBigDecimal("precio_total"),
                rs.getString("estado"),
                (Integer) rs.getObject("id_cliente")   // puede ser NULL
        ), idPlan);
        return lista.stream().findFirst();
    }

    @Override
    public boolean sedeActiva(Integer idSede) {
        String sql = "SELECT COUNT(*) FROM SEDE WHERE id_sede = ? AND estado = 'Activo'";
        Integer n = jdbcTemplate.queryForObject(sql, Integer.class, idSede);
        return n != null && n > 0;
    }

    @Override
    public boolean usuarioEsRecepcionista(Integer idUsuario) {
        String sql = "SELECT COUNT(*) FROM USUARIO u JOIN ROL r ON u.id_rol = r.id_rol " +
                "WHERE u.id_usuario = ? AND u.estado_usuario = 'ACTIVO' " +
                "AND r.nombre_rol = 'RECEPCIONISTA'";
        Integer n = jdbcTemplate.queryForObject(sql, Integer.class, idUsuario);
        return n != null && n > 0;
    }

    @Override
    public int insertar(Integer idCliente, Integer idPlan, Integer idUsuario, Integer idSede,
                        LocalDate fechaInicio, LocalDate fechaFin, BigDecimal monto) {
        String sql = "INSERT INTO SUSCRIPCION (id_cliente, id_plan, id_usuario, id_sede, " +
                "fecha_inicio, fecha_fin, monto_pagado, estado_suscripcion) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, 'ACTIVO')";
        return jdbcTemplate.update(sql, idCliente, idPlan, idUsuario, idSede,
                Date.valueOf(fechaInicio), Date.valueOf(fechaFin), monto);
    }

    @Override
    public List<Map<String, Object>> listarPlanesActivos() {
        return jdbcTemplate.queryForList(
                "SELECT id_plan, nombre_plan, tipo_plan, duracion_meses, precio_total " +
                        "FROM PLAN WHERE estado = 'Activo' AND tipo_plan = 'PREDETERMINADO' ORDER BY id_plan\"");
    }

    @Override
    public List<Map<String, Object>> listarSedesActivas() {
        return jdbcTemplate.queryForList(
                "SELECT id_sede, nombre_sede FROM SEDE WHERE estado = 'Activo' ORDER BY id_sede");
    }
    @Override
    public List<Map<String, Object>> listarClientesActivos() {
        return jdbcTemplate.queryForList(
                "SELECT id_cliente, dni, nombre, apellido " +
                        "FROM CLIENTE WHERE estado_cliente = 'ACTIVO' " +
                        "ORDER BY apellido, nombre");
    }
    @Override
    public Map<Integer, List<Map<String, Object>>> listarDetallePlanes() {
        String sql = "SELECT pd.id_plan, pr.nombre_subproducto, pr.precio_individual " +
                "FROM PLAN_DETALLE pd " +
                "JOIN PRODUCTO pr ON pd.id_producto = pr.id_producto " +
                "JOIN PLAN p ON pd.id_plan = p.id_plan " +
                "WHERE pd.estado = 'Activo' AND pr.estado = 'Activo' AND p.estado = 'Activo' " +
                "ORDER BY pd.id_plan, pr.id_producto" + "AND p.tipo_plan = 'PREDETERMINADO'";

        List<Map<String, Object>> filas = jdbcTemplate.queryForList(sql);

        // Agrupa las filas por plan: id_plan -> lista de subproductos
        Map<Integer, List<Map<String, Object>>> porPlan = new LinkedHashMap<>();
        for (Map<String, Object> fila : filas) {
            Integer idPlan = ((Number) fila.get("id_plan")).intValue();
            porPlan.computeIfAbsent(idPlan, k -> new ArrayList<>()).add(fila);
        }
        return porPlan;

    }
    @Override
    public Optional<Integer> buscarIdClientePorDni(String dni) {
        List<Integer> ids = jdbcTemplate.queryForList(
                "SELECT id_cliente FROM CLIENTE WHERE dni = ?", Integer.class, dni);
        return ids.stream().findFirst();
    }
    @Override
    public Optional<SuscripcionEstado> buscarEstado(Integer idSuscripcion) {
        return jdbcTemplate.query(
                "SELECT id_cliente, estado_suscripcion, fecha_fin FROM SUSCRIPCION WHERE id_suscripcion = ?",
                (rs, i) -> new SuscripcionEstado(rs.getInt("id_cliente"),
                        rs.getString("estado_suscripcion"),
                        rs.getDate("fecha_fin").toLocalDate()),
                idSuscripcion).stream().findFirst();
    }

    @Override
    public int actualizarEstado(Integer idSuscripcion, String nuevoEstado) {
        return jdbcTemplate.update(
                "UPDATE SUSCRIPCION SET estado_suscripcion = ? WHERE id_suscripcion = ?",
                nuevoEstado, idSuscripcion);
    }

    @Override
    public List<Map<String, Object>> listarSuscripcionesDetalle() {
        String sql = "SELECT s.id_suscripcion, c.dni, c.nombre, c.apellido, p.nombre_plan, sd.nombre_sede, " +
                "s.fecha_inicio, s.fecha_fin, s.monto_pagado, s.estado_suscripcion, " +
                "CASE WHEN s.estado_suscripcion = 'ACTIVO' AND s.fecha_fin >= CURRENT_DATE THEN 'VIGENTE' " +
                "     WHEN s.estado_suscripcion = 'ACTIVO' THEN 'VENCIDA' " +
                "     ELSE s.estado_suscripcion END AS situacion " +
                "FROM SUSCRIPCION s " +
                "JOIN CLIENTE c ON s.id_cliente = c.id_cliente " +
                "JOIN PLAN p ON s.id_plan = p.id_plan " +
                "JOIN SEDE sd ON s.id_sede = sd.id_sede " +
                "ORDER BY s.id_suscripcion";
        return jdbcTemplate.queryForList(sql);
    }
}