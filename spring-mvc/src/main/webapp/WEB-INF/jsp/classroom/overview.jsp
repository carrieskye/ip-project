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
                <th class="sort">Location<a href="<c:url value="/classroom/sortedByLocation.htm"/>"><img
                        src="<c:url value="/images/sort.png"/>" alt="Sort"></a></th>
                <th class="sort">Seats<a href="<c:url value="/classroom/sortedBySeats.htm"/>"><img
                        src="<c:url value="/images/sort.png"/>" alt="Sort"></a></th>
                <th class="sort">Type<a href="<c:url value="/classroom/sortedByType.htm"/>"><img
                        src="<c:url value="/images/sort.png"/>" alt="Sort"></a></th>
                <th></th>
            </tr>

            <c:forEach var="classroom" items="${classrooms}">
                <tr>
                    <td><a href="<c:url value="/classroom/${classroom.id}.htm"/>">${classroom.location}</a></td>
                    <td>${classroom.seats}</td>
                    <td>${classroom.type}</td>
                    <td><a href="<c:url value="/classroom/confirmRemoval${classroom.id}.htm"/>">Remove</a></td>
                </tr>
            </c:forEach>

            <caption>Classroom Overview</caption>
        </table>

        <p class="new"><img src="<c:url value="/images/add.png"/>" alt="Add"><a href="<c:url value="/classroom/new.htm"/>">Add classroom</a></p>
    </main>
    <footer> &copy; Carolyne Peelman, UC Leuven-Limburg</footer>
</div>
</body>

</html>