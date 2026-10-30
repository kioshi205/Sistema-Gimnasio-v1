<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contratar Suscripción</title>
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
            max-width: 900px;
            padding: 30px 40px;
            border-radius: 12px;
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
        }

        h1 {
            color: var(--cyan-dark);
            text-align: center;
            margin-top: 0;
            margin-bottom: 30px;
            font-weight: 600;
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

        /* Layout dividido */
        .grid-layout {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 40px;
            align-items: start;
        }

        @media (max-width: 768px) {
            .grid-layout {
                grid-template-columns: 1fr;
            }
        }

        /* Estilos del Formulario */
        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            font-weight: 500;
            margin-bottom: 8px;
            color: var(--text-main);
        }

        /* Agregado input[type="text"] para que comparta el estilo del select */
        select, input[type="text"] {
            width: 100%;
            padding: 12px;
            border: 1px solid var(--border-color);
            border-radius: 6px;
            background-color: var(--white);
            font-size: 14px;
            color: var(--text-main);
            transition: all 0.3s ease;
            box-sizing: border-box;
            outline: none;
        }

        select:focus, input[type="text"]:focus {
            border-color: var(--cyan-primary);
            box-shadow: 0 0 0 3px rgba(0, 188, 212, 0.15);
        }

        /* Estilo para el nombre validado del cliente */
        #nombre-cliente {
            display: block;
            margin-top: 6px;
            color: #2e7d32;
            font-weight: 600;
            font-size: 13.5px;
            min-height: 20px; /* Evita saltos visuales cuando está vacío */
        }

        .btn-submit {
            background-color: var(--cyan-primary);
            color: var(--white);
            border: none;
            padding: 12px 20px;
            width: 100%;
            font-size: 16px;
            font-weight: 600;
            border-radius: 6px;
            cursor: pointer;
            transition: background-color 0.3s ease;
            margin-top: 10px;
        }

        .btn-submit:hover {
            background-color: var(--cyan-dark);
        }

        /* Panel de Detalles (Lado derecho) */
        #panel-detalle {
            background-color: var(--bg-color);
            border-radius: 8px;
            padding: 25px;
            border-top: 4px solid var(--cyan-primary);
            min-height: 250px;
        }

        #detalle-vacio {
            color: var(--text-muted);
            text-align: center;
            margin-top: 40px;
            font-style: italic;
        }

        .detalle-plan h3 {
            color: var(--cyan-dark);
            margin-top: 0;
            margin-bottom: 5px;
        }

        .detalle-plan p {
            color: var(--text-muted);
            font-size: 14px;
            margin-bottom: 20px;
        }

        /* Tabla de Detalles */
        .modern-table {
            width: 100%;
            border-collapse: collapse;
            background-color: var(--white);
            border-radius: 6px;
            overflow: hidden;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
        }

        .modern-table th, .modern-table td {
            padding: 12px 15px;
            text-align: left;
        }

        .modern-table thead th {
            background-color: var(--cyan-light);
            color: var(--cyan-dark);
            font-weight: 600;
            font-size: 14px;
            border-bottom: 2px solid var(--cyan-primary);
        }

        .modern-table tbody tr {
            border-bottom: 1px solid var(--border-color);
        }

        .modern-table tbody tr:last-child {
            border-bottom: none;
        }

        .modern-table tfoot th {
            background-color: var(--white);
            color: var(--text-main);
            font-weight: bold;
            font-size: 16px;
            border-top: 2px solid var(--border-color);
        }

        .badge-gratis {
            background-color: #e8f5e9;
            color: #2e7d32;
            padding: 4px 8px;
            border-radius: 12px;
            font-size: 12px;
            font-weight: bold;
        }
    </style>
</head>
<body>

