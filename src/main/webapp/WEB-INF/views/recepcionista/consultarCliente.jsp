<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Consultar Cliente</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f1f3f5;
            margin: 0;
        }

        .contenedor {
            width: 500px;
            margin: 60px auto;
            padding: 25px;
            background: white;
            border-radius: 10px;
            box-shadow: 0 0 10px #ccc;
        }

        h2 {
            text-align: center;
        }

        input {
            width: 100%;
            padding: 12px;
            margin-top: 15px;
            box-sizing: border-box;
        }

        button {
            width: 100%;
            padding: 12px;
            margin-top: 15px;
            background: #0d6efd;
            color: white;
            border: none;
            cursor: pointer;
        }

        .volver {
            display: block;
            margin-top: 20px;
            text-align: center;
        }
    </style>
</head>

<body>

<div class="contenedor">

    <h2>Consultar Cliente</h2>

    <form action="#" method="get">

        <input type="text" name="busqueda"
               placeholder="Ingrese DNI o apellido" required>

        <button type="submit">Buscar Cliente</button>

    </form>

    <a class="volver"
       href="${pageContext.request.contextPath}/recepcionista/registrarCliente">
        Registrar nuevo cliente
    </a>

</div>
<div style="text-align: center; margin-top: 20px;">
    <a href="${pageContext.request.contextPath}/inicio"
       style="display: inline-block;
              padding: 10px 20px;
              background-color: #444;
              color: white;
              text-decoration: none;
              border-radius: 6px;">
        Volver al inicio
    </a>
</div>
</body>
</html>