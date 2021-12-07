<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.epam.jwd.dao.model.account.Role" %>
<html>
<head>
    <title>Main Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</head>
<body>
<H1>Hello</H1>
<%--<a href="controller?command=show_login">Log in</a> / <a href="controller?command=logout">Logout</a></br>--%>

<c:choose>
    <c:when test="${empty sessionScope.userLogin}">
        <a href="${pageContext.request.contextPath}/controller?command=show_login">Log In</a>
    </c:when>
    <c:otherwise>
        <c:if test="${sessionScope.userRole eq Role.ADMIN.roleId}">
            <c:choose>
                <c:when test="${sessionScope.userRole eq Role.USER.roleId}">Пользователь</c:when>
                <c:when test="${sessionScope.userRole eq Role.ADMIN.roleId}">Администратор</c:when>
            </c:choose></br>
            <a href="${pageContext.request.contextPath}/controller?command=show_users">Users page</a> </br>
        </c:if>
        <a href="${pageContext.request.contextPath}/controller?command=logout">Logout</a>
    </c:otherwise>
</c:choose>
</body>
</html>
