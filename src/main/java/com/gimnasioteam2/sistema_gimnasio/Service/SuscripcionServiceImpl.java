package com.gimnasioteam2.sistema_gimnasio.Service;

import com.gimnasioteam2.sistema_gimnasio.Repository.SuscripcionDAO;
import com.gimnasioteam2.sistema_gimnasio.clases.PlanInfo;
import com.gimnasioteam2.sistema_gimnasio.clases.ReglaNegocioException;
import com.gimnasioteam2.sistema_gimnasio.clases.SuscripcionEstado;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class SuscripcionServiceImpl implements SuscripcionService {

    private final SuscripcionDAO suscripcionDAO;

    public SuscripcionServiceImpl(SuscripcionDAO suscripcionDAO) {
        this.suscripcionDAO = suscripcionDAO;
    }

    @Override
    @Transactional
    public void contratarSuscripcion(String dni, Integer idPlan, Integer idSede, Integer idUsuario) {

        if (dni == null || dni.isBlank() || idPlan == null || idSede == null) {
            throw new ReglaNegocioException("Debe completar DNI, plan y sede.");
        }
        if (!suscripcionDAO.usuarioEsRecepcionista(idUsuario)) {
            throw new ReglaNegocioException("Solo un recepcionista activo puede contratar suscripciones.");
        }
        Integer idCliente = suscripcionDAO.buscarIdClientePorDni(dni.trim())
                .orElseThrow(() -> new ReglaNegocioException("No existe un cliente con ese DNI."));
        // F27: cliente activo
        if (!suscripcionDAO.clienteActivo(idCliente)) {
            throw new ReglaNegocioException("El cliente no existe o no está activo.");
        }
        // RN05: una suscripción vigente por cliente
        if (suscripcionDAO.contarVigentes(idCliente) > 0) {
            throw new ReglaNegocioException("RN05: el cliente ya tiene una suscripción vigente.");
        }
        // Plan válido
        PlanInfo plan = suscripcionDAO.buscarPlan(idPlan)
                .orElseThrow(() -> new ReglaNegocioException("El plan no existe."));
        if (!"Activo".equalsIgnoreCase(plan.estado())) {
            throw new ReglaNegocioException("El plan no está activo.");
        }
        // Un plan personalizado solo lo puede contratar su cliente
        if (plan.idClienteDueno() != null && !plan.idClienteDueno().equals(idCliente)) {
            throw new ReglaNegocioException("Este plan personalizado pertenece a otro cliente.");
        }
        // Sede válida
        if (!suscripcionDAO.sedeActiva(idSede)) {
            throw new ReglaNegocioException("La sede no existe o no está activa.");
        }

        // F27: calcular fechas y monto
        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaFin = fechaInicio.plusMonths(plan.duracionMeses());

        suscripcionDAO.insertar(idCliente, idPlan, idUsuario, idSede,
                fechaInicio, fechaFin, plan.precioTotal());
    }

    @Override
    public List<Map<String, Object>> listarPlanesActivos() {
        return suscripcionDAO.listarPlanesActivos();
    }

    @Override
    public List<Map<String, Object>> listarSedesActivas() {
        return suscripcionDAO.listarSedesActivas();
    }
    @Override
    public List<Map<String, Object>> listarClientesActivos() {
        return suscripcionDAO.listarClientesActivos();
    }
    @Override
    public Map<Integer, List<Map<String, Object>>> listarDetallePlanes() {
        return suscripcionDAO.listarDetallePlanes();
    }
    @Override
    @Transactional
    public String cambiarEstado(Integer idSuscripcion) {
        SuscripcionEstado s = suscripcionDAO.buscarEstado(idSuscripcion)
                .orElseThrow(() -> new ReglaNegocioException("La suscripción no existe."));

        String nuevoEstado;
        if ("ACTIVO".equals(s.estado())) {
            nuevoEstado = "DESACTIVADA";
        } else if ("DESACTIVADA".equals(s.estado())) {
            if (s.fechaFin().isBefore(LocalDate.now())) {
                throw new ReglaNegocioException("No se puede activar: la suscripción ya venció. Contrate un plan nuevo.");
            }
            if (suscripcionDAO.contarVigentes(s.idCliente()) > 0) {   // RN05
                throw new ReglaNegocioException("RN05: el cliente ya tiene otra suscripción vigente.");
            }
            nuevoEstado = "ACTIVO";
        } else {
            throw new ReglaNegocioException("Estado no reconocido: " + s.estado());
        }
        suscripcionDAO.actualizarEstado(idSuscripcion, nuevoEstado);
        return nuevoEstado;
    }

    @Override
    public List<Map<String, Object>> listarSuscripcionesDetalle() {
        return suscripcionDAO.listarSuscripcionesDetalle();
    }
}