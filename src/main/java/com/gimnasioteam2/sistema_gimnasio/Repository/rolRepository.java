package com.gimnasioteam2.sistema_gimnasio.Repository;

//import com.gimnasioteam2.sistema_gimnasio.Model.Rol;
import com.gimnasioteam2.sistema_gimnasio.Model.RolG;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class rolRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<RolG> filaRol = (rs, numeroFila) -> new RolG(
            rs.getInt("id_rol"),
            rs.getString("nombre_rol"),
            rs.getString("descripcion")
    );

    public rolRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /** F20: obtiene los roles fijos registrados en la tabla ROL. */
    public List<RolG> listarRoles() {
        String sql = "SELECT id_rol, nombre_rol, descripcion FROM ROL ORDER BY id_rol";
        return jdbcTemplate.query(sql, filaRol);
    }

    public RolG buscarPorId(int idRol) {
        String sql = "SELECT id_rol, nombre_rol, descripcion FROM ROL WHERE id_rol = ?";
        List<RolG> resultados = jdbcTemplate.query(sql, filaRol, idRol);
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    /** F21: actualiza únicamente la descripción del rol. */
    public int actualizarDescripcion(int idRol, String descripcion) {
        String sql = "UPDATE ROL SET descripcion = ? WHERE id_rol = ?";
        return jdbcTemplate.update(sql, descripcion, idRol);
    }
}
