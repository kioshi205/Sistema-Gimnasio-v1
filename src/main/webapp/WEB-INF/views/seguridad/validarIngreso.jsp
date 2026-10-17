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
            background: #12141a;
            color: #f2f4f8;
        }
        /* Barra superior de seguridad */
        .topbar {
            background: #1b1e27;
            padding: 14px 24px;
            display: flex;
            align-items: center;
            gap: 24px;
            border-bottom: 2px solid #262a36;
        }
        .topbar .logo { font-weight: bold; color: #4da3ff; font-size: 18px; }
        .topbar .item { color: #9aa3b2; font-size: 15px; }
        .topbar .item.activo { color: #ffffff; font-weight: bold; }
        .ruta { padding: 12px 24px; color: #7d8798; font-size: 13px; }

        /* Panel central minimalista */
        .panel {
            max-width: 560px;
            margin: 40px auto;
            background: #1b1e27;
            border: 1px solid #262a36;
            border-radius: 12px;
            padding: 32px;
            text-align: center;
        }
        .panel h1 { margin: 0 0 6px 0; font-size: 22px; }
        .panel .ayuda { margin: 0 0 24px 0; color: #7d8798; font-size: 13px; }

        .campo {
            width: 100%;
            padding: 18px;
            font-size: 28px;
            text-align: center;
            letter-spacing: 4px;
            border: 2px solid #2f3543;
            border-radius: 10px;
            background: #0e1015;
            color: #ffffff;
            outline: none;
        }
        .campo:focus { border-color: #4da3ff; }

        .boton {
            width: 100%;
            margin-top: 16px;
            padding: 16px;
            font-size: 17px;
            font-weight: bold;
            border: none;
            border-radius: 10px;
            background: #4da3ff;
            color: #0e1015;
            cursor: pointer;
        }
        .boton:hover { background: #6fb5ff; }

        /* Respuesta visual: verde permitido / rojo denegado */
        .resultado {
            margin-top: 24px;
            padding: 24px;
            border-radius: 10px;
            font-size: 24px;
            font-weight: bold;
        }
        .resultado span { display: block; margin-top: 8px; font-size: 16px; font-weight: normal; }
        .resultado.permitido { background: #1e7d43; color: #ffffff; }
        .resultado.denegado { background: #a32121; color: #ffffff; }
        .resultado.aviso { background: #2a2f3c; color: #c9cfda; font-size: 18px; }
    </style>
</head>
<body>

<div class="topbar">
    <span class="logo">GYM</span>
    <span class="item">Seguridad</span>
    <span class="item activo">Validar Ingreso</span>
</div>

<div class="ruta">Topbar Seguridad &gt; Validar Ingreso</div>

<main class="panel">
    <h1>Control de Acceso por DNI</h1>
    <p class="ayuda">F19: Validar Ingreso por DNI</p>

    <form method="post" action="${pageContext.request.contextPath}/seguridad/validar">
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
