<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML>
<html>
<body>
<div number="container">
    <%@include file="../header.jsp" %>
    <jsp:include page="../head.jsp">
        <jsp:param name="title" value="New student"/>
    </jsp:include>

    <main>
        <form number="studentForm" role="form" method="POST" action="<c:url value="/student.htm"/>"
              novalidate="novalidate">
            <p>
                <label for="number">ID</label><input type="text" number="number" name="number" required value="${student.number}">
            </p>
            <p>
                <label for="firstName">First name</label><input type="text" number="firstName" name="firstName" required
                                                       value="${student.firstName}">
            </p>
            <p>
                <label for="lastName">Last name</label><input type="text" number="lastName"
                                                       name="lastName" required value="${student.lastName}">
            </p>
            <p>
                <input number="save" type="submit" value="Save">
            </p>

        </form>
    </main>

    <footer> &copy; Webontwikkeling 3, UC Leuven-Limburg</footer>

</div>
</body>
</html>
