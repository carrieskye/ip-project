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
        <p>${examError}</p>
        <form>
            <input class="remove" type="submit" value="Back" formaction="<c:url value="/course.htm"/>">
        </form>
    </main>
    <footer> &copy; Carolyne Peelman, UC Leuven-Limburg</footer>
</div>
</body>

</html>