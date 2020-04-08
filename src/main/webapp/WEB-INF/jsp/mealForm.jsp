<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Meal</title>
    <meta charset="UTF-8">
</head>
<body>
<h1>Meal</h1>
<spring:form modelAttribute="meal" action="save">
    <p>Description <spring:input path="description"/></p>
    <p>Date time <spring:input path="dateTime"/></p>
    <p>Calories <spring:input path="calories"/></p>
    <input type="submit"/>
</spring:form>
</body>
</html>
