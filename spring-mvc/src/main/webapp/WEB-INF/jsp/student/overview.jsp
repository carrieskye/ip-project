<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>

<html>

<body>
<div id="container">
    <%@include file="../header.jsp" %>
    <jsp:include page="../head.jsp">
        <jsp:param name="title" value="Students"/>
    </jsp:include>
    <main>
        <table>
            <tr>
                <th>Number</th>
                <th>First name</th>
                <th>Last name</th>
                <th></th>
            </tr>

            <c:forEach var="student" items="${students}">
                <tr>
                    <td><a href="<c:url value="student/${student.id}.htm"/>">${student.number}</a></td>
                    <td>${student.firstName}</td>
                    <td>${student.lastName}</td>
                    <td>Remove</td>
                </tr>
            </c:forEach>

            <td><a href="<c:url value="/student/new.htm"/>">Add student</a></td>
            <td></td>
            <td></td>
            <td></td>
            <caption>Student Overview</caption>
        </table>
    </main>
    <footer> &copy; Carolyne Peelman, UC Leuven-Limburg</footer>
</div>
</body>

</html>