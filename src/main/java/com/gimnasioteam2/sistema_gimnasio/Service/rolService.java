package com.gimnasioteam2.sistema_gimnasio.Service;

//import com.gimnasioteam2.sistema_gimnasio.Model.Rol;
import com.gimnasioteam2.sistema_gimnasio.Model.RolG;
import com.gimnasioteam2.sistema_gimnasio.Repository.rolRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class rolService {

    /** Límite definido para la descripción de un rol. */
    public static final int MAX_CARACTERES_DESCRIPCION = 150;

    private final rolRepository rol_Repository;

    public rolService(rolRepository rol_Repository) {
        this.rol_Repository = rol_Repository;
    }

    public List<RolG> obtenerRoles() {
        return rol_Repository.listarRoles();
    }

    public RolG obtenerRolPorId(int idRol) {
        return rol_Repository.buscarPorId(idRol);
    }

    /**
     * Valida y guarda la nueva descripción de un rol.
     *
     * @return null si se guardó correctamente, o el mensaje de error a mostrar.
     */
    public String actualizarDescripcion(int idRol, String descripcion) {
        if (rol_Repository.buscarPorId(idRol) == null) {
            return "El rol indicado no existe.";
        }

        String descripcionLimpia = descripcion == null ? "" : descripcion.trim();

        if (descripcionLimpia.length() > MAX_CARACTERES_DESCRIPCION) {
            return "La descripción no puede superar los " + MAX_CARACTERES_DESCRIPCION + " caracteres.";
        }

        // La descripción puede quedar vacía: se guarda como NULL.
        String valorAGuardar = descripcionLimpia.isEmpty() ? null : descripcionLimpia;
        rol_Repository.actualizarDescripcion(idRol, valorAGuardar);
        return null;
    }
}
