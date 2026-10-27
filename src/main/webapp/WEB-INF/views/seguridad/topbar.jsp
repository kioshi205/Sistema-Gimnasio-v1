<%@ page contentType="text/html;charset=UTF-8" %>
<style>
    .topbar {
        background: #ffffff;
        padding: 14px 24px;
        border-bottom: 1px solid #e5e7eb;
    }
    .topbar a {
        color: #6b7280;
        font-size: 15px;
        text-decoration: none;
        margin-right: 24px;
        padding-bottom: 4px;
    }
    .topbar a:hover { color: #111827; }

    /* La pagina activa se marca con una clase en el body */
    .pagina-inicio a.enlace-inicio,
    .pagina-validar a.enlace-validar {
        color: #111827;
        font-weight: bold;
        border-bottom: 2px solid #2563eb;
    }
</style>
<div class="topbar">
    <a class="enlace-inicio" href="/seguridad/inicio">Inicio</a>
    <a class="enlace-validar" href="/seguridad/validar">Control de Acceso</a>
</div>
