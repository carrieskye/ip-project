<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>

<html>

<body>
<div id="container">
    <%@include file="../header.jsp" %>
    <jsp:include page="../head.jsp">
        <jsp:param name="title" value="Classrooms"/>
    </jsp:include>
    <main>
        <table>
            <tr>
                <th>Location</th>
                <th>Seats</th>
                <th>Type</th>
                <th></th>
            </tr>

            <c:forEach var="classroom" items="${classrooms}">
                <tr>
                    <td><a href="<c:url value="classroom/${classroom.id}.htm"/>">${classroom.location}</a></td>
                    <td>${classroom.seats}</td>
                    <td>${classroom.type}</td>
                    <td><a href="<c:url value="/classroom/confirmRemoval${classroom.id}.htm"/>">Remove</a></td>
                </tr>
            </c:forEach>

            <td><a href="<c:url value="/classroom/new.htm"/>">Add classroom</a></td>
            <td></td>
            <td></td>
            <td></td>
            <caption>Classroom Overview</caption>
        </table>
    </main>
    <footer> &copy; Carolyne Peelman, UC Leuven-Limburg</footer>
</div>
</body>

</html>