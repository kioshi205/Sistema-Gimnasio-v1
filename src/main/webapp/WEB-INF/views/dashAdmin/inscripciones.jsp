<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head><title>Inscripciones</title></head>
<body>
<jsp:include page="topbar.jsp"/>
<div class="contenido">
    <h2>Historial de Inscripciones</h2>

    <c:if test="${ok != null}">
        <div class="ok">Inscripción registrada correctamente.</div>
    </c:if>

    <table>
        <tr>
            <th>#</th><th>Cliente</th><th>Plan base</th><th>Subproductos</th>
            <th>Total</th><th>Inicio</th><th>Vence</th>
        </tr>
        <c:forEach var="i" items="${inscripciones}">
            <tr>
                <td>${i.id}</td>
                <td>${i.cliente}</td>
                <td>${i.plan}</td>
                <td>${i.subproductos}</td>
                <td><fmt:formatNumber value="${i.total}" type="currency" currencySymbol="S/ "/></td>
                <td>${i.fecha_inicio}</td>
                <td>${i.fecha_fin}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>