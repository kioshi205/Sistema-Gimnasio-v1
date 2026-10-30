<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.gimnasioteam2.sistema_gimnasio.Model.Plan" %>
<%@ page import="com.gimnasioteam2.sistema_gimnasio.Model.PlanDetalle" %>
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
    List<Plan> planes = (List<Plan>) request.getAttribute("planes");
    String exito = (String) request.getAttribute("exito");
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Planes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/planes.css">
</head>
<body>
<main class="planes">
    <header class="planes__header">
        <div>
            <h1>Planes</h1>
            <p>Gestión de planes predeterminados y personalizados</p>
        </div>
        <a class="boton" href="${pageContext.request.contextPath}/planes/nuevo">Registrar plan</a>
    </header>

    <% if (exito != null) { %>
    <div class="aviso aviso--exito"><%= esc(exito) %></div>
    <% } %>
    <% if (error != null) { %>
    <div class="aviso aviso--error"><%= esc(error) %></div>
    <% } %>

    <section class="tabla-contenedor">
        <table class="tabla">
            <thead>
            <tr>
                <th>Nombre</th>
                <th>Tipo</th>
                <th>Duración</th>
                <th>Precio total</th>
                <th>Subproductos</th>
                <th>Estado</th>
                <th>Acciones</th>
            </tr>
            </thead>
            <tbody>
            <% if (planes == null || planes.isEmpty()) { %>
            <tr>
                <td colspan="7" class="tabla__vacia">No hay planes registrados.</td>
            </tr>
            <% } else {
                for (Plan plan : planes) {
                    List<PlanDetalle> activos = plan.getDetallesActivos();
            %>
            <tr>
                <td><strong><%= esc(plan.getNombrePlan()) %></strong></td>
                <td><%= esc(plan.getTipoPlan()) %></td>
                <td><%= plan.getDuracionMeses() %> mes<%= plan.getDuracionMeses() == 1 ? "" : "es" %></td>
                <td class="precio">S/ <%= moneda(plan.getPrecioTotal()) %></td>
                <td>
                    <div class="chips">
                        <% if (activos.isEmpty()) { %>
                        <span class="chip chip--vacio">Sin subproductos activos</span>
                        <% } else {
                            for (PlanDetalle detalle : activos) {
                                String nombreSubproducto = detalle.getProducto() == null
                                        ? "Subproducto " + detalle.getIdProducto()
                                        : detalle.getProducto().getNombreSubproducto();
                                String precioSubproducto = detalle.getProducto() == null
                                        ? "0.00"
                                        : moneda(detalle.getProducto().getPrecioIndividual());
                                String categoria = detalle.getProducto() == null
                                        ? ""
                                        : detalle.getProducto().getNombreCategoria();
                        %>
                        <span class="chip" title="<%= esc(categoria) %>">
                            <%= esc(nombreSubproducto) %>
                            <em>S/ <%= precioSubproducto %></em>
                        </span>
                        <%      }
                           } %>
                    </div>
                </td>
                <td>
                    <span class="estado <%= plan.isActivo() ? "estado--activo" : "estado--inactivo" %>">
                        <%= esc(plan.getEstado()) %>
                    </span>
                </td>
                <td class="acciones">
                    <% if (plan.isActivo()) { %>
                    <a class="boton boton--secundario"
                       href="${pageContext.request.contextPath}/planes/editar/<%= plan.getIdPlan() %>">Editar</a>
                    <form method="post" class="acciones__form"
                          action="${pageContext.request.contextPath}/planes/desactivar/<%= plan.getIdPlan() %>"
                          onsubmit="return confirm('¿Desactivar este plan? Las suscripciones existentes se conservarán.');">
                        <button type="submit" class="boton boton--peligro">Desactivar</button>
                    </form>
                    <% } else { %>
                    <span class="acciones__nota">Plan inactivo</span>
                    <form method="post" class="acciones__form"
                          action="${pageContext.request.contextPath}/planes/activar/<%= plan.getIdPlan() %>"
                          onsubmit="return confirm('¿Reactivar este plan?');">
                        <button type="submit" class="boton">Reactivar</button>
                    </form>
                    <% } %>
                </td>
            </tr>
            <%      }
               } %>
            </tbody>
        </table>
    </section>
</main>
</body>
</html>