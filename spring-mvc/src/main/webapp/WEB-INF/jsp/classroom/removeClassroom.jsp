<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>

<html>

<body>
<div id="container">
    <%@include file="../header.jsp" %>
    <jsp:include page="../head.jsp">
        <jsp:param name="title" value="Remove classroom"/>
    </jsp:include>
    <main>
        <p>Are you sure you want to remove classroom ${classroom.info}?</p>
        <form>
            <input class="remove" type="submit" value="OK"
                   formaction="<c:url value="/classroom/remove${classroom.id}.htm"/>">
            <input class="remove" type="submit" value="Cancel" formaction="<c:url value="/classroom.htm"/>">
        </form>
    </main>
    <footer> &copy; Carolyne Peelman, UC Leuven-Limburg</footer>
</div>
</body>

</html>