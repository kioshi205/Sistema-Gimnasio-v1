package com.gimnasioteam2.sistema_gimnasio.Service;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InscripcionService {

    private final JdbcTemplate jdbc;

    public InscripcionService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // F15: arma el resumen y calcula el total
    public Map<String, Object> generarResumen(int clienteId, int planId, List<Integer> subIds) {
        Map<String, Object> cliente = jdbc.queryForMap("SELECT * FROM cliente WHERE id = ?", clienteId);
        Map<String, Object> plan = jdbc.queryForMap("SELECT * FROM plan WHERE id = ?", planId);

        BigDecimal total = (BigDecimal) plan.get("precio");
        List<Map<String, Object>> subproductos = new ArrayList<>();
        for (Integer subId : subIds) {
            Map<String, Object> sub = jdbc.queryForMap("SELECT * FROM subproducto WHERE id = ?", subId);
            subproductos.add(sub);
            total = total.add((BigDecimal) sub.get("precio"));
        }

        return Map.of("cliente", cliente, "plan", plan, "subproductos", subproductos, "total", total);
    }

    // F16: guarda la inscripción y calcula la vigencia (hoy + duración del plan)
    @Transactional
    public int registrar(int clienteId, int planId, List<Integer> subIds) {
        Map<String, Object> resumen = generarResumen(clienteId, planId, subIds);
        Map<String, Object> plan = (Map<String, Object>) resumen.get("plan");
        BigDecimal total = (BigDecimal) resumen.get("total");
        int dias = (Integer) plan.get("duracion_dias");

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO inscripcion (cliente_id, plan_id, total, fecha_inicio, fecha_fin) "
                            + "VALUES (?, ?, ?, CURRENT_DATE, DATEADD('DAY', ?, CURRENT_DATE))",
                    new String[] { "id" });
            ps.setInt(1, clienteId);
            ps.setInt(2, planId);
            ps.setBigDecimal(3, total);
            ps.setInt(4, dias);
            return ps;
        }, keyHolder);

        int inscripcionId = keyHolder.getKey().intValue();
        for (Integer subId : subIds) {
            jdbc.update("INSERT INTO inscripcion_subproducto VALUES (?, ?)", inscripcionId, subId);
        }
        return inscripcionId;
    }

    // F17: historial con plan base y subproductos
    public List<Map<String, Object>> consultarInscripciones() {
        return jdbc.queryForList(
                "SELECT i.id, c.nombre AS cliente, p.nombre AS plan, i.total, i.fecha_inicio, i.fecha_fin, "
                        + "COALESCE(LISTAGG(s.nombre, ', '), 'Solo plan base') AS subproductos "
                        + "FROM inscripcion i "
                        + "JOIN cliente c ON c.id = i.cliente_id "
                        + "JOIN plan p ON p.id = i.plan_id "
                        + "LEFT JOIN inscripcion_subproducto isp ON isp.inscripcion_id = i.id "
                        + "LEFT JOIN subproducto s ON s.id = isp.subproducto_id "
                        + "GROUP BY i.id, c.nombre, p.nombre, i.total, i.fecha_inicio, i.fecha_fin "
                        + "ORDER BY i.id DESC");
    }

    // F18: estado de cada membresía (ACTIVA, POR_VENCER, VENCIDA)
    public List<Map<String, Object>> consultarVigencias() {
        List<Map<String, Object>> filas = jdbc.queryForList(
                "SELECT i.id, c.nombre AS cliente, p.nombre AS plan, i.fecha_fin, "
                        + "DATEDIFF('DAY', CURRENT_DATE, i.fecha_fin) AS dias "
                        + "FROM inscripcion i "
                        + "JOIN cliente c ON c.id = i.cliente_id "
                        + "JOIN plan p ON p.id = i.plan_id "
                        + "ORDER BY i.fecha_fin");

        for (Map<String, Object> fila : filas) {
            int dias = ((Number) fila.get("dias")).intValue();
            String estado = dias < 0 ? "VENCIDA" : (dias <= 5 ? "POR_VENCER" : "ACTIVA");
            fila.put("estado", estado);
        }
        return filas;
    }
}