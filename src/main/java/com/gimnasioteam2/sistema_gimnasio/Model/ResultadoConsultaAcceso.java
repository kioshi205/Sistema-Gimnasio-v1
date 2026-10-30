package com.gimnasioteam2.sistema_gimnasio.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Resultado de una consulta de acceso. También transporta los datos que se
 * muestran en pantalla cuando el cliente existe.
 */
public class ResultadoConsultaAcceso {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMATO_FECHA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private String dni;
    private String nombreSedeConsultada;
    private boolean permitido;
    private String resultado;
    private String motivo;

    private boolean clienteExiste;
    private String nombreCliente;
    private String nombrePlan;
    private String nombreSedeBase;
    private LocalDate fechaVencimiento;

    private LocalDateTime fechaHoraRegistro;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombreSedeConsultada() {
        return nombreSedeConsultada;
    }

    public void setNombreSedeConsultada(String nombreSedeConsultada) {
        this.nombreSedeConsultada = nombreSedeConsultada;
    }

    public boolean isPermitido() {
        return permitido;
    }

    public void setPermitido(boolean permitido) {
        this.permitido = permitido;
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

    public boolean isClienteExiste() {
        return clienteExiste;
    }

    public void setClienteExiste(boolean clienteExiste) {
        this.clienteExiste = clienteExiste;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombrePlan() {
        return nombrePlan;
    }

    public void setNombrePlan(String nombrePlan) {
        this.nombrePlan = nombrePlan;
    }

    public String getNombreSedeBase() {
        return nombreSedeBase;
    }

    public void setNombreSedeBase(String nombreSedeBase) {
        this.nombreSedeBase = nombreSedeBase;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public LocalDateTime getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }

    public void setFechaHoraRegistro(LocalDateTime fechaHoraRegistro) {
        this.fechaHoraRegistro = fechaHoraRegistro;
    }

    public String getFechaVencimientoTexto() {
        return fechaVencimiento == null ? "Sin suscripción" : fechaVencimiento.format(FORMATO_FECHA);
    }

    public String getFechaHoraRegistroTexto() {
        return fechaHoraRegistro == null ? "" : fechaHoraRegistro.format(FORMATO_FECHA_HORA);
    }
}
