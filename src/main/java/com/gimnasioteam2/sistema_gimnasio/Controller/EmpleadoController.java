package com.gimnasioteam2.sistema_gimnasio.Controller;

import com.gimnasioteam2.sistema_gimnasio.Model.Usuario;
import com.gimnasioteam2.sistema_gimnasio.Service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;

/**
 * Controlador de la interfaz I05 (Empleados).
 */
@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

    private static final Logger log = LoggerFactory.getLogger(EmpleadoController.class);

    private final UsuarioService usuarioService;

    public EmpleadoController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * F03: lista empleados. Si llega ?editar=id, precarga el formulario.
     */
    @GetMapping
    public String listar(@RequestParam(name = "filtro", required = false) String filtro,
                         @RequestParam(name = "editar", required = false) Integer editar,
                         Model model,
                         RedirectAttributes redirectAttributes) {

        if (!esAdministrador()) {
            return "redirect:/login";
        }

        String filtroSeguro = filtro == null ? "" : filtro.trim();
        model.addAttribute("filtro", filtroSeguro);

        try {
            model.addAttribute("empleados", usuarioService.listar(filtroSeguro));
            model.addAttribute("roles", usuarioService.listarRoles());

            if (editar != null) {
                model.addAttribute("empleadoForm", usuarioService.obtener(editar));
            } else {
                Usuario nuevo = new Usuario();
                nuevo.setEstadoUsuario("ACTIVO");
                model.addAttribute("empleadoForm", nuevo);
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/empleados";
        } catch (Exception e) {
            log.error("Error al cargar la lista de empleados.", e);
            model.addAttribute("empleados", Collections.emptyList());
            model.addAttribute("roles", Collections.emptyList());
            model.addAttribute("empleadoForm", new Usuario());
            model.addAttribute("error", "No se pudo cargar la información de empleados. Intente nuevamente.");
        }

        return "empleados/empleados";
    }

    /**
     * F02 y F04: crea o actualiza según venga o no el id oculto.
     */
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("empleadoForm") Usuario empleado,
                          RedirectAttributes redirectAttributes) {

        if (!esAdministrador()) {
            return "redirect:/login";
        }

        boolean esEdicion = empleado.getIdUsuario() != null;

        try {
            if (esEdicion) {
                usuarioService.actualizar(empleado);
                redirectAttributes.addFlashAttribute("exito", "Empleado actualizado correctamente.");
            } else {
                usuarioService.registrar(empleado);
                redirectAttributes.addFlashAttribute("exito", "Empleado registrado correctamente.");
            }
            return "redirect:/empleados";
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            log.error("Error al guardar el empleado.", e);
            redirectAttributes.addFlashAttribute(
                    "error", "No se pudo guardar el empleado. Intente nuevamente.");
        }

        if (esEdicion) {
            return "redirect:/empleados?editar=" + empleado.getIdUsuario();
        }
        return "redirect:/empleados";
    }

    /**
     * F05: desactiva lógicamente al empleado (RN01).
     */
    @PostMapping("/desactivar")
    public String desactivar(@RequestParam(name = "idUsuario", required = false) Integer idUsuario,
                             RedirectAttributes redirectAttributes) {

        if (!esAdministrador()) {
            return "redirect:/login";
        }

        if (idUsuario == null) {
            redirectAttributes.addFlashAttribute("error", "Debe indicar el empleado que desea desactivar.");
            return "redirect:/empleados";
        }

        try {
            usuarioService.desactivar(idUsuario);
            redirectAttributes.addFlashAttribute("exito", "Empleado desactivado correctamente.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            log.error("Error al desactivar el empleado.", e);
            redirectAttributes.addFlashAttribute(
                    "error", "No se pudo desactivar el empleado. Intente nuevamente.");
        }

        return "redirect:/empleados";
    }

    /**
     * Reactiva un empleado desactivado previamente.
     */
    @PostMapping("/activar")
    public String activar(@RequestParam(name = "idUsuario", required = false) Integer idUsuario,
                          RedirectAttributes redirectAttributes) {

        if (!esAdministrador()) {
            return "redirect:/login";
        }

        if (idUsuario == null) {
            redirectAttributes.addFlashAttribute("error", "Debe indicar el empleado que desea activar.");
            return "redirect:/empleados";
        }

        try {
            usuarioService.activar(idUsuario);
            redirectAttributes.addFlashAttribute("exito", "Empleado activado correctamente.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            log.error("Error al activar el empleado.", e);
            redirectAttributes.addFlashAttribute(
                    "error", "No se pudo activar el empleado. Intente nuevamente.");
        }

        return "redirect:/empleados";
    }

    /**
     * Validación del rol ADMINISTRADOR aislada en un solo método.
     *
     * TODO F01: cuando el login esté implementado, reemplazar el return true por
     * la comprobación de la sesión. Por ejemplo:
     *
     * HttpSession session...
     * Usuario autenticado = (Usuario) session.getAttribute("usuarioLogueado");
     * return autenticado != null
     *         && "ADMINISTRADOR".equalsIgnoreCase(autenticado.getNombreRol());
     *
     * Mientras F01 no esté listo se devuelve true para poder probar I05.
     */
    private boolean esAdministrador() {
        return true;
    }
}
