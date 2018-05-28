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
                <th class="sort">Code<a href="<c:url value="/course/sortedByCode.htm"/>"><img
                        src="<c:url value="/images/sort.png"/>" alt="Sort"></a></th>
                <th class="sort">Name<a href="<c:url value="/course/sortedByName.htm"/>"><img
                        src="<c:url value="/images/sort.png"/>" alt="Sort"></a></th>
                <th class="sort">Teacher<a href="<c:url value="/course/sortedByTeacher.htm"/>"><img
                        src="<c:url value="/images/sort.png"/>" alt="Sort"></a></th>
                <th></th>
            </tr>

            <c:forEach var="course" items="${courses}">
                <tr>
                    <td><a href="<c:url value="/course/${course.id}.htm"/>">${course.code}</a></td>
                    <td>${course.name}</td>
                    <td>${course.attributes.get("teacher").info}</td>
                    <td><a href="<c:url value="/course/confirmRemoval${course.id}.htm"/>">Remove</a></td>
                </tr>
            </c:forEach>

            <caption>Course Overview</caption>
        </table>

        <p class="new"><img src="<c:url value="/images/add.png"/>" alt="Add"><a href="<c:url value="/course/new.htm"/>">Add course</a></p>

    </main>
    <footer> &copy; Carolyne Peelman, UC Leuven-Limburg</footer>
</div>
</body>

</html>