<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML>
<html>
<body>
<div id="container">
    <%@include file="../header.jsp" %>
    <jsp:include page="../head.jsp">
        <jsp:param name="title" value="New teacher"/>
    </jsp:include>

    <main>
        <form id="teacherForm" role="form" method="POST" action="<c:url value="/teacher.htm"/>"
              novalidate="novalidate">
            <p>
                <label for="id">ID</label><input type="text" id="id" name="id" required value="${teacher.id}">
            </p>
            <p>
                <label for="firstName">First name</label><input type="text" id="firstName" name="firstName" required
                                                       value="${teacher.firstName}">
            </p>
            <p>
                <label for="lastName">Last name</label><input type="text" id="lastName"
                                                       name="lastName" required value="${teacher.lastName}">
            </p>
            <p>
                <input id="save" type="submit" value="Save">
            </p>

        </form>
    </main>

    <footer> &copy; Webontwikkeling 3, UC Leuven-Limburg</footer>

</div>
</body>
</html>
