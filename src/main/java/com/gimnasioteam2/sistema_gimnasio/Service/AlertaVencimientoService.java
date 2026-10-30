package com.gimnasioteam2.sistema_gimnasio.Service;

import com.gimnasioteam2.sistema_gimnasio.clases.AlertaVencimiento;

import java.util.List;

public interface AlertaVencimientoService {

    // F31: genera las alertas de las suscripciones que vencen en los proximos
    // "diasAnticipacion" dias, mas las que ya vencieron.
    List<AlertaVencimiento> GenerarAlertas(int diasAnticipacion);
}