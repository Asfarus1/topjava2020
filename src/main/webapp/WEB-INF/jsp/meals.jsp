<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Meals</title>
    <link rel="stylesheet" href="<spring:url value='/css/style.css'/>">
</head>
<body>
<h3><a href="<spring:url value="/"/>">Home</a></h3>
<h2>Meals</h2>
<hr>
<a href="meals/new">Add new meal</a>
<table>
    <thead>
    <tr>
        <td>Date/Time</td>
        <td>Description</td>
        <td>Calories</td>
        <td></td>
        <td></td>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${meals}" var="meal">
        <tr class="${meal.excess ? "excess" : ''}">
            <td>${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals/edit?id=${meal.id}">edit</a></td>
            <td><a href="meals/remove?id=${meal.id}">remove</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
