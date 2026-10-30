package com.gimnasioteam2.sistema_gimnasio.Model;

import java.time.LocalDate;

/**
 * Suscripción de un cliente, junto con los datos del plan y su sede base.
 */
public class Suscripcion {

    private int idSuscripcion;
    private int idPlan;
    private String nombrePlan;
    private int idSede;
    private String nombreSede;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estadoSuscripcion;

    public Suscripcion() {
    }

    public boolean estaVigente(LocalDate fechaConsulta) {
        return "ACTIVO".equalsIgnoreCase(estadoSuscripcion)
                && !fechaInicio.isAfter(fechaConsulta)
                && !fechaFin.isBefore(fechaConsulta);
    }

    public int getIdSuscripcion() {
        return idSuscripcion;
    }

    public void setIdSuscripcion(int idSuscripcion) {
        this.idSuscripcion = idSuscripcion;
    }

    public int getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(int idPlan) {
        this.idPlan = idPlan;
    }

    public String getNombrePlan() {
        return nombrePlan;
    }

    public void setNombrePlan(String nombrePlan) {
        this.nombrePlan = nombrePlan;
    }

    public int getIdSede() {
        return idSede;
    }

    public void setIdSede(int idSede) {
        this.idSede = idSede;
    }

    public String getNombreSede() {
        return nombreSede;
    }

    public void setNombreSede(String nombreSede) {
        this.nombreSede = nombreSede;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstadoSuscripcion() {
        return estadoSuscripcion;
    }

    public void setEstadoSuscripcion(String estadoSuscripcion) {
        this.estadoSuscripcion = estadoSuscripcion;
    }
}
