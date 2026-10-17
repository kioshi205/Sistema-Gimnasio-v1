<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head><title>Guardar Inscripción</title></head>
<body>
<jsp:include page="topbar.jsp"/>
<div class="contenido">
    <h2>Confirmar Inscripción</h2>

    <c:if test="${resumen == null}">
        <p>No hay un plan armado para guardar.</p>
    </c:if>

    <c:if test="${resumen != null}">
        <p>Cliente: <b>${resumen.cliente.nombre}</b> | Plan: <b>${resumen.plan.nombre}</b></p>
        <div class="total">
            Total cobrado: <fmt:formatNumber value="${resumen.total}" type="currency" currencySymbol="S/ "/>
        </div>
        <p>Verifica que el pago ya fue recibido en caja antes de confirmar.</p>

        <form method="post" action="/recepcionista/guardar">
            <input type="hidden" name="clienteId" value="${clienteId}">
            <input type="hidden" name="planId" value="${planId}">
            <c:forEach var="s" items="${resumen.subproductos}">
                <input type="hidden" name="sub" value="${s.id}">
            </c:forEach>
            <button type="submit" class="btn">Confirmar pago y guardar inscripción</button>
        </form>
    </c:if>
</div>
</body>
</html>