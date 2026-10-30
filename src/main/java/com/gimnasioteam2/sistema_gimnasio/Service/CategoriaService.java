
package com.gimnasioteam2.sistema_gimnasio.Service;

import com.gimnasioteam2.sistema_gimnasio.Repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Map<String, Object>> listar() {
        return categoriaRepository.listar();
    }

    public void registrar(String nombre, String descripcion) {
        validarNombre(nombre);

        if (categoriaRepository.existeNombre(nombre.trim(), 0)) {
            throw new IllegalArgumentException(
                    "Ya existe una categoría con ese nombre."
            );
        }

        categoriaRepository.registrar(nombre.trim(), descripcion);
    }

    public void actualizar(int id, String nombre, String descripcion) {
        validarNombre(nombre);

        if (categoriaRepository.buscarPorId(id) == null) {
            throw new IllegalArgumentException(
                    "La categoría no existe."
            );
        }

        if (categoriaRepository.existeNombre(nombre.trim(), id)) {
            throw new IllegalArgumentException(
                    "Ya existe otra categoría con ese nombre."
            );
        }

        categoriaRepository.actualizar(id, nombre.trim(), descripcion);
    }

    public void desactivar(int id) {
        if (categoriaRepository.buscarPorId(id) == null) {
            throw new IllegalArgumentException(
                    "La categoría no existe."
            );
        }

        if (categoriaRepository.tieneProductosActivos(id)) {
            throw new IllegalArgumentException(
                    "No se puede desactivar: la categoría tiene subproductos activos."
            );
        }

        categoriaRepository.desactivar(id);
    }

    public void activar(int id) {
        if (categoriaRepository.buscarPorId(id) == null) {
            throw new IllegalArgumentException(
                    "La categoría no existe."
            );
        }

        categoriaRepository.activar(id);
    }

    private void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre de la categoría es obligatorio."
            );
        }
    }
}
