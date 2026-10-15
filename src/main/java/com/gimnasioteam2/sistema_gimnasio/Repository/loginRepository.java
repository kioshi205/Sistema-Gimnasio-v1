
package com.gimnasioteam2.sistema_gimnasio.Repository;

import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;

@Repository
public class loginRepository {

    private final Map<String, String> usuarios = new HashMap<>();

    public loginRepository() {
        usuarios.put("marco@gmail.com", "123456");
        usuarios.put("admin@gmail.com", "admin123");
    }

    public String buscarUsuario(String correo) {
        return usuarios.get(correo);
    }
}
