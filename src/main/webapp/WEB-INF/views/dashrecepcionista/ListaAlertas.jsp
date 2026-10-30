<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Alertas de vencimiento</title>
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

        /* Sección de Filtro (Formulario de días) */
        .filter-section {
            background-color: #f8fdfe;
            border: 1px solid var(--cyan-light);
            border-radius: 8px;
            padding: 16px 20px;
            margin-bottom: 25px;
            display: flex;
            align-items: center;
        }

        .form-inline {
            display: flex;
            align-items: center;
            gap: 12px;
            flex-wrap: wrap;
        }

        .filter-text {
            font-weight: 500;
            color: var(--text-main);
            font-size: 15px;
        }

        input[type="number"] {
            width: 80px;
            padding: 10px;
            border: 1px solid var(--border-color);
            border-radius: 6px;
            background-color: var(--white);
            font-size: 15px;
            color: var(--text-main);
            text-align: center;
            transition: all 0.3s ease;
            outline: none;
        }

        input[type="number"]:focus {
            border-color: var(--cyan-primary);
            box-shadow: 0 0 0 3px rgba(0, 188, 212, 0.15);
        }

        .btn-submit {
            background-color: var(--cyan-primary);
            color: var(--white);
            border: none;
            padding: 10px 18px;
            font-size: 14px;
            font-weight: 600;
            border-radius: 6px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .btn-submit:hover {
            background-color: var(--cyan-dark);
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

        /* Colores de Fondo para cada Nivel de Alerta */
        .modern-table tbody tr.VENCIDA {
            background-color: #fff0f0;
        }
        .modern-table tbody tr.VENCIDA:hover {
            background-color: #ffe4e4;
        }

        .modern-table tbody tr.CRITICA {
            background-color: #fffde7;
        }
        .modern-table tbody tr.CRITICA:hover {
            background-color: #fff9c4;
        }

        .modern-table tbody tr.PROXIMA {
            background-color: #f4fbfb;
        }
        .modern-table tbody tr.PROXIMA:hover {
            background-color: #e0f7fa;
        }

        .modern-table tbody tr:last-child {
            border-bottom: none;
        }

        /* Badges de Nivel */
        .badge {
            display: inline-block;
            padding: 5px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 700;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .badge-vencida {
            background-color: #ffebee;
            color: #c62828;
            border: 1px solid #ffcdd2;
        }

        .badge-critica {
            background-color: #fff8e1;
            color: #f57f17;
            border: 1px solid #ffecb3;
        }

        .badge-proxima {
            background-color: #e0f7fa;
            color: #00838f;
            border: 1px solid #b2ebf2;
        }

        /* Estado Vacío */
        .empty-state {
            text-align: center;
            padding: 50px 20px;
            background-color: #f8fdfe;
            border: 1px dashed var(--cyan-primary);
            border-radius: 8px;
            color: var(--cyan-dark);
            font-weight: 500;
            font-size: 16px;
        }
    </style>
</head>
<body>

<div class="container">
    <h1 class="header-title">
        <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="var(--cyan-primary)" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"></path>
            <path d="M13.73 21a2 2 0 0 1-3.46 0"></path>
        </svg>
        Alertas de vencimiento
    </h1>

    <!-- Panel superior de filtros -->
    <div class="filter-section">
        <form method="get" action="${pageContext.request.contextPath}/alertas/Vencimiento" class="form-inline">
            <span class="filter-text">Avisar con</span>
            <input type="number" name="dias" value="${dias}" min="1" max="60" required>
            <span class="filter-text">días de anticipación</span>
            <button type="submit" class="btn-submit">
                <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="vertical-align: middle; margin-right: 4px;">
                    <path d="M21.5 2v6h-6M2.5 22v-6h6M2 11.5a10 10 0 0 1 18.8-4.3M22 12.5a10 10 0 0 1-18.8 4.2"></path>
                </svg>
                Actualizar
            </button>
        </form>
    </div>

    <!-- Contenido de la Tabla / Estado Vacío -->
    <c:choose>
        <c:when test="${empty alertas}">
            <div class="empty-state">
                <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="var(--cyan-primary)" stroke-width="1.5" style="margin-bottom:15px; opacity: 0.8;">
                    <circle cx="12" cy="12" r="10"></circle>
                    <polyline points="12 6 12 12 16 14"></polyline>
                </svg>
                <br>
                Excelente, no hay suscripciones por vencer en los próximos <strong>${dias}</strong> días.
            </div>
        </c:when>
        <c:otherwise>
            <div class="table-responsive">
                <table class="modern-table">
                    <thead>
                    <tr>
                        <th>Nivel</th>
                        <th>Cliente</th>
                        <th>DNI</th>
                        <th>Teléfono</th>
                        <th>Email</th>
                        <th>Plan</th>
                        <th>Fecha fin</th>
                        <th>Días restantes</th>
                        <th>Mensaje</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="a" items="${alertas}">
                        <tr class="${a.nivel}">
                            <td>
                                <c:set var="nivelUpper" value="${a.nivel.toString().toUpperCase()}" />
                                <span class="badge
                                        <c:choose>
                                            <c:when test="${nivelUpper == 'VENCIDA'}">badge-vencida</c:when>
                                            <c:when test="${nivelUpper == 'CRITICA'}">badge-critica</c:when>
                                            <c:otherwise>badge-proxima</c:otherwise>
                                        </c:choose>
                                    ">
                                        ${a.nivel}
                                </span>
                            </td>
                            <td><strong>${a.nombre_cliente}</strong></td>
                            <td>${a.dni}</td>
                            <td>${a.telefono}</td>
                            <td>${a.email}</td>
                            <td>${a.nombre_plan}</td>
                            <td>${a.fecha_fin}</td>
                            <td><strong>${a.dias_restantes}</strong></td>
                            <td style="max-width: 250px; line-height: 1.4;">${a.mensaje}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>