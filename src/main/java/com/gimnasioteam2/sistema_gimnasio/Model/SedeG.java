package com.gimnasioteam2.sistema_gimnasio.Model;

/**
 * Sede del gimnasio (tabla SEDE).
 */
public class SedeG {

    private int idSede;
    private String nombreSede;
    private String direccion;

    public SedeG() {
    }

    public SedeG(int idSede, String nombreSede, String direccion) {
        this.idSede = idSede;
        this.nombreSede = nombreSede;
        this.direccion = direccion;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
