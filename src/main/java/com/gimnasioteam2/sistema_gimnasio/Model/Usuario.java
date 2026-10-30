package com.gimnasioteam2.sistema_gimnasio.Model;

/**
 * Representa a un empleado del sistema (tabla USUARIO).
 */
public class Usuario {

    private Integer idUsuario;
    private Integer idRol;
    private String dni;
    private String nombre;
    private String apellido;
    private String username;
    private String password;
    private String estadoUsuario;

    /**
     * Solo se usa para mostrar el nombre del rol en el listado (JOIN con ROL).
     */
    private String nombreRol;

    public Usuario() {
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(String estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    /**
     * Devuelve el nombre completo del empleado para el listado.
     */
    public String getNombreCompleto() {
        String nombreSeguro = nombre == null ? "" : nombre.trim();
        String apellidoSeguro = apellido == null ? "" : apellido.trim();
        return (nombreSeguro + " " + apellidoSeguro).trim();
    }
}