package com.gimnasioteam2.sistema_gimnasio.Service;
import org.springframework.stereotype.Service;

@Service
public class loginService {
    public String obtenerMensajeBienvenida() {
        return "¡Hola! Los datos vienen desde el Servicio hacia la vista JSP.";
    }
}
