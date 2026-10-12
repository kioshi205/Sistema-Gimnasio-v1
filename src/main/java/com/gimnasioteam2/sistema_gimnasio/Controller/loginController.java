package com.gimnasioteam2.sistema_gimnasio.Controller;

import com.gimnasioteam2.sistema_gimnasio.Service.loginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class loginController {

    private final loginService login_Service;
    public loginController(loginService login_Service) {
        this.login_Service = login_Service;
    }



    @GetMapping("/login")
    public String mostrarDatos(Model model) {
        String mensaje = login_Service.obtenerMensajeBienvenida();
        model.addAttribute("mensajeVista", mensaje);

        return "/login/loginViews";
    }
}


