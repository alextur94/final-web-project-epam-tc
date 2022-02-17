<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error Page 500</title>
</head>
<body>
    Request from ${pageContext.errorData.requestURI} is failed </br>
    Servlet name: ${pageContext.errorData.servletName} </br>
    Status code: ${pageContext.errorData.statusCode} </br>
    Exception: ${pageContext.exception} </br>
    Message from exception: ${pageContext.exception.message} </br>
    StackTrace:</br>
    <c:forEach var="stack" items="${pageContext.exception.stackTrace}">
            ${stack.toString()} </br>
    </c:forEach>
    <a href="index.jsp">Back to index</a>
</body>
</html>
