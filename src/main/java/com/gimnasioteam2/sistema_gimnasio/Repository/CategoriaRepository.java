
package com.gimnasioteam2.sistema_gimnasio.Repository;

import com.gimnasioteam2.sistema_gimnasio.Model.Categoria;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoriaRepository {

    private final List<Categoria> categorias = new ArrayList<>();
    private int siguienteId = 1;

    public boolean guardar(Categoria categoria) {
        if (existeNombre(categoria.getNombre(), 0)) {
            return false;
        }

        categoria.setIdCategoria(siguienteId++);
        categorias.add(categoria);
        return true;
    }

    public List<Categoria> listar() {
        return new ArrayList<>(categorias);
    }

    public Categoria buscarPorId(int id) {
        for (Categoria categoria : categorias) {
            if (categoria.getIdCategoria() == id) {
                return categoria;
            }
        }
        return null;
    }

    public boolean existeNombre(String nombre, int idExcluir) {
        for (Categoria categoria : categorias) {
            if (categoria.getNombre().equalsIgnoreCase(nombre.trim())
                    && categoria.getIdCategoria() != idExcluir) {
                return true;
            }
        }
        return false;
    }

    public boolean actualizar(Categoria datos) {
        Categoria categoria = buscarPorId(datos.getIdCategoria());

        if (categoria == null ||
                existeNombre(datos.getNombre(), datos.getIdCategoria())) {
            return false;
        }

        categoria.setNombre(datos.getNombre());
        categoria.setDescripcion(datos.getDescripcion());
        categoria.setEstado(datos.getEstado());

        return true;
    }

    public boolean desactivar(int id) {
        Categoria categoria = buscarPorId(id);

        if (categoria == null) {
            return false;
        }

        categoria.setEstado("Inactivo");
        return true;
    }
}