<div class="container">
    <h1>Contratar Suscripción</h1>

    <!-- Mensajes del sistema -->
    <c:if test="${not empty mensaje}">
        <div class="alert alert-success">${mensaje}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-error">${error}</div>
    </c:if>

    <div class="grid-layout">
        <!-- FORMULARIO -->
        <div class="form-section">
            <form method="post" action="${pageContext.request.contextPath}/suscripciones/Contratar">

                <!-- NUEVO CAMPO CLIENTE CON DATALIST -->
                <div class="form-group">
                    <label for="dni">DNI del cliente:</label>
                    <input type="text" name="dni" id="dni" list="lista-clientes" autocomplete="off" placeholder="Ingrese el DNI" required>
                    <span id="nombre-cliente"></span>
                    <datalist id="lista-clientes">
                        <c:forEach var="c" items="${clientes}">
                            <option value="${c.dni}" label="${c.nombre} ${c.apellido}"></option>
                        </c:forEach>
                    </datalist>
                </div>

                <div class="form-group">
                    <label for="idPlan">Plan:</label>
                    <select name="idPlan" id="idPlan" required>
                        <option value="">-- Seleccione un plan --</option>
                        <c:forEach var="p" items="${planes}">
                            <option value="${p.id_plan}">${p.nombre_plan}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="idSede">Sede:</label>
                    <select name="idSede" id="idSede" required>
                        <option value="">-- Seleccione una sede --</option>
                        <c:forEach var="s" items="${sedes}">
                            <option value="${s.id_sede}">${s.nombre_sede}</option>
                        </c:forEach>
                    </select>
                </div>

                <button type="submit" class="btn-submit">Contratar Suscripción</button>
            </form>
        </div>

        <!-- PANEL DE DETALLE DEL PLAN -->
        <div id="panel-detalle">
            <div id="detalle-vacio">
                <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="var(--cyan-primary)" stroke-width="1.5" style="margin-bottom:10px;">
                    <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
                    <polyline points="14 2 14 8 20 8"></polyline>
                    <line x1="16" y1="13" x2="8" y2="13"></line>
                    <line x1="16" y1="17" x2="8" y2="17"></line>
                    <polyline points="10 9 9 9 8 9"></polyline>
                </svg>
                <br>
                Seleccione un plan en el formulario para visualizar los detalles y beneficios.
            </div>

            <c:forEach var="p" items="${planes}">
                <div class="detalle-plan" id="detalle-${p.id_plan}" style="display:none;">
                    <h3>${p.nombre_plan}</h3>
                    <p>Duración del plan: <strong>${p.duracion_meses} mes/es</strong></p>

                    <table class="modern-table">
                        <thead>
                        <tr>
                            <th>Subproducto</th>
                            <th>Costo individual</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="d" items="${detallePlanes[p.id_plan]}">
                            <tr>
                                <td>${d.nombre_subproducto}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${d.precio_individual == 0}">
                                            <span class="badge-gratis">Gratis</span>
                                        </c:when>
                                        <c:otherwise>S/ ${d.precio_individual}</c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                        <tfoot>
                        <tr>
                            <th>Precio total del plan</th>
                            <th>S/ ${p.precio_total}</th>
                        </tr>
                        </tfoot>
                    </table>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

<script>
    // Lógica para mostrar detalles del plan
    document.getElementById('idPlan').addEventListener('change', function () {
        document.querySelectorAll('.detalle-plan').forEach(function (d) {
            d.style.display = 'none';
        });

        var seleccionado = document.getElementById('detalle-' + this.value);
        if (seleccionado) {
            seleccionado.style.display = 'block';
        }

        document.getElementById('detalle-vacio').style.display = seleccionado ? 'none' : 'block';
    });

    // Lógica para autocompletado y validación de DNI
    document.getElementById('dni').addEventListener('input', function () {
        var op = document.querySelector('#lista-clientes option[value="' + CSS.escape(this.value) + '"]');
        document.getElementById('nombre-cliente').textContent = op ? '✔ ' + op.label : '';
    });
</script>

</body>
</html>