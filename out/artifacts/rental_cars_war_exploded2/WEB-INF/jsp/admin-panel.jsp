<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="en"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="titleAdminPanelPage" var="titleAdminPanelPage"/>
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
    <title>Dashboard Template for Bootstrap</title>

    <link href="${pageContext.request.contextPath}/assets/css/my/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/my/dashboard.css" rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/my/carNewForm.css">
</head>
<body>
    <nav class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0">
        <a class="navbar-brand col-sm-3 col-md-2 mr-0" href="#">Company name</a>
        <input class="form-control form-control-dark w-100" type="text" placeholder="Search" aria-label="Search">
        <ul class="navbar-nav px-3">
            <li class="nav-item text-nowrap">
                <a class="nav-link" href="#">Sign out</a>
            </li>
        </ul>
    </nav>

    <div class="container-fluid">
        <div class="row">
            <nav class="col-md-2 d-none d-md-block bg-light sidebar">
                <div class="sidebar-sticky">
                    <ul class="nav flex-column">
                        <li class="nav-item">
                            <a class="nav-link active" href="#">
                                <span data-feather="home"></span>
                                Admin panel <span class="sr-only">(current)</span>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=orders">
                                <span data-feather="file"></span>
                                Orders
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=cars">
                                <span data-feather="shopping-cart"></span>
                                Cars
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=users">
                                <span data-feather="users"></span>
                                Users
                            </a>
                        </li>
    <%--                    <li class="nav-item">--%>
    <%--                        <a class="nav-link" href="#">--%>
    <%--                            <span data-feather="bar-chart-2"></span>--%>
    <%--                            Reports--%>
    <%--                        </a>--%>
    <%--                    </li>--%>
    <%--                    <li class="nav-item">--%>
    <%--                        <a class="nav-link" href="#">--%>
    <%--                            <span data-feather="layers"></span>--%>
    <%--                            Integrations--%>
    <%--                        </a>--%>
    <%--                    </li>--%>
                    </ul>

                    <h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
                        <span>Saved reports</span>
                        <a class="d-flex align-items-center text-muted" href="#">
                            <span data-feather="plus-circle"></span>
                        </a>
                    </h6>
                    <ul class="nav flex-column mb-2">
                        <li class="nav-item">
                            <a class="nav-link" href="#">
                                <span data-feather="file-text"></span>
                                Current month
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">
                                <span data-feather="file-text"></span>
                                Last quarter
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">
                                <span data-feather="file-text"></span>
                                Social engagement
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">
                                <span data-feather="file-text"></span>
                                Year-end sale
                            </a>
                        </li>
                    </ul>
                </div>
            </nav>

            <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">

                <h2>Section title</h2>
                <c:if test="${not empty requestScope.users}">
                    <div class="table-responsive">
                        <table class="table table-striped table-sm">
                            <thead>
                            <tr>
                                <th>Login</th>
                                <th>Password</th>
                                <th>AccountId</th>
                            </tr>
                            </thead>
                            <c:forEach var="user" items="${requestScope.users}">
                                <tbody>
                                <tr>
                                    <th>${user.login}</th>
                                    <th>${user.password}</th>
                                    <th>${user.accountId}</th>
                                </tr>
                                </tbody>
                            </c:forEach>
                        </table>
                    </div>
                </c:if>
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
