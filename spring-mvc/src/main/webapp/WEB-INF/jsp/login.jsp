<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>

<html>

<body>
<div id="container">
    <%@include file="header.jsp" %>
    <jsp:include page="head.jsp">
        <jsp:param name="title" value="Home"/>
    </jsp:include>
    <form name='login' action="<c:url value="/login"/>" method="POST" id="loginForm">
        <div class="form-group input-group">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input class="form-control" type="text" name='username' placeholder="Username"/>
        </div>
        <div class="form-group input-group">
            <input class="form-control" type="password" name='password' placeholder="Password"/>
        </div>
        <div class="form-group">
            <button id="login" type="submit" class="btn btn-primary">Log in</button>
        </div>
        <br><br><br>
    </form>
    <footer> &copy; Carolyne Peelman, UC Leuven-Limburg</footer>
</div>
</body>

</html>