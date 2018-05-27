<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>

<html>

<body>
<div id="container">
    <%@include file="header.jsp" %>
    <jsp:include page="head.jsp">
        <jsp:param name="title" value="Login"/>
    </jsp:include>
    <main>
        <form name='login' action="<c:url value="/login.htm"/>" method="POST" id="loginForm">
            <p>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <label for="username">Username:</label>
                <input id='username' type="text" name='username' placeholder="Username"/></p>
            <p>
                <label for="password">Password:</label>
                <input id='password' type="password" name='password' placeholder="Password"/>
            </p>
            <p>
                <input id="login" type="submit" value="Log in"/>
            </p>
        </form>
    </main>

    <footer> &copy; Carolyne Peelman, UC Leuven-Limburg</footer>

</div>
</body>

</html>