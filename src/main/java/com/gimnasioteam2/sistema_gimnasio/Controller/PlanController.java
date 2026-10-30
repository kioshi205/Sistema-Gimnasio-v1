package com.gimnasioteam2.sistema_gimnasio.Controller;

import com.gimnasioteam2.sistema_gimnasio.Model.Plan;
import com.gimnasioteam2.sistema_gimnasio.Model.PlanDetalle;
import com.gimnasioteam2.sistema_gimnasio.Service.PlanService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/planes")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    // F11: listado de planes predeterminados y personalizados
    @GetMapping({"", "/"})
    public String listar(HttpSession session, Model model) {
        if (!esAdministrador(session)) {
            return "redirect:/login";
        }
        try {
            model.addAttrgit config --global user.email "tu_correo@gmail.com"ibute("planes", planService.listarPlanes());
        } catch (RuntimeException e) {
            model.addAttribute("error", "No se pudieron cargar los planes. Intenta nuevamente.");
        }
        return "planes/planes";
    }

    // F10: formulario de registro
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(HttpSession session, Model model) {
        if (!esAdministrador(session)) {
            return "redirect:/login";
        }
        if (!model.containsAttribute("modo")) {
            cargarFormulario(model, "crear", null, "", "", new HashSet<>());
        } else {
            model.addAttribute("productos", planService.listarSubproductosActivos());
        }
        return "planes/formulario";
    }

    // F12: formulario de edición
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable int id, HttpSession session,
                                          Model model, RedirectAttributes redirectAttributes) {
        if (!esAdministrador(session)) {
            return "redirect:/login";
        }
        try {
            Plan plan = planService.obtenerPlan(id);
            if (!plan.isActivo()) {
                redirectAttributes.addFlashAttribute("error", "Un plan inactivo no se puede editar.");
                return "redirect:/planes";
            }

            model.addAttribute("plan", plan);
            if (!model.containsAttribute("modo")) {
                Set<Integer> seleccionados = new HashSet<>();
                for (PlanDetalle detalle : plan.getDetallesActivos()) {
                    seleccionados.add(detalle.getIdProducto());
                }
                cargarFormulario(model, "editar", plan.getIdPlan(), plan.getNombrePlan(),
                        String.valueOf(plan.getDuracionMeses()), seleccionados);
            } else {
                model.addAttribute("idPlan", plan.getIdPlan());
                model.addAttribute("productos", planService.listarSubproductosActivos());
            }

            model.addAttribute("avisoSubproductosInactivos", construirAvisoInactivos(plan));
            return "planes/formulario";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/planes";
        }
    }

    // F10: guardar plan predeterminado
    @PostMapping("/guardar")
    public String guardar(@RequestParam(value = "nombrePlan", required = false) String nombrePlan,
                          @RequestParam(value = "duracionMeses", required = false) String duracionMeses,
                          @RequestParam(value = "idProductos", required = false) List<Integer> idProductos,
                          HttpSession session, RedirectAttributes redirectAttributes) {
        if (!esAdministrador(session)) {
            return "redirect:/login";
        }
        try {
            Integer duracion = parsearDuracion(duracionMeses);
            planService.registrarPlanPredeterminado(nombrePlan, duracion, idProductos,
                    obtenerIdUsuarioSesion(session));
            redirectAttributes.addFlashAttribute("exito", "Plan registrado correctamente.");
            return "redirect:/planes";
        } catch (IllegalArgumentException | IllegalStateException e) {
            prepararFormularioConError(redirectAttributes, "crear", null, nombrePlan,
                    duracionMeses, idProductos, e.getMessage());
            return "redirect:/planes/nuevo";
        } catch (RuntimeException e) {
            prepararFormularioConError(redirectAttributes, "crear", null, nombrePlan,
                    duracionMeses, idProductos,
                    "No se pudo registrar el plan. Intenta nuevamente.");
            return "redirect:/planes/nuevo";
        }
    }

    // F12: actualizar plan
    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable int id,
                             @RequestParam(value = "nombrePlan", required = false) String nombrePlan,
                             @RequestParam(value = "duracionMeses", required = false) String duracionMeses,
                             @RequestParam(value = "idProductos", required = false) List<Integer> idProductos,
                             HttpSession session, RedirectAttributes redirectAttributes) {
        if (!esAdministrador(session)) {
            return "redirect:/login";
        }
        try {
            Integer duracion = parsearDuracion(duracionMeses);
            planService.actualizarPlan(id, nombrePlan, duracion, idProductos);
            redirectAttributes.addFlashAttribute("exito", "Plan actualizado correctamente.");
            return "redirect:/planes";
        } catch (IllegalArgumentException | IllegalStateException e) {
            prepararFormularioConError(redirectAttributes, "editar", id, nombrePlan,
                    duracionMeses, idProductos, e.getMessage());
            return "redirect:/planes/editar/" + id;
        } catch (RuntimeException e) {
            prepararFormularioConError(redirectAttributes, "editar", id, nombrePlan,
                    duracionMeses, idProductos,
                    "No se pudo actualizar el plan. Intenta nuevamente.");
            return "redirect:/planes/editar/" + id;
        }
    }

    // F13: baja lógica
    @PostMapping("/desactivar/{id}")
    public String desactivar(@PathVariable int id, HttpSession session,
                             RedirectAttributes redirectAttributes) {
        if (!esAdministrador(session)) {
            return "redirect:/login";
        }
        try {
            planService.desactivarPlan(id);
            redirectAttributes.addFlashAttribute("exito", "Plan desactivado correctamente.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error",
                    "No se pudo desactivar el plan. Intenta nuevamente.");
        }
        return "redirect:/planes";
    }

    // F14: reactivación lógica
    @PostMapping("/activar/{id}")
    public String activar(@PathVariable int id, HttpSession session,
                          RedirectAttributes redirectAttributes) {
        if (!esAdministrador(session)) {
            return "redirect:/login";
        }
        try {
            planService.activarPlan(id);
            redirectAttributes.addFlashAttribute("exito", "Plan reactivado correctamente.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error",
                    "No se pudo reactivar el plan. Intenta nuevamente.");
        }
        return "redirect:/planes";
    }

    private void cargarFormulario(Model model, String modo, Integer idPlan, String nombrePlan,
                                  String duracionMeses, Set<Integer> seleccionados) {
        model.addAttribute("modo", modo);
        model.addAttribute("idPlan", idPlan);
        model.addAttribute("nombrePlan", nombrePlan);
        model.addAttribute("duracionMeses", duracionMeses);
        model.addAttribute("seleccionados", seleccionados);
        model.addAttribute("productos", planService.listarSubproductosActivos());
    }

    private void prepararFormularioConError(RedirectAttributes redirectAttributes, String modo,
                                            Integer idPlan, String nombrePlan, String duracionMeses,
                                            List<Integer> idProductos, String mensaje) {
        redirectAttributes.addFlashAttribute("error", mensaje);
        redirectAttributes.addFlashAttribute("modo", modo);
        redirectAttributes.addFlashAttribute("idPlan", idPlan);
        redirectAttributes.addFlashAttribute("nombrePlan", nombrePlan);
        redirectAttributes.addFlashAttribute("duracionMeses", duracionMeses);
        redirectAttributes.addFlashAttribute("seleccionados",
                idProductos == null ? new HashSet<Integer>() : new HashSet<>(idProductos));
    }

    private Integer parsearDuracion(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return null;
        }
        try {
            return Integer.valueOf(texto.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La duración en meses debe ser un número entero.");
        }
    }

    private String construirAvisoInactivos(Plan plan) {
        StringBuilder nombres = new StringBuilder();
        for (PlanDetalle detalle : plan.getDetallesActivos()) {
            if (detalle.getProducto() != null && !detalle.getProducto().isActivo()) {
                if (nombres.length() > 0) {
                    nombres.append(", ");
                }
                nombres.append(detalle.getProducto().getNombreSubproducto());
            }
        }
        if (nombres.length() == 0) {
            return null;
        }
        return "Estos subproductos ya no están activos y se retirarán al guardar: " + nombres + ".";
    }

    /**
     * TEMPORAL: el login (F01) todavía no está implementado.
     * Cuando esté listo, debe leer el id del usuario autenticado desde la sesión.
     * Mientras tanto se usa 201 = Marta Salazar Quispe, ADMINISTRADOR en data.sql.
     */
    private int obtenerIdUsuarioSesion(HttpSession session) {
        Object valor = session.getAttribute("idUsuario");
        if (valor instanceof Number) {
            return ((Number) valor).intValue();
        }
        if (valor != null) {
            try {
                return Integer.parseInt(valor.toString());
            } catch (NumberFormatException ignored) {
                // Se usa el usuario temporal de abajo.
            }
        }
        return 201; // TEMPORAL: quitar cuando exista el login.
    }

    /**
     * TEMPORAL: el login (F01) todavía no está implementado.
     * Cuando esté listo, activar la línea comentada y quitar el return true.
     */
    private boolean esAdministrador(HttpSession session) {
        Object rol = session.getAttribute("rol"); // ajustar al nombre real del atributo
        // return rol != null && "ADMINISTRADOR".equalsIgnoreCase(rol.toString());
        return true; // TEMPORAL: quitar esta línea cuando exista el login.
    }
}