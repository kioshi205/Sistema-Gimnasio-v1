<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Seguridad - Inicio</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, Helvetica, sans-serif;
            background: #ffffff;
            color: #111827;
        }
        .ruta { padding: 12px 24px; color: #9ca3af; font-size: 13px; }

        .contenido { max-width: 700px; margin: 48px auto; padding: 0 24px; }
        .contenido h1 { margin: 0 0 8px 0; font-size: 26px; }
        .contenido .ayuda { margin: 0 0 28px 0; color: #6b7280; font-size: 14px; }

        .tarjeta {
            display: block;
            max-width: 420px;
            padding: 24px;
            background: #ffffff;
            border: 1px solid #e5e7eb;
            border-radius: 12px;
            color: #111827;
            text-decoration: none;
        }
        .tarjeta:hover { border-color: #2563eb; }
        .tarjeta .titulo { display: block; font-size: 19px; font-weight: bold; margin-bottom: 8px; }
        .tarjeta .detalle { display: block; color: #6b7280; font-size: 14px; line-height: 1.5; }
        .tarjeta .flecha { display: block; margin-top: 16px; color: #2563eb; font-size: 14px; font-weight: bold; }

        .nota {
            margin-top: 28px;
            padding: 16px 20px;
            background: #f8fafc;
            border: 1px solid #e5e7eb;
            border-left: 4px solid #2563eb;
            border-radius: 8px;
            color: #475569;
            font-size: 14px;
        }
    </style>
</head>
<body class="pagina-inicio">

<jsp:include page="topbar.jsp"/>

<div class="ruta">Seguridad &gt; Inicio</div>

<div class="contenido">
    <h1>Panel del Personal de Seguridad</h1>
    <p class="ayuda">Selecciona una opción para comenzar.</p>

    <a class="tarjeta" href="/seguridad/validar">
        <span class="titulo">Control de Acceso</span>
        <span class="detalle">Valida el ingreso de socios por DNI y verifica si su plan está vigente (F19).</span>
        <span class="flecha">Validar ingreso →</span>
    </a>

    <div class="nota">
        Acceso exclusivo para el personal de seguridad del gimnasio.
    </div>
</div>

</body>
</html>
