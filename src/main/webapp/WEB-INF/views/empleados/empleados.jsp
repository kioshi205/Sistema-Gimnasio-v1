<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="esEdicion" value="${not empty empleadoForm.idUsuario}" />
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Empleados | Sistema de Gimnasio</title>
    <link rel="stylesheet" href="<c:url value='/static/css/administracion.css'/>">
</head>
<body>
<header class="topbar">
    <div class="topbar-inner">
        <span class="brand">Sistema de Gimnasio</span>
        <nav class="nav" aria-label="Módulos de administración">
            <a class="nav-link activo" href="<c:url value='/empleados'/>" aria-current="page">Empleados</a>
            <a class="nav-link" href="<c:url value='/sedes'/>">Sedes</a>
        </nav>
    </div>
</header>

<main class="contenido">
    <h1>Gestión de empleados</h1>
    <p class="subtitulo">Registre, edite y desactive las cuentas del personal del gimnasio.</p>

    <c:if test="${not empty exito}">
        <div class="mensaje mensaje-exito" role="status"><c:out value="${exito}"/></div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="mensaje mensaje-error" role="alert"><c:out value="${error}"/></div>
    </c:if>

    <section class="panel">
        <h2><c:out value="${esEdicion ? 'Editar empleado' : 'Registrar empleado'}"/></h2>

        <form class="formulario" method="post" action="<c:url value='/empleados/guardar'/>">
            <input type="hidden" name="idUsuario" value="${fn:escapeXml(empleadoForm.idUsuario)}">

            <div class="campo">
                <label for="nombre">Nombre <span class="obligatorio">*</span></label>
                <input type="text" id="nombre" name="nombre" maxlength="100" required
                       value="${fn:escapeXml(empleadoForm.nombre)}">
            </div>

            <div class="campo">
                <label for="apellido">Apellido <span class="obligatorio">*</span></label>
                <input type="text" id="apellido" name="apellido" maxlength="100" required
                       value="${fn:escapeXml(empleadoForm.apellido)}">
            </div>

            <div class="campo">
                <label for="dni">DNI <span class="obligatorio">*</span></label>
                <input type="text" id="dni" name="dni" maxlength="8" minlength="8"
                       pattern="[0-9]{8}" inputmode="numeric" required
                       value="${fn:escapeXml(empleadoForm.dni)}"
                       <c:if test="${esEdicion}">readonly</c:if>>
                <small class="ayuda">
                    <c:choose>
                        <c:when test="${esEdicion}">El DNI no se puede modificar.</c:when>
                        <c:otherwise>Ingrese exactamente 8 dígitos numéricos.</c:otherwise>
                    </c:choose>
                </small>
            </div>

            <div class="campo">
                <label for="username">Nombre de usuario <span class="obligatorio">*</span></label>
                <input type="text" id="username" name="username" maxlength="50" required
                       value="${fn:escapeXml(empleadoForm.username)}">
            </div>

            <div class="campo">
                <label for="password">Contraseña
                    <c:if test="${not esEdicion}"><span class="obligatorio">*</span></c:if>
                </label>
                <input type="password" id="password" name="password" maxlength="255"
                       autocomplete="new-password"
                       placeholder="${esEdicion ? 'Dejar vacío para conservar la actual' : 'Contraseña del empleado'}"
                       <c:if test="${not esEdicion}">required</c:if>>
                <small class="ayuda">
                    <c:choose>
                        <c:when test="${esEdicion}">
                            Si deja este campo vacío, se conserva la contraseña actual.
                        </c:when>
                        <c:otherwise>
                            La contraseña se almacena como en el modelo del proyecto.
                        </c:otherwise>
                    </c:choose>
                </small>
            </div>

            <div class="campo">
                <label for="idRol">Rol <span class="obligatorio">*</span></label>
                <select id="idRol" name="idRol" required>
                    <option value="">Seleccione un rol</option>
                    <c:forEach var="rol" items="${roles}">
                        <option value="${fn:escapeXml(rol.idRol)}" ${rol.idRol == empleadoForm.idRol ? 'selected' : ''}>
                            <c:out value="${rol.nombreRol}"/>
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="acciones-form">
                <button type="submit" class="btn btn-primario">
                    <c:out value="${esEdicion ? 'Guardar cambios' : 'Registrar empleado'}"/>
                </button>
                <c:if test="${esEdicion}">
                    <a class="btn btn-secundario" href="<c:url value='/empleados'/>">Cancelar edición</a>
                </c:if>
            </div>
        </form>
    </section>

    <section class="panel">
        <div class="panel-cabecera">
            <h2>Empleados registrados</h2>

            <form class="filtro" method="get" action="<c:url value='/empleados'/>">
                <label class="sr-only" for="filtro">Buscar empleado</label>
                <input type="text" id="filtro" name="filtro" value="${fn:escapeXml(filtro)}"
                       placeholder="Buscar por nombre o DNI">
                <button type="submit" class="btn btn-secundario">Buscar</button>
                <c:if test="${not empty filtro}">
                    <a class="btn btn-secundario" href="<c:url value='/empleados'/>">Limpiar</a>
                </c:if>
            </form>
        </div>

        <div class="tabla-scroll">
            <table class="tabla">
                <thead>
                <tr>
                    <th scope="col">Empleado</th>
                    <th scope="col">DNI</th>
                    <th scope="col">Usuario</th>
                    <th scope="col">Rol</th>
                    <th scope="col">Estado</th>
                    <th scope="col">Acciones</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="empleado" items="${empleados}">
                    <tr>
                        <td><c:out value="${empleado.nombreCompleto}"/></td>
                        <td><c:out value="${empleado.dni}"/></td>
                        <td><c:out value="${empleado.username}"/></td>
                        <td><c:out value="${empleado.nombreRol}"/></td>
                        <td>
                            <c:choose>
                                <c:when test="${empleado.estadoUsuario == 'ACTIVO'}">
                                    <span class="badge badge-activo">Activo</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge badge-inactivo">Inactivo</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="acciones">
                            <c:url var="urlEditar" value="/empleados">
                                <c:param name="editar" value="${empleado.idUsuario}"/>
                            </c:url>
                            <a class="btn btn-secundario btn-pequeno" href="${fn:escapeXml(urlEditar)}">Editar</a>

                            <c:choose>
                                <c:when test="${empleado.estadoUsuario == 'ACTIVO'}">
                                    <form class="form-inline" method="post"
                                          action="<c:url value='/empleados/desactivar'/>"
                                          onsubmit="return confirm('¿Confirma que desea desactivar a este empleado? La cuenta no se eliminará.');">
                                        <input type="hidden" name="idUsuario"
                                               value="${fn:escapeXml(empleado.idUsuario)}">
                                        <button type="submit" class="btn btn-desactivar btn-pequeno">Desactivar</button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <form class="form-inline" method="post"
                                          action="<c:url value='/empleados/activar'/>"
                                          onsubmit="return confirm('¿Confirma que desea activar a este empleado?');">
                                        <input type="hidden" name="idUsuario"
                                               value="${fn:escapeXml(empleado.idUsuario)}">
                                        <button type="submit" class="btn btn-activar btn-pequeno">Activar</button>
                                    </form>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty empleados}">
                    <tr>
                        <td colspan="6" class="vacio">
                            <c:choose>
                                <c:when test="${not empty filtro}">
                                    No se encontraron empleados para el filtro ingresado.
                                </c:when>
                                <c:otherwise>No hay empleados registrados.</c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </section>
</main>
</body>
</html>