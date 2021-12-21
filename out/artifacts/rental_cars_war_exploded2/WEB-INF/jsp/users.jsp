<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="en"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="titleAdminPanel" var="titleAdminPanel"/>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../../../favicon.ico">
    <title>${titleAdminPanelUser}</title>

    <link href="${pageContext.request.contextPath}/assets/css/my/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/my/dashboard.css" rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/my/carNewForm.css">

</head>
<body>

    <nav class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0">
        <a class="navbar-brand col-sm-3 col-md-2 mr-0" href="#">${titleAdminPanel}</a>
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
                        <a class="nav-link" href="#">
                            <span data-feather="home"></span>
                            Main <span class="sr-only">(current)</span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=orders_page">
                            <span data-feather="file"></span>
                            Orders
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=cars_page">
                            <span data-feather="shopping-cart"></span>
                            Cars
                        </a>
                    </li>
                    <li class="nav-item active">
                        <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=users_page">
                            <span data-feather="users"></span>
                            Users
                        </a>
                    </li>
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
            <form action="${pageContext.request.contextPath}/controller?command=users" method="POST">
                <div>login: <input type="text" name="login" placeholder="Login"></div>
<%--                <div>login: <input type="text" name="phone" placeholder="Phone"></div>--%>
                <button type="submit">Submit</button>
            </form>
            <c:choose>
                <c:when test="${not empty requestScope.userLogin}">
                    <div class="table-responsive">
                        <table class="table table-striped table-sm">
                            <thead>
                            <tr>
                                <th>Role</th>
                                <th>Login</th>
                                <th>Name</th>
                                <th>Surname</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Document ID</th>
                                <th>Drive license</th>
                                <th>Balance</th>
                                <th>Address</th>
                                <th>EDIT</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <th>${accountLogin.role}</th>
                                <th>${userLogin.login}</th>
                                <th>${accountLogin.name}</th>
                                <th>${accountLogin.surname}</th>
                                <th>${accountLogin.email}</th>
                                <th>${accountLogin.phone}</th>
                                <th>${accountLogin.documentId}</th>
                                <th>${accountLogin.driveLicenseNumber}</th>
                                <th>${accountLogin.balance}</th>
                                <th>${accountLogin.address}</th>
                                <th>edit</th>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </c:when>
                <c:when test="${not empty requestScope.userPhone}">

                </c:when>
                //SHOW ALL USERS
<%--                <c:otherwise>--%>
<%--                    <c:if test="${not empty requestScope.userList}">--%>
<%--                        <div class="table-responsive">--%>
<%--                            <table class="table table-striped table-sm">--%>
<%--                                <thead>--%>
<%--                                <tr>--%>
<%--                                    <th>Role</th>--%>
<%--                                    <th>Login</th>--%>
<%--                                    <th>Name</th>--%>
<%--                                    <th>Surname</th>--%>
<%--                                    <th>Email</th>--%>
<%--                                    <th>Phone</th>--%>
<%--                                    <th>Document ID</th>--%>
<%--                                    <th>Drive license</th>--%>
<%--                                    <th>Balance</th>--%>
<%--                                    <th>Address</th>--%>
<%--                                    <th>EDIT</th>--%>
<%--                                </tr>--%>
<%--                                </thead>--%>
<%--                                <c:forEach var="account" items="${requestScope.accountList}">--%>
<%--                                    <tbody>--%>
<%--                                    <tr>--%>
<%--                                        <th>${account.role}</th>--%>
<%--                                        <th>${userList[userList[account.id-1].accountId-1].login}</th>--%>
<%--                                        <th>${account.name}</th>--%>
<%--                                        <th>${account.surname}</th>--%>
<%--                                        <th>${account.email}</th>--%>
<%--                                        <th>${account.phone}</th>--%>
<%--                                        <th>${account.documentId}</th>--%>
<%--                                        <th>${account.driveLicenseNumber}</th>--%>
<%--                                        <th>${account.balance}</th>--%>
<%--                                        <th>${account.address}</th>--%>
<%--                                        <th>edit</th>--%>
<%--                                    </tr>--%>
<%--                                    </tbody>--%>
<%--                                </c:forEach>--%>
<%--                            </table>--%>
<%--                        </div>--%>
<%--                    </c:if>--%>
<%--                </c:otherwise>--%>
<%--            </c:choose>--%>
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

