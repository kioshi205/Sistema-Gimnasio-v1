package com.gimnasioteam2.sistema_gimnasio.Model;

import java.math.BigDecimal;


public class PuntoGrafico {

    private String etiqueta;
    private BigDecimal valor;

    public PuntoGrafico() {
    }

    public PuntoGrafico(String etiqueta, BigDecimal valor) {
        this.etiqueta = etiqueta;
        this.valor = valor;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}