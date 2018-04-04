<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>

<html>

<body>
<div id="container">
    <%@include file="../header.jsp" %>
    <jsp:include page="../head.jsp">
        <jsp:param name="title" value="Remove exam"/>
    </jsp:include>
    <main>
        <p>Are you sure you want to remove exam ${course} on ${exam.date} (${exam.beginTime} - ${exam.endTime})?</p>

        <p>
            <a id="confirm" href="<c:url value="/exam/remove${exam.id}.htm"/>">OK</a>
        </p>
        <p>
            <a href="<c:url value="/exam.htm"/>">Cancel</a>
        </p>

    </main>
    <footer> &copy; Carolyne Peelman, UC Leuven-Limburg</footer>
</div>
</body>

</html>