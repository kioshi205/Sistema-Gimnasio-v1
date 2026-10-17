<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registrar Cliente</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f1f3f5;
            margin: 0;
        }

        .contenedor {
            width: 400px;
            margin: 60px auto;
            padding: 25px;
            background: white;
            border-radius: 10px;
            box-shadow: 0 0 10px #ccc;
        }

        h2 {
            text-align: center;
        }

        label {
            display: block;
            margin-top: 15px;
        }

        input {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            box-sizing: border-box;
        }

        button {
            width: 100%;
            padding: 12px;
            margin-top: 20px;
            background: #198754;
            color: white;
            border: none;
            cursor: pointer;
        }

        .volver {
            display: block;
            margin-top: 15px;
            text-align: center;
        }
    </style>
</head>

<body>

<div class="contenedor">

    <h2>Registrar Cliente</h2>

    <form action="#" method="post">

        <label>DNI:</label>
        <input type="text" name="dni" maxlength="8"
               pattern="[0-9]{8}" required>

        <label>Nombres y apellidos:</label>
        <input type="text" name="nombres" required>

        <label>Teléfono:</label>
        <input type="tel" name="telefono" required>

        <label>Correo electrónico:</label>
        <input type="email" name="correo" required>

        <button type="submit">Registrar Cliente</button>

    </form>

    <a class="volver"
       href="${pageContext.request.contextPath}/recepcionista/consultarCliente">
        Consultar clientes
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