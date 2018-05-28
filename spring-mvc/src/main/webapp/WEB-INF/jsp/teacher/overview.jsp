<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>

<html>

<body>
<div id="container">
    <%@include file="../header.jsp" %>
    <jsp:include page="../head.jsp">
        <jsp:param name="title" value="Teachers"/>
    </jsp:include>
    <main>
        <table>
            <tr>
                <th class="sort">Number<a href="<c:url value="/teacher/sortedByNumber.htm"/>"><img
                        src="<c:url value="/images/sort.png"/>" alt="Sort"></a></th>
                <th class="sort">First name<a href="<c:url value="/teacher/sortedByFirstName.htm"/>"><img
                        src="<c:url value="/images/sort.png"/>" alt="Sort"></a></th>
                <th class="sort">Last name<a href="<c:url value="/teacher/sortedByLastName.htm"/>"><img
                        src="<c:url value="/images/sort.png"/>" alt="Sort"></a></th>
                <th></th>
            </tr>

            <c:forEach var="teacher" items="${teachers}">
                <tr>
                    <td><a href="<c:url value="/teacher/${teacher.id}.htm"/>">${teacher.number}</a></td>
                    <td>${teacher.firstName}</td>
                    <td>${teacher.lastName}</td>
                    <td><a href="<c:url value="/teacher/confirmRemoval${teacher.id}.htm"/>">Remove</a></td>
                </tr>
            </c:forEach>

            <caption>Teacher Overview</caption>
        </table>

        <p class="new"><img src="<c:url value="/images/add.png"/>" alt="Add"><a
                href="<c:url value="/teacher/new.htm"/>">Add teacher</a></p>

    </main>
    <footer> &copy; Carolyne Peelman, UC Leuven-Limburg</footer>
</div>
</body>

</html>