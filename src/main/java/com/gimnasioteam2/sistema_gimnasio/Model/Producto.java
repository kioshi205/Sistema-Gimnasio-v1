
package com.gimnasioteam2.sistema_gimnasio.Model;

public class Producto {

    private int idProducto;
    private String nombre;
    private String descripcion;
    private double precioIndividual;
    private int idCategoria;
    private String estado = "Activo";

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioIndividual() {
        return precioIndividual;
    }

    public void setPrecioIndividual(double precioIndividual) {
        this.precioIndividual = precioIndividual;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
