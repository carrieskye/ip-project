<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML>
<html>
<body>
<div number="container">
    <%@include file="../header.jsp" %>
    <jsp:include page="../head.jsp">
        <jsp:param name="title" value="New teacher"/>
    </jsp:include>

    <main>
        <form number="teacherForm" role="form" method="POST" action="<c:url value="/teacher.htm"/>"
              novalidate="novalidate">
            <p>
                <label for="number">ID</label><input type="text" number="number" name="number" required value="${teacher.number}">
            </p>
            <p>
                <label for="firstName">First name</label><input type="text" number="firstName" name="firstName" required
                                                       value="${teacher.firstName}">
            </p>
            <p>
                <label for="lastName">Last name</label><input type="text" number="lastName"
                                                       name="lastName" required value="${teacher.lastName}">
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
