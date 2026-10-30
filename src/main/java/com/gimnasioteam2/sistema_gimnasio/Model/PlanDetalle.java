package com.gimnasioteam2.sistema_gimnasio.Model;

/**
 * Fila de PLAN_DETALLE con los datos del subproducto asociado.
 */
public class PlanDetalle {

    private int idPlanDetalle;
    private int idPlan;
    private int idProducto;
    private String estado;
    private Producto producto;

    public PlanDetalle() {
    }

    public PlanDetalle(int idPlanDetalle, int idPlan, int idProducto, String estado, Producto producto) {
        this.idPlanDetalle = idPlanDetalle;
        this.idPlan = idPlan;
        this.idProducto = idProducto;
        this.estado = estado;
        this.producto = producto;
    }

    public boolean isActivo() {
        return "Activo".equalsIgnoreCase(estado);
    }

    public int getIdPlanDetalle() {
        return idPlanDetalle;
    }

    public void setIdPlanDetalle(int idPlanDetalle) {
        this.idPlanDetalle = idPlanDetalle;
    }

    public int getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(int idPlan) {
        this.idPlan = idPlan;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}