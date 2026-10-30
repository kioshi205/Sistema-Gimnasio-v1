<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Actualizar estado de suscripciones</title>
    <style>
        /* Variables de colores modernos */
        :root {
            --cyan-primary: #00BCD4;
            --cyan-dark: #0097A7;
            --cyan-light: #E0F7FA;
            --white: #FFFFFF;
            --bg-color: #f4f7f8;
            --text-main: #333333;
            --text-muted: #666666;
            --border-color: #E0E0E0;
            --danger-color: #ef5350;
            --danger-dark: #d32f2f;
            --success-color: #4caf50;
            --success-dark: #388e3c;
        }

        body {
            font-family: 'Segoe UI', Roboto, Helvetica, Arial, sans-serif;
            background-color: var(--bg-color);
            color: var(--text-main);
            margin: 0;
            padding: 40px 20px;
            display: flex;
            justify-content: center;
        }

        /* Contenedor Principal */
        .container {
            background-color: var(--white);
            width: 100%;
            max-width: 1250px;
            padding: 30px 40px;
            border-radius: 12px;
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
        }

        /* Encabezado */
        .header-title {
            color: var(--cyan-dark);
            margin-top: 0;
            margin-bottom: 25px;
            font-weight: 600;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        /* Alertas de Mensaje */
        .alert {
            padding: 12px 15px;
            border-radius: 6px;
            margin-bottom: 20px;
            font-weight: 500;
        }
        .alert-success {
            background-color: #e8f5e9;
            color: #2e7d32;
            border-left: 4px solid #4caf50;
        }
        .alert-error {
            background-color: #ffebee;
            color: #c62828;
            border-left: 4px solid #f44336;
        }

        /* Tabla Moderna */
        .table-responsive {
            overflow-x: auto;
        }

        .modern-table {
            width: 100%;
            border-collapse: collapse;
            background-color: var(--white);
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04);
        }

        .modern-table th,
        .modern-table td {
            padding: 14px 16px;
            text-align: left;
            font-size: 14px;
            vertical-align: middle;
        }

        .modern-table thead th {
            background-color: var(--cyan-light);
            color: var(--cyan-dark);
            font-weight: 600;
            border-bottom: 2px solid var(--cyan-primary);
            white-space: nowrap;
        }

        .modern-table tbody tr {
            border-bottom: 1px solid var(--border-color);
            transition: background-color 0.2s ease;
        }

        .modern-table tbody tr:hover {
            background-color: #f8fdfe;
        }

        .modern-table tbody tr:last-child {
            border-bottom: none;
        }

        .font-weight-bold {
            font-weight: 600;
        }

        /* Badges para el Estado / Situación */
        .badge {
            display: inline-block;
            padding: 4px 10px;
            border-radius: 12px;
            font-size: 12px;
            font-weight: 600;
            text-transform: capitalize;
        }

        .badge-activo {
            background-color: #e8f5e9;
            color: #2e7d32;
        }

        .badge-inactivo {
            background-color: #ffebee;
            color: #c62828;
        }

        .badge-default {
            background-color: #eceff1;
            color: #455a64;
        }

        /* Botones de Acción */
        .btn-action {
            color: var(--white);
            border: none;
            padding: 8px 14px;
            border-radius: 6px;
            font-size: 13px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            display: inline-flex;
            align-items: center;
            gap: 6px;
        }

        .btn-desactivar {
            background-color: var(--danger-color);
        }
        .btn-desactivar:hover {
            background-color: var(--danger-dark);
            box-shadow: 0 2px 6px rgba(211, 47, 47, 0.4);
        }

        .btn-activar {
            background-color: var(--success-color);
        }
        .btn-activar:hover {
            background-color: var(--success-dark);
            box-shadow: 0 2px 6px rgba(76, 175, 80, 0.4);
        }

        /* Estado Vacío */
        .empty-state {
            text-align: center;
            padding: 40px;
            color: var(--text-muted);
            font-style: italic;
        }

        .action-form {
            margin: 0;
        }
    </style>
</head>
<body>

<div class="container">
    <h1 class="header-title">
        <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="var(--cyan-primary)" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M20 11a8.1 8.1 0 0 0 -15.5 -2m-.5 -4v4h4"></path>
            <path d="M4 13a8.1 8.1 0 0 0 15.5 2m.5 4v-4h-4"></path>
        </svg>
        Actualizar estado de suscripciones
    </h1>

    <!-- Mensajes del sistema -->
    <c:if test="${not empty mensaje}">
        <div class="alert alert-success">${mensaje}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-error">${error}</div>
    </c:if>

    <div class="table-responsive">
        <table class="modern-table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Cliente</th>
                <th>DNI</th>
                <th>Plan</th>
                <th>Sede</th>
                <th>Inicio</th>
                <th>Fin</th>
                <th>Monto</th>
                <th>Situación</th>
                <th>Acción</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="s" items="${suscripciones}">
                <tr>
                    <td class="font-weight-bold">#${s.id_suscripcion}</td>
                    <td>${s.nombre} ${s.apellido}</td>
                    <td>${s.dni}</td>
                    <td>${s.nombre_plan}</td>
                    <td>${s.nombre_sede}</td>
                    <td>${s.fecha_inicio}</td>
                    <td>${s.fecha_fin}</td>
                    <td>S/ ${s.monto_pagado}</td>
                    <td>
                        <!-- Determinamos el color del badge basado en el estado_suscripcion -->
                        <c:set var="estadoUpper" value="${s.estado_suscripcion.toString().toUpperCase()}" />
                        <span class="badge
                            <c:choose>
                                <c:when test="${estadoUpper == 'ACTIVO'}">badge-activo</c:when>
                                <c:when test="${estadoUpper == 'INACTIVO' || estadoUpper == 'CANCELADO'}">badge-inactivo</c:when>
                                <c:otherwise>badge-default</c:otherwise>
                            </c:choose>
                        ">
                                ${s.situacion}
                        </span>
                    </td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/suscripciones/ActualizarEstado"
                              onsubmit="return confirm('¿Confirmar el cambio de estado para la suscripción #${s.id_suscripcion}?');"
                              class="action-form">
                            <input type="hidden" name="idSuscripcion" value="${s.id_suscripcion}">

                            <c:choose>
                                <c:when test="${estadoUpper == 'ACTIVO'}">
                                    <button type="submit" class="btn-action btn-desactivar">
                                        <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                            <circle cx="12" cy="12" r="10"></circle>
                                            <line x1="15" y1="9" x2="9" y2="15"></line>
                                            <line x1="9" y1="9" x2="15" y2="15"></line>
                                        </svg>
                                        Desactivar
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="submit" class="btn-action btn-activar">
                                        <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                            <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path>
                                            <polyline points="22 4 12 14.01 9 11.01"></polyline>
                                        </svg>
                                        Activar
                                    </button>
                                </c:otherwise>
                            </c:choose>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty suscripciones}">
                <tr>
                    <td colspan="10" class="empty-state">No hay suscripciones registradas en el sistema.</td>
                </tr>
            </c:if>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>