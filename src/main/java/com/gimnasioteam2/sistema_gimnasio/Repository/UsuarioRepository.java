package com.gimnasioteam2.sistema_gimnasio.Repository;

import com.gimnasioteam2.sistema_gimnasio.Model.Usuario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Acceso a datos de la tabla USUARIO.
 *
 * RN01: en este repositorio no existe ninguna sentencia DELETE. Los empleados
 * se desactivan lógicamente mediante un UPDATE sobre estado_usuario.
 */
@Repository
public class UsuarioRepository {

    private static final String SELECT_BASE =
            "SELECT u.id_usuario, u.id_rol, u.dni, u.nombre, u.apellido, u.username, "
                    + "u.password, u.estado_usuario, r.nombre_rol "
                    + "FROM USUARIO u INNER JOIN ROL r ON u.id_rol = r.id_rol ";

    private static final RowMapper<Usuario> USUARIO_ROW_MAPPER = (rs, rowNum) -> {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(rs.getInt("id_usuario"));
        usuario.setIdRol(rs.getInt("id_rol"));
        usuario.setDni(rs.getString("dni"));
        usuario.setNombre(rs.getString("nombre"));
        usuario.setApellido(rs.getString("apellido"));
        usuario.setUsername(rs.getString("username"));
        usuario.setPassword(rs.getString("password"));
        usuario.setEstadoUsuario(rs.getString("estado_usuario"));
        usuario.setNombreRol(rs.getString("nombre_rol"));
        return usuario;
    };

    private final JdbcTemplate jdbcTemplate;

    public UsuarioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Lista empleados. Si el filtro tiene texto, busca por nombre, apellido o DNI.
     */
    public List<Usuario> listar(String filtro) {
        if (filtro != null && !filtro.isBlank()) {
            String patron = "%" + filtro.trim().toLowerCase(Locale.ROOT) + "%";
            String sql = SELECT_BASE
                    + "WHERE LOWER(u.nombre) LIKE ? OR LOWER(u.apellido) LIKE ? OR u.dni LIKE ? "
                    + "ORDER BY u.apellido, u.nombre";
            return jdbcTemplate.query(sql, USUARIO_ROW_MAPPER, patron, patron, patron);
        }

        return jdbcTemplate.query(SELECT_BASE + "ORDER BY u.apellido, u.nombre", USUARIO_ROW_MAPPER);
    }

    public Optional<Usuario> buscarPorId(int idUsuario) {
        return jdbcTemplate.query(SELECT_BASE + "WHERE u.id_usuario = ?", USUARIO_ROW_MAPPER, idUsuario)
                .stream()
                .findFirst();
    }

    public Optional<Usuario> buscarPorUsername(String username) {
        return jdbcTemplate.query(SELECT_BASE + "WHERE u.username = ?", USUARIO_ROW_MAPPER, username)
                .stream()
                .findFirst();
    }

    public boolean existeDni(String dni, Integer idExcluir) {
        if (idExcluir == null) {
            Integer total = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM USUARIO WHERE dni = ?",
                    Integer.class,
                    dni);
            return total != null && total > 0;
        }

        Integer total = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM USUARIO WHERE dni = ? AND id_usuario <> ?",
                Integer.class,
                dni,
                idExcluir);
        return total != null && total > 0;
    }

    public boolean existeUsername(String username, Integer idExcluir) {
        if (idExcluir == null) {
            Integer total = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM USUARIO WHERE username = ?",
                    Integer.class,
                    username);
            return total != null && total > 0;
        }

        Integer total = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM USUARIO WHERE username = ? AND id_usuario <> ?",
                Integer.class,
                username,
                idExcluir);
        return total != null && total > 0;
    }

    /**
     * Inserta un empleado calculando el ID con MAX + 1.
     *
     * data.sql inserta IDs explícitos (201, 202, 203). En H2, esos INSERT
     * explícitos no adelantan la secuencia AUTO_INCREMENT; por eso un INSERT
     * sin id podría reutilizar valores ya existentes. El cálculo con MAX + 1
     * mantiene IDs consecutivos y evita el choque de clave primaria.
     */
    public int insertar(Usuario usuario) {
        String sql = "INSERT INTO USUARIO "
                + "(id_usuario, id_rol, dni, nombre, apellido, username, password, estado_usuario) "
                + "SELECT COALESCE(MAX(id_usuario), 0) + 1, ?, ?, ?, ?, ?, ?, ? FROM USUARIO";

        return jdbcTemplate.update(sql,
                usuario.getIdRol(),
                usuario.getDni(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getEstadoUsuario());
    }

    /**
     * Actualiza los datos editables del empleado. El DNI no se modifica.
     */
    public int actualizar(Usuario usuario) {
        String sql = "UPDATE USUARIO "
                + "SET id_rol = ?, nombre = ?, apellido = ?, username = ? "
                + "WHERE id_usuario = ?";

        return jdbcTemplate.update(sql,
                usuario.getIdRol(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getUsername(),
                usuario.getIdUsuario());
    }

    public int actualizarPassword(int idUsuario, String password) {
        return jdbcTemplate.update(
                "UPDATE USUARIO SET password = ? WHERE id_usuario = ?",
                password,
                idUsuario);
    }

    /**
     * RN01: desactivación lógica. Nunca se elimina el registro.
     */
    public int desactivar(int idUsuario) {
        return jdbcTemplate.update(
                "UPDATE USUARIO SET estado_usuario = 'INACTIVO' WHERE id_usuario = ?",
                idUsuario);
    }

    /**
     * Reactivación lógica de un empleado previamente desactivado.
     */
    public int activar(int idUsuario) {
        return jdbcTemplate.update(
                "UPDATE USUARIO SET estado_usuario = 'ACTIVO' WHERE id_usuario = ?",
                idUsuario);
    }
}