
package com.gimnasioteam2.sistema_gimnasio.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CategoriaRepository {

    private final JdbcTemplate jdbcTemplate;

    public CategoriaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> listar() {
        String sql = """
            SELECT * FROM CATEGORIA
            ORDER BY id_categoria
            """;

        return jdbcTemplate.queryForList(sql);
    }

    public Map<String, Object> buscarPorId(int id) {
        String sql = """
            SELECT * FROM CATEGORIA WHERE id_categoria = ?
            """;

        List<Map<String, Object>> resultado =
                jdbcTemplate.queryForList(sql, id);

        return resultado.isEmpty() ? null : resultado.get(0);
    }

    public void registrar(String nombre, String descripcion) {
        String sql = """
            INSERT INTO CATEGORIA
            (nombre_categoria, descripcion, estado)
            VALUES (?, ?, 'Activo')
            """;

        jdbcTemplate.update(sql, nombre, descripcion);
    }

    public void actualizar(int id, String nombre, String descripcion) {
        String sql = """
            UPDATE CATEGORIA
            SET nombre_categoria = ?, descripcion = ?
            WHERE id_categoria = ?
            """;

        jdbcTemplate.update(sql, nombre, descripcion, id);
    }

    public boolean existeNombre(String nombre, int idExcluir) {
        String sql = """
            SELECT COUNT(*) FROM CATEGORIA
            WHERE LOWER(nombre_categoria) = LOWER(?)
            AND id_categoria <> ?
            """;

        Integer cantidad = jdbcTemplate.queryForObject(
                sql, Integer.class, nombre, idExcluir
        );

        return cantidad != null && cantidad > 0;
    }

    public boolean tieneProductosActivos(int idCategoria) {
        String sql = """
            SELECT COUNT(*) FROM PRODUCTO
            WHERE id_categoria = ? AND estado = 'Activo'
            """;

        Integer cantidad = jdbcTemplate.queryForObject(
                sql, Integer.class, idCategoria
        );

        return cantidad != null && cantidad > 0;
    }

    public void desactivar(int id) {
        String sql = """
            UPDATE CATEGORIA SET estado = 'Inactivo'
            WHERE id_categoria = ?
            """;

        jdbcTemplate.update(sql, id);
    }

    public void activar(int id) {
        String sql = """
            UPDATE CATEGORIA SET estado = 'Activo'
            WHERE id_categoria = ?
            """;

        jdbcTemplate.update(sql, id);
    }
}
