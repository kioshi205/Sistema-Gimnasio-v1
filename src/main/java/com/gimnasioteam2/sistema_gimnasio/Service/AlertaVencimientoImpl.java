package com.gimnasioteam2.sistema_gimnasio.Service;

import com.gimnasioteam2.sistema_gimnasio.Repository.AlertaVencimientoDAO;
import com.gimnasioteam2.sistema_gimnasio.clases.AlertaVencimiento;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class AlertaVencimientoImpl implements AlertaVencimientoService {

    private static final int DIAS_POR_DEFECTO = 7;
    private static final int DIAS_CRITICA = 3;

    private final AlertaVencimientoDAO alertaVencimientoDAO;

    public AlertaVencimientoImpl(AlertaVencimientoDAO alertaVencimientoDAO) {
        this.alertaVencimientoDAO = alertaVencimientoDAO;
    }

    @Override
    public List<AlertaVencimiento> GenerarAlertas(int diasAnticipacion) {
        if (diasAnticipacion <= 0) {
            diasAnticipacion = DIAS_POR_DEFECTO;
        }

        LocalDate hoy = LocalDate.now();
        List<AlertaVencimiento> alertas =
                alertaVencimientoDAO.ListaPorVencer(hoy.plusDays(diasAnticipacion));

        for (AlertaVencimiento a : alertas) {
            long dias = ChronoUnit.DAYS.between(hoy, a.getFecha_fin());
            a.setDias_restantes(dias);

            if (dias < 0) {
                a.setNivel("VENCIDA");
                a.setMensaje("Venció hace " + Math.abs(dias) + " día(s). Debe renovar para ingresar.");
            } else if (dias == 0) {
                a.setNivel("CRITICA");
                a.setMensaje("Vence HOY.");
            } else if (dias <= DIAS_CRITICA) {
                a.setNivel("CRITICA");
                a.setMensaje("Vence en " + dias + " día(s).");
            } else {
                a.setNivel("PROXIMA");
                a.setMensaje("Vence en " + dias + " día(s).");
            }
        }
        return alertas;
    }
}