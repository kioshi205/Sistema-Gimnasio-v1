package com.gimnasioteam2.sistema_gimnasio.Controller;

import com.gimnasioteam2.sistema_gimnasio.Model.ResultadoConsultaAcceso;
import com.gimnasioteam2.sistema_gimnasio.Service.accesoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/acceso")
public class accesoController {

    /**
     * Usuario de seguridad de los datos de prueba. Se usa mientras el login no
     * guarde el usuario autenticado en la sesión.
     */
    private static final int ID_USUARIO_SEGURIDAD_PREDETERMINADO = 203;

    private final accesoService acceso_Service;

    public accesoController(accesoService acceso_Service) {
        this.acceso_Service = acceso_Service;
    }

    /** Muestra el formulario de consulta y, si existe, el resultado anterior. */
    @GetMapping
    public String mostrarConsulta(Model model, HttpSession session) {
        cargarDatosGenerales(model, session);
        return "acceso/consultaAcceso";
    }

    /** F32: consulta el acceso por DNI y sede. */
    @PostMapping("/consultar")
    public String consultar(@RequestParam(required = false) String dni,
                            @RequestParam(defaultValue = "0") int idSede,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        String dniLimpio = dni == null ? "" : dni.trim();

        // Se conservan los valores ingresados para volver a mostrarlos.
        redirectAttributes.addFlashAttribute("dniIngresado", dniLimpio);
        redirectAttributes.addFlashAttribute("sedeSeleccionada", idSede);

        if (!dniLimpio.matches("\\d{8}")) {
            redirectAttributes.addFlashAttribute("mensajeError",
                    "El DNI debe tener exactamente 8 dígitos.");
            return "redirect:/acceso";
        }

        if (acceso_Service.obtenerSedeActiva(idSede) == null) {
            redirectAttributes.addFlashAttribute("mensajeError",
                    "Debe seleccionar una sede activa.");
            return "redirect:/acceso";
        }

        ResultadoConsultaAcceso resultado = acceso_Service.consultarAcceso(
                dniLimpio, idSede, obtenerIdUsuario(session));

        redirectAttributes.addFlashAttribute("resultado", resultado);
        return "redirect:/acceso";
    }

    private void cargarDatosGenerales(Model model, HttpSession session) {
        int idUsuario = obtenerIdUsuario(session);
        model.addAttribute("sedes", acceso_Service.obtenerSedesActivas());
        model.addAttribute("nombreGuardia", acceso_Service.obtenerNombreUsuario(idUsuario));
    }

    private int obtenerIdUsuario(HttpSession session) {
        Object idSesion = session.getAttribute("idUsuario");
        if (idSesion instanceof Number) {
            return ((Number) idSesion).intValue();
        }
        return ID_USUARIO_SEGURIDAD_PREDETERMINADO;
    }
}
