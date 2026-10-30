package com.gimnasioteam2.sistema_gimnasio.Service;

import com.gimnasioteam2.sistema_gimnasio.Model.Plan;
import com.gimnasioteam2.sistema_gimnasio.Model.Producto;
import com.gimnasioteam2.sistema_gimnasio.Repository.PlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class PlanService {

    private static final String ESTADO_ACTIVO = "Activo";
    private static final String TIPO_PREDETERMINADO = "PREDETERMINADO";

    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public List<Plan> listarPlanes() {
        return planRepository.listarTodos();
    }

    public Plan obtenerPlan(int idPlan) {
        Plan plan = planRepository.buscarPorId(idPlan);
        if (plan == null) {
            throw new IllegalArgumentException("El plan no existe.");
        }
        return plan;
    }

    public List<Producto> listarSubproductosActivos() {
        return planRepository.listarProductosActivos();
    }

    /**
     * F10: registra un plan predeterminado junto con sus detalles en una sola transacción.
     * El precio total se calcula siempre en el servidor (RN06); no se recibe del formulario.
     */
    @Transactional
    public int registrarPlanPredeterminado(String nombre, Integer duracionMeses,
                                           List<Integer> idsProductos, int idUsuario) {
        String nombreLimpio = validarNombre(nombre);
        int duracion = validarDuracion(duracionMeses);
        List<Integer> ids = normalizarIds(idsProductos);
        List<Producto> productos = validarSubproductos(ids);
        BigDecimal precioTotal = calcularPrecioTotal(productos);

        Plan plan = new Plan();
        plan.setNombrePlan(nombreLimpio);
        plan.setTipoPlan(TIPO_PREDETERMINADO);
        plan.setDuracionMeses(duracion);
        plan.setPrecioTotal(precioTotal);
        plan.setEstado(ESTADO_ACTIVO);
        plan.setIdUsuario(idUsuario);
        plan.setIdCliente(null);

        int idPlan = planRepository.insertarPlan(plan);
        planRepository.insertarDetalles(idPlan, ids);
        return idPlan;
    }

    /**
     * F12: actualiza datos y subproductos. Los retirados quedan inactivos en
     * PLAN_DETALLE (no se borran) y el precio se recalcula (RN06).
     */
    @Transactional
    public void actualizarPlan(int idPlan, String nombre, Integer duracionMeses,
                               List<Integer> idsProductos) {
        Plan planActual = planRepository.buscarPorId(idPlan);
        if (planActual == null) {
            throw new IllegalArgumentException("El plan no existe.");
        }
        if (!ESTADO_ACTIVO.equalsIgnoreCase(planActual.getEstado())) {
            throw new IllegalStateException("Un plan inactivo no se puede editar.");
        }

        String nombreLimpio = validarNombre(nombre);
        int duracion = validarDuracion(duracionMeses);
        List<Integer> ids = normalizarIds(idsProductos);
        List<Producto> productos = validarSubproductos(ids);
        BigDecimal precioTotal = calcularPrecioTotal(productos);

        int filas = planRepository.actualizarPlan(idPlan, nombreLimpio, duracion, precioTotal);
        if (filas == 0) {
            throw new IllegalStateException("El plan ya no está activo y no se puede editar.");
        }

        planRepository.desactivarDetallesActivos(idPlan);
        for (Integer idProducto : ids) {
            if (planRepository.activarDetalle(idPlan, idProducto) == 0) {
                planRepository.insertarDetalle(idPlan, idProducto);
            }
        }
    }

    /**
     * F13: baja lógica. No se elimina el plan ni sus suscripciones (RN01).
     */
    @Transactional
    public void desactivarPlan(int idPlan) {
        Plan planActual = planRepository.buscarPorId(idPlan);
        if (planActual == null) {
            throw new IllegalArgumentException("El plan no existe.");
        }
        if (!ESTADO_ACTIVO.equalsIgnoreCase(planActual.getEstado())) {
            throw new IllegalStateException("El plan ya se encuentra inactivo.");
        }

        int filas = planRepository.desactivarPlan(idPlan);
        if (filas == 0) {
            throw new IllegalStateException("No se pudo desactivar el plan. Intenta nuevamente.");
        }
    }

    /**
     * Reactiva un plan inactivo. Solo cambia el estado de PLAN.
     */
    @Transactional
    public void activarPlan(int idPlan) {
        Plan planActual = planRepository.buscarPorId(idPlan);
        if (planActual == null) {
            throw new IllegalArgumentException("El plan no existe.");
        }
        if (ESTADO_ACTIVO.equalsIgnoreCase(planActual.getEstado())) {
            throw new IllegalStateException("El plan ya se encuentra activo.");
        }

        int filas = planRepository.activarPlan(idPlan);
        if (filas == 0) {
            throw new IllegalStateException("No se pudo reactivar el plan. Intenta nuevamente.");
        }
    }

    private String validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del plan es obligatorio.");
        }
        String nombreLimpio = nombre.trim();
        if (nombreLimpio.length() > 100) {
            throw new IllegalArgumentException("El nombre del plan no puede superar los 100 caracteres.");
        }
        return nombreLimpio;
    }

    private int validarDuracion(Integer duracionMeses) {
        if (duracionMeses == null || duracionMeses <= 0) {
            throw new IllegalArgumentException("La duración en meses debe ser mayor a 0.");
        }
        return duracionMeses;
    }

    private List<Integer> normalizarIds(List<Integer> idsProductos) {
        Set<Integer> unicos = new LinkedHashSet<>();
        if (idsProductos != null) {
            for (Integer id : idsProductos) {
                if (id != null) {
                    unicos.add(id);
                }
            }
        }
        return new ArrayList<>(unicos);
    }

    /**
     * RN02: vuelve a validar contra la base de datos que todos los subproductos
     * existan y estén activos, sin confiar en lo que envió el navegador.
     */
    private List<Producto> validarSubproductos(List<Integer> idsProductos) {
        if (idsProductos == null || idsProductos.isEmpty()) {
            throw new IllegalArgumentException("Debe seleccionar al menos un subproducto.");
        }

        List<Producto> productos = planRepository.buscarProductosPorIds(idsProductos);
        if (productos.size() != idsProductos.size()) {
            throw new IllegalArgumentException("Uno o más subproductos seleccionados no existen.");
        }

        for (Producto producto : productos) {
            if (!producto.isActivo()) {
                throw new IllegalArgumentException("El subproducto \"" + producto.getNombreSubproducto()
                        + "\" no está activo y no puede formar parte del plan.");
            }
        }
        return productos;
    }

    /**
     * RN06: el precio del plan es siempre la suma de los precios individuales.
     */
    private BigDecimal calcularPrecioTotal(List<Producto> productos) {
        BigDecimal total = BigDecimal.ZERO;
        for (Producto producto : productos) {
            if (producto.getPrecioIndividual() != null) {
                total = total.add(producto.getPrecioIndividual());
            }
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }
}