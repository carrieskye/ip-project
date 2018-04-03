<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE HTML>
<html>
<body>
<div id="container">
    <%@include file="../header.jsp" %>
    <jsp:include page="../head.jsp">
        <jsp:param name="title" value="${action} classroom"/>
    </jsp:include>

    <main>
        <c:url var="post_url" value="/classroom/save.htm"/>
        <form:form modelAttribute="classroom" method="post" action="${post_url}">
            <p>
                <form:hidden path="id"/>
            </p>

            <p>
                <label for="location">Location:</label>
                <form:input path="location"/>
                <form:errors path="location" cssClass="has-error"/>
            </p>

            <p>
                <label for="seats">Seats:</label>
                <form:input type="number" path="seats"/>
                <form:errors path="seats" cssClass="has-error"/>
            </p>

            <p>
                <label for="type">Type:</label>
                <form:select path="type">
                    <form:option value="" label="Select type" />
                    <form:options items="${types}"/>
                </form:select>
                <form:errors path="type" cssClass="has-error"/>
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
