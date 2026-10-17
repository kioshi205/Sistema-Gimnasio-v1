package com.gimnasioteam2.sistema_gimnasio.Repository;

import com.gimnasioteam2.sistema_gimnasio.Model.Socio;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class seguridadRepository {

    private final JdbcTemplate jdbcTemplate;

    public seguridadRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Socio buscarPorDni(String dni) {
        String sql = "SELECT id, nombre, correo, dni, fecha_vencimiento FROM socio WHERE dni = ?";

        List<Socio> lista = jdbcTemplate.query(sql, new RowMapper<Socio>() {
            @Override
            public Socio mapRow(ResultSet rs, int rowNum) throws SQLException {
                Socio socio = new Socio();
                socio.setId(rs.getLong("id"));
                socio.setNombre(rs.getString("nombre"));
                socio.setCorreo(rs.getString("correo"));
                socio.setDni(rs.getString("dni"));
                if (rs.getDate("fecha_vencimiento") != null) {
                    socio.setFechaVencimiento(rs.getDate("fecha_vencimiento").toLocalDate());
                }
                return socio;
            }
        }, dni);

        if (lista.isEmpty()) {
            return null;
        }
        return lista.get(0);
    }
}
