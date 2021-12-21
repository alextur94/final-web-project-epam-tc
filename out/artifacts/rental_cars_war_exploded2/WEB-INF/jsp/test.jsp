<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Test</title>
</head>
<body>
<c:forEach var="car" items="${requestScope.listCars}">
    <p>${car}<p>
</c:forEach>

    <a href="${pageContext.request.contextPath}/controller?command=test&page=1">1</a>

    <c:url var="myURL" value="/controller">
        <c:param name="command" value="test"/>
        <c:param name="page" value="2"/>
    </c:url>
    <a href="${myURL}">2</a>

<%--    <a href="${pageContext.request.contextPath}/controller?command=test?page=2">2</a>--%>
    <a href="${pageContext.request.contextPath}/controller?command=test?page=3">3</a>
</body>
</html>
