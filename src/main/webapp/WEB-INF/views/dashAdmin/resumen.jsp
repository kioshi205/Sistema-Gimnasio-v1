<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head><title>Resumen del Plan</title></head>
<body>
<jsp:include page="topbar.jsp"/>
<div class="contenido">
    <h2>Resumen de Inscripción</h2>

    <c:if test="${resumen == null}">
        <p>No hay un plan armado. Inicia el flujo de inscripción primero.</p>
    </c:if>

    <c:if test="${resumen != null}">
        <p><b>Cliente:</b> ${resumen.cliente.nombre} (DNI ${resumen.cliente.dni})</p>

        <table>
            <tr><th>Concepto</th><th>Precio</th></tr>
            <tr>
                <td>Plan ${resumen.plan.nombre} (${resumen.plan.duracion_dias} días)</td>
                <td><fmt:formatNumber value="${resumen.plan.precio}" type="currency" currencySymbol="S/ "/></td>
            </tr>
            <c:forEach var="s" items="${resumen.subproductos}">
                <tr>
                    <td>${s.nombre}</td>
                    <td><fmt:formatNumber value="${s.precio}" type="currency" currencySymbol="S/ "/></td>
                </tr>
            </c:forEach>
        </table>

        <div class="total">
            Total a cobrar: <fmt:formatNumber value="${resumen.total}" type="currency" currencySymbol="S/ "/>
        </div>
        <p>
            <a href="/recepcionista/guardar?clienteId=${clienteId}&planId=${planId}<c:forEach var="s" items="${resumen.subproductos}">&sub=${s.id}</c:forEach>">
                Continuar a guardar inscripción →
            </a>
        </p>
    </c:if>
</div>
</body>
</html>