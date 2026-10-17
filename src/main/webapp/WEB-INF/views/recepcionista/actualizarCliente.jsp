<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Actualizar Cliente</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f1f3f5;
            margin: 0;
        }

        .contenedor {
            width: 400px;
            margin: 50px auto;
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
            background: #fd7e14;
            color: white;
            border: none;
            cursor: pointer;
        }
    </style>
</head>

<body>

<div class="contenedor">

    <h2>Actualizar Cliente</h2>

    <form action="#" method="post">

        <label>DNI del cliente:</label>
        <input type="text" name="dni" maxlength="8"
               pattern="[0-9]{8}" required>

        <label>Nombres y apellidos:</label>
        <input type="text" name="nombres" required>

        <label>Teléfono:</label>
        <input type="tel" name="telefono" required>

        <label>Correo electrónico:</label>
        <input type="email" name="correo" required>

        <label>Dirección:</label>
        <input type="text" name="direccion">

        <button type="submit">Actualizar información</button>

    </form>

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