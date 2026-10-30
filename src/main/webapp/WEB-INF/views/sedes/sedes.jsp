<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="esEdicion" value="${not empty sedeForm.idSede}" />
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Sedes | Sistema de Gimnasio</title>
    <link rel="stylesheet" href="<c:url value='/static/css/administracion.css'/>">
</head>
<body>
<header class="topbar">
    <div class="topbar-inner">
        <span class="brand">Sistema de Gimnasio</span>
        <nav class="nav" aria-label="Módulos de administración">
            <a class="nav-link" href="<c:url value='/empleados'/>">Empleados</a>
            <a class="nav-link activo" href="<c:url value='/sedes'/>" aria-current="page">Sedes</a>
        </nav>
    </div>
</header>

<main class="contenido">
    <h1>Gestión de sedes</h1>
    <p class="subtitulo">Registre, edite y desactive las sedes del gimnasio.</p>

    <c:if test="${not empty exito}">
        <div class="mensaje mensaje-exito" role="status"><c:out value="${exito}"/></div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="mensaje mensaje-error" role="alert"><c:out value="${error}"/></div>
    </c:if>

    <section class="panel">
        <h2><c:out value="${esEdicion ? 'Editar sede' : 'Registrar sede'}"/></h2>

        <form class="formulario" method="post" action="<c:url value='/sedes/guardar'/>">
            <input type="hidden" name="idSede" value="${fn:escapeXml(sedeForm.idSede)}">

            <div class="campo">
                <label for="nombreSede">Nombre de la sede <span class="obligatorio">*</span></label>
                <input type="text" id="nombreSede" name="nombreSede" maxlength="100" required
                       value="${fn:escapeXml(sedeForm.nombreSede)}">
            </div>

            <div class="campo">
                <label for="direccion">Dirección <span class="obligatorio">*</span></label>
                <input type="text" id="direccion" name="direccion" maxlength="255" required
                       value="${fn:escapeXml(sedeForm.direccion)}">
            </div>

            <div class="campo">
                <label for="estado">Estado <span class="obligatorio">*</span></label>
                <select id="estado" name="estado" required>
                    <option value="Activo" ${sedeForm.estado == 'Activo' ? 'selected' : ''}>Activo</option>
                    <option value="Inactivo" ${sedeForm.estado == 'Inactivo' ? 'selected' : ''}>Inactivo</option>
                </select>
                <small class="ayuda">
                    No se puede desactivar una sede con suscripciones vigentes (RN12).
                </small>
            </div>

            <div class="acciones-form">
                <button type="submit" class="btn btn-primario">
                    <c:out value="${esEdicion ? 'Guardar cambios' : 'Registrar sede'}"/>
                </button>
                <c:if test="${esEdicion}">
                    <a class="btn btn-secundario" href="<c:url value='/sedes'/>">Cancelar edición</a>
                </c:if>
            </div>
        </form>
    </section>

    <section class="panel">
        <div class="panel-cabecera">
            <h2>Sedes registradas</h2>
        </div>

        <div class="tabla-scroll">
            <table class="tabla">
                <thead>
                <tr>
                    <th scope="col">Sede</th>
                    <th scope="col">Dirección</th>
                    <th scope="col">Estado</th>
                    <th scope="col">Acciones</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="sede" items="${sedes}">
                    <tr>
                        <td><c:out value="${sede.nombreSede}"/></td>
                        <td><c:out value="${sede.direccion}"/></td>
                        <td>
                            <c:choose>
                                <c:when test="${sede.estado == 'Activo'}">
                                    <span class="badge badge-activo">Activo</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge badge-inactivo">Inactivo</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="acciones">
                            <c:url var="urlEditar" value="/sedes">
                                <c:param name="editar" value="${sede.idSede}"/>
                            </c:url>
                            <a class="btn btn-secundario btn-pequeno" href="${fn:escapeXml(urlEditar)}">Editar</a>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty sedes}">
                    <tr>
                        <td colspan="4" class="vacio">No hay sedes registradas.</td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </section>
</main>
</body>
</html>