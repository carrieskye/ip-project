<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE HTML>
<html>
<body>
<div id="container">
    <%@include file="../header.jsp" %>
    <jsp:include page="../head.jsp">
        <jsp:param name="title" value="New classroom"/>
    </jsp:include>

    <main>
        <form id="classroomForm" role="form" method="POST" action="<c:url value="/classroom.htm"/>"
              novalidate="novalidate">
            <p>
                <label for="location">Location</label><input type="text" id="location" name="location" required
                                                             value="${classroom.location}">
            </p>
            <p>
                <label for="seats">Seats</label><input type="number" id="seats" name="seats" required
                                                       value="${classroom.seats}">
            </p>
            <p>
                <label for="type">Type</label><input type="text"
                                                     id="type"
                                                     name="type" required value="${classroom.type}">
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
