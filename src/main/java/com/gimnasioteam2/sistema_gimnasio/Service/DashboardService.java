package com.gimnasioteam2.sistema_gimnasio.Service;

import com.gimnasioteam2.sistema_gimnasio.Model.DashboardMetricasDTO;
import com.gimnasioteam2.sistema_gimnasio.Model.PuntoGrafico;
import com.gimnasioteam2.sistema_gimnasio.Repository.DashboardRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Arma las series del dashboard: define el rango de meses/días,
 * rellena con 0 los períodos sin datos y genera las etiquetas en español.
 */
@Service
public class DashboardService {

    private static final String[] MESES_CORTOS = {
            "Ene", "Feb", "Mar", "Abr", "May", "Jun",
            "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"
    };

    private static final int MESES_GRAFICA = 12;
    private static final int DIAS_GRAFICA = 30;

    private final DashboardRepository dashboardRepository;

    public DashboardService(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    public DashboardMetricasDTO obtenerMetricas() {
        LocalDate hoy = LocalDate.now();
        YearMonth mesActual = YearMonth.from(hoy);
        YearMonth primerMes = mesActual.minusMonths(MESES_GRAFICA - 1);

        LocalDate inicioMeses = primerMes.atDay(1);
        LocalDate finMesesExclusivo = mesActual.plusMonths(1).atDay(1);
        LocalDate inicioDias = hoy.minusDays(DIAS_GRAFICA - 1);

        DashboardMetricasDTO metricas = new DashboardMetricasDTO();

        metricas.setIngresosPorMes(completarMeses(
                dashboardRepository.ingresosPorMes(inicioMeses, finMesesExclusivo),
                primerMes, MESES_GRAFICA));

        metricas.setSuscripcionesPorMes(completarMeses(
                dashboardRepository.suscripcionesPorMes(inicioMeses, finMesesExclusivo),
                primerMes, MESES_GRAFICA));

        metricas.setIngresosPuertaPorDia(completarDias(
                dashboardRepository.ingresosPuertaPorDia(inicioDias, hoy),
                inicioDias, DIAS_GRAFICA));

        metricas.setClientesPorMes(completarMeses(
                dashboardRepository.clientesPorMes(inicioMeses, finMesesExclusivo),
                primerMes, MESES_GRAFICA));

        return metricas;
    }

    private List<PuntoGrafico> completarMeses(List<PuntoGrafico> datos, YearMonth primerMes, int cantidad) {
        Map<String, BigDecimal> valores = indexar(datos);
        List<PuntoGrafico> serie = new ArrayList<>(cantidad);

        for (int i = 0; i < cantidad; i++) {
            YearMonth mes = primerMes.plusMonths(i);
            BigDecimal valor = valores.getOrDefault(mes.toString(), BigDecimal.ZERO);
            serie.add(new PuntoGrafico(etiquetaMes(mes), valor));
        }
        return serie;
    }

    private List<PuntoGrafico> completarDias(List<PuntoGrafico> datos, LocalDate primerDia, int cantidad) {
        Map<String, BigDecimal> valores = indexar(datos);
        List<PuntoGrafico> serie = new ArrayList<>(cantidad);

        for (int i = 0; i < cantidad; i++) {
            LocalDate dia = primerDia.plusDays(i);
            BigDecimal valor = valores.getOrDefault(dia.toString(), BigDecimal.ZERO);
            serie.add(new PuntoGrafico(etiquetaDia(dia), valor));
        }
        return serie;
    }

    private Map<String, BigDecimal> indexar(List<PuntoGrafico> datos) {
        Map<String, BigDecimal> valores = new LinkedHashMap<>();
        if (datos == null) {
            return valores;
        }
        for (PuntoGrafico punto : datos) {
            if (punto != null && punto.getEtiqueta() != null) {
                valores.put(punto.getEtiqueta(),
                        punto.getValor() == null ? BigDecimal.ZERO : punto.getValor());
            }
        }
        return valores;
    }

    private String etiquetaMes(YearMonth mes) {
        return MESES_CORTOS[mes.getMonthValue() - 1] + " " + mes.getYear();
    }

    private String etiquetaDia(LocalDate dia) {
        return dia.getDayOfMonth() + " " + MESES_CORTOS[dia.getMonthValue() - 1];
    }
}