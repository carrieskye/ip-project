<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>

<html>

<body>
<div id="container">
    <%@include file="../header.jsp" %>
    <jsp:include page="../head.jsp">
        <jsp:param name="title" value="Remove teacher"/>
    </jsp:include>
    <main>
        <p>Are you sure you want to remove ${teacher.firstName} ${teacher.lastName}?</p>

        <p>
            <a id="confirm" href="<c:url value="/teacher/remove${teacher.id}.htm"/>">OK</a>
        </p>
        <p>
            <a href="<c:url value="/teacher.htm"/>">Cancel</a>
        </p>

    </main>
    <footer> &copy; Carolyne Peelman, UC Leuven-Limburg</footer>
</div>
</body>

</html>