
package com.gimnasioteam2.sistema_gimnasio.Service;

import com.gimnasioteam2.sistema_gimnasio.Model.Categoria;
import com.gimnasioteam2.sistema_gimnasio.Repository.CategoriaRepository;
import com.gimnasioteam2.sistema_gimnasio.Repository.ProductoRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    public CategoriaService(
            CategoriaRepository categoriaRepository,
            ProductoRepository productoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    public boolean registrar(Categoria categoria) {
        if (categoria.getNombre() == null
                || categoria.getNombre().trim().isEmpty()) {
            return false;
        }

        categoria.setNombre(categoria.getNombre().trim());
        categoria.setEstado("Activo");

        return categoriaRepository.guardar(categoria);
    }

    public List<Categoria> listar() {
        return categoriaRepository.listar();
    }

    public boolean actualizar(Categoria categoria) {
        if (categoria.getNombre() == null
                || categoria.getNombre().trim().isEmpty()) {
            return false;
        }

        Categoria actual = categoriaRepository.buscarPorId(
                categoria.getIdCategoria());

        if (actual == null) {
            return false;
        }

        if ("Inactivo".equalsIgnoreCase(categoria.getEstado())
                && productoRepository.tieneProductosActivos(
                categoria.getIdCategoria())) {
            return false;
        }

        categoria.setNombre(categoria.getNombre().trim());
        return categoriaRepository.actualizar(categoria);
    }

    public boolean desactivar(int id) {
        Categoria categoria = categoriaRepository.buscarPorId(id);

        if (categoria == null
                || productoRepository.tieneProductosActivos(id)) {
            return false;
        }

        return categoriaRepository.desactivar(id);
    }
}
