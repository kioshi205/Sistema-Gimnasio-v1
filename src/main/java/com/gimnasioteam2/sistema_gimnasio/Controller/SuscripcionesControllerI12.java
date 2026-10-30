package com.gimnasioteam2.sistema_gimnasio.Controller;


import com.gimnasioteam2.sistema_gimnasio.Service.ListaSuscripcionesService;
import com.gimnasioteam2.sistema_gimnasio.Service.SuscripcionService;
import com.gimnasioteam2.sistema_gimnasio.clases.ReglaNegocioException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/suscripciones")
public class SuscripcionesControllerI12 {
    private final ListaSuscripcionesService listaSuscripcionesService;
    //para F27.
    private final SuscripcionService suscripcionService;
    // TEMPORAL: hasta tener login, se usa el recepcionista 202 (Jorge Paredes)
    private static final Integer ID_RECEPCIONISTA_TEMPORAL = 202;

    public SuscripcionesControllerI12(ListaSuscripcionesService listaSuscripcionesService, SuscripcionService suscripcionService) {
        this.listaSuscripcionesService = listaSuscripcionesService;
        this.suscripcionService = suscripcionService;
    }


    // F27 - formulario
    @GetMapping("/Contratar")
    public String mostrarFormularioContratar(Model model) {
        model.addAttribute("clientes", suscripcionService.listarClientesActivos());
        model.addAttribute("planes", suscripcionService.listarPlanesActivos());
        model.addAttribute("sedes", suscripcionService.listarSedesActivas());
        model.addAttribute("detallePlanes", suscripcionService.listarDetallePlanes());
        return "/dashrecepcionista/ContratarSuscripcion";
    }

    // F27 - proceso
    @PostMapping("/Contratar")
    public String contratar(@RequestParam("dni") String dni,
                            @RequestParam("idPlan") Integer idPlan,
                            @RequestParam("idSede") Integer idSede,
                            RedirectAttributes redirectAttributes) {
        try {
            suscripcionService.contratarSuscripcion(dni, idPlan, idSede, ID_RECEPCIONISTA_TEMPORAL);
            redirectAttributes.addFlashAttribute("mensaje", "Suscripción contratada correctamente.");
        } catch (ReglaNegocioException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/suscripciones/Contratar";
    }

    @GetMapping("/ListarSuscripciones")
    public String mostrarSuscripciones(Model model){
    model.addAttribute("suscripciones", listaSuscripcionesService.ListaSuscripciones());
        return "/dashrecepcionista/ListaSuscripcion";
    }
    //para la F28
        // Vista donde se elige qué suscripción actualizar
    @GetMapping("/ActualizarEstado")
    public String mostrarActualizarEstado(Model model) {
        model.addAttribute("suscripciones", suscripcionService.listarSuscripcionesDetalle());
        return "/dashrecepcionista/ActualizarEstadoSuscripcion";
    }

        // Acción que Actualizar Estado
        @PostMapping("/ActualizarEstado")
        public String actualizarEstado(@RequestParam("idSuscripcion") Integer idSuscripcion,
                                       RedirectAttributes redirectAttributes) {
            try {
                String nuevo = suscripcionService.cambiarEstado(idSuscripcion);
                redirectAttributes.addFlashAttribute("mensaje",
                        "Suscripción " + idSuscripcion + " ahora está " + nuevo + ".");
            } catch (ReglaNegocioException e) {
                redirectAttributes.addFlashAttribute("error", e.getMessage());
            }
            return "redirect:/suscripciones/ActualizarEstado";
        }
}
