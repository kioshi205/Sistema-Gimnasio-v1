package com.gimnasioteam2.sistema_gimnasio.Controller;

import com.gimnasioteam2.sistema_gimnasio.Model.MarcacionAcceso;
import com.gimnasioteam2.sistema_gimnasio.Service.historialService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/historial")
public class historialController {

    private final historialService historial_Service;

    public historialController(historialService historial_Service) {
        this.historial_Service = historial_Service;
    }

    /** F33: muestra el historial de accesos con filtros opcionales combinables. */
    @GetMapping
    public String mostrarHistorial(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            @RequestParam(defaultValue = "0") int idSede,
            @RequestParam(defaultValue = "TODOS") String resultado,
            Model model) {

        String resultadoNormalizado = normalizarResultado(resultado);
        List<MarcacionAcceso> marcaciones;

        if (desde != null && hasta != null && desde.isAfter(hasta)) {
            marcaciones = Collections.emptyList();
            model.addAttribute("mensajeError",
                    "La fecha \"desde\" no puede ser mayor que la fecha \"hasta\".");
        } else {
            marcaciones = historial_Service.consultarHistorial(desde, hasta, idSede, resultadoNormalizado);
        }

        long totalPermitidos = marcaciones.stream().filter(MarcacionAcceso::isPermitido).count();

        model.addAttribute("marcaciones", marcaciones);
        model.addAttribute("sedes", historial_Service.obtenerSedes());
        model.addAttribute("desde", desde);
        model.addAttribute("hasta", hasta);
        model.addAttribute("idSedeSeleccionada", idSede);
        model.addAttribute("resultadoSeleccionado", resultadoNormalizado);
        model.addAttribute("totalRegistros", marcaciones.size());
        model.addAttribute("totalPermitidos", totalPermitidos);
        model.addAttribute("totalDenegados", marcaciones.size() - totalPermitidos);

        return "historial/historialAccesos";
    }

    private String normalizarResultado(String resultado) {
        if ("PERMITIDO".equalsIgnoreCase(resultado) || "DENEGADO".equalsIgnoreCase(resultado)) {
            return resultado.toUpperCase();
        }
        return "TODOS";
    }
}
