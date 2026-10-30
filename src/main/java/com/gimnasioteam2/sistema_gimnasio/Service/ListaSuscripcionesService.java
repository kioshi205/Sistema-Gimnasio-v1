package com.gimnasioteam2.sistema_gimnasio.Service;

import com.gimnasioteam2.sistema_gimnasio.Repository.ListaSuscripcionDAO;
import com.gimnasioteam2.sistema_gimnasio.clases.Cliente;
import com.gimnasioteam2.sistema_gimnasio.clases.Suscripciones;

import java.util.List;

public interface ListaSuscripcionesService {



    public List<Suscripciones> ListaSuscripciones();

    //para F28
    public boolean desactivarSuscripcion(Integer idSuscripcion);
}
