package com.gimnasioteam2.sistema_gimnasio.Repository;

import com.gimnasioteam2.sistema_gimnasio.clases.PlanInfo;
import com.gimnasioteam2.sistema_gimnasio.clases.SuscripcionEstado;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SuscripcionDAO {
    boolean clienteActivo(Integer idCliente);
    int contarVigentes(Integer idCliente);
    Optional<PlanInfo> buscarPlan(Integer idPlan);
    boolean sedeActiva(Integer idSede);
    boolean usuarioEsRecepcionista(Integer idUsuario);
    int insertar(Integer idCliente, Integer idPlan, Integer idUsuario, Integer idSede,
                 LocalDate fechaInicio, LocalDate fechaFin, java.math.BigDecimal monto);
    List<Map<String, Object>> listarPlanesActivos();
    List<Map<String, Object>> listarSedesActivas();
    List<Map<String, Object>> listarClientesActivos();
    Map<Integer, List<Map<String, Object>>> listarDetallePlanes();
    Optional<Integer> buscarIdClientePorDni(String dni);
    Optional<SuscripcionEstado> buscarEstado(Integer idSuscripcion);
    int actualizarEstado(Integer idSuscripcion, String nuevoEstado);
    List<Map<String, Object>> listarSuscripcionesDetalle();
}