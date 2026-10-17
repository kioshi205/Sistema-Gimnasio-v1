package com.gimnasioteam2.sistema_gimnasio.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gimnasioteam2.sistema_gimnasio.Service.InscripcionService;

import jakarta.servlet.http.HttpSession;

@Controller
public class RecepcionistaController {

    private final InscripcionService service;

    public RecepcionistaController(InscripcionService service) {
        this.service = service;
    }

    // F15: Resumen del plan. Ej: /recepcionista/resumen?clienteId=4&planId=2&sub=1&sub=3
    @GetMapping("/recepcionista/resumen")
    public String resumen(@RequestParam(required = false) Integer clienteId,
                          @RequestParam(required = false) Integer planId,
                          @RequestParam(required = false) List<Integer> sub,
                          Model model) {
        cargarResumen(clienteId, planId, sub, model);
        return "/dashAdmin/resumen";
    }

    // F16: Pantalla de confirmación
    @GetMapping("/recepcionista/guardar")
    public String guardarForm(@RequestParam(required = false) Integer clienteId,
                              @RequestParam(required = false) Integer planId,
                              @RequestParam(required = false) List<Integer> sub,
                              Model model) {
        cargarResumen(clienteId, planId, sub, model);
        return "/dashAdmin/guardar";
    }

    // F16: Registrar inscripción
    @PostMapping("/recepcionista/guardar")
    public String guardar(@RequestParam Integer clienteId,
                          @RequestParam Integer planId,
                          @RequestParam(required = false) List<Integer> sub) {
        service.registrar(clienteId, planId, sub == null ? new ArrayList<>() : sub);
        return "redirect:/recepcionista/inscripciones?ok=1";
    }

    // F17: Historial
    @GetMapping("/recepcionista/inscripciones")
    public String inscripciones(@RequestParam(required = false) String ok, Model model) {
        model.addAttribute("inscripciones", service.consultarInscripciones());
        model.addAttribute("ok", ok);
        return "/dashAdmin/inscripciones";
    }

    // F18: Tablero de vigencias (filtro opcional por estado)
    @GetMapping("/recepcionista/vigencias")
    public String vigencias(@RequestParam(required = false) String estado, Model model) {
        List<Map<String, Object>> todas = service.consultarVigencias();

        model.addAttribute("activas", todas.stream().filter(v -> v.get("estado").equals("ACTIVA")).count());
        model.addAttribute("porVencer", todas.stream().filter(v -> v.get("estado").equals("POR_VENCER")).count());
        model.addAttribute("vencidas", todas.stream().filter(v -> v.get("estado").equals("VENCIDA")).count());

        if (estado != null && !estado.isEmpty()) {
            todas = todas.stream().filter(v -> v.get("estado").equals(estado)).toList();
        }
        model.addAttribute("vigencias", todas);
        return "/dashAdmin/vigencia";
    }

    // Arma el resumen solo si llegaron cliente y plan
    private void cargarResumen(Integer clienteId, Integer planId, List<Integer> sub, Model model) {
        if (clienteId != null && planId != null) {
            model.addAttribute("resumen",
                    service.generarResumen(clienteId, planId, sub == null ? new ArrayList<>() : sub));
            model.addAttribute("clienteId", clienteId);
            model.addAttribute("planId", planId);
        }
    }
}