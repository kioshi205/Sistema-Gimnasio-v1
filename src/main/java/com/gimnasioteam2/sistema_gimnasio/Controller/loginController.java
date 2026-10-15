
package com.gimnasioteam2.sistema_gimnasio.Controller;

import com.gimnasioteam2.sistema_gimnasio.Service.loginService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class loginController {

    private final loginService service;

    public loginController(loginService service) {
        this.service = service;
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String validarLogin(
            @RequestParam("correo") String correo,
            @RequestParam("contraseña") String contraseña,
            Model model) {

        if (service.validarLogin(correo, contraseña)) {
            return "redirect:/inicio";
        }

        model.addAttribute("error", "Correo o contraseña incorrectos");
        return "login";
    }
}
