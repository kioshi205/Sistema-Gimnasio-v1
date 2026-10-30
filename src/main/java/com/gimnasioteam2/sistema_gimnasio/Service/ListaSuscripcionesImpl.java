package com.gimnasioteam2.sistema_gimnasio.Service;

import com.gimnasioteam2.sistema_gimnasio.Repository.ListaSuscripcionDAO;
import com.gimnasioteam2.sistema_gimnasio.clases.Cliente;
import com.gimnasioteam2.sistema_gimnasio.clases.Suscripciones;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ListaSuscripcionesImpl implements ListaSuscripcionesService {

    private final ListaSuscripcionDAO listaSuscripcionDAO;

    public ListaSuscripcionesImpl(ListaSuscripcionDAO listaSuscripcionDAO) {
        this.listaSuscripcionDAO = listaSuscripcionDAO;
    }
    @Override
    public List<Suscripciones> ListaSuscripciones() {
        return listaSuscripcionDAO.ListaSuscripcion();
    }
    //nuevo para F28
    @Override
    public boolean desactivarSuscripcion(Integer idSuscripcion) {
        return listaSuscripcionDAO.desactivarSuscripcion(idSuscripcion) > 0;
    }
}
