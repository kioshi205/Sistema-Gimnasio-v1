package com.gimnasioteam2.sistema_gimnasio.Model;

import java.math.BigDecimal;

/**
 * Subproducto ofrecido dentro de un plan (tabla PRODUCTO).
 */
public class Producto {

    private int idProducto;
    private String nombreSubproducto;
    private int idCategoria;
    private String nombreCategoria;
    private BigDecimal precioIndividual;
    private String estado;

    public Producto() {
    }

    public Producto(int idProducto, String nombreSubproducto, int idCategoria,
                    String nombreCategoria, BigDecimal precioIndividual, String estado) {
        this.idProducto = idProducto;
        this.nombreSubproducto = nombreSubproducto;
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
        this.precioIndividual = precioIndividual;
        this.estado = estado;
    }

    public boolean isActivo() {
        return "Activo".equalsIgnoreCase(estado);
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreSubproducto() {
        return nombreSubproducto;
    }

    public void setNombreSubproducto(String nombreSubproducto) {
        this.nombreSubproducto = nombreSubproducto;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public BigDecimal getPrecioIndividual() {
        return precioIndividual;
    }

    public void setPrecioIndividual(BigDecimal precioIndividual) {
        this.precioIndividual = precioIndividual;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}