package com.gimnasioteam2.sistema_gimnasio.Repository;

import com.gimnasioteam2.sistema_gimnasio.clases.Cliente;
import com.gimnasioteam2.sistema_gimnasio.clases.Suscripciones;

import java.util.List;

public interface ListaSuscripcionDAO {

    public List<Suscripciones> ListaSuscripcion();
    //para la F28
    public int desactivarSuscripcion(Integer idSuscripcion);
}
