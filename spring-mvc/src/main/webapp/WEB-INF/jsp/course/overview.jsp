<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>

<html>

<body>
<div id="container">
    <%@include file="../header.jsp" %>
    <jsp:include page="../head.jsp">
        <jsp:param name="title" value="Courses"/>
    </jsp:include>
    <main>
        <table>
            <tr>
                <th>Code</th>
                <th>Name</th>
                <th>Teacher</th>
                <th></th>
            </tr>

            <c:forEach var="course" items="${courses}">
                <tr>
                    <td><a href="<c:url value="course/${course.code}.htm"/>">${course.code}</a></td>
                    <td>${course.name}</td>
                    <td>${course.teacher.firstName} ${course.teacher.lastName}</td>
                    <td>Remove</td>
                </tr>
            </c:forEach>

            <td><a href="<c:url value="/course/new.htm"/>">Add course</a></td>
            <td></td>
            <td></td>
            <td></td>
            <caption>Course Overview</caption>
        </table>
    </main>
    <footer> &copy; Carolyne Peelman, UC Leuven-Limburg</footer>
</div>
</body>

</html>