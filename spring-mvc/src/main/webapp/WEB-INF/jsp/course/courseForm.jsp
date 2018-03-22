<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE HTML>
<html>
<body>
<div id="container">
    <%@include file="../header.jsp" %>
    <jsp:include page="../head.jsp">
        <jsp:param name="title" value="New course"/>
    </jsp:include>

    <main>
        <form id="courseForm" role="form" method="POST" action="<c:url value="/course.htm"/>"
              novalidate="novalidate">
            <p>
                <label for="code">Code</label><input type="text" id="code" name="code" required value="${course.code}">
            </p>
            <p>
                <label for="name">Name</label><input type="text" id="name" name="name" required
                                                     value="${course.name}">
            </p>
            <p>
                <label for="teacherId">Teacher ID</label><input type="text" id="teacherId"
                                                                name="teacherId" required value="${course.teacher.id}">
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
