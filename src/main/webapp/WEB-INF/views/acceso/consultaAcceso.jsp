<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Consulta de acceso | GymTeam</title>
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
            --verde-600: #059669;
            --verde-500: #10b981;
            --rojo-600: #dc2626;
            --rojo-500: #ef4444;
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
            gap: 14px;
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

        .guardia {
            display: flex;
            align-items: center;
            gap: 9px;
            font-size: 13px;
            color: var(--texto-suave);
        }

        .guardia-avatar {
            width: 32px;
            height: 32px;
            border-radius: 50%;
            background: var(--cyan-50);
            border: 1px solid var(--cyan-100);
            color: var(--cyan-700);
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 12px;
            font-weight: 800;
        }

        .guardia strong {
            display: block;
            color: var(--texto);
            font-size: 13px;
            line-height: 1.2;
        }

        .guardia span {
            font-size: 11px;
        }

        .contenedor {
            max-width: 1000px;
            margin: 0 auto;
            padding: 36px 24px 56px;
        }

        .encabezado {
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
            max-width: 680px;
            line-height: 1.5;
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

        .tarjeta-formulario {
            background: var(--blanco);
            border: 1px solid var(--borde);
            border-radius: 18px;
            padding: 26px 28px;
            box-shadow: 0 10px 30px rgba(8, 145, 178, 0.07);
        }

        .tarjeta-titulo {
            display: flex;
            align-items: center;
            gap: 10px;
            margin-bottom: 20px;
            font-size: 13px;
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

        .campos {
            display: grid;
            grid-template-columns: minmax(0, 1fr) minmax(0, 1.25fr) auto;
            gap: 16px;
            align-items: end;
        }

        .campo label {
            display: block;
            font-size: 12px;
            font-weight: 700;
            letter-spacing: 0.4px;
            text-transform: uppercase;
            color: var(--cyan-700);
            margin-bottom: 8px;
        }

        .control {
            width: 100%;
            height: 48px;
            border: 1px solid var(--borde);
            border-radius: 12px;
            background: #fbfeff;
            padding: 0 15px;
            font-family: inherit;
            font-size: 15px;
            color: var(--texto);
            outline: none;
            transition: border-color 0.15s ease, box-shadow 0.15s ease, background 0.15s ease;
        }

        .control::placeholder {
            color: #a5bdc6;
        }

        .control:focus {
            border-color: var(--cyan-500);
            box-shadow: 0 0 0 4px rgba(6, 182, 212, 0.14);
            background: var(--blanco);
        }

        #dni {
            letter-spacing: 2px;
            font-weight: 600;
        }

        .ayuda-campo {
            margin-top: 7px;
            font-size: 11px;
            color: var(--texto-suave);
        }

        .boton {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            gap: 9px;
            height: 48px;
            padding: 0 28px;
            border-radius: 12px;
            font-family: inherit;
            font-size: 14px;
            font-weight: 700;
            text-decoration: none;
            border: 1px solid transparent;
            cursor: pointer;
            transition: all 0.15s ease;
            white-space: nowrap;
        }

        .boton-consultar {
            background: var(--cyan-500);
            color: var(--blanco);
            box-shadow: 0 6px 16px rgba(6, 182, 212, 0.22);
        }

        .boton-consultar:hover {
            background: var(--cyan-600);
            transform: translateY(-1px);
            box-shadow: 0 9px 20px rgba(8, 145, 178, 0.3);
        }

        .separador {
            display: flex;
            align-items: center;
            gap: 14px;
            margin: 26px 0 18px;
            color: #8aa8b3;
            font-size: 11px;
            font-weight: 700;
            letter-spacing: 1px;
            text-transform: uppercase;
        }

        .separador::before,
        .separador::after {
            content: "";
            height: 1px;
            background: var(--borde);
            flex: 1;
        }

        .resultado {
            border-radius: 20px;
            overflow: hidden;
            border: 1px solid transparent;
            box-shadow: 0 14px 34px rgba(8, 145, 178, 0.1);
            animation: aparecer 0.25s ease-out;
        }

        @keyframes aparecer {
            from {
                opacity: 0;
                transform: translateY(8px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .resultado.permitido {
            border-color: #a7f3d0;
        }

        .resultado.denegado {
            border-color: #fecaca;
        }

        .resultado-cabecera {
            display: flex;
            align-items: center;
            gap: 20px;
            padding: 28px 32px;
            color: var(--blanco);
        }

        .permitido .resultado-cabecera {
            background: linear-gradient(135deg, var(--verde-600), var(--verde-500));
        }

        .denegado .resultado-cabecera {
            background: linear-gradient(135deg, var(--rojo-600), var(--rojo-500));
        }

        .resultado-icono {
            width: 62px;
            height: 62px;
            border-radius: 50%;
            background: rgba(255, 255, 255, 0.18);
            border: 2px solid rgba(255, 255, 255, 0.35);
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 28px;
            font-weight: 800;
            flex-shrink: 0;
        }

        .resultado-textos {
            min-width: 0;
        }

        .resultado-etiqueta {
            font-size: 11px;
            font-weight: 700;
            letter-spacing: 1.4px;
            text-transform: uppercase;
            opacity: 0.82;
            margin-bottom: 4px;
        }

        .resultado-estado {
            font-size: 34px;
            line-height: 1.05;
            font-weight: 800;
            letter-spacing: 0.5px;
        }

        .resultado-motivo {
            margin-top: 8px;
            font-size: 14px;
            opacity: 0.95;
        }

        .resultado-cuerpo {
            background: var(--blanco);
            padding: 26px 32px 24px;
        }

        .datos-grid {
            display: grid;
            grid-template-columns: 1.3fr 1fr 1fr 1fr;
            gap: 18px;
        }

        .dato {
            min-width: 0;
        }

        .dato-etiqueta {
            font-size: 10px;
            font-weight: 700;
            letter-spacing: 0.9px;
            text-transform: uppercase;
            color: #8aa8b3;
            margin-bottom: 6px;
        }

        .dato-valor {
            font-size: 14px;
            font-weight: 700;
            color: var(--texto);
            line-height: 1.35;
            overflow-wrap: anywhere;
        }

        .dato-valor.suave {
            color: var(--texto-suave);
            font-weight: 600;
        }

        .sin-cliente {
            display: flex;
            align-items: center;
            gap: 10px;
            background: #fbfeff;
            border: 1px dashed var(--borde);
            border-radius: 12px;
            padding: 15px 18px;
            color: var(--texto-suave);
            font-size: 13px;
        }

        .resultado-pie {
            display: flex;
            align-items: center;
            justify-content: space-between;
            gap: 14px;
            flex-wrap: wrap;
            margin-top: 22px;
            padding-top: 18px;
            border-top: 1px solid #edf7fa;
            font-size: 12px;
            color: #8aa8b3;
        }

        .resultado-pie strong {
            color: var(--cyan-700);
        }

        .registro-ok {
            display: inline-flex;
            align-items: center;
            gap: 7px;
            color: var(--cyan-700);
            font-weight: 700;
        }

        .registro-ok::before {
            content: "";
            width: 7px;
            height: 7px;
            border-radius: 50%;
            background: var(--cyan-500);
        }

        .estado-vacio {
            background: var(--blanco);
            border: 1px dashed var(--borde);
            border-radius: 18px;
            padding: 28px 30px;
        }

        .estado-vacio h2 {
            margin: 0 0 6px;
            font-size: 16px;
            color: var(--texto);
        }

        .estado-vacio > p {
            margin: 0 0 20px;
            color: var(--texto-suave);
            font-size: 13px;
        }

        .pasos {
            display: grid;
            grid-template-columns: repeat(4, minmax(0, 1fr));
            gap: 14px;
            margin: 0;
            padding: 0;
            list-style: none;
        }

        .paso {
            background: #fbfeff;
            border: 1px solid #edf7fa;
            border-radius: 12px;
            padding: 14px 14px 15px;
        }

        .paso-numero {
            width: 26px;
            height: 26px;
            border-radius: 8px;
            background: var(--cyan-50);
            border: 1px solid var(--cyan-100);
            color: var(--cyan-700);
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 12px;
            font-weight: 800;
            margin-bottom: 9px;
        }

        .paso strong {
            display: block;
            font-size: 12px;
            color: var(--texto);
            margin-bottom: 3px;
        }

        .paso span {
            font-size: 11px;
            color: var(--texto-suave);
            line-height: 1.4;
        }

        .pie {
            text-align: center;
            margin-top: 32px;
            color: #8aa8b3;
            font-size: 12px;
        }

        @media (max-width: 840px) {
            .campos {
                grid-template-columns: 1fr 1fr;
            }

            .campos .campo-dni,
            .campos .campo-sede {
                grid-column: span 1;
            }

            .campos .campo-boton {
                grid-column: 1 / -1;
            }

            .boton-consultar {
                width: 100%;
            }

            .datos-grid {
                grid-template-columns: 1fr 1fr;
            }

            .pasos {
                grid-template-columns: 1fr 1fr;
            }
        }

        @media (max-width: 600px) {
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

            .tarjeta-formulario {
                padding: 20px;
            }

            .campos {
                grid-template-columns: 1fr;
            }

            .resultado-cabecera {
                padding: 22px;
                gap: 15px;
            }

            .resultado-icono {
                width: 52px;
                height: 52px;
                font-size: 24px;
            }

            .resultado-estado {
                font-size: 27px;
            }

            .resultado-cuerpo {
                padding: 22px;
            }

            .datos-grid {
                grid-template-columns: 1fr;
            }

            .pasos {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>

<main class="contenedor">
    <div class="encabezado">
        <h1>Consulta de acceso</h1>
        <p>Verifica en segundos si un cliente puede ingresar al gimnasio. Toda consulta queda registrada en el historial de accesos.</p>
    </div>

    <c:if test="${not empty mensajeError}">
        <div class="alerta alerta-error">
            <span class="alerta-icono">!</span>
            <span><c:out value="${mensajeError}"/></span>
        </div>
    </c:if>

    <section class="tarjeta-formulario">
        <div class="tarjeta-titulo">Datos de la consulta</div>
        <form action="${pageContext.request.contextPath}/acceso/consultar" method="post">
            <div class="campos">
                <div class="campo campo-dni">
                    <label for="dni">DNI del cliente</label>
                    <input class="control"
                           type="text"
                           id="dni"
                           name="dni"
                           maxlength="8"
                           pattern="\d{8}"
                           inputmode="numeric"
                           autocomplete="off"
                           placeholder="Ej. 45920182"
                           value="<c:out value='${dniIngresado}'/>"
                           required>
                    <div class="ayuda-campo">Exactamente 8 dígitos</div>
                </div>

                <div class="campo campo-sede">
                    <label for="idSede">Sede donde se encuentra</label>
                    <select class="control" id="idSede" name="idSede" required>
                        <option value="0" disabled
                                <c:if test="${empty sedeSeleccionada or sedeSeleccionada == 0}">selected</c:if>>
                            Seleccione su sede
                        </option>
                        <c:forEach var="sede" items="${sedes}">
                            <option value="${sede.idSede}"
                                    <c:if test="${sedeSeleccionada == sede.idSede}">selected</c:if>>
                                <c:out value="${sede.nombreSede}"/>
                            </option>
                        </c:forEach>
                    </select>
                    <div class="ayuda-campo">Solo sedes activas</div>
                </div>

                <div class="campo campo-boton">
                    <button class="boton boton-consultar" type="submit">Consultar</button>
                </div>
            </div>
        </form>
    </section>

    <c:if test="${not empty resultado}">
        <div class="separador">Resultado</div>

        <section class="resultado ${resultado.permitido ? 'permitido' : 'denegado'}">
                <div class="resultado-cabecera">
                    <div class="resultado-icono">
                        <c:choose>
                            <c:when test="${resultado.permitido}">&#10003;</c:when>
                            <c:otherwise>&#10005;</c:otherwise>
                        </c:choose>
                    </div>
                    <div class="resultado-textos">
                        <div class="resultado-etiqueta">Acceso ${resultado.resultado}</div>
                        <div class="resultado-estado">
                            <c:choose>
                                <c:when test="${resultado.permitido}">PERMITIDO</c:when>
                                <c:otherwise>DENEGADO</c:otherwise>
                            </c:choose>
                        </div>
                        <div class="resultado-motivo">
                            Motivo: <c:out value="${resultado.motivo}"/>
                        </div>
                    </div>
                </div>

                <div class="resultado-cuerpo">
                    <c:choose>
                        <c:when test="${resultado.clienteExiste}">
                            <div class="datos-grid">
                                <div class="dato">
                                    <div class="dato-etiqueta">Cliente</div>
                                    <div class="dato-valor"><c:out value="${resultado.nombreCliente}"/></div>
                                </div>
                                <div class="dato">
                                    <div class="dato-etiqueta">Plan</div>
                                    <div class="dato-valor">
                                        <c:choose>
                                            <c:when test="${empty resultado.nombrePlan}">Sin plan registrado</c:when>
                                            <c:otherwise><c:out value="${resultado.nombrePlan}"/></c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                                <div class="dato">
                                    <div class="dato-etiqueta">Sede base</div>
                                    <div class="dato-valor suave">
                                        <c:choose>
                                            <c:when test="${empty resultado.nombreSedeBase}">Sin sede registrada</c:when>
                                            <c:otherwise><c:out value="${resultado.nombreSedeBase}"/></c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                                <div class="dato">
                                    <div class="dato-etiqueta">Fecha de vencimiento</div>
                                    <div class="dato-valor"><c:out value="${resultado.fechaVencimientoTexto}"/></div>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="sin-cliente">
                                <span>No se encontraron datos de cliente para el DNI consultado.</span>
                            </div>
                        </c:otherwise>
                    </c:choose>

                    <div class="resultado-pie">
                        <span>
                            Consulta: <strong>DNI <c:out value="${resultado.dni}"/></strong>
                            &middot; Sede <strong><c:out value="${resultado.nombreSedeConsultada}"/></strong>
                            &middot; <c:out value="${resultado.fechaHoraRegistroTexto}"/>
                        </span>
                        <span class="registro-ok">Registrado en el historial</span>
                    </div>
                </div>
            </section>
    </c:if>
</main>

<script>
    (function () {
        var dni = document.getElementById('dni');

        dni.addEventListener('input', function () {
            this.value = this.value.replace(/\D/g, '').slice(0, 8);
        });

        if (!document.querySelector('.resultado')) {
            dni.focus();
        }
    })();
</script>

</body>
</html>
