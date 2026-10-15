
<form action="${pageContext.request.contextPath}/login" method="post">

    <label>Correo electrónico</label>
    <input type="email" name="correo" required>

    <label>Contraseña</label>
    <input type="password" name="contraseña" required>

    <button type="submit">Iniciar sesión</button>

    <p style="color: red;">${error}</p>

</form>
