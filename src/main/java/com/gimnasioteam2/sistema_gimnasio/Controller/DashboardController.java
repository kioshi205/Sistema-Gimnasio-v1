package com.gimnasioteam2.sistema_gimnasio.Controller;

import com.gimnasioteam2.sistema_gimnasio.Model.DashboardMetricasDTO;
import com.gimnasioteam2.sistema_gimnasio.Model.PuntoGrafico;
import com.gimnasioteam2.sistema_gimnasio.Service.DashboardService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

/**
 * F22: consultar gráficas de métricas. Interfaz de solo lectura.
 */
@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping({"", "/"})
    public String mostrarDashboard(HttpSession session, Model model) {
        if (!esAdministrador(session)) {
            return "redirect:/login";
        }

        try {
            DashboardMetricasDTO metricas = dashboardService.obtenerMetricas();
            model.addAttribute("metricas", metricas);
            model.addAttribute("datosJson", construirJson(metricas));
        } catch (RuntimeException e) {
            DashboardMetricasDTO vacio = new DashboardMetricasDTO();
            model.addAttribute("error", "No se pudieron cargar las métricas en este momento. Intenta nuevamente.");
            model.addAttribute("metricas", vacio);
            model.addAttribute("datosJson", construirJson(vacio));
        }
        return "dashboard/dashboard";
    }

    /**
     * Arma a mano el JSON que consume el JSP. No se usa Jackson.
     */
    private String construirJson(DashboardMetricasDTO metricas) {
        StringBuilder json = new StringBuilder();
        json.append('{');
        agregarSerie(json, "ingresosPorMes", metricas.getIngresosPorMes());
        json.append(',');
        agregarSerie(json, "suscripcionesPorMes", metricas.getSuscripcionesPorMes());
        json.append(',');
        agregarSerie(json, "ingresosPuertaPorDia", metricas.getIngresosPuertaPorDia());
        json.append(',');
        agregarSerie(json, "clientesPorMes", metricas.getClientesPorMes());
        json.append('}');
        return json.toString();
    }

    private void agregarSerie(StringBuilder json, String nombre, List<PuntoGrafico> puntos) {
        json.append('"').append(nombre).append("\":{\"etiquetas\":[");
        if (puntos != null) {
            for (int i = 0; i < puntos.size(); i++) {
                if (i > 0) {
                    json.append(',');
                }
                json.append('"').append(escaparJson(puntos.get(i).getEtiqueta())).append('"');
            }
        }
        json.append("],\"valores\":[");
        if (puntos != null) {
            for (int i = 0; i < puntos.size(); i++) {
                if (i > 0) {
                    json.append(',');
                }
                BigDecimal valor = puntos.get(i).getValor();
                json.append(valor == null ? "0" : valor.toPlainString());
            }
        }
        json.append("]}");
    }

    private String escaparJson(String texto) {
        if (texto == null) {
            return "";
        }
        StringBuilder salida = new StringBuilder(texto.length() + 8);
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            switch (c) {
                case '"':
                    salida.append("\\\"");
                    break;
                case '\\':
                    salida.append("\\\\");
                    break;
                case '/':
                    salida.append("\\/");
                    break;
                case '<':
                    salida.append("\\u003c");
                    break;
                case '\n':
                    salida.append("\\n");
                    break;
                case '\r':
                    salida.append("\\r");
                    break;
                case '\t':
                    salida.append("\\t");
                    break;
                default:
                    salida.append(c);
            }
        }
        return salida.toString();
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