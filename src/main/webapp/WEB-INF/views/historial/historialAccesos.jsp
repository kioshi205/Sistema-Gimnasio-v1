<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Historial de accesos | GymTeam</title>
    <style>
        :root {
            --cyan-700: #0e7490;
            --cyan-600: #0891b2;
            --cyan-500: #06b6d4;
            --cyan-100: #cffafe;
            --cyan-50: #ecfeff;
            --texto: #164e63;
            --texto-suave: #5b7c8a;
            --borde: #d7eef4;
            --blanco: #ffffff;
            --fondo: #f4fbfd;
            --verde-700: #047857;
            --verde-600: #059669;
            --verde-100: #d1fae5;
            --verde-50: #ecfdf5;
            --rojo-700: #b91c1c;
            --rojo-600: #dc2626;
            --rojo-100: #fee2e2;
            --rojo-50: #fef2f2;
        }

        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            min-height: 100vh;
            background: var(--fondo);
            color: var(--texto);
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
        }

        .barra-superior {
            background: var(--blanco);
            border-bottom: 3px solid var(--cyan-500);
            padding: 0 32px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            gap: 16px;
            min-height: 72px;
            box-shadow: 0 2px 12px rgba(8, 145, 178, 0.08);
        }

        .marca {
            display: flex;
            align-items: center;
            gap: 12px;
            font-weight: 700;
            font-size: 20px;
            letter-spacing: 0.3px;
            color: var(--cyan-700);
            white-space: nowrap;
        }

        .marca-icono {
            width: 38px;
            height: 38px;
            border-radius: 12px;
            background: linear-gradient(135deg, var(--cyan-500), var(--cyan-700));
            color: var(--blanco);
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 18px;
            font-weight: 800;
        }

        .barra-derecha {
            display: flex;
            align-items: center;
            gap: 12px;
            flex-wrap: wrap;
            justify-content: flex-end;
        }

        .nav-modulo {
            display: flex;
            gap: 4px;
            padding: 4px;
            background: var(--cyan-50);
            border: 1px solid var(--cyan-100);
            border-radius: 999px;
        }

        .nav-modulo a {
            padding: 7px 14px;
            border-radius: 999px;
            color: var(--cyan-700);
            text-decoration: none;
            font-size: 12px;
            font-weight: 700;
            transition: all 0.15s ease;
        }

        .nav-modulo a:hover {
            background: var(--blanco);
        }

        .nav-modulo a.activo {
            background: var(--cyan-500);
            color: var(--blanco);
            box-shadow: 0 4px 10px rgba(6, 182, 212, 0.28);
        }

        .distintivo {
            background: var(--cyan-50);
            border: 1px solid var(--cyan-100);
            color: var(--cyan-700);
            font-size: 12px;
            font-weight: 700;
            letter-spacing: 0.6px;
            text-transform: uppercase;
            padding: 7px 14px;
            border-radius: 999px;
            white-space: nowrap;
        }

        .contenedor {
            max-width: 1120px;
            margin: 0 auto;
            padding: 36px 24px 56px;
        }

        .encabezado {
            display: flex;
            align-items: flex-end;
            justify-content: space-between;
            gap: 20px;
            flex-wrap: wrap;
            margin-bottom: 22px;
        }

        .encabezado h1 {
            margin: 0 0 8px;
            font-size: 30px;
            color: var(--texto);
        }

        .encabezado p {
            margin: 0;
            color: var(--texto-suave);
            font-size: 15px;
            max-width: 700px;
            line-height: 1.5;
        }

        .etiqueta-lectura {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            background: var(--blanco);
            border: 1px solid var(--borde);
            border-radius: 999px;
            padding: 9px 15px;
            color: var(--texto-suave);
            font-size: 12px;
            font-weight: 700;
            white-space: nowrap;
        }

        .etiqueta-lectura::before {
            content: "";
            width: 7px;
            height: 7px;
            border-radius: 50%;
            background: var(--cyan-500);
        }

        .resumen {
            display: grid;
            grid-template-columns: repeat(3, minmax(0, 1fr));
            gap: 14px;
            margin-bottom: 20px;
        }

        .resumen-item {
            background: var(--blanco);
            border: 1px solid var(--borde);
            border-radius: 14px;
            padding: 16px 18px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            gap: 12px;
        }

        .resumen-texto span {
            display: block;
            font-size: 11px;
            font-weight: 700;
            letter-spacing: 0.7px;
            text-transform: uppercase;
            color: #8aa8b3;
            margin-bottom: 4px;
        }

        .resumen-texto strong {
            font-size: 22px;
            color: var(--texto);
            line-height: 1;
        }

        .resumen-punto {
            width: 38px;
            height: 38px;
            border-radius: 11px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 16px;
            font-weight: 800;
            flex-shrink: 0;
        }

        .punto-total {
            background: var(--cyan-50);
            border: 1px solid var(--cyan-100);
            color: var(--cyan-700);
        }

        .punto-permitido {
            background: var(--verde-50);
            border: 1px solid var(--verde-100);
            color: var(--verde-700);
        }

        .punto-denegado {
            background: var(--rojo-50);
            border: 1px solid var(--rojo-100);
            color: var(--rojo-700);
        }

        .alerta {
            display: flex;
            align-items: center;
            gap: 10px;
            border-radius: 12px;
            padding: 14px 18px;
            margin-bottom: 18px;
            font-size: 14px;
            font-weight: 600;
        }

        .alerta-error {
            background: #fff1f2;
            border: 1px solid #fecdd3;
            color: #be123c;
        }

        .alerta-icono {
            width: 24px;
            height: 24px;
            border-radius: 50%;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            background: #e11d48;
            color: var(--blanco);
            font-size: 13px;
            flex-shrink: 0;
        }

        .tarjeta-filtros {
            background: var(--blanco);
            border: 1px solid var(--borde);
            border-radius: 18px;
            padding: 22px 24px;
            box-shadow: 0 10px 30px rgba(8, 145, 178, 0.06);
            margin-bottom: 20px;
        }

        .tarjeta-titulo {
            display: flex;
            align-items: center;
            gap: 10px;
            margin-bottom: 18px;
            font-size: 12px;
            font-weight: 700;
            letter-spacing: 0.7px;
            text-transform: uppercase;
            color: var(--cyan-700);
        }

        .tarjeta-titulo::before {
            content: "";
            width: 6px;
            height: 6px;
            border-radius: 50%;
            background: var(--cyan-500);
            box-shadow: 0 0 0 4px var(--cyan-50);
        }

        .filtros {
            display: grid;
            grid-template-columns: 1fr 1fr 1.15fr 1fr auto;
            gap: 14px;
            align-items: end;
        }

        .campo label {
            display: block;
            font-size: 11px;
            font-weight: 700;
            letter-spacing: 0.4px;
            text-transform: uppercase;
            color: var(--cyan-700);
            margin-bottom: 7px;
        }

        .control {
            width: 100%;
            height: 44px;
            border: 1px solid var(--borde);
            border-radius: 11px;
            background: #fbfeff;
            padding: 0 13px;
            font-family: inherit;
            font-size: 14px;
            color: var(--texto);
            outline: none;
            color-scheme: light;
            transition: border-color 0.15s ease, box-shadow 0.15s ease, background 0.15s ease;
        }

        .control:focus {
            border-color: var(--cyan-500);
            box-shadow: 0 0 0 4px rgba(6, 182, 212, 0.14);
            background: var(--blanco);
        }

        .acciones-filtro {
            display: flex;
            gap: 8px;
        }

        .boton {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            gap: 7px;
            height: 44px;
            padding: 0 20px;
            border-radius: 11px;
            font-family: inherit;
            font-size: 13px;
            font-weight: 700;
            text-decoration: none;
            border: 1px solid transparent;
            cursor: pointer;
            transition: all 0.15s ease;
            white-space: nowrap;
        }

        .boton-filtrar {
            background: var(--cyan-500);
            color: var(--blanco);
            box-shadow: 0 5px 14px rgba(6, 182, 212, 0.22);
        }

        .boton-filtrar:hover {
            background: var(--cyan-600);
            transform: translateY(-1px);
        }

        .boton-limpiar {
            background: var(--blanco);
            color: var(--cyan-700);
            border-color: var(--borde);
        }

        .boton-limpiar:hover {
            background: var(--cyan-50);
            border-color: var(--cyan-100);
        }

        .tarjeta-tabla {
            background: var(--blanco);
            border: 1px solid var(--borde);
            border-radius: 18px;
            overflow: hidden;
            box-shadow: 0 10px 30px rgba(8, 145, 178, 0.07);
        }

        .tabla-cabecera {
            display: flex;
            align-items: center;
            justify-content: space-between;
            gap: 14px;
            flex-wrap: wrap;
            padding: 18px 22px;
            border-bottom: 1px solid #edf7fa;
        }

        .tabla-cabecera h2 {
            margin: 0;
            font-size: 15px;
            color: var(--texto);
        }

        .tabla-cabecera span {
            font-size: 12px;
            color: var(--texto-suave);
        }

        .tabla-scroll {
            overflow-x: auto;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            min-width: 900px;
        }

        thead {
            background: var(--cyan-50);
        }

        th {
            text-align: left;
            padding: 13px 18px;
            font-size: 11px;
            letter-spacing: 0.7px;
            text-transform: uppercase;
            color: var(--cyan-700);
            border-bottom: 1px solid var(--borde);
            white-space: nowrap;
        }

        td {
            padding: 15px 18px;
            border-bottom: 1px solid #edf7fa;
            font-size: 13px;
            vertical-align: middle;
        }

        tbody tr:last-child td {
            border-bottom: none;
        }

        tbody tr:hover {
            background: #fbfeff;
        }

        .fecha-dia {
            display: block;
            font-weight: 700;
            color: var(--texto);
        }

        .fecha-hora {
            display: block;
            margin-top: 2px;
            font-size: 11px;
            color: #8aa8b3;
        }

        .dni {
            font-family: "Consolas", "Courier New", monospace;
            font-size: 13px;
            font-weight: 700;
            color: var(--texto);
            letter-spacing: 0.5px;
        }

        .cliente {
            font-weight: 600;
            color: var(--texto);
        }

        .no-registrado {
            color: #9db6c0;
            font-style: italic;
            font-weight: 500;
        }

        .sede {
            color: var(--texto-suave);
        }

        .resultado {
            display: inline-flex;
            align-items: center;
            gap: 6px;
            padding: 5px 10px;
            border-radius: 999px;
            font-size: 10px;
            font-weight: 800;
            letter-spacing: 0.7px;
            text-transform: uppercase;
            white-space: nowrap;
        }

        .resultado::before {
            content: "";
            width: 6px;
            height: 6px;
            border-radius: 50%;
            background: currentColor;
        }

        .resultado.permitido {
            background: var(--verde-50);
            border: 1px solid var(--verde-100);
            color: var(--verde-700);
        }

        .resultado.denegado {
            background: var(--rojo-50);
            border: 1px solid var(--rojo-100);
            color: var(--rojo-700);
        }

        .motivo {
            color: var(--texto-suave);
            line-height: 1.45;
            min-width: 190px;
        }

        .sin-resultados {
            padding: 54px 24px;
            text-align: center;
        }

        .sin-resultados-icono {
            width: 54px;
            height: 54px;
            border-radius: 16px;
            background: var(--cyan-50);
            border: 1px solid var(--cyan-100);
            color: var(--cyan-700);
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 auto 14px;
            font-size: 24px;
        }

        .sin-resultados h3 {
            margin: 0 0 6px;
            font-size: 16px;
            color: var(--texto);
        }

        .sin-resultados p {
            margin: 0 0 18px;
            color: var(--texto-suave);
            font-size: 13px;
        }

        .nota {
            display: flex;
            gap: 12px;
            align-items: flex-start;
            margin-top: 18px;
            padding: 16px 20px;
            background: var(--blanco);
            border: 1px dashed var(--cyan-100);
            border-radius: 14px;
            color: var(--texto-suave);
            font-size: 13px;
            line-height: 1.55;
        }

        .nota-icono {
            color: var(--cyan-500);
            font-size: 17px;
            line-height: 1.2;
        }

        .nota strong {
            color: var(--cyan-700);
        }

        .pie {
            text-align: center;
            margin-top: 32px;
            color: #8aa8b3;
            font-size: 12px;
        }

        @media (max-width: 980px) {
            .filtros {
                grid-template-columns: 1fr 1fr;
            }

            .filtros .campo-sede,
            .filtros .campo-resultado {
                grid-column: span 1;
            }

            .filtros .campo-acciones {
                grid-column: 1 / -1;
            }

            .acciones-filtro .boton {
                flex: 1;
            }
        }

        @media (max-width: 700px) {
            .barra-superior {
                padding: 12px 16px;
                flex-wrap: wrap;
            }

            .barra-derecha {
                width: 100%;
                justify-content: space-between;
            }

            .contenedor {
                padding: 24px 16px 48px;
            }

            .encabezado h1 {
                font-size: 24px;
            }

            .etiqueta-lectura {
                display: none;
            }

            .resumen {
                grid-template-columns: 1fr;
            }

            .filtros {
                grid-template-columns: 1fr;
            }

            .filtros .campo-acciones {
                grid-column: auto;
            }
        }
    </style>
