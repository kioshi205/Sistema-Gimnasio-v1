package com.gimnasioteam2.sistema_gimnasio.Model;

/**
 * Representa un perfil de acceso del sistema (tabla ROL).
 */
public class RolG {

    private int idRol;
    private String nombreRol;
    private String descripcion;

    public RolG() {
    }

    public RolG(int idRol, String nombreRol, String descripcion) {
        this.idRol = idRol;
        this.nombreRol = nombreRol;
        this.descripcion = descripcion;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
