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
                <th>Number</th>
                <th>First name</th>
                <th>Last name</th>
                <th></th>
            </tr>

            <c:forEach var="teacher" items="${teachers}">
                <tr>
                    <td><a href="<c:url value="teacher/${teacher.id}.htm"/>">${teacher.number}</a></td>
                    <td>${teacher.firstName}</td>
                    <td>${teacher.lastName}</td>
                    <td><a href="<c:url value="/teacher/confirmRemoval${teacher.id}.htm"/>">Remove</a></td>
                </tr>
            </c:forEach>

            <td><a href="<c:url value="/teacher/new.htm"/>">Add teacher</a></td>
            <td></td>
            <td></td>
            <td></td>
            <caption>Teacher Overview</caption>
        </table>
    </main>
    <footer> &copy; Carolyne Peelman, UC Leuven-Limburg</footer>
</div>
</body>

</html>