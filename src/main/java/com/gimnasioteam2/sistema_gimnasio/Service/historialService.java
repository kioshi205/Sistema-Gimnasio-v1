package com.gimnasioteam2.sistema_gimnasio.Service;

import com.gimnasioteam2.sistema_gimnasio.Model.MarcacionAcceso;
import com.gimnasioteam2.sistema_gimnasio.Model.Sede;
import com.gimnasioteam2.sistema_gimnasio.Repository.historialRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class historialService {

    private final historialRepository historial_Repository;

    public historialService(historialRepository historial_Repository) {
        this.historial_Repository = historial_Repository;
    }

    public List<MarcacionAcceso> consultarHistorial(LocalDate fechaDesde,
                                                    LocalDate fechaHasta,
                                                    Integer idSede,
                                                    String resultado) {
        return historial_Repository.consultarHistorial(fechaDesde, fechaHasta, idSede, resultado);
    }

    public List<Sede> obtenerSedes() {
        return historial_Repository.listarSedes();
    }
}
