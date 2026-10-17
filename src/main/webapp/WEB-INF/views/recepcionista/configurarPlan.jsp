<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Configurar Plan</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f1f3f5;
            margin: 0;
        }

        .contenedor {
            width: 450px;
            margin: 50px auto;
            padding: 25px;
            background: white;
            border-radius: 10px;
            box-shadow: 0 0 10px #ccc;
        }

        h2, h3 {
            text-align: center;
        }

        select {
            width: 100%;
            padding: 10px;
            margin-top: 10px;
        }

        .opcion {
            margin: 15px 0;
        }

        button {
            width: 100%;
            padding: 12px;
            margin-top: 15px;
            background: #198754;
            color: white;
            border: none;
            cursor: pointer;
        }
    </style>
</head>

<body>

<div class="contenedor">

    <h2>Configurar Plan</h2>

    <form action="#" method="post">

        <h3>Seleccionar plan base</h3>

        <select name="plan" required>
            <option value="">Seleccione un plan</option>
            <option value="basico">Básico</option>
            <option value="medio">Medio</option>
            <option value="pro">Pro</option>
        </select>

        <h3>Subproductos adicionales</h3>

        <div class="opcion">
            <input type="checkbox" name="subproductos" value="entrenamiento">
            Entrenamiento personalizado
        </div>

        <div class="opcion">
            <input type="checkbox" name="subproductos" value="asesoramiento">
            Asesoramiento personalizado
        </div>

        <div class="opcion">
            <input type="checkbox" name="subproductos" value="clases">
            Clases grupales
        </div>

        <div class="opcion">
            <input type="checkbox" name="subproductos" value="evaluacion">
            Evaluación física
        </div>

        <button type="submit">Configurar plan</button>

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