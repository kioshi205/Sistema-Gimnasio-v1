<%@ page contentType="text/html;charset=UTF-8" %>
<style>
    body { font-family: Arial, sans-serif; margin: 0; background: #f4f4f4; }
    .topbar { background: #222; padding: 12px 20px; }
    .topbar a { color: #fff; text-decoration: none; margin-right: 20px; }
    .topbar a:hover { text-decoration: underline; }
    .contenido { padding: 20px; max-width: 900px; margin: auto; }
    table { width: 100%; border-collapse: collapse; background: #fff; }
    th, td { padding: 8px; border: 1px solid #ddd; text-align: left; }
    th { background: #eee; }
    .btn { background: #28a745; color: #fff; border: none; padding: 10px 20px; cursor: pointer; font-size: 16px; }
    .total { font-size: 22px; font-weight: bold; margin-top: 15px; }
    .ok { background: #d4edda; padding: 10px; margin-bottom: 15px; }
    .tarjetas a { display: inline-block; padding: 15px 25px; color: #fff; text-decoration: none; margin-right: 10px; }
    .verde { background: #28a745; } .amarillo { background: #e0a800; } .rojo { background: #dc3545; }
</style>
<div class="topbar">
    <a href="/recepcionista/resumen">Resumen Plan</a>
    <a href="/recepcionista/guardar">Guardar Inscripción</a>
    <a href="/recepcionista/inscripciones">Inscripciones</a>
    <a href="/recepcionista/vigencias">Vigencias</a>
</div>