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
        <c:url var="post_url" value="/exam/save.htm"/>
        <form:form modelAttribute="exam" method="post" action="${post_url}">
            <p>
                <form:hidden path="id"/>
            </p>

            <p>
                <label for="course">Course code:</label>
                <form:input id="course" path="course"/>
                <form:errors path="course" cssClass="has-error"/>
            </p>

            <p>
                <label for="date">Date:</label>
                <form:input type="datetime-local" id="date" path="date"/>
                <form:errors path="date" cssClass="has-error"/>
            </p>

            <p>
                <label for="classroom">Classroom:</label>
                <form:input id="classroom" path="classroom"/>
                <form:errors path="classroom" cssClass="has-error"/>
            </p>

            <p>
                <input id="save" type="submit" value="Save"/>
            </p>
        </form:form>
    </main>

    <footer> &copy; Webontwikkeling 3, UC Leuven-Limburg</footer>

</div>
</body>
</html>
