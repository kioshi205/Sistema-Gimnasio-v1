package com.gimnasioteam2.sistema_gimnasio.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/RegistrarRol")
    public String registrarRol() {
        return "admin/registrar_rol";
    }
    @PostMapping("/RegistrarRol")
    public String registrarRolPost(@RequestParam("nombre")String name,
                                   @RequestParam("rol")String role,
                                   @RequestParam("Apellido")String lastName,
                                   Model model) {
        return"";
    }
}
