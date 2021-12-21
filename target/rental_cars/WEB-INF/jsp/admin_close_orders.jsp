<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${not empty sessionScope.lang ? sessionScope.lang : 'en'}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="titleUserPage" var="titleUserPage"/>
<fmt:message bundle="${loc}" key="lUserPanel" var="lUserPanel"/>
<fmt:message bundle="${loc}" key="lAdminPanel" var="lAdminPanel"/>
<fmt:message bundle="${loc}" key="lOrdersNew" var="lOrdersNew"/>
<fmt:message bundle="${loc}" key="lOrdersReady" var="lOrdersReady"/>
<fmt:message bundle="${loc}" key="lOrdersActive" var="lOrdersActive"/>
<fmt:message bundle="${loc}" key="lOrdersBlock" var="lOrdersBlock"/>
<fmt:message bundle="${loc}" key="butLogout" var="butLogout"/>
<fmt:message bundle="${loc}" key="brand" var="brand"/>
<fmt:message bundle="${loc}" key="model" var="model"/>
<fmt:message bundle="${loc}" key="transmissionAuto" var="transmissionAuto"/>
<fmt:message bundle="${loc}" key="transAuto" var="transAuto"/>
<fmt:message bundle="${loc}" key="transMan" var="transMan"/>
<fmt:message bundle="${loc}" key="tPriceDay" var="tPriceDay"/>
<fmt:message bundle="${loc}" key="statusWaitPayment" var="statusWaitPayment"/>
<fmt:message bundle="${loc}" key="statusNew" var="statusNew"/>
<fmt:message bundle="${loc}" key="statusActive" var="statusActive"/>
<fmt:message bundle="${loc}" key="statusBlock" var="statusBlock"/>
<fmt:message bundle="${loc}" key="statusClose" var="statusClose"/>
<fmt:message bundle="${loc}" key="butPay" var="butPay"/>
<fmt:message bundle="${loc}" key="butCancelOrder" var="butCancelOrder"/>
<fmt:message bundle="${loc}" key="lPaid" var="lPaid"/>
<fmt:message bundle="${loc}" key="lDetails" var="lDetails"/>
<fmt:message bundle="${loc}" key="butApprove" var="butApprove"/>
<fmt:message bundle="${loc}" key="butStart" var="bitStart"/>
<fmt:message bundle="${loc}" key="butCancelOrder" var="butCancelOrder"/>
<fmt:message bundle="${loc}" key="lOrdersClose" var="lOrdersClose"/>
<fmt:message bundle="${loc}" key="lNewCar" var="lNewCar"/>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="https://faviconka.ru/ico/1/faviconka.ru_1_103832.ico">
    <title>${titleUserPage}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>
    <link href="/examples/vendors/bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/my/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/my/dashboard.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/my/carNewForm.css">
</head>
<body>

    <nav class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0">
        <a class="navbar-brand col-sm-3 col-md-2 mr-0" href="${pageContext.request.contextPath}/controller">CarRent</a>
        <ul class="navbar-nav px-3">
            <li class="nav-item text-nowrap">
                <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=logout">${butLogout}</a>
            </li>
        </ul>
    </nav>

    <div class="container-fluid">
        <div class="row">
            <nav class="col-md-2 d-none d-md-block bg-light sidebar">
                <div class="sidebar-sticky">
                    <ul class="nav flex-column">
                        <li class="nav-item">
                            <a class="nav-link"
                               href="${pageContext.request.contextPath}/controller?command=admin_panel_page">
                                <span data-feather="home"></span>
                                ${lAdminPanel}
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link"
                               href="${pageContext.request.contextPath}/controller?command=admin_orders_page">
                                <span data-feather="file"></span>
                                ${lOrdersNew}
                                (${countRow[0]})
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link"
                               href="${pageContext.request.contextPath}/controller?command=admin_ready_orders_page">
                                <span data-feather="file"></span>
                                ${lOrdersReady}
                                (${countRow[1]})
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link"
                               href="${pageContext.request.contextPath}/controller?command=admin_active_orders_page">
                                <span data-feather="file"></span>
                                ${lOrdersActive}
                                (${countRow[2]})
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active"
                               href="${pageContext.request.contextPath}/controller?command=admin_close_orders_page">
                                <span data-feather="file"></span>
                                ${lOrdersClose}
                                (${orderList.size()})
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link"
                               href="${pageContext.request.contextPath}/controller?command=admin_new_car_page">
                                <span data-feather="truck"></span>
                                ${lNewCar}
                            </a>
                        </li>
                    </ul>
                </div>
            </nav>
            <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
                    <c:if test="${not empty sessionScope.error}">
                        <div class="alert alert-danger alert-dismissible" style="margin-top: 10px">
                            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                ${sessionScope.error}
                            <c:remove var="error" scope="session"/>
                        </div>
                    </c:if>
                    <div class="table-responsive">
                        <table class="table table-striped table-sm">
                            <thead>
                            <tr>
                                <th>Order ID</th>
                                <th>Car</th>
                                <th>Name</th>
                                <th>Surname</th>
                                <th>Phone</th>
                                <th>Begin</th>
                                <th>Cancel</th>
                            </thead>
                            <c:forEach var="order" items="${sessionScope.orderList}">
                                <tbody>
                                <tr>
                                    <td> ${order.id}</td>
                                    <td>
                                        ${carList[order.carId-1].brand} ${carList[order.carId-1].model}
                                    </td>
                                    <td>${sessionScope.personMap.get(order.userId).name}</td>
                                    <td>${sessionScope.personMap.get(order.userId).surname}</td>
                                    <td>${sessionScope.personMap.get(order.userId).phone}</td>
                                    <td>
                                        <c:set scope="session" var="orderId" value="${order.id}"/>
                                        <a href="${pageContext.request.contextPath}/controller?command=begin_rent">${bitStart}</a>
                                    </td>
                                    <td>
                                        <c:set scope="session" var="orderId" value="${order.id}"/>
                                        <a href="${pageContext.request.contextPath}/controller?command=cancel_order_admin">${butCancelOrder}</a>
                                    </td>
                                </tr>
                                </tbody>
                            </c:forEach>
                        </table>
                    </div>
            </main>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
            integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
            crossorigin="anonymous"></script>
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

