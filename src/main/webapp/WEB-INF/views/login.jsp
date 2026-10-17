<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Gimnasio</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #eeeeee;
            margin: 0;
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .login {
            background-color: white;
            width: 320px;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 10px #cccccc;
        }

        h2 {
            text-align: center;
            margin-bottom: 25px;
            color: #333333;
        }

        label {
            display: block;
            margin-bottom: 7px;
        }

        input {
            width: 100%;
            padding: 10px;
            margin-bottom: 18px;
            border: 1px solid #cccccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        button {
            width: 100%;
            padding: 11px;
            background-color: #333333;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 15px;
        }

        button:hover {
            background-color: #555555;
        }

        .error {
            color: red;
            text-align: center;
        }
    </style>
</head>

<body>

    <div class="login">

        <h2>Iniciar sesión</h2>

        <form action="${pageContext.request.contextPath}/login" method="post">

            <label for="correo">Correo electrónico</label>
            <input type="email" id="correo" name="correo"
                   placeholder="Ingresa tu correo" required>

            <label for="contraseña">Contraseña</label>
            <input type="password" id="contraseña" name="contraseña"
                   placeholder="Ingresa tu contraseña" required>

            <button type="submit">Ingresar</button>

        </form>

        <p class="error">${error}</p>

    </div>

</body>
</html>