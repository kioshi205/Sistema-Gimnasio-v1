
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Catálogo de Categorías</title>
    <style>
        * { box-sizing: border-box; }
        body {
            font-family: Arial, sans-serif;
            background: #f2f4f8;
            margin: 0;
            padding: 30px;
            color: #263238;
        }
        .contenedor { max-width: 950px; margin: auto; }
        h1 { color: #17365d; }
        .panel {
            background: white;
            padding: 22px;
            margin-bottom: 25px;
            border-radius: 10px;
            box-shadow: 0 2px 8px #00000012;
        }
        .formulario {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 15px;
        }
        label { display: block; font-weight: bold; margin-bottom: 6px; }
        input, select {
            width: 100%;
            padding: 10px;
            border: 1px solid #cbd5e1;
            border-radius: 5px;
        }
        button, .volver {
            display: inline-block;
            padding: 10px 14px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            font-size: 14px;
        }
        .registrar { background: #17365d; color: white; }
        .editar { background: #e8a317; color: #222; }
        .desactivar { background: #c0392b; color: white; }
        .volver { background: #526477; color: white; margin-bottom: 18px; }
        .tabla { width: 100%; border-collapse: collapse; }
        .tabla th, .tabla td {
            padding: 12px;
            border-bottom: 1px solid #e2e8f0;
            text-align: left;
            vertical-align: top;
        }
        .tabla th { background: #17365d; color: white; }
        .tabla input { margin-bottom: 7px; }
        .acciones form { margin-bottom: 8px; }
        @media (max-width: 650px) {
            body { padding: 12px; }
            .formulario { grid-template-columns: 1fr; }
            .panel { overflow-x: auto; }
        }
    </style>
</head>
<body>
<div class="contenedor">

    <a class="volver" href="${pageContext.request.contextPath}/inicio">
        Volver al inicio
    </a>

    <h1>Catálogo de Categorías</h1>
    <p>Administra las categorías de los servicios del gimnasio.</p>

    <section class="panel">
        <h2>Registrar categoría</h2>

        <form action="${pageContext.request.contextPath}/catalogo/categorias/registrar"
              method="post" class="formulario">

            <div>
                <label for="nombre">Nombre de la categoría</label>
                <input id="nombre" type="text" name="nombre"
                       maxlength="100" required>
            </div>

            <div>
                <label for="descripcion">Descripción</label>
                <input id="descripcion" type="text" name="descripcion"
                       maxlength="255">
            </div>

            <div>
                <button type="submit" class="registrar">
                    Registrar categoría
                </button>
            </div>
        </form>
    </section>

    <section class="panel">
        <h2>Lista de categorías</h2>

        <c:if test="${empty categorias}">
            <p>No hay categorías registradas.</p>
        </c:if>

        <table class="tabla">
            <thead>
            <tr>
                <th>ID</th>
                <th>Nombre y descripción</th>
                <th>Estado</th>
                <th>Acciones</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="categoria" items="${categorias}">
                <tr>
                    <td><c:out value="${categoria.idCategoria}"/></td>

                    <td>
                        <form action="${pageContext.request.contextPath}/catalogo/categorias/editar"
                              method="post">
                            <input type="hidden" name="idCategoria"
                                   value="${categoria.idCategoria}">

                            <label>Nombre</label>
                            <input type="text" name="nombre"
                                   value="${categoria.nombre}"
                                   maxlength="100" required>

                            <label>Descripción</label>
                            <input type="text" name="descripcion"
                                   value="${categoria.descripcion}"
                                   maxlength="255" required>

                            <label>Estado</label>
                            <select name="estado">
                                <option value="Activo"
                                    <c:if test="${categoria.estado == 'Activo'}">
                                        selected
                                    </c:if>>
                                    Activo
                                </option>
                                <option value="Inactivo"
                                    <c:if test="${categoria.estado == 'Inactivo'}">
                                        selected
                                    </c:if>>
                                    Inactivo
                                </option>
                            </select>

                            <button type="submit" class="editar">
                                Guardar cambios
                            </button>
                        </form>
                    </td>

                    <td><c:out value="${categoria.estado}"/></td>

                    <td class="acciones">
                        <c:if test="${categoria.estado == 'Activo'}">
                            <form action="${pageContext.request.contextPath}/catalogo/categorias/desactivar"
                                  method="post"
                                  onsubmit="return confirm('¿Deseas desactivar esta categoría?');">
                                <input type="hidden" name="idCategoria"
                                       value="${categoria.idCategoria}">
                                <button type="submit" class="desactivar">
                                    Desactivar
                                </button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </section>
</div>
</body>
</html>
