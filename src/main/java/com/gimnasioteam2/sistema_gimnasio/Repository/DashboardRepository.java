package com.gimnasioteam2.sistema_gimnasio.Repository;

import com.gimnasioteam2.sistema_gimnasio.Model.PuntoGrafico;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Consultas de solo lectura (SELECT) para las métricas del dashboard (F22).
 * Las etiquetas que devuelve son claves técnicas ("yyyy-MM" o "yyyy-MM-dd");
 * el Service se encarga de convertirlas a texto en español y rellenar ceros.
 */
@Repository
public class DashboardRepository {

    private final JdbcTemplate jdbcTemplate;

    public DashboardRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<PuntoGrafico> puntoMesMapper = (rs, rowNum) ->
            new PuntoGrafico(rs.getString("periodo"), rs.getBigDecimal("total"));

    private final RowMapper<PuntoGrafico> puntoConteoMapper = (rs, rowNum) ->
            new PuntoGrafico(rs.getString("periodo"), BigDecimal.valueOf(rs.getLong("total")));

    private final RowMapper<PuntoGrafico> puntoDiaMapper = (rs, rowNum) -> {
        java.sql.Date dia = rs.getDate("dia");
        String etiqueta = dia == null ? "" : dia.toLocalDate().toString();
        return new PuntoGrafico(etiqueta, BigDecimal.valueOf(rs.getLong("total")));
    };

    /**
     * Gráfico 1: ingresos por mes. Excluye suscripciones canceladas.
     */
    public List<PuntoGrafico> ingresosPorMes(LocalDate desde, LocalDate hastaExclusivo) {
        String sql = "SELECT FORMATDATETIME(fecha_inicio, 'yyyy-MM') AS periodo, "
                + "SUM(monto_pagado) AS total "
                + "FROM SUSCRIPCION "
                + "WHERE estado_suscripcion <> 'CANCELADO' "
                + "AND fecha_inicio >= ? AND fecha_inicio < ? "
                + "GROUP BY FORMATDATETIME(fecha_inicio, 'yyyy-MM') "
                + "ORDER BY periodo";
        return jdbcTemplate.query(sql, puntoMesMapper, desde, hastaExclusivo);
    }

    /**
     * Gráfico 2: cantidad de suscripciones nuevas por mes.
     */
    public List<PuntoGrafico> suscripcionesPorMes(LocalDate desde, LocalDate hastaExclusivo) {
        String sql = "SELECT FORMATDATETIME(fecha_inicio, 'yyyy-MM') AS periodo, "
                + "COUNT(*) AS total "
                + "FROM SUSCRIPCION "
                + "WHERE fecha_inicio >= ? AND fecha_inicio < ? "
                + "GROUP BY FORMATDATETIME(fecha_inicio, 'yyyy-MM') "
                + "ORDER BY periodo";
        return jdbcTemplate.query(sql, puntoConteoMapper, desde, hastaExclusivo);
    }

    /**
     * Gráfico 3: ingresos permitidos por día en los últimos 30 días.
     */
    public List<PuntoGrafico> ingresosPuertaPorDia(LocalDate desde, LocalDate hasta) {
        String sql = "SELECT CAST(fecha_hora AS DATE) AS dia, "
                + "COUNT(*) AS total "
                + "FROM MARCACION_ACCESO "
                + "WHERE resultado = 'PERMITIDO' "
                + "AND CAST(fecha_hora AS DATE) >= ? "
                + "AND CAST(fecha_hora AS DATE) <= ? "
                + "GROUP BY CAST(fecha_hora AS DATE) "
                + "ORDER BY dia";
        return jdbcTemplate.query(sql, puntoDiaMapper, desde, hasta);
    }

    /**
     * Gráfico 4: cantidad de clientes nuevos por mes.
     */
    public List<PuntoGrafico> clientesPorMes(LocalDate desde, LocalDate hastaExclusivo) {
        String sql = "SELECT FORMATDATETIME(fecha_registro, 'yyyy-MM') AS periodo, "
                + "COUNT(*) AS total "
                + "FROM CLIENTE "
                + "WHERE fecha_registro >= ? AND fecha_registro < ? "
                + "GROUP BY FORMATDATETIME(fecha_registro, 'yyyy-MM') "
                + "ORDER BY periodo";
        return jdbcTemplate.query(sql, puntoConteoMapper, desde, hastaExclusivo);
    }
}