
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Catálogo de Subproductos</title>
    <style>
        * { box-sizing: border-box; }
        body {
            font-family: Arial, sans-serif;
            background: #f2f4f8;
            margin: 0;
            padding: 30px;
            color: #263238;
        }
        .contenedor {
            max-width: 1100px;
            margin: auto;
        }
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
            grid-template-columns: repeat(2, minmax(0, 1fr));
            gap: 14px;
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
            padding: 10px;
            border-bottom: 1px solid #e2e8f0;
            text-align: left;
            vertical-align: top;
        }
        .tabla th { background: #17365d; color: white; }
        .tabla input, .tabla select { min-width: 110px; }
        .estado { font-weight: bold; }
        .mensaje { color: #176b3a; }
        .acciones { min-width: 150px; }
        @media (max-width: 700px) {
            body { padding: 12px; }
            .formulario { grid-template-columns: 1fr; }
            .tabla { font-size: 12px; }
            .panel { overflow-x: auto; }
        }
    </style>
</head>
<body>
<div class="contenedor">

    <a class="volver" href="${pageContext.request.contextPath}/inicio">
        Volver al inicio
    </a>

    <h1>Catálogo de Subproductos</h1>
    <p>Registra, consulta, edita y desactiva los subproductos del gimnasio.</p>

    <section class="panel">
        <h2>Registrar subproducto</h2>

        <form action="${pageContext.request.contextPath}/catalogo/subproductos/registrar"
              method="post" class="formulario">

            <div>
                <label for="nombre">Nombre</label>
                <input id="nombre" type="text" name="nombre" required maxlength="100">
            </div>

            <div>
                <label for="precioIndividual">Precio individual (S/)</label>
                <input id="precioIndividual" type="number" name="precioIndividual"
                       min="0" step="0.01" required>
            </div>

            <div>
                <label for="descripcion">Descripción</label>
                <input id="descripcion" type="text" name="descripcion" maxlength="255">
            </div>

            <div>
                <label for="idCategoria">Categoría</label>
                <select id="idCategoria" name="idCategoria" required>
                    <option value="">Seleccione una categoría</option>
                    <c:forEach var="categoria" items="${categorias}">
                        <c:if test="${categoria.estado == 'Activo'}">
                            <option value="${categoria.idCategoria}">
                                <c:out value="${categoria.nombre}"/>
                            </option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>

            <div>
                <button type="submit" class="registrar">Registrar subproducto</button>
            </div>
        </form>
    </section>

    <section class="panel">
        <h2>Lista de subproductos</h2>

        <c:if test="${empty productos}">
            <p>No hay subproductos registrados.</p>
        </c:if>

        <table class="tabla">
            <thead>
            <tr>
                <th>ID</th>
                <th>Nombre y descripción</th>
                <th>Precio (S/)</th>
                <th>Categoría</th>
                <th>Estado</th>
                <th>Acciones</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="producto" items="${productos}">
                <tr>
                    <td><c:out value="${producto.idProducto}"/></td>

                    <td>
                        <form action="${pageContext.request.contextPath}/catalogo/subproductos/editar"
                              method="post">
                            <input type="hidden" name="idProducto"
                                   value="${producto.idProducto}">

                            <label>Nombre</label>
                            <input type="text" name="nombre"
                                   value="${producto.nombre}" required maxlength="100">

                            <label>Descripción</label>
                            <input type="text" name="descripcion"
                                   value="${producto.descripcion}" maxlength="255">

                            <label>Categoría</label>
                            <select name="idCategoria" required>
                                <c:forEach var="categoria" items="${categorias}">
                                    <option value="${categoria.idCategoria}"
                                        <c:if test="${categoria.idCategoria == producto.idCategoria}">
                                            selected
                                        </c:if>>
                                        <c:out value="${categoria.nombre}"/>
                                    </option>
                                </c:forEach>
                            </select>
                    </td>

                    <td>
                            <input type="number" name="precioIndividual"
                                   value="${producto.precioIndividual}"
                                   min="0" step="0.01" required>
                    </td>

                    <td>
                        <c:forEach var="categoria" items="${categorias}">
                            <c:if test="${categoria.idCategoria == producto.idCategoria}">
                                <c:out value="${categoria.nombre}"/>
                            </c:if>
                        </c:forEach>
                    </td>

                    <td class="estado"><c:out value="${producto.estado}"/></td>

                    <td class="acciones">
                            <button type="submit" class="editar">Guardar cambios</button>
                        </form>

                        <c:if test="${producto.estado == 'Activo'}">
                            <form action="${pageContext.request.contextPath}/catalogo/subproductos/desactivar"
                                  method="post"
                                  onsubmit="return confirm('¿Deseas desactivar este subproducto?');">
                                <input type="hidden" name="idProducto"
                                       value="${producto.idProducto}">
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
