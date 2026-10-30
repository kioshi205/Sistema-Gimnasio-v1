
package com.gimnasioteam2.sistema_gimnasio.Controller;

import com.gimnasioteam2.sistema_gimnasio.Service.ProductoService;
import com.gimnasioteam2.sistema_gimnasio.Service.CategoriaService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping("/catalogo")
public class CatalogoController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    public CatalogoController(
            ProductoService productoService,
            CategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    // SUBPRODUCTOS

    @GetMapping("/subproductos")
    public String listarSubproductos(Model model) {
        model.addAttribute("productos", productoService.listar());
        model.addAttribute("categorias", categoriaService.listar());

        return "catalogo/subproductos";
    }

    @PostMapping("/subproductos/registrar")
    public String registrarSubproducto(
            @RequestParam String nombreSubproducto,
            @RequestParam int idCategoria,
            @RequestParam BigDecimal precioIndividual,
            RedirectAttributes redirect) {

        try {
            productoService.registrar(
                    nombreSubproducto, idCategoria, precioIndividual
            );
            redirect.addFlashAttribute(
                    "mensaje", "Subproducto registrado correctamente."
            );
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/catalogo/subproductos";
    }

    @PostMapping("/subproductos/editar")
    public String editarSubproducto(
            @RequestParam int idProducto,
            @RequestParam String nombreSubproducto,
            @RequestParam int idCategoria,
            @RequestParam BigDecimal precioIndividual,
            RedirectAttributes redirect) {

        try {
            productoService.actualizar(
                    idProducto, nombreSubproducto,
                    idCategoria, precioIndividual
            );
            redirect.addFlashAttribute(
                    "mensaje", "Subproducto actualizado correctamente."
            );
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/catalogo/subproductos";
    }

    @PostMapping("/subproductos/desactivar")
    public String desactivarSubproducto(
            @RequestParam int idProducto,
            RedirectAttributes redirect) {

        try {
            productoService.desactivar(idProducto);
            redirect.addFlashAttribute(
                    "mensaje", "Subproducto desactivado correctamente."
            );
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/catalogo/subproductos";
    }

    @PostMapping("/subproductos/activar")
    public String activarSubproducto(
            @RequestParam int idProducto,
            RedirectAttributes redirect) {

        try {
            productoService.activar(idProducto);
            redirect.addFlashAttribute(
                    "mensaje", "Subproducto activado correctamente."
            );
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/catalogo/subproductos";
    }

    // CATEGORÍAS

    @GetMapping("/categorias")
    public String listarCategorias(Model model) {
        model.addAttribute("categorias", categoriaService.listar());

        return "catalogo/categorias";
    }

    @PostMapping("/categorias/registrar")
    public String registrarCategoria(
            @RequestParam String nombreCategoria,
            @RequestParam(required = false) String descripcion,
            RedirectAttributes redirect) {

        try {
            categoriaService.registrar(nombreCategoria, descripcion);
            redirect.addFlashAttribute(
                    "mensaje", "Categoría registrada correctamente."
            );
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/catalogo/categorias";
    }

    @PostMapping("/categorias/editar")
    public String editarCategoria(
            @RequestParam int idCategoria,
            @RequestParam String nombreCategoria,
            @RequestParam(required = false) String descripcion,
            RedirectAttributes redirect) {

        try {
            categoriaService.actualizar(
                    idCategoria, nombreCategoria, descripcion
            );
            redirect.addFlashAttribute(
                    "mensaje", "Categoría actualizada correctamente."
            );
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/catalogo/categorias";
    }

    @PostMapping("/categorias/desactivar")
    public String desactivarCategoria(
            @RequestParam int idCategoria,
            RedirectAttributes redirect) {

        try {
            categoriaService.desactivar(idCategoria);
            redirect.addFlashAttribute(
                    "mensaje", "Categoría desactivada correctamente."
            );
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/catalogo/categorias";
    }

    @PostMapping("/categorias/activar")
    public String activarCategoria(
            @RequestParam int idCategoria,
            RedirectAttributes redirect) {

        try {
            categoriaService.activar(idCategoria);
            redirect.addFlashAttribute(
                    "mensaje", "Categoría activada correctamente."
            );
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/catalogo/categorias";
    }
}
