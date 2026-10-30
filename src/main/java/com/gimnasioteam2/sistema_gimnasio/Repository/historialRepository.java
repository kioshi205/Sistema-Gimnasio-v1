package com.gimnasioteam2.sistema_gimnasio.Repository;

import com.gimnasioteam2.sistema_gimnasio.Model.MarcacionAcceso;
import com.gimnasioteam2.sistema_gimnasio.Model.SedeG;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class historialRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<SedeG> filaSede = (rs, numeroFila) -> new SedeG(
            rs.getInt("id_sede"),
            rs.getString("nombre_sede"),
            rs.getString("direccion")
    );

    private final RowMapper<MarcacionAcceso> filaMarcacion = (rs, numeroFila) -> {
        MarcacionAcceso marcacion = new MarcacionAcceso();
        marcacion.setIdMarcacion(rs.getInt("id_marcacion"));
        if (rs.getTimestamp("fecha_hora") != null) {
            marcacion.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
        }
        marcacion.setDniConsultado(rs.getString("dni_consultado"));
        marcacion.setNombreSede(rs.getString("nombre_sede"));
        marcacion.setResultado(rs.getString("resultado"));
        marcacion.setMotivo(rs.getString("motivo"));

        String nombre = rs.getString("nombre_cliente");
        String apellido = rs.getString("apellido_cliente");
        if (nombre != null) {
            marcacion.setNombreCliente(apellido == null ? nombre : nombre + " " + apellido);
        }

        return marcacion;
    };

    public historialRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * F33: consulta el historial con filtros opcionales. Usa LEFT JOIN con
     * CLIENTE para que las consultas con DNI no registrado también aparezcan.
     */
    public List<MarcacionAcceso> consultarHistorial(LocalDate fechaDesde,
                                                    LocalDate fechaHasta,
                                                    Integer idSede,
                                                    String resultado) {
        StringBuilder sql = new StringBuilder(
                "SELECT m.id_marcacion, m.fecha_hora, m.dni_consultado, m.resultado, m.motivo, "
                        + "sd.nombre_sede, c.nombre AS nombre_cliente, c.apellido AS apellido_cliente "
                        + "FROM MARCACION_ACCESO m "
                        + "LEFT JOIN CLIENTE c ON c.id_cliente = m.id_cliente "
                        + "JOIN SEDE sd ON sd.id_sede = m.id_sede "
                        + "WHERE 1 = 1");

        List<Object> parametros = new ArrayList<>();

        if (fechaDesde != null) {
            sql.append(" AND CAST(m.fecha_hora AS DATE) >= ?");
            parametros.add(fechaDesde);
        }

        if (fechaHasta != null) {
            sql.append(" AND CAST(m.fecha_hora AS DATE) <= ?");
            parametros.add(fechaHasta);
        }

        if (idSede != null && idSede > 0) {
            sql.append(" AND m.id_sede = ?");
            parametros.add(idSede);
        }

        if (resultado != null && !resultado.isBlank() && !"TODOS".equalsIgnoreCase(resultado)) {
            sql.append(" AND UPPER(m.resultado) = ?");
            parametros.add(resultado.toUpperCase());
        }

        sql.append(" ORDER BY m.fecha_hora DESC, m.id_marcacion DESC");

        return jdbcTemplate.query(sql.toString(), filaMarcacion, parametros.toArray());
    }

    /** Todas las sedes, para poder filtrar también registros históricos. */
    public List<SedeG> listarSedes() {
        String sql = "SELECT id_sede, nombre_sede, direccion FROM SEDE ORDER BY nombre_sede";
        return jdbcTemplate.query(sql, filaSede);
    }
}
