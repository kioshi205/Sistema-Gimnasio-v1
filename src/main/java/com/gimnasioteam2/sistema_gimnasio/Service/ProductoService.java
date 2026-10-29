
package com.gimnasioteam2.sistema_gimnasio.Service;

import com.gimnasioteam2.sistema_gimnasio.Model.Categoria;
import com.gimnasioteam2.sistema_gimnasio.Model.Producto;
import com.gimnasioteam2.sistema_gimnasio.Repository.CategoriaRepository;
import com.gimnasioteam2.sistema_gimnasio.Repository.ProductoRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(
            ProductoRepository productoRepository,
            CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public boolean registrar(Producto producto) {
        if (producto.getNombre() == null
                || producto.getNombre().trim().isEmpty()
                || producto.getPrecioIndividual() < 0) {
            return false;
        }

        Categoria categoria = categoriaRepository.buscarPorId(
                producto.getIdCategoria());

        if (categoria == null
                || !"Activo".equalsIgnoreCase(categoria.getEstado())) {
            return false;
        }

        if (productoRepository.existeNombreEnCategoria(
                producto.getNombre(), producto.getIdCategoria())) {
            return false;
        }

        producto.setNombre(producto.getNombre().trim());
        producto.setEstado("Activo");

        return productoRepository.guardar(producto);
    }

    public List<Producto> listar() {
        return productoRepository.listar();
    }

    public boolean actualizar(Producto producto) {
        if (producto.getNombre() == null
                || producto.getNombre().trim().isEmpty()
                || producto.getPrecioIndividual() < 0) {
            return false;
        }

        Categoria categoria = categoriaRepository.buscarPorId(
                producto.getIdCategoria());

        if (categoria == null
                || !"Activo".equalsIgnoreCase(categoria.getEstado())) {
            return false;
        }

        Producto actual = productoRepository.buscarPorId(
                producto.getIdProducto());

        if (actual == null
                || !"Activo".equalsIgnoreCase(actual.getEstado())) {
            return false;
        }

        producto.setNombre(producto.getNombre().trim());
        return productoRepository.actualizar(producto);
    }

    public boolean desactivar(int id) {
        Producto producto = productoRepository.buscarPorId(id);

        if (producto == null
                || !"Activo".equalsIgnoreCase(producto.getEstado())) {
            return false;
        }

        if (productoRepository.estaEnPlanActivo(id)) {
            return false;
        }

        return productoRepository.desactivar(id);
    }
}
