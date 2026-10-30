package com.gimnasioteam2.sistema_gimnasio.Service;

import com.gimnasioteam2.sistema_gimnasio.Model.Sede;
import com.gimnasioteam2.sistema_gimnasio.Repository.SedeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Reglas de negocio de las sedes (I09: F17, F18 y F19).
 */
@Service
public class SedeService {

    private static final String ESTADO_ACTIVO = "Activo";
    private static final String ESTADO_INACTIVO = "Inactivo";
    private static final String MENSAJE_RN12 =
            "No se puede desactivar: es sede base de suscripciones vigentes";

    private final SedeRepository sedeRepository;

    public SedeService(SedeRepository sedeRepository) {
        this.sedeRepository = sedeRepository;
    }

    /**
     * F18: lista todas las sedes.
     */
    public List<Sede> listar() {
        return sedeRepository.listar();
    }

    public Sede obtener(int idSede) {
        return sedeRepository.buscarPorId(idSede)
                .orElseThrow(() -> new IllegalArgumentException("La sede indicada no existe."));
    }

    /**
     * F17: registra una sede con estado Activo.
     */
    public void registrar(Sede sede) {
        normalizar(sede);
        validarDatosObligatorios(sede);

        if (sedeRepository.existeNombre(sede.getNombreSede(), null)) {
            throw new IllegalArgumentException("Ya existe una sede registrada con ese nombre.");
        }

        sede.setEstado(ESTADO_ACTIVO);
        sedeRepository.insertar(sede);
    }

    /**
     * F19: actualiza nombre, dirección y estado. Aplica RN12 antes de desactivar.
     */
    @Transactional
    public void actualizar(Sede datos) {
        if (datos == null || datos.getIdSede() == null) {
            throw new IllegalArgumentException("No se indicó la sede que se desea actualizar.");
        }

        Sede actual = obtener(datos.getIdSede());

        normalizar(datos);
        validarDatosObligatorios(datos);

        if (sedeRepository.existeNombre(datos.getNombreSede(), datos.getIdSede())) {
            throw new IllegalArgumentException("Ya existe una sede registrada con ese nombre.");
        }

        String nuevoEstado = normalizarEstado(datos.getEstado());

        // RN12: solo se valida al intentar pasar de Activo a Inactivo.
        if (ESTADO_INACTIVO.equals(nuevoEstado)
                && !ESTADO_INACTIVO.equalsIgnoreCase(actual.getEstado())
                && sedeRepository.contarSuscripcionesVigentes(datos.getIdSede()) > 0) {
            throw new IllegalStateException(MENSAJE_RN12);
        }

        datos.setEstado(nuevoEstado);
        sedeRepository.actualizar(datos);
    }

    /**
     * Desactiva una sede desde el botón de la tabla, aplicando la misma RN12.
     */
    @Transactional
    public void desactivar(int idSede) {
        Sede sede = obtener(idSede);

        if (ESTADO_INACTIVO.equalsIgnoreCase(sede.getEstado())) {
            throw new IllegalStateException("La sede ya se encuentra inactiva.");
        }

        if (sedeRepository.contarSuscripcionesVigentes(idSede) > 0) {
            throw new IllegalStateException(MENSAJE_RN12);
        }

        sedeRepository.desactivar(idSede);
    }

    private void normalizar(Sede sede) {
        if (sede == null) {
            throw new IllegalArgumentException("No se recibieron los datos de la sede.");
        }

        sede.setNombreSede(recortar(sede.getNombreSede()));
        sede.setDireccion(recortar(sede.getDireccion()));
    }

    private void validarDatosObligatorios(Sede sede) {
        if (sede.getNombreSede() == null || sede.getNombreSede().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la sede es obligatorio.");
        }
        if (sede.getNombreSede().length() > 100) {
            throw new IllegalArgumentException("El nombre de la sede no puede superar los 100 caracteres.");
        }

        // En schema.sql, SEDE.direccion es NOT NULL; por eso se valida como obligatoria.
        if (sede.getDireccion() == null || sede.getDireccion().isEmpty()) {
            throw new IllegalArgumentException("La dirección de la sede es obligatoria.");
        }
        if (sede.getDireccion().length() > 255) {
            throw new IllegalArgumentException("La dirección no puede superar los 255 caracteres.");
        }
    }

    private String normalizarEstado(String estado) {
        if (estado == null || estado.isBlank()) {
            throw new IllegalArgumentException("Debe seleccionar el estado de la sede.");
        }
        if (ESTADO_ACTIVO.equalsIgnoreCase(estado.trim())) {
            return ESTADO_ACTIVO;
        }
        if (ESTADO_INACTIVO.equalsIgnoreCase(estado.trim())) {
            return ESTADO_INACTIVO;
        }
        throw new IllegalArgumentException("El estado de la sede debe ser Activo o Inactivo.");
    }

    private String recortar(String valor) {
        return valor == null ? null : valor.trim();
    }
}