package com.gimnasioteam2.sistema_gimnasio.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Registro del historial de accesos (tabla MARCACION_ACCESO).
 */
public class MarcacionAcceso {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm:ss");

    private int idMarcacion;
    private LocalDateTime fechaHora;
    private String dniConsultado;
    private String nombreCliente;
    private String nombreSede;
    private String resultado;
    private String motivo;

    public int getIdMarcacion() {
        return idMarcacion;
    }

    public void setIdMarcacion(int idMarcacion) {
        this.idMarcacion = idMarcacion;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getDniConsultado() {
        return dniConsultado;
    }

    public void setDniConsultado(String dniConsultado) {
        this.dniConsultado = dniConsultado;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreSede() {
        return nombreSede;
    }

    public void setNombreSede(String nombreSede) {
        this.nombreSede = nombreSede;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public boolean isPermitido() {
        return "PERMITIDO".equalsIgnoreCase(resultado);
    }

    public String getFechaTexto() {
        return fechaHora == null ? "" : fechaHora.format(FORMATO_FECHA);
    }

    public String getHoraTexto() {
        return fechaHora == null ? "" : fechaHora.format(FORMATO_HORA);
    }
}
