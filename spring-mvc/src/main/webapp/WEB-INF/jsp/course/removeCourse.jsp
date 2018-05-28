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
        <form>
            <input class="remove" id="OK" type="submit" value="OK"
                   formaction="<c:url value="/course/remove${course.id}.htm"/>">
            <input class="remove" id="Cancel" type="submit" value="Cancel" formaction="<c:url value="/course.htm"/>">
        </form>
    </main>
    <footer> &copy; Carolyne Peelman, UC Leuven-Limburg</footer>
</div>
</body>

</html>