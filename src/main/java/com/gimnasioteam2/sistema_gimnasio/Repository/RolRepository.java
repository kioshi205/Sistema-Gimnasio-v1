package com.gimnasioteam2.sistema_gimnasio.Repository;

import com.gimnasioteam2.sistema_gimnasio.Model.Rol;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Acceso de solo lectura a la tabla ROL. Los roles son datos fijos del sistema.
 */
@Repository
public class RolRepository {

    private static final RowMapper<Rol> ROL_ROW_MAPPER = (rs, rowNum) -> {
        Rol rol = new Rol();
        rol.setIdRol(rs.getInt("id_rol"));
        rol.setNombreRol(rs.getString("nombre_rol"));
        rol.setDescripcion(rs.getString("descripcion"));
        return rol;
    };

    private final JdbcTemplate jdbcTemplate;

    public RolRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Rol> listar() {
        return jdbcTemplate.query(
                "SELECT id_rol, nombre_rol, descripcion FROM ROL ORDER BY id_rol",
                ROL_ROW_MAPPER);
    }

    public Optional<Rol> buscarPorId(int idRol) {
        return jdbcTemplate.query(
                        "SELECT id_rol, nombre_rol, descripcion FROM ROL WHERE id_rol = ?",
                        ROL_ROW_MAPPER,
                        idRol)
                .stream()
                .findFirst();
    }
}