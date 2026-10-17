package com.gimnasioteam2.sistema_gimnasio.Controller;

import com.gimnasioteam2.sistema_gimnasio.Model.Cliente;
import com.gimnasioteam2.sistema_gimnasio.Service.ClienteService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/recepcionista")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/registrarCliente")
    public String mostrarRegistro() {
        return "recepcionista/registrarCliente";
    }

    @PostMapping("/registrarCliente")
    public String registrarCliente(
            @ModelAttribute Cliente cliente,
            Model model) {

        if (clienteService.registrar(cliente)) {
            model.addAttribute("mensaje", "Cliente registrado correctamente");
        } else {
            model.addAttribute("mensaje",
                    "No se pudo registrar. Verifica el DNI o los datos.");
        }

        return "recepcionista/registrarCliente";
    }

    @GetMapping("/consultarCliente")
    public String mostrarConsulta() {
        return "recepcionista/consultarCliente";
    }

    @PostMapping("/consultarCliente")
    public String consultarCliente(
            @RequestParam("busqueda") String busqueda,
            Model model) {

        if (busqueda.matches("[0-9]{8}")) {
            Cliente cliente = clienteService.buscarPorDni(busqueda);

            if (cliente != null) {
                model.addAttribute("clientes",
                        java.util.List.of(cliente));
            }
        } else {
            model.addAttribute("clientes",
                    clienteService.buscarPorNombre(busqueda));
        }

        return "recepcionista/consultarCliente";
    }

    @GetMapping("/actualizarCliente")
    public String mostrarActualizacion() {
        return "recepcionista/actualizarCliente";
    }

    @PostMapping("/actualizarCliente")
    public String actualizarCliente(
            @ModelAttribute Cliente cliente,
            Model model) {

        if (clienteService.actualizar(cliente)) {
            model.addAttribute("mensaje",
                    "Cliente actualizado correctamente");
        } else {
            model.addAttribute("mensaje",
                    "No se encontró el cliente con ese DNI");
        }

        return "recepcionista/actualizarCliente";
    }

    @GetMapping("/configurarPlan")
    public String mostrarConfiguracionPlan() {
        return "recepcionista/configurarPlan";
    }
}