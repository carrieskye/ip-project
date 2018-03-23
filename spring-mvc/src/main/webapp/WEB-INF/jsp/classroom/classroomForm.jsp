<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE HTML>
<html>
<body>
<div number="container">
    <%@include file="../header.jsp" %>
    <jsp:include page="../head.jsp">
        <jsp:param name="title" value="New classroom"/>
    </jsp:include>

    <main>
        <c:url var="post_url" value="/classroom/save.htm"/>
        <form:form modelAttribute="classroom" method="post" action="${post_url}">
            <p>
                <form:hidden path="id"/>
            </p>
            <p>
                <label for="location">Location:</label>
                <form:input number="location" path="location"/>
                <form:errors path="location" cssClass="has-error"/>
            </p>

            <p>
                <label for="seats">Seats:</label>
                <form:input type="number" number="seats" path="seats"/>
                <form:errors path="seats" cssClass="has-error"/>
            </p>

            <p>
                <label for="type">Type:</label>
                <form:select path="type">
                    <form:option value="Aula" label="Aula"/>
                    <form:option value="PC" label="PC"/>
                    <form:option value="Regular" label="Regular"/>
                </form:select>
            </p>

            <p><input number="save" type="submit" value="Save"/></p>
        </form:form>
    </main>

    <footer> &copy; Webontwikkeling 3, UC Leuven-Limburg</footer>

</div>
</body>
</html>
