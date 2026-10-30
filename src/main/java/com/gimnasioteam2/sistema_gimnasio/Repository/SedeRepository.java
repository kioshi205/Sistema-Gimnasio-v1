package com.gimnasioteam2.sistema_gimnasio.Repository;

import com.gimnasioteam2.sistema_gimnasio.Model.Sede;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Acceso a datos de la tabla SEDE.
 *
 * No se eliminan sedes físicamente: su estado se cambia con UPDATE.
 */
@Repository
public class SedeRepository {

    private static final RowMapper<Sede> SEDE_ROW_MAPPER = (rs, rowNum) -> {
        Sede sede = new Sede();
        sede.setIdSede(rs.getInt("id_sede"));
        sede.setNombreSede(rs.getString("nombre_sede"));
        sede.setDireccion(rs.getString("direccion"));
        sede.setEstado(rs.getString("estado"));
        return sede;
    };

    private final JdbcTemplate jdbcTemplate;

    public SedeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Sede> listar() {
        return jdbcTemplate.query(
                "SELECT id_sede, nombre_sede, direccion, estado FROM SEDE ORDER BY id_sede",
                SEDE_ROW_MAPPER);
    }

    public Optional<Sede> buscarPorId(int idSede) {
        return jdbcTemplate.query(
                        "SELECT id_sede, nombre_sede, direccion, estado FROM SEDE WHERE id_sede = ?",
                        SEDE_ROW_MAPPER,
                        idSede)
                .stream()
                .findFirst();
    }

    /**
     * Verifica si ya existe una sede con el mismo nombre, sin distinguir
     * mayúsculas/minúsculas. Si idExcluir no es null, excluye ese registro.
     */
    public boolean existeNombre(String nombreSede, Integer idExcluir) {
        if (idExcluir == null) {
            Integer total = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM SEDE WHERE LOWER(nombre_sede) = LOWER(?)",
                    Integer.class,
                    nombreSede);
            return total != null && total > 0;
        }

        Integer total = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM SEDE WHERE LOWER(nombre_sede) = LOWER(?) AND id_sede <> ?",
                Integer.class,
                nombreSede,
                idExcluir);
        return total != null && total > 0;
    }

    /**
     * Inserta una sede calculando el ID con MAX + 1.
     *
     * data.sql inserta las sedes con IDs explícitos (1 y 2) y H2 no adelanta la
     * secuencia AUTO_INCREMENT; un INSERT sin id intentaría usar el 1 y
     * provocaría una clave duplicada.
     */
    public int insertar(Sede sede) {
        String sql = "INSERT INTO SEDE (id_sede, nombre_sede, direccion, estado) "
                + "SELECT COALESCE(MAX(id_sede), 0) + 1, ?, ?, ? FROM SEDE";

        return jdbcTemplate.update(sql,
                sede.getNombreSede(),
                sede.getDireccion(),
                sede.getEstado());
    }

    public int actualizar(Sede sede) {
        return jdbcTemplate.update(
                "UPDATE SEDE SET nombre_sede = ?, direccion = ?, estado = ? WHERE id_sede = ?",
                sede.getNombreSede(),
                sede.getDireccion(),
                sede.getEstado(),
                sede.getIdSede());
    }

    /**
     * Desactivación lógica de la sede.
     */
    public int desactivar(int idSede) {
        return jdbcTemplate.update(
                "UPDATE SEDE SET estado = 'Inactivo' WHERE id_sede = ?",
                idSede);
    }

    /**
     * RN12: cuenta las suscripciones vigentes que tienen a esta sede como base.
     */
    public int contarSuscripcionesVigentes(int idSede) {
        Integer total = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM SUSCRIPCION "
                        + "WHERE id_sede = ? "
                        + "AND estado_suscripcion = 'ACTIVO' "
                        + "AND fecha_fin >= CURRENT_DATE",
                Integer.class,
                idSede);
        return total == null ? 0 : total;
    }
}
