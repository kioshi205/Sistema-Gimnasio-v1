package com.gimnasioteam2.sistema_gimnasio.Model;

public class Cliente {

    private String dni;
    private String nombres;
    private String telefono;
    private String correo;
    private String direccion;

    public Cliente() {
    }

    public Cliente(String dni, String nombres, String telefono,
                   String correo, String direccion) {
        this.dni = dni;
        this.nombres = nombres;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}