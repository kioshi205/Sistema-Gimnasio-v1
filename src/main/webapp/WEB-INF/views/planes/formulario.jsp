<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page import="com.gimnasioteam2.sistema_gimnasio.Model.Plan" %>
<%@ page import="com.gimnasioteam2.sistema_gimnasio.Model.Producto" %>
<%!
    private String esc(String valor) {
        if (valor == null) {
            return "";
        }
        return valor.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    private String moneda(java.math.BigDecimal valor) {
        if (valor == null) {
            return "0.00";
        }
        return valor.setScale(2, java.math.RoundingMode.HALF_UP).toPlainString();
    }
%>
<%
    String modo = (String) request.getAttribute("modo");
    boolean esEdicion = "editar".equals(modo);
    Integer idPlan = (Integer) request.getAttribute("idPlan");
    Plan plan = (Plan) request.getAttribute("plan");

    String nombrePlan = (String) request.getAttribute("nombrePlan");
    if (nombrePlan == null) {
        nombrePlan = "";
    }

    Object duracionAttr = request.getAttribute("duracionMeses");
    String duracionMeses = duracionAttr == null ? "" : duracionAttr.toString();

    Set<Integer> seleccionados = (Set<Integer>) request.getAttribute("seleccionados");
    if (seleccionados == null) {
        seleccionados = new HashSet<Integer>();
    }

    List<Producto> productos = (List<Producto>) request.getAttribute("productos");
    String error = (String) request.getAttribute("error");
    String avisoInactivos = (String) request.getAttribute("avisoSubproductosInactivos");

    Map<String, List<Producto>> productosPorCategoria = new LinkedHashMap<>();
    java.math.BigDecimal totalInicial = java.math.BigDecimal.ZERO;
    if (productos != null) {
        for (Producto producto : productos) {
            List<Producto> grupo = productosPorCategoria.get(producto.getNombreCategoria());
            if (grupo == null) {
                grupo = new ArrayList<>();
                productosPorCategoria.put(producto.getNombreCategoria(), grupo);
            }
            grupo.add(producto);
            if (seleccionados.contains(producto.getIdProducto()) && producto.getPrecioIndividual() != null) {
                totalInicial = totalInicial.add(producto.getPrecioIndividual());
            }
        }
    }

    String accion = esEdicion
            ? request.getContextPath() + "/planes/actualizar/" + idPlan
            : request.getContextPath() + "/planes/guardar";
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><%= esEdicion ? "Editar plan" : "Registrar plan" %></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/planes.css">
</head>
<body>
<main class="planes">
    <header class="planes__header">
        <div>
            <h1><%= esEdicion ? "Editar plan" : "Registrar plan predeterminado" %></h1>
            <% if (esEdicion && plan != null) { %>
            <p>Tipo: <strong><%= esc(plan.getTipoPlan()) %></strong>
                &middot; Estado: <strong><%= esc(plan.getEstado()) %></strong></p>
            <% } else { %>
            <p>El precio total se calcula automáticamente con los subproductos activos.</p>
            <% } %>
        </div>
        <a class="boton boton--secundario" href="${pageContext.request.contextPath}/planes">Volver al listado</a>
    </header>

    <% if (error != null) { %>
    <div class="aviso aviso--error"><%= esc(error) %></div>
    <% } %>
    <% if (avisoInactivos != null) { %>
    <div class="aviso aviso--info"><%= esc(avisoInactivos) %></div>
    <% } %>

    <form class="formulario" id="formulario-plan" method="post" action="<%= accion %>">
        <div class="campo">
            <label for="nombrePlan">Nombre del plan</label>
            <input type="text" id="nombrePlan" name="nombrePlan" maxlength="100" required
                   value="<%= esc(nombrePlan) %>" placeholder="Ej. Plan Básico">
        </div>

        <div class="campo">
            <label for="duracionMeses">Duración en meses</label>
            <input type="number" id="duracionMeses" name="duracionMeses" min="1" step="1" required
                   value="<%= esc(duracionMeses) %>" placeholder="Ej. 1">
            <p class="campo__ayuda">Debe ser mayor a 0.</p>
        </div>

        <div class="campo">
            <span class="campo__titulo">Subproductos activos</span>
            <p class="campo__ayuda">Solo se muestran subproductos con estado Activo.</p>

            <% if (productosPorCategoria.isEmpty()) { %>
            <p class="sin-productos">No hay subproductos activos disponibles.</p>
            <% } else {
                for (Map.Entry<String, List<Producto>> grupo : productosPorCategoria.entrySet()) {
            %>
            <fieldset class="grupo">
                <legend><%= esc(grupo.getKey()) %></legend>
                <div class="grupo__items">
                    <% for (Producto producto : grupo.getValue()) { %>
                    <label class="opcion">
                        <input type="checkbox" name="idProductos" value="<%= producto.getIdProducto() %>"
                               data-precio="<%= moneda(producto.getPrecioIndividual()) %>"
                               <%= seleccionados.contains(producto.getIdProducto()) ? "checked" : "" %>>
                        <span class="opcion__nombre"><%= esc(producto.getNombreSubproducto()) %></span>
                        <span class="opcion__precio">S/ <%= moneda(producto.getPrecioIndividual()) %></span>
                    </label>
                    <% } %>
                </div>
            </fieldset>
            <%      }
               } %>
        </div>

        <div class="resumen-precio">
            <div>
                <span>Precio total (calculado)</span>
                <p class="campo__ayuda">No es editable: se obtiene de la suma de los subproductos seleccionados.</p>
            </div>
            <strong id="precio-total">S/ <%= moneda(totalInicial) %></strong>
        </div>

        <div class="formulario__acciones">
            <button type="submit" class="boton"><%= esEdicion ? "Guardar cambios" : "Registrar plan" %></button>
            <a class="boton boton--secundario" href="${pageContext.request.contextPath}/planes">Cancelar</a>
        </div>
    </form>
</main>

<script>
    (function () {
        var formulario = document.getElementById('formulario-plan');
        var casillas = document.querySelectorAll('input[name="idProductos"]');
        var salida = document.getElementById('precio-total');

        function actualizarPrecio() {
            var total = 0;
            for (var i = 0; i < casillas.length; i++) {
                if (casillas[i].checked) {
                    total += parseFloat(casillas[i].getAttribute('data-precio')) || 0;
                }
            }
            salida.textContent = 'S/ ' + total.toFixed(2);
        }

        for (var i = 0; i < casillas.length; i++) {
            casillas[i].addEventListener('change', actualizarPrecio);
        }
        actualizarPrecio();

        formulario.addEventListener('submit', function (evento) {
            var marcadas = document.querySelectorAll('input[name="idProductos"]:checked');
            if (marcadas.length === 0) {
                evento.preventDefault();
                alert('Selecciona al menos un subproducto.');
            }
        });
    })();
</script>
</body>
</html>