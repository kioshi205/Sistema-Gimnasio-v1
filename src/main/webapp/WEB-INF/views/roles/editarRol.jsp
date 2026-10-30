<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar rol | GymTeam</title>
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
            max-width: 760px;
            margin: 0 auto;
            padding: 36px 24px 56px;
        }

        .volver {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            color: var(--cyan-700);
            text-decoration: none;
            font-size: 14px;
            font-weight: 600;
            margin-bottom: 20px;
        }

        .volver:hover {
            color: var(--cyan-500);
        }

        .encabezado {
            margin-bottom: 24px;
        }

        .encabezado h1 {
            margin: 0 0 8px;
            font-size: 30px;
        }

        .encabezado p {
            margin: 0;
            color: var(--texto-suave);
            font-size: 15px;
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

        .tarjeta {
            background: var(--blanco);
            border: 1px solid var(--borde);
            border-radius: 18px;
            padding: 32px;
            box-shadow: 0 10px 30px rgba(8, 145, 178, 0.07);
        }

        .campo {
            margin-bottom: 24px;
        }

        .campo:last-of-type {
            margin-bottom: 0;
        }

        .campo label {
            display: block;
            font-size: 13px;
            font-weight: 700;
            letter-spacing: 0.4px;
            text-transform: uppercase;
            color: var(--cyan-700);
            margin-bottom: 9px;
        }

        .lectura {
            display: flex;
            align-items: center;
            gap: 12px;
            background: var(--cyan-50);
            border: 1px solid var(--cyan-100);
            border-radius: 12px;
            padding: 14px 18px;
        }

        .lectura-id {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            min-width: 34px;
            height: 34px;
            padding: 0 8px;
            border-radius: 10px;
            background: var(--blanco);
            border: 1px solid var(--cyan-100);
            color: var(--cyan-700);
            font-weight: 700;
            font-size: 14px;
        }

        .lectura-nombre {
            font-weight: 700;
            font-size: 15px;
            color: var(--texto);
        }

        .lectura-nota {
            margin-left: auto;
            font-size: 12px;
            color: var(--texto-suave);
        }

        textarea {
            width: 100%;
            min-height: 130px;
            resize: vertical;
            border: 1px solid var(--borde);
            border-radius: 12px;
            background: #fbfeff;
            padding: 14px 16px;
            font-family: inherit;
            font-size: 14px;
            line-height: 1.55;
            color: var(--texto);
            outline: none;
            transition: border-color 0.15s ease, box-shadow 0.15s ease;
        }

        textarea:focus {
            border-color: var(--cyan-500);
            box-shadow: 0 0 0 4px rgba(6, 182, 212, 0.14);
            background: var(--blanco);
        }

        .ayuda {
            display: flex;
            justify-content: space-between;
            gap: 12px;
            margin-top: 8px;
            font-size: 12px;
            color: var(--texto-suave);
        }

        .contador {
            font-weight: 700;
            color: var(--cyan-700);
            white-space: nowrap;
        }

        .contador.limite {
            color: #be123c;
        }

        .acciones {
            display: flex;
            justify-content: flex-end;
            gap: 12px;
            margin-top: 30px;
            padding-top: 24px;
            border-top: 1px solid #edf7fa;
        }

        .boton {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            gap: 8px;
            border-radius: 10px;
            padding: 12px 24px;
            font-size: 14px;
            font-weight: 700;
            text-decoration: none;
            border: 1px solid transparent;
            cursor: pointer;
            transition: all 0.15s ease;
            font-family: inherit;
        }

        .boton-guardar {
            background: var(--cyan-500);
            color: var(--blanco);
        }

        .boton-guardar:hover {
            background: var(--cyan-600);
            box-shadow: 0 6px 16px rgba(8, 145, 178, 0.25);
            transform: translateY(-1px);
        }

        .boton-cancelar {
            background: var(--blanco);
            color: var(--cyan-700);
            border-color: var(--borde);
        }

        .boton-cancelar:hover {
            background: var(--cyan-50);
            border-color: var(--cyan-100);
        }

        .pie {
            text-align: center;
            margin-top: 32px;
            color: #8aa8b3;
            font-size: 12px;
        }

        @media (max-width: 640px) {
            .barra-superior {
                padding: 0 18px;
            }

            .contenedor {
                padding: 24px 16px 48px;
            }

            .encabezado h1 {
                font-size: 24px;
            }

            .tarjeta {
                padding: 22px;
            }

            .lectura {
                flex-wrap: wrap;
            }

            .lectura-nota {
                margin-left: 0;
                width: 100%;
            }

            .acciones {
                flex-direction: column-reverse;
            }

            .boton {
                width: 100%;
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
    <span class="distintivo">Módulo Administrador</span>
</header>

<main class="contenedor">
    <a class="volver" href="${pageContext.request.contextPath}/roles">
        <span>&#8592;</span> Volver a la lista de roles
    </a>

    <div class="encabezado">
        <h1>Editar rol</h1>
        <p>Actualiza la descripción del perfil de acceso seleccionado.</p>
    </div>

    <c:if test="${not empty mensajeError}">
        <div class="alerta alerta-error">
            <span class="alerta-icono">!</span>
            <span><c:out value="${mensajeError}"/></span>
        </div>
    </c:if>

    <section class="tarjeta">
        <form action="${pageContext.request.contextPath}/roles/editar" method="post">
            <input type="hidden" name="idRol" value="${rol.idRol}">

            <div class="campo">
                <label>ID del rol</label>
                <div class="lectura">
                    <span class="lectura-id"><c:out value="${rol.idRol}"/></span>
                    <span class="lectura-nombre"><c:out value="${rol.nombreRol}"/></span>
                    <span class="lectura-nota">El nombre del rol no se puede modificar</span>
                </div>
            </div>

            <div class="campo">
                <label for="descripcion">Descripción</label>
                <textarea id="descripcion"
                          name="descripcion"
                          maxlength="${maxCaracteres}"
                          placeholder="Describe brevemente las funciones de este rol (opcional)"><c:out value="${rol.descripcion}"/></textarea>
                <div class="ayuda">
                    <span>Puede quedar vacía. Máximo ${maxCaracteres} caracteres.</span>
                    <span class="contador" id="contador">0 / ${maxCaracteres}</span>
                </div>
            </div>

            <div class="acciones">
                <a class="boton boton-cancelar" href="${pageContext.request.contextPath}/roles">Cancelar</a>
                <button class="boton boton-guardar" type="submit">Guardar cambios</button>
            </div>
        </form>
    </section>

    <p class="pie">Sistema de Gimnasio &middot; Gestión de roles (F21)</p>
</main>

<script>
    (function () {
        var texto = document.getElementById('descripcion');
        var contador = document.getElementById('contador');
        var maximo = parseInt(texto.getAttribute('maxlength'), 10);

        function actualizarContador() {
            var longitud = texto.value.length;
            contador.textContent = longitud + ' / ' + maximo;
            contador.classList.toggle('limite', longitud >= maximo);
        }

        texto.addEventListener('input', actualizarContador);
        actualizarContador();
    })();
</script>

</body>
</html>
