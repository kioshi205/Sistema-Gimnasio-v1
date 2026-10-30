package com.gimnasioteam2.sistema_gimnasio.Repository;

import com.gimnasioteam2.sistema_gimnasio.Model.Cliente;
import com.gimnasioteam2.sistema_gimnasio.Model.Sede;
import com.gimnasioteam2.sistema_gimnasio.Model.Suscripcion;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class accesoRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Sede> filaSede = (rs, numeroFila) -> new Sede(
            rs.getInt("id_sede"),
            rs.getString("nombre_sede"),
            rs.getString("direccion")
    );

    private final RowMapper<Cliente> filaCliente = (rs, numeroFila) -> new Cliente(
            rs.getInt("id_cliente"),
            rs.getString("dni"),
            rs.getString("nombre"),
            rs.getString("apellido"),
            rs.getString("estado_cliente")
    );

    private final RowMapper<Suscripcion> filaSuscripcion = (rs, numeroFila) -> {
        Suscripcion suscripcion = new Suscripcion();
        suscripcion.setIdSuscripcion(rs.getInt("id_suscripcion"));
        suscripcion.setIdPlan(rs.getInt("id_plan"));
        suscripcion.setNombrePlan(rs.getString("nombre_plan"));
        suscripcion.setIdSede(rs.getInt("id_sede"));
        suscripcion.setNombreSede(rs.getString("nombre_sede"));
        Date fechaInicio = rs.getDate("fecha_inicio");
        Date fechaFin = rs.getDate("fecha_fin");
        suscripcion.setFechaInicio(fechaInicio == null ? null : fechaInicio.toLocalDate());
        suscripcion.setFechaFin(fechaFin == null ? null : fechaFin.toLocalDate());
        suscripcion.setEstadoSuscripcion(rs.getString("estado_suscripcion"));
        return suscripcion;
    };

    public accesoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /** Sedes disponibles para indicar dónde está el guardia. */
    public List<Sede> listarSedesActivas() {
        String sql = "SELECT id_sede, nombre_sede, direccion FROM SEDE "
                + "WHERE UPPER(estado) = 'ACTIVO' ORDER BY nombre_sede";
        return jdbcTemplate.query(sql, filaSede);
    }

    public Sede buscarSedeActivaPorId(int idSede) {
        String sql = "SELECT id_sede, nombre_sede, direccion FROM SEDE "
                + "WHERE id_sede = ? AND UPPER(estado) = 'ACTIVO'";
        List<Sede> resultados = jdbcTemplate.query(sql, filaSede, idSede);
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    /** Paso 1: verifica si el DNI pertenece a un cliente registrado. */
    public Cliente buscarClientePorDni(String dni) {
        String sql = "SELECT id_cliente, dni, nombre, apellido, estado_cliente "
                + "FROM CLIENTE WHERE dni = ?";
        List<Cliente> resultados = jdbcTemplate.query(sql, filaCliente, dni);
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    /** Suscripción más reciente del cliente (sirve para validar y para mostrar sus datos). */
    public Suscripcion buscarSuscripcionMasReciente(int idCliente) {
        String sql = "SELECT s.id_suscripcion, s.id_sede, s.fecha_inicio, s.fecha_fin, "
                + "s.estado_suscripcion, p.id_plan, p.nombre_plan, sd.nombre_sede "
                + "FROM SUSCRIPCION s "
                + "JOIN PLAN p ON p.id_plan = s.id_plan "
                + "JOIN SEDE sd ON sd.id_sede = s.id_sede "
                + "WHERE s.id_cliente = ? "
                + "ORDER BY s.fecha_fin DESC, s.id_suscripcion DESC LIMIT 1";
        List<Suscripcion> resultados = jdbcTemplate.query(sql, filaSuscripcion, idCliente);
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    /** Paso 4 (RN09): verifica si el plan incluye el subproducto 104 (acceso a todas las sedes). */
    public boolean planIncluyeAccesoTotal(int idPlan) {
        String sql = "SELECT COUNT(*) FROM PLAN_DETALLE pd "
                + "JOIN PRODUCTO pr ON pr.id_producto = pd.id_producto "
                + "WHERE pd.id_plan = ? AND pd.id_producto = 104 "
                + "AND UPPER(pd.estado) = 'ACTIVO' AND UPPER(pr.estado) = 'ACTIVO'";
        Integer cantidad = jdbcTemplate.queryForObject(sql, Integer.class, idPlan);
        return cantidad != null && cantidad > 0;
    }

    public String buscarNombreUsuario(int idUsuario) {
        String sql = "SELECT nombre, apellido FROM USUARIO WHERE id_usuario = ?";
        List<String> resultados = jdbcTemplate.query(sql,
                (rs, numeroFila) -> rs.getString("nombre") + " " + rs.getString("apellido"),
                idUsuario);
        return resultados.isEmpty() ? "Personal de Seguridad" : resultados.get(0);
    }

    /** Guarda la marcación en el historial, sea el acceso permitido o denegado. */
    public void registrarMarcacion(Integer idCliente,
                                   Integer idSuscripcion,
                                   int idUsuario,
                                   int idSede,
                                   String dniConsultado,
                                   String resultado,
                                   String motivo) {
        String sql = "INSERT INTO MARCACION_ACCESO "
                + "(id_cliente, id_suscripcion, id_usuario, id_sede, dni_consultado, "
                + "fecha_hora, resultado, motivo) "
                + "VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, ?)";
        jdbcTemplate.update(sql, idCliente, idSuscripcion, idUsuario, idSede, dniConsultado, resultado, motivo);
    }
}
