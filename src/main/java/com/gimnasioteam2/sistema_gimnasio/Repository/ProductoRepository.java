
package com.gimnasioteam2.sistema_gimnasio.Repository;

import com.gimnasioteam2.sistema_gimnasio.Model.Producto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductoRepository {

    private final List<Producto> productos = new ArrayList<>();
    private int siguienteId = 1;

    public boolean guardar(Producto producto) {
        producto.setIdProducto(siguienteId++);
        productos.add(producto);
        return true;
    }

    public List<Producto> listar() {
        return new ArrayList<>(productos);
    }

    public Producto buscarPorId(int id) {
        for (Producto producto : productos) {
            if (producto.getIdProducto() == id) {
                return producto;
            }
        }
        return null;
    }

    public boolean actualizar(Producto datos) {
        Producto producto = buscarPorId(datos.getIdProducto());

        if (producto == null) {
            return false;
        }

        producto.setNombre(datos.getNombre());
        producto.setDescripcion(datos.getDescripcion());
        producto.setPrecioIndividual(datos.getPrecioIndividual());
        producto.setIdCategoria(datos.getIdCategoria());

        return true;
    }

    public boolean tieneProductosActivos(int idCategoria) {
        for (Producto producto : productos) {
            if (producto.getIdCategoria() == idCategoria
                    && producto.getEstado().equals("Activo")) {
                return true;
            }
        }
        return false;
    }

    public boolean desactivar(int id) {
        Producto producto = buscarPorId(id);

        if (producto == null) {
            return false;
        }

        producto.setEstado("Inactivo");
        return true;
    }

    public boolean existeNombreEnCategoria(
            String nombre, int idCategoria) {

        for (Producto producto : productos) {
            if (producto.getNombre().equalsIgnoreCase(nombre.trim())
                    && producto.getIdCategoria() == idCategoria
                    && producto.getEstado().equals("Activo")) {
                return true;
            }
        }

        return false;
    }

    public boolean estaEnPlanActivo(int idProducto) {
        // Pendiente de conectar con PLAN y PLAN_DETALLE en la base de datos.
        return false;
    }
}
