<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Roles del sistema | GymTeam</title>
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
        }

        .contenedor {
            max-width: 1060px;
            margin: 0 auto;
            padding: 36px 24px 56px;
        }

        .encabezado {
            display: flex;
            align-items: flex-end;
            justify-content: space-between;
            gap: 24px;
            flex-wrap: wrap;
            margin-bottom: 24px;
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
        }

        .contador-roles {
            background: var(--blanco);
            border: 1px solid var(--borde);
            border-radius: 14px;
            padding: 12px 18px;
            font-size: 14px;
            color: var(--texto-suave);
        }

        .contador-roles strong {
            color: var(--cyan-700);
            font-size: 18px;
        }

        .alerta {
            display: flex;
            align-items: center;
            gap: 10px;
            border-radius: 12px;
            padding: 14px 18px;
            margin-bottom: 20px;
            font-size: 14px;
            font-weight: 600;
        }

        .alerta-exito {
            background: var(--cyan-50);
            border: 1px solid var(--cyan-100);
            color: var(--cyan-700);
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
            background: var(--cyan-500);
            color: var(--blanco);
            font-size: 13px;
            flex-shrink: 0;
        }

        .alerta-error .alerta-icono {
            background: #e11d48;
        }

        .tarjeta {
            background: var(--blanco);
            border: 1px solid var(--borde);
            border-radius: 18px;
            overflow: hidden;
            box-shadow: 0 10px 30px rgba(8, 145, 178, 0.07);
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        thead {
            background: var(--cyan-50);
        }

        th {
            text-align: left;
            padding: 16px 22px;
            font-size: 12px;
            letter-spacing: 0.8px;
            text-transform: uppercase;
            color: var(--cyan-700);
            border-bottom: 1px solid var(--borde);
        }

        td {
            padding: 20px 22px;
            border-bottom: 1px solid #edf7fa;
            font-size: 14px;
            vertical-align: middle;
        }

        tbody tr:last-child td {
            border-bottom: none;
        }

        tbody tr:hover {
            background: #fbfeff;
        }

        .id-rol {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            min-width: 34px;
            height: 34px;
            padding: 0 8px;
            border-radius: 10px;
            background: var(--cyan-50);
            border: 1px solid var(--cyan-100);
            color: var(--cyan-700);
            font-weight: 700;
        }

        .nombre-rol {
            font-weight: 700;
            color: var(--texto);
        }

        .descripcion {
            color: var(--texto-suave);
            line-height: 1.5;
            max-width: 480px;
        }

        .sin-descripcion {
            color: #9db6c0;
            font-style: italic;
        }

        .boton {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            border-radius: 10px;
            padding: 10px 18px;
            font-size: 14px;
            font-weight: 700;
            text-decoration: none;
            border: 1px solid transparent;
            cursor: pointer;
            transition: all 0.15s ease;
            font-family: inherit;
        }

        .boton-editar {
            background: var(--cyan-500);
            color: var(--blanco);
        }

        .boton-editar:hover {
            background: var(--cyan-600);
            box-shadow: 0 6px 16px rgba(8, 145, 178, 0.25);
            transform: translateY(-1px);
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

        .nota strong {
            color: var(--cyan-700);
        }

        .nota-icono {
            color: var(--cyan-500);
            font-size: 18px;
            line-height: 1;
        }

        .pie {
            text-align: center;
            margin-top: 32px;
            color: #8aa8b3;
            font-size: 12px;
        }

        @media (max-width: 720px) {
            .barra-superior {
                padding: 0 18px;
            }

            .contenedor {
                padding: 24px 16px 48px;
            }

            .encabezado h1 {
                font-size: 24px;
            }

            th, td {
                padding: 14px 14px;
            }

            .descripcion {
                max-width: 220px;
            }

            .tabla-scroll {
                overflow-x: auto;
            }

            table {
                min-width: 620px;
            }
        }
    </style>
</head>
<body>

<main class="contenedor">
    <div class="encabezado">
        <div>
            <h1>Roles del sistema</h1>
            <p>Consulta los perfiles de acceso y ajusta la descripción de cada uno.</p>
        </div>
        <div class="contador-roles">
            <strong>${roles.size()}</strong> roles registrados
        </div>
    </div>

    <c:if test="${not empty mensajeExito}">
        <div class="alerta alerta-exito">
            <span class="alerta-icono">&#10003;</span>
            <span><c:out value="${mensajeExito}"/></span>
        </div>
    </c:if>

    <c:if test="${not empty mensajeError}">
        <div class="alerta alerta-error">
            <span class="alerta-icono">!</span>
            <span><c:out value="${mensajeError}"/></span>
        </div>
    </c:if>

    <section class="tarjeta">
        <div class="tabla-scroll">
            <table>
                <thead>
                <tr>
                    <th style="width: 90px;">ID</th>
                    <th style="width: 230px;">Nombre del rol</th>
                    <th>Descripción</th>
                    <th style="width: 130px;">Acción</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="rol" items="${roles}">
                    <tr>
                        <td><span class="id-rol"><c:out value="${rol.idRol}"/></span></td>
                        <td><span class="nombre-rol"><c:out value="${rol.nombreRol}"/></span></td>
                        <td>
                            <c:choose>
                                <c:when test="${empty rol.descripcion}">
                                    <span class="descripcion sin-descripcion">Sin descripción registrada</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="descripcion"><c:out value="${rol.descripcion}"/></span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <a class="boton boton-editar"
                               href="${pageContext.request.contextPath}/roles/editar/${rol.idRol}">
                                Editar
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </section>
</main>

</body>
</html>
