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
                <th class="sort">Number<a href="<c:url value="/student/sortedByNumber.htm"/>"><img
                        src="<c:url value="/images/sort.png"/>" alt="Sort"></a></th>
                <th class="sort">First name<a href="<c:url value="/student/sortedByFirstName.htm"/>"><img
                        src="<c:url value="/images/sort.png"/>" alt="Sort"></a></th>
                <th class="sort">Last name<a href="<c:url value="/student/sortedByLastName.htm"/>"><img
                        src="<c:url value="/images/sort.png"/>" alt="Sort"></a></th>
                <th></th>
            </tr>

            <c:forEach var="student" items="${students}">
                <tr>
                    <td><a href="<c:url value="/student/${student.id}.htm"/>">${student.number}</a></td>
                    <td>${student.firstName}</td>
                    <td>${student.lastName}</td>
                    <td><a href="<c:url value="/student/confirmRemoval${student.id}.htm"/>">Remove</a></td>
                </tr>
            </c:forEach>

            <caption>Student Overview</caption>
        </table>

        <p class="new"><img src="<c:url value="/images/add.png"/>" alt="Add"><a
                href="<c:url value="/student/new.htm"/>">Add student</a></p>

    </main>
    <footer> &copy; Carolyne Peelman, UC Leuven-Limburg</footer>
</div>
</body>

</html>