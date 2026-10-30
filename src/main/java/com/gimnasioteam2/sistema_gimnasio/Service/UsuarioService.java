package com.gimnasioteam2.sistema_gimnasio.Service;

import com.gimnasioteam2.sistema_gimnasio.Model.Rol;
import com.gimnasioteam2.sistema_gimnasio.Model.Usuario;
import com.gimnasioteam2.sistema_gimnasio.Repository.RolRepository;
import com.gimnasioteam2.sistema_gimnasio.Repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Reglas de negocio de los empleados (I05: F02, F03, F04 y F05).
 */
@Service
public class UsuarioService {

    private static final String ESTADO_ACTIVO = "ACTIVO";
    private static final String ESTADO_INACTIVO = "INACTIVO";
    private static final Pattern PATRON_DNI = Pattern.compile("\\d{8}");

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    /**
     * F03: lista empleados con filtro opcional por nombre, apellido o DNI.
     */
    public List<Usuario> listar(String filtro) {
        return usuarioRepository.listar(filtro == null ? "" : filtro.trim());
    }

    /**
     * Roles fijos que llenan el selector del formulario.
     */
    public List<Rol> listarRoles() {
        return rolRepository.listar();
    }

    public Usuario obtener(int idUsuario) {
        return usuarioRepository.buscarPorId(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("El empleado indicado no existe."));
    }

    /**
     * F02: registra un empleado con estado ACTIVO.
     */
    public void registrar(Usuario empleado) {
        normalizar(empleado);
        validarDatosObligatorios(empleado, true);
        validarRol(empleado.getIdRol());

        if (usuarioRepository.existeDni(empleado.getDni(), null)) {
            throw new IllegalArgumentException("Ya existe un empleado registrado con ese DNI.");
        }
        if (usuarioRepository.existeUsername(empleado.getUsername(), null)) {
            throw new IllegalArgumentException("Ya existe un empleado registrado con ese nombre de usuario.");
        }

        // La contraseña se almacena tal como la define el modelo del proyecto,
        // sin hashing ni librerías de seguridad adicionales.
        empleado.setEstadoUsuario(ESTADO_ACTIVO);
        usuarioRepository.insertar(empleado);
    }

    /**
     * F04: actualiza nombre, apellido, rol, username y, opcionalmente, password.
     */
    @Transactional
    public void actualizar(Usuario datos) {
        if (datos == null || datos.getIdUsuario() == null) {
            throw new IllegalArgumentException("No se indicó el empleado que se desea actualizar.");
        }

        Usuario actual = obtener(datos.getIdUsuario());

        normalizar(datos);

        // El DNI es de solo lectura en la edición: se conserva el valor almacenado.
        datos.setDni(actual.getDni());

        validarDatosObligatorios(datos, false);
        validarRol(datos.getIdRol());

        // Revalidación de unicidad excluyendo al propio registro.
        if (usuarioRepository.existeDni(actual.getDni(), datos.getIdUsuario())) {
            throw new IllegalArgumentException("Ya existe un empleado registrado con ese DNI.");
        }
        if (usuarioRepository.existeUsername(datos.getUsername(), datos.getIdUsuario())) {
            throw new IllegalArgumentException("Ya existe un empleado registrado con ese nombre de usuario.");
        }

        // El estado no se modifica desde el formulario de edición.
        datos.setEstadoUsuario(actual.getEstadoUsuario());
        usuarioRepository.actualizar(datos);

        // Si el campo contraseña viene vacío, se conserva la contraseña actual.
        if (datos.getPassword() != null && !datos.getPassword().isBlank()) {
            usuarioRepository.actualizarPassword(datos.getIdUsuario(), datos.getPassword());
        }
    }

