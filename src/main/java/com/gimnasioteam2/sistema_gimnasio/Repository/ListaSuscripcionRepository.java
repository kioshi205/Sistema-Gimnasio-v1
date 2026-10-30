package com.gimnasioteam2.sistema_gimnasio.Repository;

import com.gimnasioteam2.sistema_gimnasio.clases.Cliente;
import com.gimnasioteam2.sistema_gimnasio.clases.Suscripciones;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ListaSuscripcionRepository implements  ListaSuscripcionDAO {
    private final JdbcTemplate jdbcTemplate;

    public ListaSuscripcionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private final RowMapper<Suscripciones> ListaSuscripcionRowMapper = (rs, rowNum) -> {
        return new Suscripciones(
                rs.getInt("id_suscripcion"),
                rs.getInt("id_cliente"),
                rs.getInt("id_plan"),
                rs.getInt("id_usuario"),
                rs.getInt("id_sede"),
                rs.getDate("fecha_inicio"),
                rs.getDate("fecha_fin"),
                rs.getDouble("monto_pagado"),
                rs.getString("estado_suscripcion")
        );
    };
    public List<Suscripciones> ListaSuscripcion() {
        String sql = "SELECT * FROM SUSCRIPCION";
        return jdbcTemplate.query(sql, ListaSuscripcionRowMapper);
    }
    //para el F28
    @Override
    public int desactivarSuscripcion(Integer idSuscripcion) {
        String sql = "UPDATE SUSCRIPCION SET estado_suscripcion = 'DESACTIVADO' " +
                "WHERE id_suscripcion = ? AND estado_suscripcion = 'ACTIVO'";
        return jdbcTemplate.update(sql, idSuscripcion);
    }
}




