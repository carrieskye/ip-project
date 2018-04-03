<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>

<html>

<body>
<div id="container">
    <%@include file="../header.jsp" %>
    <jsp:include page="../head.jsp">
        <jsp:param name="title" value="Remove course"/>
    </jsp:include>
    <main>
        <p>Are you sure you want to remove ${course.info}?</p>

        <p>
            <a id="confirm" href="<c:url value="/course/remove${course.id}.htm"/>">OK</a>
        </p>
        <p>
            <a href="<c:url value="/course.htm"/>">Cancel</a>
        </p>

    </main>
    <footer> &copy; Carolyne Peelman, UC Leuven-Limburg</footer>
</div>
</body>

</html>