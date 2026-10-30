<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Suscripciones</title>
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
            max-width: 1100px;
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

        /* Badges para el Estado */
        .badge {
            display: inline-block;
            padding: 4px 10px;
            border-radius: 12px;
            font-size: 12px;
            font-weight: 600;
            text-transform: capitalize;
        }

        .badge-activo,
        .badge-activa {
            background-color: #e8f5e9;
            color: #2e7d32;
        }

        .badge-pendiente {
            background-color: #fff8e1;
            color: #f57f17;
        }

        .badge-cancelado,
        .badge-inactivo,
        .badge-vencido {
            background-color: #ffebee;
            color: #c62828;
        }

        .badge-default {
            background-color: #eceff1;
            color: #455a64;
        }

        /* Estado Vacío */
        .empty-state {
            text-align: center;
            padding: 40px;
            color: var(--text-muted);
            font-style: italic;
        }
    </style>
</head>
<body>

<div class="container">
    <h1 class="header-title">
        <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="var(--cyan-primary)" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
            <polyline points="14 2 14 8 20 8"></polyline>
            <line x1="16" y1="13" x2="8" y2="13"></line>
            <line x1="16" y1="17" x2="8" y2="17"></line>
            <polyline points="10 9 9 9 8 9"></polyline>
        </svg>
        Suscripciones Registradas
    </h1>

    <div class="table-responsive">
        <table class="modern-table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Cliente</th>
                <th>Plan</th>
                <th>Sede</th>
                <th>Inicio</th>
                <th>Fin</th>
                <th>Monto</th>
                <th>Estado</th>
            </tr>
            </thead>
            <tbody>
            <!-- Iteramos sobre la lista 'suscripciones' -->
            <c:forEach items="${suscripciones}" var="suscripcion">
                <tr>
                    <td class="font-weight-bold">#${suscripcion.id_suscripcion}</td>
                    <td>${suscripcion.id_cliente}</td>
                    <td>${suscripcion.id_plan}</td>
                    <td>${suscripcion.id_sede}</td>
                    <td>${suscripcion.fecha_inicio}</td>
                    <td>${suscripcion.fecha_fin}</td>
                    <td>S/ ${suscripcion.monto_pagado}</td>
                    <td>
                        <c:set var="estadoLower" value="${suscripcion.estado.toString().toLowerCase()}" />
                        <span class="badge
                            <c:choose>
                                <c:when test="${estadoLower == 'activo' || estadoLower == 'activa'}">badge-activo</c:when>
                                <c:when test="${estadoLower == 'pendiente'}">badge-pendiente</c:when>
                                <c:when test="${estadoLower == 'cancelado' || estadoLower == 'inactivo' || estadoLower == 'vencido'}">badge-cancelado</c:when>
                                <c:otherwise>badge-default</c:otherwise>
                            </c:choose>
                        ">
                                ${suscripcion.estado}
                        </span>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty suscripciones}">
                <tr>
                    <td colspan="8" class="empty-state">No hay suscripciones registradas en el sistema.</td>
                </tr>
            </c:if>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>