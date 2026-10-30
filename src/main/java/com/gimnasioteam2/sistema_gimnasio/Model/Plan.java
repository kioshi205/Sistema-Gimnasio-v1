package com.gimnasioteam2.sistema_gimnasio.Model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Plan del gimnasio (tabla PLAN) con sus filas de PLAN_DETALLE.
 */
public class Plan {

    private int idPlan;
    private String nombrePlan;
    private String tipoPlan;
    private int duracionMeses;
    private BigDecimal precioTotal;
    private String estado;
    private int idUsuario;
    private Integer idCliente;
    private List<PlanDetalle> detalles = new ArrayList<>();

    public Plan() {
    }

    public boolean isActivo() {
        return "Activo".equalsIgnoreCase(estado);
    }

    public List<PlanDetalle> getDetallesActivos() {
        List<PlanDetalle> activos = new ArrayList<>();
        for (PlanDetalle detalle : detalles) {
            if (detalle.isActivo()) {
                activos.add(detalle);
            }
        }
        return activos;
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

    public String getTipoPlan() {
        return tipoPlan;
    }

    public void setTipoPlan(String tipoPlan) {
        this.tipoPlan = tipoPlan;
    }

    public int getDuracionMeses() {
        return duracionMeses;
    }

    public void setDuracionMeses(int duracionMeses) {
        this.duracionMeses = duracionMeses;
    }

    public BigDecimal getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public List<PlanDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<PlanDetalle> detalles) {
        this.detalles = detalles == null ? new ArrayList<>() : detalles;
    }
}