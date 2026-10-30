package com.gimnasioteam2.sistema_gimnasio.Controller;

import com.gimnasioteam2.sistema_gimnasio.Model.Sede;
import com.gimnasioteam2.sistema_gimnasio.Service.SedeService;
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
 * Controlador de la interfaz I09 (Sedes).
 */
@Controller
@RequestMapping("/sedes")
public class SedeController {

    private static final Logger log = LoggerFactory.getLogger(SedeController.class);

    private final SedeService sedeService;

    public SedeController(SedeService sedeService) {
        this.sedeService = sedeService;
    }

    /**
     * F18: lista sedes. Si llega ?editar=id, precarga el formulario.
     */
    @GetMapping
    public String listar(@RequestParam(name = "editar", required = false) Integer editar,
                         Model model,
                         RedirectAttributes redirectAttributes) {

        if (!esAdministrador()) {
            return "redirect:/login";
        }

        try {
            model.addAttribute("sedes", sedeService.listar());

            if (editar != null) {
                model.addAttribute("sedeForm", sedeService.obtener(editar));
            } else {
                Sede nueva = new Sede();
                nueva.setEstado("Activo");
                model.addAttribute("sedeForm", nueva);
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/sedes";
        } catch (Exception e) {
            log.error("Error al cargar la lista de sedes.", e);
            model.addAttribute("sedes", Collections.emptyList());
            model.addAttribute("sedeForm", new Sede());
            model.addAttribute("error", "No se pudo cargar la información de sedes. Intente nuevamente.");
        }

        return "sedes/sedes";
    }

    /**
     * F17 y F19: crea o actualiza según venga o no el id oculto.
     */
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("sedeForm") Sede sede,
                          RedirectAttributes redirectAttributes) {

        if (!esAdministrador()) {
            return "redirect:/login";
        }

        boolean esEdicion = sede.getIdSede() != null;

        try {
            if (esEdicion) {
                sedeService.actualizar(sede);
                redirectAttributes.addFlashAttribute("exito", "Sede actualizada correctamente.");
            } else {
                sedeService.registrar(sede);
                redirectAttributes.addFlashAttribute("exito", "Sede registrada correctamente.");
            }
            return "redirect:/sedes";
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            log.error("Error al guardar la sede.", e);
            redirectAttributes.addFlashAttribute(
                    "error", "No se pudo guardar la sede. Intente nuevamente.");
        }

        if (esEdicion) {
            return "redirect:/sedes?editar=" + sede.getIdSede();
        }
        return "redirect:/sedes";
    }

    /**
     * Desactiva una sede desde el botón de la tabla, aplicando RN12.
     */
    @PostMapping("/desactivar")
    public String desactivar(@RequestParam(name = "idSede", required = false) Integer idSede,
                             RedirectAttributes redirectAttributes) {

        if (!esAdministrador()) {
            return "redirect:/login";
        }

        if (idSede == null) {
            redirectAttributes.addFlashAttribute("error", "Debe indicar la sede que desea desactivar.");
            return "redirect:/sedes";
        }

        try {
            sedeService.desactivar(idSede);
            redirectAttributes.addFlashAttribute("exito", "Sede desactivada correctamente.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            log.error("Error al desactivar la sede.", e);
            redirectAttributes.addFlashAttribute(
                    "error", "No se pudo desactivar la sede. Intente nuevamente.");
        }

        return "redirect:/sedes";
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
     * Mientras F01 no esté listo se devuelve true para poder probar I09.
     */
    private boolean esAdministrador() {
        return true;
    }
}