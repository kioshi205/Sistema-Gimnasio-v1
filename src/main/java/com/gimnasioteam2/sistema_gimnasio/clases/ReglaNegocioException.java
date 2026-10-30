package com.gimnasioteam2.sistema_gimnasio.clases;

public class ReglaNegocioException extends RuntimeException {
    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}