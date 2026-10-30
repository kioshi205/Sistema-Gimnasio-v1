
package com.gimnasioteam2.sistema_gimnasio.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public class ProductoRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> listar() {
        String sql = """
            SELECT p.id_producto, p.nombre_subproducto, p.id_categoria,
                   c.nombre_categoria, p.precio_individual, p.estado
            FROM PRODUCTO p
            INNER JOIN CATEGORIA c ON p.id_categoria = c.id_categoria
            ORDER BY p.id_producto
            """;

        return jdbcTemplate.queryForList(sql);
    }

    public Map<String, Object> buscarPorId(int id) {
        String sql = "SELECT * FROM PRODUCTO WHERE id_producto = ?";
        List<Map<String, Object>> resultado = jdbcTemplate.queryForList(sql, id);
        return resultado.isEmpty() ? null : resultado.get(0);
    }

    public void registrar(String nombre, int idCategoria, BigDecimal precio) {
        String sql = """
            INSERT INTO PRODUCTO
            (nombre_subproducto, id_categoria, precio_individual, estado)
            VALUES (?, ?, ?, 'Activo')
            """;

        jdbcTemplate.update(sql, nombre, idCategoria, precio);
    }

    public void actualizar(int id, String nombre, int idCategoria,
                           BigDecimal precio) {
        String sql = """
            UPDATE PRODUCTO
            SET nombre_subproducto = ?, id_categoria = ?, precio_individual = ?
            WHERE id_producto = ?
            """;

        jdbcTemplate.update(sql, nombre, idCategoria, precio, id);
    }

    public boolean existeNombre(String nombre, int idExcluir) {
        String sql = """
            SELECT COUNT(*) FROM PRODUCTO
            WHERE LOWER(nombre_subproducto) = LOWER(?)
            AND id_producto <> ?
            """;

        Integer cantidad = jdbcTemplate.queryForObject(
                sql, Integer.class, nombre, idExcluir
        );

        return cantidad != null && cantidad > 0;
    }

    public boolean estaEnPlanActivo(int idProducto) {
        String sql = """
            SELECT COUNT(*)
            FROM PLAN_DETALLE pd
            INNER JOIN PLAN p ON pd.id_plan = p.id_plan
            WHERE pd.id_producto = ?
            AND pd.estado = 'Activo'
            AND p.estado = 'Activo'
            """;

        Integer cantidad = jdbcTemplate.queryForObject(
                sql, Integer.class, idProducto
        );

        return cantidad != null && cantidad > 0;
    }

    public boolean categoriaActiva(int idCategoria) {
        String sql = """
            SELECT COUNT(*) FROM CATEGORIA
            WHERE id_categoria = ? AND estado = 'Activo'
            """;

        Integer cantidad = jdbcTemplate.queryForObject(
                sql, Integer.class, idCategoria
        );

        return cantidad != null && cantidad > 0;
    }

    public void desactivar(int id) {
        String sql = """
            UPDATE PRODUCTO SET estado = 'Inactivo'
            WHERE id_producto = ?
            """;

        jdbcTemplate.update(sql, id);
    }

    public void activar(int id) {
        String sql = """
            UPDATE PRODUCTO SET estado = 'Activo'
            WHERE id_producto = ?
            """;

        jdbcTemplate.update(sql, id);
    }
}
