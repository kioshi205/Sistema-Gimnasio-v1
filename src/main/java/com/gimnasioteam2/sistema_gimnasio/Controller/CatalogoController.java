
package com.gimnasioteam2.sistema_gimnasio.Controller;

import com.gimnasioteam2.sistema_gimnasio.Model.Categoria;
import com.gimnasioteam2.sistema_gimnasio.Model.Producto;
import com.gimnasioteam2.sistema_gimnasio.Repository.CategoriaRepository;
import com.gimnasioteam2.sistema_gimnasio.Service.CategoriaService;
import com.gimnasioteam2.sistema_gimnasio.Service.ProductoService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/catalogo")
public class CatalogoController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;
    private final CategoriaRepository categoriaRepository;

    public CatalogoController(
            ProductoService productoService,
            CategoriaService categoriaService,
            CategoriaRepository categoriaRepository) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
        this.categoriaRepository = categoriaRepository;
    }

    // INTERFAZ 107: SUBPRODUCTOS

    @GetMapping("/subproductos")
    public String listarSubproductos(Model model) {
        model.addAttribute("productos", productoService.listar());
        model.addAttribute("categorias", categoriaService.listar());
        return "catalogo/subproductos";
    }

    @PostMapping("/subproductos/registrar")
    public String registrarProducto(
            @ModelAttribute Producto producto,
            Model model) {

        boolean resultado = productoService.registrar(producto);

        model.addAttribute("mensaje", resultado
                ? "Subproducto registrado correctamente"
                : "No se pudo registrar. Verifica los datos y la categoría.");

        return listarSubproductos(model);
    }

    @PostMapping("/subproductos/editar")
    public String editarProducto(
            @ModelAttribute Producto producto,
            Model model) {

        boolean resultado = productoService.actualizar(producto);

        model.addAttribute("mensaje", resultado
                ? "Subproducto actualizado correctamente"
                : "No se pudo actualizar el subproducto.");

        return listarSubproductos(model);
    }

    @PostMapping("/subproductos/desactivar")
    public String desactivarProducto(
            @RequestParam("idProducto") int id,
            Model model) {

        boolean resultado = productoService.desactivar(id);

        model.addAttribute("mensaje", resultado
                ? "Subproducto desactivado correctamente"
                : "No se pudo desactivar. Puede pertenecer a un plan activo.");

        return listarSubproductos(model);
    }

    // INTERFAZ 108: CATEGORÍAS

    @GetMapping("/categorias")
    public String listarCategorias(Model model) {
        model.addAttribute("categorias", categoriaService.listar());
        return "catalogo/categorias";
    }

    @PostMapping("/categorias/registrar")
    public String registrarCategoria(
            @ModelAttribute Categoria categoria,
            Model model) {

        boolean resultado = categoriaService.registrar(categoria);

        model.addAttribute("mensaje", resultado
                ? "Categoría registrada correctamente"
                : "No se pudo registrar. Verifica el nombre.");

        return listarCategorias(model);
    }

    @PostMapping("/categorias/editar")
    public String editarCategoria(
            @ModelAttribute Categoria categoria,
            Model model) {

        boolean resultado = categoriaService.actualizar(categoria);

        model.addAttribute("mensaje", resultado
                ? "Categoría actualizada correctamente"
                : "No se pudo actualizar. Verifica sus subproductos activos.");

        return listarCategorias(model);
    }

    @PostMapping("/categorias/desactivar")
    public String desactivarCategoria(
            @RequestParam("idCategoria") int id,
            Model model) {

        boolean resultado = categoriaService.desactivar(id);

        model.addAttribute("mensaje", resultado
                ? "Categoría desactivada correctamente"
                : "No se puede desactivar: tiene subproductos activos.");

        return listarCategorias(model);
    }
}
