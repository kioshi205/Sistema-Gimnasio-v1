package com.gimnasioteam2.sistema_gimnasio.Controller;

import com.gimnasioteam2.sistema_gimnasio.Model.Socio;
import com.gimnasioteam2.sistema_gimnasio.Service.seguridadService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class seguridadController {

    private final seguridadService seguridad_Service;

    public seguridadController(seguridadService seguridad_Service) {
        this.seguridad_Service = seguridad_Service;
    }

    // Pagina principal del dashboard de seguridad
    @GetMapping("/seguridad/inicio")
    public String mostrarInicio() {
        return "/seguridad/inicio";
    }

    // Si entran a /seguridad, tambien se muestra la pagina principal
    @GetMapping("/seguridad")
    public String mostrarInicioCorto() {
        return "/seguridad/inicio";
    }

    // Muestra el dashboard del personal de seguridad (F19: Validar Ingreso)
    @GetMapping("/seguridad/validar")
    public String mostrarValidarIngreso(Model model) {
        model.addAttribute("estado", "INICIO");
        return "/seguridad/validarIngreso";
    }

    // Recibe el DNI y consulta la vigencia en la base de datos
    @PostMapping("/seguridad/validar")
    public String validarIngreso(@RequestParam(value = "dni", required = false) String dni, Model model) {

        model.addAttribute("dni", dni);

        if (dni == null || dni.trim().isEmpty()) {
            model.addAttribute("estado", "SIN_DNI");
            return "/seguridad/validarIngreso";
        }

        Socio socio = seguridad_Service.buscarSocio(dni.trim());

        if (socio == null) {
            model.addAttribute("estado", "DENEGADO");
        } else if (seguridad_Service.estaVencido(socio)) {
            model.addAttribute("estado", "VENCIDO");
            model.addAttribute("nombre", socio.getNombre());
        } else {
            model.addAttribute("estado", "PERMITIDO");
            model.addAttribute("nombre", socio.getNombre());
        }

        return "/seguridad/validarIngreso";
    }
}