    /**
     * F05: desactiva un empleado. RN01: nunca se elimina el registro.
     */
    @Transactional
    public void desactivar(int idUsuario) {
        Usuario empleado = obtener(idUsuario);

        if (ESTADO_INACTIVO.equalsIgnoreCase(empleado.getEstadoUsuario())) {
            throw new IllegalStateException("El empleado ya se encuentra inactivo.");
        }

        /*
         * TODO F01: impedir que el administrador autenticado se desactive a sí mismo.
         * Se deja aislado y comentado porque el login todavía no está implementado.
         *
         * Usuario autenticado = (Usuario) session.getAttribute("usuarioLogueado");
         * if (autenticado != null && autenticado.getIdUsuario().equals(idUsuario)) {
         *     throw new IllegalStateException("No puede desactivar su propia cuenta de administrador.");
         * }
         */

        usuarioRepository.desactivar(idUsuario);
    }

    /**
     * Reactiva un empleado que fue desactivado previamente.
     */
    @Transactional
    public void activar(int idUsuario) {
        Usuario empleado = obtener(idUsuario);

        if (ESTADO_ACTIVO.equalsIgnoreCase(empleado.getEstadoUsuario())) {
            throw new IllegalStateException("El empleado ya se encuentra activo.");
        }

        usuarioRepository.activar(idUsuario);
    }

    /**
     * RN02: un empleado inactivo no puede iniciar sesión.
     *
     * Queda listo para que F01 (login) lo invoque cuando esté implementado:
     * si devuelve false, el login debe rechazar el acceso.
     */
    public boolean puedeIniciarSesion(String username) {
        if (username == null || username.isBlank()) {
            return false;
        }

        return usuarioRepository.buscarPorUsername(username.trim())
                .map(usuario -> ESTADO_ACTIVO.equalsIgnoreCase(usuario.getEstadoUsuario()))
                .orElse(false);
    }

    private void normalizar(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("No se recibieron los datos del empleado.");
        }

        usuario.setDni(recortar(usuario.getDni()));
        usuario.setNombre(recortar(usuario.getNombre()));
        usuario.setApellido(recortar(usuario.getApellido()));
        usuario.setUsername(recortar(usuario.getUsername()));
        // La contraseña no se recorta para no alterar el valor ingresado.
    }

    private void validarDatosObligatorios(Usuario usuario, boolean esRegistro) {
        if (usuario.getNombre() == null || usuario.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        if (usuario.getNombre().length() > 100) {
            throw new IllegalArgumentException("El nombre no puede superar los 100 caracteres.");
        }

        if (usuario.getApellido() == null || usuario.getApellido().isEmpty()) {
            throw new IllegalArgumentException("El apellido es obligatorio.");
        }
        if (usuario.getApellido().length() > 100) {
            throw new IllegalArgumentException("El apellido no puede superar los 100 caracteres.");
        }

        if (usuario.getDni() == null || usuario.getDni().isEmpty()) {
            throw new IllegalArgumentException("El DNI es obligatorio.");
        }
        if (!PATRON_DNI.matcher(usuario.getDni()).matches()) {
            throw new IllegalArgumentException("El DNI debe tener exactamente 8 dígitos numéricos.");
        }

        if (usuario.getUsername() == null || usuario.getUsername().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario es obligatorio.");
        }
        if (usuario.getUsername().length() > 50) {
            throw new IllegalArgumentException("El nombre de usuario no puede superar los 50 caracteres.");
        }

        if (esRegistro && (usuario.getPassword() == null || usuario.getPassword().isBlank())) {
            throw new IllegalArgumentException("La contraseña es obligatoria.");
        }
        if (usuario.getPassword() != null && usuario.getPassword().length() > 255) {
            throw new IllegalArgumentException("La contraseña no puede superar los 255 caracteres.");
        }
    }

    private void validarRol(Integer idRol) {
        if (idRol == null) {
            throw new IllegalArgumentException("Debe seleccionar un rol.");
        }
        if (rolRepository.buscarPorId(idRol).isEmpty()) {
            throw new IllegalArgumentException("El rol seleccionado no existe.");
        }
    }

    private String recortar(String valor) {
        return valor == null ? null : valor.trim();
    }
}
