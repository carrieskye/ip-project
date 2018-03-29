<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE HTML>
<html>
<body>
<div id="container">
    <%@include file="../header.jsp" %>
    <jsp:include page="../head.jsp">
        <jsp:param name="title" value="New course"/>
    </jsp:include>

    <main>
        <c:url var="post_url" value="/course/save.htm"/>
        <form:form modelAttribute="course" method="post" action="${post_url}">
            <p>
                <form:hidden path="id"/>
            </p>

            <p>
                <label for="code">Code:</label>
                <form:input id="code" path="code"/>
                <form:errors path="code" cssClass="has-error"/>
            </p>

            <p>
                <label for="name">Name:</label>
                <form:input type="name" id="name" path="name"/>
                <form:errors path="name" cssClass="has-error"/>
            </p>

            <p>
                <label for="teacher">Teacher:</label>
                <form:select id="teacher" path="teacher">
                    <form:option value="0" label="Select teacher" disabled="true"/>
                    <form:options items="${teachers}" itemValue="id" itemLabel="info"/>
                </form:select>
                <form:errors path="teacher" cssClass="has-error"/>
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
