package com.gimnasioteam2.sistema_gimnasio.Controller;

import com.gimnasioteam2.sistema_gimnasio.Service.AlertaVencimientoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/alertas")
public class AlertasControllerI14 {

    private final AlertaVencimientoService alertaVencimientoService;

    public AlertasControllerI14(AlertaVencimientoService alertaVencimientoService) {
        this.alertaVencimientoService = alertaVencimientoService;
    }

    // Ejemplo: /alertas/Vencimiento  o  /alertas/Vencimiento?dias=10
    @GetMapping("/Vencimiento")
    public String mostrarAlertas(@RequestParam(defaultValue = "7") int dias, Model model) {
        model.addAttribute("alertas", alertaVencimientoService.GenerarAlertas(dias));
        model.addAttribute("dias", dias);
        return "/dashrecepcionista/ListaAlertas";
    }
}