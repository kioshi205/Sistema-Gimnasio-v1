package com.gimnasioteam2.sistema_gimnasio.Model;

/**
 * Cliente registrado en el gimnasio (tabla CLIENTE).
 */
public class Cliente {

    private int idCliente;
    private String dni;
    private String nombre;
    private String apellido;
    private String estadoCliente;

    public Cliente() {
    }

    public Cliente(int idCliente, String dni, String nombre, String apellido, String estadoCliente) {
        this.idCliente = idCliente;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.estadoCliente = estadoCliente;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEstadoCliente() {
        return estadoCliente;
    }

    public void setEstadoCliente(String estadoCliente) {
        this.estadoCliente = estadoCliente;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    public boolean estaActivo() {
        return "ACTIVO".equalsIgnoreCase(estadoCliente);
    }
}
