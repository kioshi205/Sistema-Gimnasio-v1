package com.gimnasioteam2.sistema_gimnasio.clases;

import java.util.Date;

public class Cliente {
    private Integer id;
    private String dni;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private Date fecha_registro;
    private String estado_cliente;

    public Cliente(){/*constructor
    vacio para la recepcion de datos*/}


    public Cliente(Integer id, String dni, String nombre, String apellido, String email, String telefono, Date fecha_registro, String estado_cliente) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.fecha_registro = fecha_registro;
        this.estado_cliente = estado_cliente;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getDni() {
        return  dni;
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public Date getFecha_registro() {
        return fecha_registro;
    }
    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }
    public String getEstado_cliente() {
        return estado_cliente;
    }
    public void setEstado_cliente(String estado_cliente) {
        this.estado_cliente = estado_cliente;
    }
}
