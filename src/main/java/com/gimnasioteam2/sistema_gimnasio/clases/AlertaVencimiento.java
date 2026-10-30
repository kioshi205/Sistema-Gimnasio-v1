package com.gimnasioteam2.sistema_gimnasio.clases;

import java.time.LocalDate;

public class AlertaVencimiento {

    private Integer id_suscripcion;
    private Integer id_cliente;
    private String dni;
    private String nombre_cliente;
    private String telefono;
    private String email;
    private String nombre_plan;
    private LocalDate fecha_fin;
    private Long dias_restantes;   // negativo = ya vencida
    private String nivel;          // VENCIDA, CRITICA, PROXIMA
    private String mensaje;

    public AlertaVencimiento() {
    }

    public Integer getId_suscripcion() { return id_suscripcion; }
    public void setId_suscripcion(Integer id_suscripcion) { this.id_suscripcion = id_suscripcion; }

    public Integer getId_cliente() { return id_cliente; }
    public void setId_cliente(Integer id_cliente) { this.id_cliente = id_cliente; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getNombre_cliente() { return nombre_cliente; }
    public void setNombre_cliente(String nombre_cliente) { this.nombre_cliente = nombre_cliente; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNombre_plan() { return nombre_plan; }
    public void setNombre_plan(String nombre_plan) { this.nombre_plan = nombre_plan; }

    public LocalDate getFecha_fin() { return fecha_fin; }
    public void setFecha_fin(LocalDate fecha_fin) { this.fecha_fin = fecha_fin; }

    public Long getDias_restantes() { return dias_restantes; }
    public void setDias_restantes(Long dias_restantes) { this.dias_restantes = dias_restantes; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}