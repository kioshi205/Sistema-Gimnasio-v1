package com.gimnasioteam2.sistema_gimnasio.clases;

import java.util.Date;

public class Suscripciones {
    private Integer id_suscripcion;
    private Integer id_cliente;
    private Integer id_plan;
    private Integer id_usuario;
    private Integer id_sede;
    private Date fecha_inicio;
    private Date fecha_fin;
    private Double monto_pagado;
    private String estado;

    //constructor vacio
    public Suscripciones()
    {
    }

    public Suscripciones(Integer id_suscripcion, Integer id_cliente, Integer id_plan, Integer id_usuario, Integer id_sede, Date fecha_inicio, Date fecha_fin, Double monto_pagado, String estado) {
        this.id_suscripcion = id_suscripcion;
        this.id_cliente = id_cliente;
        this.id_plan = id_plan;
        this.id_usuario = id_usuario;
        this.id_sede = id_sede;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.monto_pagado = monto_pagado;
        this.estado = estado;
    }

    //getters y setters de cada atributo
    public Integer getId_suscripcion() {
        return id_suscripcion;
    }
    public void setId_suscripcion(Integer id_suscripcion) {
        this.id_suscripcion = id_suscripcion;
    }

    public Integer getId_cliente() {
        return id_cliente;
    }
    public void setId_cliente(Integer id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Integer getId_plan() {
        return id_plan;
    }
    public void setId_plan(Integer id_plan) {
        this.id_plan = id_plan;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }
    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Integer getId_sede() {
        return id_sede;
    }
    public void setId_sede(Integer id_sede) {
        this.id_sede = id_sede;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }
    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }
    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public Double getMonto_pagado() {
        return monto_pagado;
    }
    public void setMonto_pagado(Double monto_pagado) {
        this.monto_pagado = monto_pagado;
    }

    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
}