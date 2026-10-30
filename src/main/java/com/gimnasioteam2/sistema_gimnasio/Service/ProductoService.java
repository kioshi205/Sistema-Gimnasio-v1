
package com.gimnasioteam2.sistema_gimnasio.Service;

import com.gimnasioteam2.sistema_gimnasio.Repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Map<String, Object>> listar() {
        return productoRepository.listar();
    }

    public void registrar(String nombre, int idCategoria, BigDecimal precio) {
        validarDatos(nombre, idCategoria, precio);

        if (productoRepository.existeNombre(nombre.trim(), 0)) {
            throw new IllegalArgumentException(
                    "Ya existe un subproducto con ese nombre."
            );
        }

        productoRepository.registrar(nombre.trim(), idCategoria, precio);
    }

    public void actualizar(int id, String nombre, int idCategoria,
                           BigDecimal precio) {
        validarDatos(nombre, idCategoria, precio);

        if (productoRepository.buscarPorId(id) == null) {
            throw new IllegalArgumentException(
                    "El subproducto no existe."
            );
        }

        if (productoRepository.existeNombre(nombre.trim(), id)) {
            throw new IllegalArgumentException(
                    "Ya existe otro subproducto con ese nombre."
            );
        }

        productoRepository.actualizar(
                id, nombre.trim(), idCategoria, precio
        );
    }

    public void desactivar(int id) {
        if (productoRepository.buscarPorId(id) == null) {
            throw new IllegalArgumentException(
                    "El subproducto no existe."
            );
        }

        if (productoRepository.estaEnPlanActivo(id)) {
            throw new IllegalArgumentException(
                    "No se puede desactivar porque pertenece a un plan activo."
            );
        }

        productoRepository.desactivar(id);
    }

    public void activar(int id) {
        Map<String, Object> producto = productoRepository.buscarPorId(id);

        if (producto == null) {
            throw new IllegalArgumentException(
                    "El subproducto no existe."
            );
        }

        int idCategoria = ((Number) producto.get("id_categoria")).intValue();

        if (!productoRepository.categoriaActiva(idCategoria)) {
            throw new IllegalArgumentException(
                    "No se puede activar: primero active su categoría."
            );
        }

        productoRepository.activar(id);
    }

    private void validarDatos(String nombre, int idCategoria,
                              BigDecimal precio) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre del subproducto es obligatorio."
            );
        }

        if (precio == null || precio.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "El precio debe ser igual o mayor que cero."
            );
        }

        if (!productoRepository.categoriaActiva(idCategoria)) {
            throw new IllegalArgumentException(
                    "Debe seleccionar una categoría activa."
            );
        }
    }
}
