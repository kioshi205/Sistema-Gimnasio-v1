package com.gimnasioteam2.sistema_gimnasio.Service;

import com.gimnasioteam2.sistema_gimnasio.Model.Socio;
import com.gimnasioteam2.sistema_gimnasio.Repository.seguridadRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class seguridadService {

    private final seguridadRepository seguridad_Repository;

    public seguridadService(seguridadRepository seguridad_Repository) {
        this.seguridad_Repository = seguridad_Repository;
    }

    public Socio buscarSocio(String dni) {
        return seguridad_Repository.buscarPorDni(dni);
    }

    public boolean estaVencido(Socio socio) {
        if (socio.getFechaVencimiento() == null) {
            return true;
        }
        return socio.getFechaVencimiento().isBefore(LocalDate.now());
    }
}
