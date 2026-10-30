package com.gimnasioteam2.sistema_gimnasio.Controller;

import com.gimnasioteam2.sistema_gimnasio.Model.Cliente;
import com.gimnasioteam2.sistema_gimnasio.Service.clienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** Interfaz 111: Clientes (F23 registrar, F24 listar y buscar, F25 editar, F26 desactivar). */
@Controller
public class clienteController {

    private final clienteService service;

    public clienteController(clienteService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String inicio() {
        return "redirect:/clientes";
    }

    // F24
    @GetMapping("/clientes")
    public String listar(@RequestParam(required = false) String q,
                         @RequestParam(required = false) String estado,
                         Model model) {
        model.addAttribute("clientes", service.buscar(q, estado));
        model.addAttribute("q", q);
        model.addAttribute("estado", estado);
        return "clientes/clientesViews";
    }

    // F23
    @GetMapping("/clientes/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("modo", "nuevo");
        return "clientes/clienteForm";
    }

    @PostMapping("/clientes/nuevo")
    public String registrar(@ModelAttribute("cliente") Cliente cliente, Model model, RedirectAttributes ra) {
        try {
            service.registrar(cliente);
            ra.addFlashAttribute("ok", "Cliente " + cliente.getNombreCompleto() + " registrado correctamente.");
            return "redirect:/clientes";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("modo", "nuevo");
            return "clientes/clienteForm";
        }
    }

    // F25
    @GetMapping("/clientes/{id}/editar")
    public String editar(@PathVariable int id, Model model, RedirectAttributes ra) {
        try {
            model.addAttribute("cliente", service.obtener(id));
            model.addAttribute("modo", "editar");
            return "clientes/clienteForm";
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/clientes";
        }
    }

    @PostMapping("/clientes/{id}/editar")
    public String actualizar(@PathVariable int id, @ModelAttribute("cliente") Cliente cliente,
                             Model model, RedirectAttributes ra) {
        cliente.setIdCliente(id);
        try {
            service.editar(cliente);
            ra.addFlashAttribute("ok", "Datos del cliente actualizados.");
            return "redirect:/clientes";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("modo", "editar");
            return "clientes/clienteForm";
        }
    }

    // F26 (RN10): desactivar
    @PostMapping("/clientes/{id}/desactivar")
    public String desactivar(@PathVariable int id, RedirectAttributes ra) {
        try {
            service.desactivar(id);
            ra.addFlashAttribute("ok", "Cliente desactivado.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/clientes";
    }

    // Activar
    @PostMapping("/clientes/{id}/activar")
    public String activar(@PathVariable int id, RedirectAttributes ra) {
        try {
            service.activar(id);
            ra.addFlashAttribute("ok", "Cliente activado.");
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/clientes";
    }
}
