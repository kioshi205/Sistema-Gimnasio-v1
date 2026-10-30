package com.gimnasioteam2.sistema_gimnasio.clases;

import java.math.BigDecimal;

public record PlanInfo(Integer idPlan, Integer duracionMeses, BigDecimal precioTotal,
                       String estado, Integer idClienteDueno) {
}