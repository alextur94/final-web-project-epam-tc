<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.epam.jwd.dao.model.account.Role"%>

<c:choose>
    <c:when test="${sessionScope.id ne userId}">
<%--        ОШИБКА при исправлении вручную в url или id или car id--%>
        <jsp:forward page="/WEB-INF/jsp/error/error500.jsp"/>
    </c:when>
    <c:when test="${car.available eq 0}">
        <%--        ОШИБКА при исправлении вручную в url или id или car id--%>
        <jsp:forward page="/WEB-INF/jsp/error/error500.jsp"/>
    </c:when>
</c:choose>

<fmt:setLocale value="en"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="titleOrderPage" var="titleOrderPage"/>
<fmt:message bundle="${loc}" key="butMenu" var="butMenu"/>
<fmt:message bundle="${loc}" key="butHome" var="butHome"/>
<fmt:message bundle="${loc}" key="butCars" var="butCars"/>
<fmt:message bundle="${loc}" key="butContacts" var="butContacts"/>
<fmt:message bundle="${loc}" key="butAboutUs" var="butAboutUs"/>
<fmt:message bundle="${loc}" key="butLogin" var="butLogin"/>
<fmt:message bundle="${loc}" key="butLogout" var="butLogout"/>
<fmt:message bundle="${loc}" key="butAccount" var="butAccount"/>
<fmt:message bundle="${loc}" key="brand" var="brand"/>
<fmt:message bundle="${loc}" key="model" var="model"/>
<fmt:message bundle="${loc}" key="year" var="year"/>
<fmt:message bundle="${loc}" key="level" var="level"/>
<fmt:message bundle="${loc}" key="economy" var="economy"/>
<fmt:message bundle="${loc}" key="standard" var="standard"/>
<fmt:message bundle="${loc}" key="business" var="business"/>
<fmt:message bundle="${loc}" key="sport" var="sport"/>
<fmt:message bundle="${loc}" key="body" var="body"/>
<fmt:message bundle="${loc}" key="sedan" var="sedan"/>
<fmt:message bundle="${loc}" key="hatchback" var="hatchback"/>
<fmt:message bundle="${loc}" key="svu" var="svu"/>
<fmt:message bundle="${loc}" key="stationWagon" var="station_wagon"/>
<fmt:message bundle="${loc}" key="compartment" var="compartment"/>
<fmt:message bundle="${loc}" key="minivan" var="minivan"/>
<fmt:message bundle="${loc}" key="pickup" var="pickup"/>
<fmt:message bundle="${loc}" key="limousine" var="limousine"/>
<fmt:message bundle="${loc}" key="van" var="van"/>
<fmt:message bundle="${loc}" key="cabriolet" var="cabriolet"/>
<fmt:message bundle="${loc}" key="engineVolume" var="engineVolume"/>
<fmt:message bundle="${loc}" key="transmissionAuto" var="transmissionAuto"/>
<fmt:message bundle="${loc}" key="doors" var="doors"/>
<fmt:message bundle="${loc}" key="color" var="color"/>
<fmt:message bundle="${loc}" key="available" var="available"/>

<fmt:message bundle="${loc}" key="white" var="white"/>
<fmt:message bundle="${loc}" key="black" var="black"/>
<fmt:message bundle="${loc}" key="gray" var="gray"/>
<fmt:message bundle="${loc}" key="red" var="red"/>
<fmt:message bundle="${loc}" key="blue" var="blue"/>
<fmt:message bundle="${loc}" key="yellow" var="yellow"/>
<fmt:message bundle="${loc}" key="orange" var="orange"/>
<fmt:message bundle="${loc}" key="green" var="green"/>
<fmt:message bundle="${loc}" key="pink" var="pink"/>
<fmt:message bundle="${loc}" key="violet" var="violet"/>
<fmt:message bundle="${loc}" key="brown" var="brown"/>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../../../favicon.ico">

    <title>${titleOrderPage}</title>

    <link href="${pageContext.request.contextPath}/assets/css/my/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/my/dashboard.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/my/carNewForm.css">
</head>
<body>

    <nav class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0">
        <a class="navbar-brand col-sm-3 col-md-2 mr-0" href="${pageContext.request.contextPath}/controller">CarRent</a>
        <ul class="navbar-nav px-3">
            <li class="nav-item text-nowrap">
                <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=logout">Logout</a>
            </li>
        </ul>
    </nav>

    <div class="container-fluid">
        <div class="row">
            <nav class="col-md-2 d-none d-md-block bg-light sidebar">
                <div class="sidebar-sticky">
                    <ul class="nav flex-column">
                        <li class="nav-item">
                            <c:choose>
                                <c:when test="${sessionScope.userRole eq Role.USER}">
                                    <a class="nav-link active" href="${pageContext.request.contextPath}/controller?command=user_panel_page">
                                        <span data-feather="home"></span>
                                            ${userPanel}
                                    </a>
                                </c:when>
                                <c:when test="${sessionScope.userRole eq Role.ADMIN}">
                                    <a class="nav-link active" href="${pageContext.request.contextPath}/controller?command=admin_panel_page">
                                        <span data-feather="home"></span>
                                            ${adminPanel}
                                    </a>
                                </c:when>
                            </c:choose>
                        </li>
                        <li class="nav-item">
                            <c:choose>
                                <c:when test="${sessionScope.userRole eq Role.USER}">
                                    <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=user_orders_page">
                                        <span data-feather="file"></span>
                                        ${orders}
                                    </a>
                                </c:when>
                            </c:choose>
                        </li>
                    </ul>
                </div>
            </nav>

            <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
                <h2>${newOrder}</h2>
                <div>
                    ${car.brand} ${car.model}
                    Год выпуска ${car.year}
                    Коробка ${car.transmission}
                    Тип кузова ${car.body}
                    Цвет ${car.color}
                    Кол-во дверей ${car.doors}
                    Класс автомобиля ${car.level}
                    <form action="${pageContext.request.contextPath}/controller?command=new_order&carId=${car.id}&userId=${sessionScope.id}" method="GET">
                        <label>Введите кол-во дней аренды:</label>
                        <div class="input-div">
                            <input type="number" name="day" placeholder="days">
                        </div>
                        <button type="submit">Отправить завяку</button>
                    </form>
                </div>
            </main>
        </div>
    </div>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script>window.jQuery || document.write('<script src="../../../../assets/js/vendor/jquery-slim.min.js"><\/script>')</script>
<script src="../../../../assets/js/vendor/popper.min.js"></script>
<script src="../../../../dist/js/bootstrap.min.js"></script>

<!-- Icons -->
<script src="https://unpkg.com/feather-icons/dist/feather.min.js"></script>
<script>
    feather.replace()
</script>

<!-- Graphs -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.1/Chart.min.js"></script>
<script>
    var ctx = document.getElementById("myChart");
    var myChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"],
            datasets: [{
                data: [15339, 21345, 18483, 24003, 23489, 24092, 12034],
                lineTension: 0,
                backgroundColor: 'transparent',
                borderColor: '#007bff',
                borderWidth: 4,
                pointBackgroundColor: '#007bff'
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: false
                    }
                }]
            },
            legend: {
                display: false,
            }
        }
    });
</script>
</body>
</html>

