
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Gestión de Subproductos</title>

<style>
*{box-sizing:border-box}
body{margin:0;padding:35px;font-family:Arial,sans-serif;background:#f0fafa;color:#24485a}
.contenedor{max-width:1200px;margin:auto}
h1{font-size:27px;color:#174b60;margin-bottom:8px}
.subtitulo{color:#78909c;font-size:14px;margin-bottom:25px}
.tarjetas{display:grid;grid-template-columns:repeat(3,1fr);gap:15px;margin-bottom:20px}
.tarjeta,.panel{background:white;border:1px solid #e0eeee;border-radius:14px;padding:20px;box-shadow:0 3px 12px #dceeee55;margin-bottom:20px}
.tarjeta span{display:block;color:#78909c;font-size:12px;font-weight:bold;margin-bottom:8px}
.tarjeta strong{font-size:25px;color:#24485a}
.panel h3{font-size:15px;margin:0 0 20px}
.formulario{display:grid;grid-template-columns:2fr 1.5fr 1fr auto;gap:12px;align-items:end}
label{display:block;font-size:11px;font-weight:bold;color:#66818c;margin-bottom:8px}
input,select{width:100%;padding:12px;border:1px solid #d8e6e9;border-radius:9px;outline:none;background:white;color:#355767}
input:focus,select:focus{border-color:#24b4c8}
button{border:0;border-radius:9px;padding:12px 15px;cursor:pointer;font-size:12px;font-weight:bold}
.principal{background:#27b4ca;color:white}
.editar{background:#e8f5ff;color:#2681b2}
.desactivar{background:#fff0f0;color:#d45b5b}
.activar{background:#e5f8ef;color:#16865d}
.tabla-contenedor{overflow-x:auto}
table{width:100%;border-collapse:collapse;font-size:13px}
th{background:#e9fafa;color:#477180;text-align:left;padding:15px 12px;font-size:11px}
td{padding:15px 12px;border-bottom:1px solid #edf2f3}
.estado{padding:6px 10px;border-radius:20px;font-size:10px;font-weight:bold}
.activo{color:#16865d;background:#e5f8ef}
.inactivo{color:#c34c4c;background:#fff0f0}
.acciones{display:flex;gap:7px;align-items:center}
.acciones form{margin:0}
.mensaje{padding:12px;border-radius:8px;background:#e5f8ef;color:#16865d;margin-bottom:15px}
.error{background:#fff0f0;color:#c34c4c}
.volver{display:inline-block;margin-top:12px;color:#248fa3;text-decoration:none;font-size:13px}
@media(max-width:800px){body{padding:15px}.tarjetas{grid-template-columns:1fr}.formulario{grid-template-columns:1fr}}
</style>
</head>

<body>
<div class="contenedor">

<h1>Gestión de subproductos</h1>
<p class="subtitulo">Registra, consulta y administra los servicios disponibles del gimnasio.</p>

<c:if test="${not empty mensaje}">
<div class="mensaje">${mensaje}</div>
</c:if>
<c:if test="${not empty error}">
<div class="mensaje error">${error}</div>
</c:if>

<div class="tarjetas">
<div class="tarjeta">
<span>SUBPRODUCTOS REGISTRADOS</span>
<strong>${productos.size()}</strong>
</div>

<div class="tarjeta">
<span>SERVICIOS ACTIVOS</span>
<strong>
<c:set var="activos" value="0"/>
<c:forEach items="${productos}" var="p">
<c:if test="${p.estado == 'Activo'}">
<c:set var="activos" value="${activos + 1}"/>
</c:if>
</c:forEach>
${activos}
</strong>
</div>

<div class="tarjeta">
<span>SERVICIOS INACTIVOS</span>
<strong>${productos.size() - activos}</strong>
</div>
</div>

<div class="panel">
<h3>● Registrar nuevo subproducto</h3>
<form action="${pageContext.request.contextPath}/catalogo/subproductos/registrar" method="post" class="formulario">

<div>
<label>NOMBRE DEL SUBPRODUCTO</label>
<input type="text" name="nombreSubproducto" placeholder="Ej. Entrenamiento personalizado" required>
</div>

<div>
<label>CATEGORÍA</label>
<select name="idCategoria" required>
<option value="">Seleccionar categoría</option>
<c:forEach items="${categorias}" var="c">
<c:if test="${c.estado == 'Activo'}">
<option value="${c.id_categoria}">${c.nombre_categoria}</option>
</c:if>
</c:forEach>
</select>
</div>

<div>
<label>PRECIO INDIVIDUAL (S/)</label>
<input type="number" name="precioIndividual" step="0.01" min="0" placeholder="0.00" required>
</div>

<button type="submit" class="principal">+ Registrar</button>
</form>
</div>

<div class="panel">
<h3>Subproductos registrados</h3>
<div class="tabla-contenedor">
<table>
<thead>
<tr>
<th>ID</th><th>SUBPRODUCTO</th><th>CATEGORÍA</th>
<th>PRECIO</th><th>ESTADO</th><th>ACCIONES</th>
</tr>
</thead>
<tbody>
<c:forEach items="${productos}" var="p">
<tr>
<td>${p.id_producto}</td>
<td><strong><c:out value="${p.nombre_subproducto}"/></strong></td>
<td><c:out value="${p.nombre_categoria}"/></td>
<td>S/ ${p.precio_individual}</td>
<td>
<c:choose>
<c:when test="${p.estado == 'Activo'}">
<span class="estado activo">● ACTIVO</span>
</c:when>
<c:otherwise>
<span class="estado inactivo">● INACTIVO</span>
</c:otherwise>
</c:choose>
</td>
<td>
<div class="acciones">
<button class="editar" type="button"
onclick="editarProducto('${p.id_producto}',this)"
data-nombre="<c:out value='${p.nombre_subproducto}'/>"
data-categoria="${p.id_categoria}"
data-precio="${p.precio_individual}">Editar</button>

<c:choose>
<c:when test="${p.estado == 'Activo'}">
<form action="${pageContext.request.contextPath}/catalogo/subproductos/desactivar" method="post"
onsubmit="return confirm('¿Deseas desactivar este subproducto?')">
<input type="hidden" name="idProducto" value="${p.id_producto}">
<button class="desactivar" type="submit">Desactivar</button>
</form>
</c:when>
<c:otherwise>
<form action="${pageContext.request.contextPath}/catalogo/subproductos/activar" method="post"
onsubmit="return confirm('¿Deseas activar este subproducto?')">
<input type="hidden" name="idProducto" value="${p.id_producto}">
<button class="activar" type="submit">Activar</button>
</form>
</c:otherwise>
</c:choose>
</div>
</td>
</tr>
</c:forEach>
</tbody>
</table>
</div>
</div>

<div class="panel" id="editarPanel" style="display:none">
<h3>Editar subproducto</h3>
<form action="${pageContext.request.contextPath}/catalogo/subproductos/editar" method="post" class="formulario">
<input type="hidden" name="idProducto" id="idProducto">

<div>
<label>NOMBRE DEL SUBPRODUCTO</label>
<input type="text" name="nombreSubproducto" id="nombreEditar" required>
</div>

<div>
<label>CATEGORÍA</label>
<select name="idCategoria" id="categoriaEditar" required>
<c:forEach items="${categorias}" var="c">
<c:if test="${c.estado == 'Activo'}">
<option value="${c.id_categoria}">${c.nombre_categoria}</option>
</c:if>
</c:forEach>
</select>
</div>

<div>
<label>PRECIO INDIVIDUAL</label>
<input type="number" name="precioIndividual" id="precioEditar" step="0.01" min="0" required>
</div>

<button type="submit" class="principal">Guardar cambios</button>
</form>
</div>

<a class="volver" href="${pageContext.request.contextPath}/inicio">← Volver al inicio</a>

</div>

<script>
function editarProducto(id, boton) {
    const panel = document.getElementById("editarPanel");
    panel.style.display = "block";

    document.getElementById("idProducto").value = id;
    document.getElementById("nombreEditar").value = boton.dataset.nombre;
    document.getElementById("categoriaEditar").value = boton.dataset.categoria;
    document.getElementById("precioEditar").value = boton.dataset.precio;

    panel.scrollIntoView({behavior:"smooth"});
}
</script>

</body>
</html>
