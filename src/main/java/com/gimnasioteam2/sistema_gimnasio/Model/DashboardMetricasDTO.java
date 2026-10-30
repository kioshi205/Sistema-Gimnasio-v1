package com.gimnasioteam2.sistema_gimnasio.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Métricas que alimentan las 4 gráficas y las 4 tarjetas del dashboard (F22).
 * Cada lista llega ya completa y en orden cronológico desde el Service.
 */
public class DashboardMetricasDTO {

    private List<PuntoGrafico> ingresosPorMes = new ArrayList<>();
    private List<PuntoGrafico> suscripcionesPorMes = new ArrayList<>();
    private List<PuntoGrafico> ingresosPuertaPorDia = new ArrayList<>();
    private List<PuntoGrafico> clientesPorMes = new ArrayList<>();

    public DashboardMetricasDTO() {
    }

    public List<PuntoGrafico> getIngresosPorMes() {
        return ingresosPorMes;
    }

    public void setIngresosPorMes(List<PuntoGrafico> ingresosPorMes) {
        this.ingresosPorMes = ingresosPorMes == null ? new ArrayList<>() : ingresosPorMes;
    }

    public List<PuntoGrafico> getSuscripcionesPorMes() {
        return suscripcionesPorMes;
    }

    public void setSuscripcionesPorMes(List<PuntoGrafico> suscripcionesPorMes) {
        this.suscripcionesPorMes = suscripcionesPorMes == null ? new ArrayList<>() : suscripcionesPorMes;
    }

    public List<PuntoGrafico> getIngresosPuertaPorDia() {
        return ingresosPuertaPorDia;
    }

    public void setIngresosPuertaPorDia(List<PuntoGrafico> ingresosPuertaPorDia) {
        this.ingresosPuertaPorDia = ingresosPuertaPorDia == null ? new ArrayList<>() : ingresosPuertaPorDia;
    }

    public List<PuntoGrafico> getClientesPorMes() {
        return clientesPorMes;
    }

    public void setClientesPorMes(List<PuntoGrafico> clientesPorMes) {
        this.clientesPorMes = clientesPorMes == null ? new ArrayList<>() : clientesPorMes;
    }
}