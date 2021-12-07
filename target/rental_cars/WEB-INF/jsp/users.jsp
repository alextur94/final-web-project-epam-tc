<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
    <c:if test="${not empty requestScope.users}">
        <table border="1" cellpadding="5">
            <tr>
                <th>Login</th>
                <th>Password</th>
                <th>AccountId</th>
            </tr>
            <c:forEach var="user" items="${requestScope.users}">
            <tr>
                <th>${user.login}</th>
                <th>${user.password}</th>
                <th>${user.accountId}</th>
            </tr>
            </c:forEach>
        </table>
    </c:if>
</body>
</html>