</head>
<body>

<header class="barra-superior">
    <div class="marca">
        <span class="marca-icono">G</span>
        <span>GymTeam</span>
    </div>
    <div class="barra-derecha">
        <nav class="nav-modulo">
            <a href="${pageContext.request.contextPath}/acceso">Consulta</a>
            <a class="activo" href="${pageContext.request.contextPath}/historial">Historial</a>
        </nav>
        <span class="distintivo">Módulo Seguridad</span>
    </div>
</header>

<main class="contenedor">
    <div class="encabezado">
        <div>
            <h1>Historial de accesos</h1>
            <p>Revisa todas las consultas realizadas en puerta, incluyendo los accesos rechazados y el motivo de cada decisión.</p>
        </div>
        <span class="etiqueta-lectura">Solo lectura</span>
    </div>

    <div class="resumen">
        <div class="resumen-item">
            <div class="resumen-texto">
                <span>Registros encontrados</span>
                <strong>${totalRegistros}</strong>
            </div>
            <div class="resumen-punto punto-total">#</div>
        </div>
        <div class="resumen-item">
            <div class="resumen-texto">
                <span>Accesos permitidos</span>
                <strong>${totalPermitidos}</strong>
            </div>
            <div class="resumen-punto punto-permitido">&#10003;</div>
        </div>
        <div class="resumen-item">
            <div class="resumen-texto">
                <span>Accesos denegados</span>
                <strong>${totalDenegados}</strong>
            </div>
            <div class="resumen-punto punto-denegado">&#10005;</div>
        </div>
    </div>

    <c:if test="${not empty mensajeError}">
        <div class="alerta alerta-error">
            <span class="alerta-icono">!</span>
            <span><c:out value="${mensajeError}"/></span>
        </div>
    </c:if>

    <section class="tarjeta-filtros">
        <div class="tarjeta-titulo">Filtros de búsqueda</div>
        <form action="${pageContext.request.contextPath}/historial" method="get">
            <div class="filtros">
                <div class="campo campo-desde">
                    <label for="desde">Desde</label>
                    <input class="control" type="date" id="desde" name="desde" value="${desde}">
                </div>

                <div class="campo campo-hasta">
                    <label for="hasta">Hasta</label>
                    <input class="control" type="date" id="hasta" name="hasta" value="${hasta}">
                </div>

                <div class="campo campo-sede">
                    <label for="idSede">Sede</label>
                    <select class="control" id="idSede" name="idSede">
                        <option value="0" <c:if test="${idSedeSeleccionada == 0}">selected</c:if>>
                            Todas las sedes
                        </option>
                        <c:forEach var="sede" items="${sedes}">
                            <option value="${sede.idSede}"
                                    <c:if test="${idSedeSeleccionada == sede.idSede}">selected</c:if>>
                                <c:out value="${sede.nombreSede}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="campo campo-resultado">
                    <label for="resultado">Resultado</label>
                    <select class="control" id="resultado" name="resultado">
                        <option value="TODOS" <c:if test="${resultadoSeleccionado == 'TODOS'}">selected</c:if>>
                            Todos
                        </option>
                        <option value="PERMITIDO" <c:if test="${resultadoSeleccionado == 'PERMITIDO'}">selected</c:if>>
                            Permitido
                        </option>
                        <option value="DENEGADO" <c:if test="${resultadoSeleccionado == 'DENEGADO'}">selected</c:if>>
                            Denegado
                        </option>
                    </select>
                </div>

                <div class="campo campo-acciones">
                    <div class="acciones-filtro">
                        <a class="boton boton-limpiar" href="${pageContext.request.contextPath}/historial">Limpiar</a>
                        <button class="boton boton-filtrar" type="submit">Filtrar</button>
                    </div>
                </div>
            </div>
        </form>
    </section>

    <section class="tarjeta-tabla">
        <div class="tabla-cabecera">
            <h2>Consultas registradas</h2>
            <span>${totalRegistros} registro(s) &middot; ordenados del más reciente al más antiguo</span>
        </div>

        <c:choose>
            <c:when test="${empty marcaciones}">
                <div class="sin-resultados">
                    <div class="sin-resultados-icono">&#9906;</div>
                    <h3>No hay registros para estos filtros</h3>
                    <p>Prueba con otro rango de fechas, sede o resultado.</p>
                    <a class="boton boton-limpiar" href="${pageContext.request.contextPath}/historial">Limpiar filtros</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="tabla-scroll">
                    <table>
                        <thead>
                        <tr>
                            <th style="width: 130px;">Fecha y hora</th>
                            <th style="width: 115px;">DNI consultado</th>
                            <th style="width: 190px;">Cliente</th>
                            <th style="width: 140px;">Sede</th>
                            <th style="width: 120px;">Resultado</th>
                            <th>Motivo</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="marcacion" items="${marcaciones}">
                            <tr>
                                <td>
                                    <span class="fecha-dia"><c:out value="${marcacion.fechaTexto}"/></span>
                                    <span class="fecha-hora"><c:out value="${marcacion.horaTexto}"/></span>
                                </td>
                                <td><span class="dni"><c:out value="${marcacion.dniConsultado}"/></span></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${empty marcacion.nombreCliente}">
                                            <span class="no-registrado">No registrado</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="cliente"><c:out value="${marcacion.nombreCliente}"/></span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td><span class="sede"><c:out value="${marcacion.nombreSede}"/></span></td>
                                <td>
                                    <span class="resultado ${marcacion.permitido ? 'permitido' : 'denegado'}">
                                        <c:out value="${marcacion.resultado}"/>
                                    </span>
                                </td>
                                <td><span class="motivo"><c:out value="${marcacion.motivo}"/></span></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
    </section>

    <div class="nota">
        <span class="nota-icono">&#9432;</span>
        <span>
            <strong>Historial de solo lectura.</strong>
            Los registros de MARCACION_ACCESO no se editan ni se eliminan. Cada consulta realizada en la pantalla de acceso se agrega automáticamente.
        </span>
    </div>

    <p class="pie">Sistema de Gimnasio &middot; Historial de accesos (F33)</p>
</main>

</body>
</html>
