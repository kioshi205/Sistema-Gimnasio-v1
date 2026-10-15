
package com.gimnasioteam2.sistema_gimnasio.Service;

import com.gimnasioteam2.sistema_gimnasio.Repository.loginRepository;

import org.springframework.stereotype.Service;

@Service
public class loginService {

    private final loginRepository repository;

    public loginService(loginRepository repository) {
        this.repository = repository;
    }

    public boolean validarLogin(String correo, String contraseña) {
        String contraseñaGuardada = repository.buscarUsuario(correo);

        return contraseñaGuardada != null
                && contraseñaGuardada.equals(contraseña);
    }
}
