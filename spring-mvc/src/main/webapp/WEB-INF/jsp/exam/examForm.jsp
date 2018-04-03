<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<!DOCTYPE HTML>
<html>
<body>
<div id="container">
    <%@include file="../header.jsp" %>
    <jsp:include page="../head.jsp">
        <jsp:param name="title" value="${action} exam"/>
    </jsp:include>

    <main>
        <c:url var="post_url" value="/exam/save.htm"/>
        <form:form modelAttribute="exam" method="post" action="${post_url}">
            <p>
                <form:hidden path="id"/>
            </p>

            <p>
                <label for="course">Course:</label>
                <form:select id="course" path="course">
                    <form:option value="0" label="Select course"/>
                    <form:options items="${courses}" itemValue="id" itemLabel="info"/>
                </form:select>
                <form:errors path="course" cssClass="has-error"/>
            </p>

            <p>
                <label for="date">Date:</label>
                <form:input type="date" id="date" path="date"/>
                <form:errors path="date" cssClass="has-error"/>
            </p>

            <p>
                <label for="begin">Begin:</label>
                <form:input type="time" path="begin"/>
                <form:errors path="begin" cssClass="has-error"/>
                <label for="end">End:</label>
                <form:input type="time" path="end"/>
                <form:errors path="end" cssClass="has-error"/>
            </p>

            <p>
                <label for="classroom">Classroom:</label>
                <form:select path="classroom">
                    <form:option value="0" label="Select classroom"/>
                    <form:options items="${classrooms}" itemValue="id" itemLabel="info"/>
                </form:select>
                <form:errors path="classroom" cssClass="has-error"/>
            </p>

            <p>
                <input id="save" type="submit" value="Save"/>
                <form:errors path="id" cssClass="has-error"/>
            </p>
        </form:form>
    </main>

    <footer> &copy; Webontwikkeling 3, UC Leuven-Limburg</footer>

</div>
</body>
</html>
