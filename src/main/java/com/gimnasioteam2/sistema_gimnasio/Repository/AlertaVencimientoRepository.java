package com.gimnasioteam2.sistema_gimnasio.Repository;

import com.gimnasioteam2.sistema_gimnasio.clases.AlertaVencimiento;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public class AlertaVencimientoRepository implements AlertaVencimientoDAO {

    private final JdbcTemplate jdbcTemplate;

    public AlertaVencimientoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<AlertaVencimiento> alertaRowMapper = (rs, rowNum) -> {
        AlertaVencimiento a = new AlertaVencimiento();
        a.setId_suscripcion(rs.getInt("id_suscripcion"));
        a.setId_cliente(rs.getInt("id_cliente"));
        a.setDni(rs.getString("dni"));
        a.setNombre_cliente(rs.getString("nombre") + " " + rs.getString("apellido"));
        a.setTelefono(rs.getString("telefono"));
        a.setEmail(rs.getString("email"));
        a.setNombre_plan(rs.getString("nombre_plan"));
        a.setFecha_fin(rs.getDate("fecha_fin").toLocalDate());
        return a;
    };

    @Override
    public List<AlertaVencimiento> ListaPorVencer(LocalDate fechaLimite) {
        String sql = "SELECT s.id_suscripcion, s.id_cliente, s.fecha_fin, " +
                "       c.dni, c.nombre, c.apellido, c.telefono, c.email, " +
                "       p.nombre_plan " +
                "FROM SUSCRIPCION s " +
                "JOIN CLIENTE c ON c.id_cliente = s.id_cliente " +
                "JOIN PLAN p ON p.id_plan = s.id_plan " +
                "WHERE s.estado_suscripcion = 'ACTIVO' " +
                "  AND s.fecha_fin <= ? " +
                "ORDER BY s.fecha_fin ASC";
        return jdbcTemplate.query(sql, alertaRowMapper, Date.valueOf(fechaLimite));
    }
}