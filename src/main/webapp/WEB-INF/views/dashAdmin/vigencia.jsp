<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Vigencias</title></head>
<body>
<jsp:include page="topbar.jsp"/>
<div class="contenido">
    <h2>Tablero de Vigencias</h2>

    <div class="tarjetas">
        <a class="verde" href="/recepcionista/vigencias?estado=ACTIVA">Activas: ${activas}</a>
        <a class="amarillo" href="/recepcionista/vigencias?estado=POR_VENCER">Por vencer (5 días): ${porVencer}</a>
        <a class="rojo" href="/recepcionista/vigencias?estado=VENCIDA">Vencidas: ${vencidas}</a>
        <a style="background:#6c757d" href="/recepcionista/vigencias">Ver todas</a>
    </div>
    <br>

    <table>
        <tr><th>Cliente</th><th>Plan</th><th>Vence</th><th>Días restantes</th><th>Estado</th></tr>
        <c:forEach var="v" items="${vigencias}">
            <tr>
                <td>${v.cliente}</td>
                <td>${v.plan}</td>
                <td>${v.fecha_fin}</td>
                <td>${v.dias}</td>
                <td>
                    <c:choose>
                        <c:when test="${v.estado == 'ACTIVA'}"><span class="verde" style="padding:3px 8px;color:#fff">Activa</span></c:when>
                        <c:when test="${v.estado == 'POR_VENCER'}"><span class="amarillo" style="padding:3px 8px;color:#fff">Por vencer</span></c:when>
                        <c:otherwise><span class="rojo" style="padding:3px 8px;color:#fff">Vencida</span></c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>