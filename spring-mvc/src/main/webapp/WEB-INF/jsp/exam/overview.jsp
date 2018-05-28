<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
<body>
<div id="container">
    <%@include file="../header.jsp" %>
    <jsp:include page="../head.jsp">
        <jsp:param name="title" value="Exams"/>
    </jsp:include>

    <main>
        <table>
            <tr>
                <th class="sort">Course<a href="<c:url value="/exam/sortedByCourse.htm"/>"><img
                        src="<c:url value="/images/sort.png"/>" alt="Sort"></a></th>
                <th class="sort">Date<a href="<c:url value="/exam/sortedByDate.htm"/>"><img
                        src="<c:url value="/images/sort.png"/>" alt="Sort"></a></th>
                <th class="sort">Time<a href="<c:url value="/exam/sortedByBeginTime.htm"/>"><img
                        src="<c:url value="/images/sort.png"/>" alt="Sort"></a></th>
                <th class="sort">Classroom<a href="<c:url value="/exam/sortedByClassroom.htm"/>"><img
                        src="<c:url value="/images/sort.png"/>" alt="Sort"></a></th>
                <th></th>
            </tr>

            <c:forEach var="exam" items="${exams}">
                <tr>
                    <td><a href="<c:url value="/exam/${exam.id}.htm"/>">${exam.attributes.get("course").info}</a></td>
                    <td>${exam.dateString}</td>
                    <td>${exam.timeString}</td>
                    <td>${exam.attributes.get("classroom").info}</td>
                    <td><a href="<c:url value="/exam/confirmRemoval${exam.id}.htm"/>">Remove</a></td>
                </tr>
            </c:forEach>

            <caption>Exam Overview</caption>
        </table>

        <p class="new"><img src="<c:url value="/images/add.png"/>" alt="Add"><a href="<c:url value="/exam/new.htm"/>">Add
            exam</a></p>

    </main>

    <footer> &copy; Carolyne Peelman, UC Leuven-Limburg</footer>

</div>
</body>
</html>