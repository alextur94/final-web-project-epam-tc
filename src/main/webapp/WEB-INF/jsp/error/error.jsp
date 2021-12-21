<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:choose>
    <c:when test="${sessionScope.userId ne userId}">
        <jsp:forward page="/WEB-INF/jsp/error/error500.jsp"/>
    </c:when>
</c:choose>
<html>
<head>
    <title>Errors 400</title>
    <style>
        div.error {
            height: 10em;
            position: relative }
        .content {
            margin: 0;
            background: yellow;
            position: absolute;
            top: 50%;
            left: 50%;
            margin-right: -50%;
            transform: translate(-50%, -50%) }
    </style>
</head>
<body>
<div class="error">
    <div class="content">
        <h2>${error}</h2>
        <a href="${pageContext.request.contextPath}/controller">Go To Homepage</a>
    </div>
</div>
</body>
</html>
