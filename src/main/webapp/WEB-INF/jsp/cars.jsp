<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Car</title>
</head>
<body>
<h1>Hello form cars</h1>
<form name="loginForm" method="POST" action="controller">
    <input type="hidden" name="command" value="login" />
    Login:</br>
    <input type="text" name="login" value="" /></br>
    Password:</br>
    <input type="password" name="pass" value="" /></br>
    ${errorLoginPassMessage} </br>
    ${wronAction} </br>
    ${nullPage} </br>
    <input type="submit" value="log in" /></br>
</form
</body>
</html>
