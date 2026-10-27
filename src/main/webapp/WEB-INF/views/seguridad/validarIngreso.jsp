<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Seguridad - Validar Ingreso</title>
    <style>
        * { box-sizing: border-box; }
        body {
            margin: 0;
            font-family: Arial, Helvetica, sans-serif;
            background: #ffffff;
            color: #111827;
        }
        .ruta { padding: 12px 24px; color: #9ca3af; font-size: 13px; }

        /* Panel central */
        .panel {
            max-width: 560px;
            margin: 40px auto;
            background: #ffffff;
            border: 1px solid #e5e7eb;
            border-radius: 12px;
            padding: 32px;
            text-align: center;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.05);
        }
        .panel h1 { margin: 0 0 6px 0; font-size: 22px; }
        .panel .ayuda { margin: 0 0 24px 0; color: #6b7280; font-size: 13px; }

        .campo {
            width: 100%;
            padding: 18px;
            font-size: 28px;
            text-align: center;
            letter-spacing: 4px;
            border: 2px solid #d1d5db;
            border-radius: 10px;
            background: #ffffff;
            color: #111827;
            outline: none;
        }
        .campo:focus { border-color: #2563eb; }

        .boton {
            width: 100%;
            margin-top: 16px;
            padding: 16px;
            font-size: 17px;
            font-weight: bold;
            border: none;
            border-radius: 10px;
            background: #2563eb;
            color: #ffffff;
            cursor: pointer;
        }
        .boton:hover { background: #1d4ed8; }

        /* Respuesta visual: verde permitido / rojo denegado */
        .resultado {
            margin-top: 24px;
            padding: 20px;
            border-radius: 10px;
            font-size: 22px;
            font-weight: bold;
            border-left: 5px solid transparent;
        }
        .resultado span { display: block; margin-top: 8px; font-size: 16px; font-weight: normal; }
        .resultado.permitido { background: #ecfdf3; color: #166534; border-left-color: #16a34a; }
        .resultado.denegado { background: #fef2f2; color: #991b1b; border-left-color: #dc2626; }
        .resultado.aviso { background: #f8fafc; color: #475569; border-left-color: #94a3b8; font-size: 18px; }
    </style>
</head>
<body class="pagina-validar">

<jsp:include page="topbar.jsp"/>

<div class="ruta">Topbar Seguridad &gt; Control de Acceso &gt; Validar Ingreso</div>

<main class="panel">
    <h1>Control de Acceso por DNI</h1>
    <p class="ayuda">F19: Validar Ingreso por DNI</p>

    <form method="post" action="/seguridad/validar">
        <input class="campo"
               id="dni"
               name="dni"
               type="text"
               inputmode="numeric"
               maxlength="20"
               placeholder="DNI"
               autocomplete="off"
               value="${dni}"
               autofocus />
        <button class="boton" type="submit">Validar Ingreso (F19)</button>
    </form>

    <!-- Respuesta visual -->
    <c:if test="${estado == 'PERMITIDO'}">
        <div class="resultado permitido">
            PASO PERMITIDO
            <span>Bienvenido/a ${nombre}</span>
        </div>
    </c:if>

    <c:if test="${estado == 'VENCIDO'}">
        <div class="resultado denegado">
            PLAN VENCIDO - ACCESO DENEGADO
            <span>El socio ${nombre} tiene la cuota vencida</span>
        </div>
    </c:if>

    <c:if test="${estado == 'DENEGADO'}">
        <div class="resultado denegado">
            ACCESO DENEGADO
            <span>DNI no registrado en el gimnasio</span>
        </div>
    </c:if>

    <c:if test="${estado == 'SIN_DNI'}">
        <div class="resultado aviso">Ingrese un DNI para validar el acceso</div>
    </c:if>
</main>

<script>
    // Al mostrar una respuesta, limpiar el campo para el siguiente cliente.
    var campoDni = document.getElementById("dni");
    var estado = "${estado}";

    if (estado != "INICIO") {
        campoDni.value = "";
    }
    campoDni.focus();

    // Tecla F19: dispara la validacion del ingreso.
    document.addEventListener("keydown", function (evento) {
        if (evento.key === "F19") {
            evento.preventDefault();
            document.forms[0].submit();
        }
    });
</script>

</body>
</html>
