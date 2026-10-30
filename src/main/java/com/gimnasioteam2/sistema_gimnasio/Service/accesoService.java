package com.gimnasioteam2.sistema_gimnasio.Service;

import com.gimnasioteam2.sistema_gimnasio.Model.Cliente;
import com.gimnasioteam2.sistema_gimnasio.Model.ResultadoConsultaAcceso;
import com.gimnasioteam2.sistema_gimnasio.Model.Sede;
import com.gimnasioteam2.sistema_gimnasio.Model.Suscripcion;
import com.gimnasioteam2.sistema_gimnasio.Repository.accesoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class accesoService {

    private final accesoRepository acceso_Repository;

    public accesoService(accesoRepository acceso_Repository) {
        this.acceso_Repository = acceso_Repository;
    }

    public List<Sede> obtenerSedesActivas() {
        return acceso_Repository.listarSedesActivas();
    }

    public Sede obtenerSedeActiva(int idSede) {
        return acceso_Repository.buscarSedeActivaPorId(idSede);
    }

    public String obtenerNombreUsuario(int idUsuario) {
        return acceso_Repository.buscarNombreUsuario(idUsuario);
    }

    /**
     * F32: ejecuta las verificaciones de acceso en el orden definido y registra
     * la marcación en MARCACION_ACCESO, sea permitida o denegada.
     */
    public ResultadoConsultaAcceso consultarAcceso(String dni, int idSede, int idUsuario) {
        Sede sede = acceso_Repository.buscarSedeActivaPorId(idSede);
        if (sede == null) {
            throw new IllegalArgumentException("La sede seleccionada no es válida.");
        }

        Cliente cliente = acceso_Repository.buscarClientePorDni(dni);
        Suscripcion suscripcion = cliente == null
                ? null
                : acceso_Repository.buscarSuscripcionMasReciente(cliente.getIdCliente());

        LocalDateTime fechaHoraConsulta = LocalDateTime.now();
        LocalDate fechaConsulta = fechaHoraConsulta.toLocalDate();

        Integer idCliente = null;
        Integer idSuscripcion = null;
        boolean permitido = false;
        String motivo;

        // Paso 1: ¿el DNI existe en CLIENTE?
        if (cliente == null) {
            motivo = "DNI no registrado";
        } else {
            idCliente = cliente.getIdCliente();

            // Paso 2 (RN02): ¿el cliente está activo?
            if (!cliente.estaActivo()) {
                motivo = "Cliente inactivo";
            }
            // Paso 3 (RN08): ¿tiene una suscripción vigente?
            else if (suscripcion == null || !suscripcion.estaVigente(fechaConsulta)) {
                motivo = "Suscripción vencida";
            } else {
                idSuscripcion = suscripcion.getIdSuscripcion();

                // Paso 4 (RN09): ¿está en su sede base o su plan da acceso a todas las sedes?
                boolean estaEnSedeBase = suscripcion.getIdSede() == idSede;
                boolean planIncluyeTodasLasSedes =
                        acceso_Repository.planIncluyeAccesoTotal(suscripcion.getIdPlan());

                if (!estaEnSedeBase && !planIncluyeTodasLasSedes) {
                    motivo = "Sede no incluida en el plan";
                } else {
                    // Paso 5: pasó todas las verificaciones.
                    permitido = true;
                    motivo = estaEnSedeBase
                            ? "Suscripción vigente en su sede"
                            : "Suscripción vigente con acceso a todas las sedes";
                }
            }
        }

        String resultado = permitido ? "PERMITIDO" : "DENEGADO";

        // Toda consulta queda registrada, incluso las denegadas.
        acceso_Repository.registrarMarcacion(idCliente, idSuscripcion, idUsuario, idSede,
                dni, resultado, motivo);

        ResultadoConsultaAcceso consulta = new ResultadoConsultaAcceso();
        consulta.setDni(dni);
        consulta.setNombreSedeConsultada(sede.getNombreSede());
        consulta.setPermitido(permitido);
        consulta.setResultado(resultado);
        consulta.setMotivo(motivo);
        consulta.setFechaHoraRegistro(fechaHoraConsulta);

        if (cliente != null) {
            consulta.setClienteExiste(true);
            consulta.setNombreCliente(cliente.getNombreCompleto());

            if (suscripcion != null) {
                consulta.setNombrePlan(suscripcion.getNombrePlan());
                consulta.setNombreSedeBase(suscripcion.getNombreSede());
                consulta.setFechaVencimiento(suscripcion.getFechaFin());
            }
        }

        return consulta;
    }
}
