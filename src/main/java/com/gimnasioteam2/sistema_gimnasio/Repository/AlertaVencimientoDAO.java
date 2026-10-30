package com.gimnasioteam2.sistema_gimnasio.Repository;

import com.gimnasioteam2.sistema_gimnasio.clases.AlertaVencimiento;

import java.time.LocalDate;
import java.util.List;

public interface AlertaVencimientoDAO {

    // Suscripciones ACTIVAS cuya fecha_fin es menor o igual a la fecha limite
    // (incluye las que ya vencieron y siguen marcadas como ACTIVO)
    List<AlertaVencimiento> ListaPorVencer(LocalDate fechaLimite);
}
