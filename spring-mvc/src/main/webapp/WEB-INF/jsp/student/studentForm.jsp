<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML>
<html>
<body>
<div id="container">
    <%@include file="../header.jsp" %>
    <jsp:include page="../head.jsp">
        <jsp:param name="title" value="${action} student"/>
    </jsp:include>

    <main>
        <c:url var="post_url" value="/student/save.htm"/>
        <form:form modelAttribute="student" method="post" action="${post_url}">
            <p>
                <form:hidden path="id"/>
            </p>

            <p>
                <label for="number">Number:</label>
                <form:input id="number" path="number"/>
                <form:errors path="number" cssClass="has-error"/>
            </p>

            <p>
                <label for="firstName">First name:</label>
                <form:input id="firstName" path="firstName"/>
                <form:errors path="firstName" cssClass="has-error"/>
            </p>

            <p>
                <label for="lastName">Last name:</label>
                <form:input id="lastName" path="lastName"/>
                <form:errors path="lastName" cssClass="has-error"/>
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
