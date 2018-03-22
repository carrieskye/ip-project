<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE HTML>
<html>
<body>
<div id="container">
    <%@include file="../header.jsp" %>
    <jsp:include page="../head.jsp">
        <jsp:param name="title" value="New exam"/>
    </jsp:include>

    <main>
        <form id="examForm" role="form" method="POST" action="<c:url value="/exam.htm"/>"
              novalidate="novalidate">
            <p>
                <label for="id">ID</label><input type="text" id="id" name="id" required value="${exam.id}">
            </p>
            <p>
                <label for="courseId">Course id</label><input type="text" id="courseId" name="courseId" required
                                                              value="${exam.course.code}">
            </p>
            <p>
                <label for="date">Date</label><input type="datetime-local" id="date"
                                                     name="date" required value="${exam.date}">
            </p>
            <p>
                <label for="classroom">Classroom</label><input type="text" id="classroom"
                                                               name="classroom" required
                                                               value="${exam.classroom.location}">
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
