<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Clientes | Sistema Gimnasio</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/gimnasio.css">
</head>
<body>
<%@ include file="/WEB-INF/views/layout/header.jspf" %>

<div class="page-head">
    <div>
        <h1>Clientes</h1>
        <p class="subtitle">Registra, busca, edita y desactiva clientes del gimnasio.</p>
    </div>
    <a class="btn btn-primary" href="${ctx}/clientes/nuevo">+ Registrar cliente</a>
</div>

<div class="card">
    <form class="filters" method="get" action="${ctx}/clientes">
        <input type="text" name="q" value="<c:out value='${q}'/>" placeholder="Buscar por DNI, nombre o apellido">
        <select name="estado" style="max-width:180px">
            <option value="">Todos los estados</option>
            <option value="ACTIVO"   ${estado == 'ACTIVO'   ? 'selected' : ''}>Activos</option>
            <option value="INACTIVO" ${estado == 'INACTIVO' ? 'selected' : ''}>Inactivos</option>
        </select>
        <button class="btn btn-primary" type="submit">Buscar</button>
        <c:if test="${not empty q or not empty estado}"><a class="btn" href="${ctx}/clientes">Limpiar</a></c:if>
    </form>

    <div class="table-wrap">
        <table>
            <thead>
            <tr>
                <th>DNI</th><th>Cliente</th><th>Correo</th><th>Teléfono</th>
                <th>Registro</th><th>Estado</th><th>Suscripción</th><th>Acciones</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="c" items="${clientes}">
                <tr>
                    <td><c:out value="${c.dni}"/></td>
                    <td><strong><c:out value="${c.nombreCompleto}"/></strong></td>
                    <td><c:out value="${empty c.email ? '—' : c.email}"/></td>
                    <td><c:out value="${empty c.telefono ? '—' : c.telefono}"/></td>
                    <td><c:out value="${c.fechaRegistro}"/></td>
                    <td>
                        <span class="badge ${c.activo ? 'badge-activo' : 'badge-inactivo'}">
                            <c:out value="${c.estadoCliente}"/>
                        </span>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${c.suscripcionVigente}"><span class="badge badge-vigente">Vigente</span></c:when>
                            <c:otherwise><span style="color:var(--muted)">Sin suscripción vigente</span></c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <div class="actions">
                            <a class="btn btn-sm" href="${ctx}/clientes/${c.idCliente}/editar">Editar</a>
                            <c:if test="${c.activo}">
                                <a class="btn btn-sm" href="${ctx}/planes-personalizados/nuevo?idCliente=${c.idCliente}">Armar plan</a>
                            </c:if>

                            <%-- Botón Activar: solo habilitado si el cliente está INACTIVO --%>
                            <form method="post" action="${ctx}/clientes/${c.idCliente}/activar">
                                <button class="btn btn-sm btn-primary" type="submit"
                                        <c:if test="${c.activo}">disabled</c:if>>Activar</button>
                            </form>

                            <%-- Botón Desactivar: solo habilitado si el cliente está ACTIVO --%>
                            <form method="post" action="${ctx}/clientes/${c.idCliente}/desactivar">
                                <button class="btn btn-sm btn-danger" type="submit"
                                        <c:if test="${not c.activo}">disabled</c:if>>Desactivar</button>
                            </form>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty clientes}">
                <tr><td colspan="8" class="empty">No se encontraron clientes.</td></tr>
            </c:if>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="/WEB-INF/views/layout/footer.jspf" %>
