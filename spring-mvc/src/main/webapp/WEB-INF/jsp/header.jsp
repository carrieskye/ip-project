<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>Home</title>
    <link rel="stylesheet" type="text/css" href="css/sample.css">
</head>

<header>
    <nav>
        <ul>
            <li><a href="index.htm">
                <img id="logo" src="${pageContext.request.contextPath}/images/ucll-logo.png" alt="Home"></a></li>
            <li><a href="<c:url value="/classroom.htm"/>">Classrooms</a></li>
            <li><a href="<c:url value="/course.htm"/>">Courses</a></li>
            <li><a href="<c:url value="/exam.htm"/>">Exams</a></li>
            <li><a href="<c:url value="/student.htm"/>">Students</a></li>
            <li><a href="<c:url value="/teacher.htm"/>">Teachers</a></li>
        </ul>
    </nav>
</header>
</html>