package com.gimnasioteam2.sistema_gimnasio.Controller;

import com.gimnasioteam2.sistema_gimnasio.Model.Rol;
import com.gimnasioteam2.sistema_gimnasio.Service.rolService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/roles")
public class rolController {

    private final rolService rol_Service;

    public rolController(rolService rol_Service) {
        this.rol_Service = rol_Service;
    }

    /** F20: lista los 3 roles del sistema. */
    @GetMapping
    public String listarRoles(Model model) {
        model.addAttribute("roles", rol_Service.obtenerRoles());
        return "roles/listaRoles";
    }

    /** F21: muestra el formulario para editar la descripción de un rol. */
    @GetMapping("/editar/{idRol}")
    public String mostrarFormularioEdicion(@PathVariable int idRol,
                                           Model model,
                                           RedirectAttributes redirectAttributes) {
        Rol rol = rol_Service.obtenerRolPorId(idRol);

        if (rol == null) {
            redirectAttributes.addFlashAttribute("mensajeError", "El rol indicado no existe.");
            return "redirect:/roles";
        }

        model.addAttribute("rol", rol);
        model.addAttribute("maxCaracteres", rolService.MAX_CARACTERES_DESCRIPCION);
        return "roles/editarRol";
    }

    /** F21: guarda la nueva descripción y vuelve a la lista. */
    @PostMapping("/editar")
    public String guardarDescripcion(@RequestParam int idRol,
                                     @RequestParam(required = false) String descripcion,
                                     Model model,
                                     RedirectAttributes redirectAttributes) {
        String error = rol_Service.actualizarDescripcion(idRol, descripcion);

        if (error != null) {
            model.addAttribute("rol", rol_Service.obtenerRolPorId(idRol));
            model.addAttribute("maxCaracteres", rolService.MAX_CARACTERES_DESCRIPCION);
            model.addAttribute("mensajeError", error);
            return "roles/editarRol";
        }

        redirectAttributes.addFlashAttribute("mensajeExito",
                "La descripción del rol se actualizó correctamente.");
        return "redirect:/roles";
    }
}
