package com.gimnasioteam2.sistema_gimnasio.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SuscripcionService {
    void contratarSuscripcion(String dni, Integer idPlan, Integer idSede, Integer idUsuario);
    List<Map<String, Object>> listarPlanesActivos();
    List<Map<String, Object>> listarSedesActivas();
    List<Map<String, Object>> listarClientesActivos();
    Map<Integer, List<Map<String, Object>>> listarDetallePlanes();
    String cambiarEstado(Integer idSuscripcion);
    List<Map<String, Object>> listarSuscripcionesDetalle();

}