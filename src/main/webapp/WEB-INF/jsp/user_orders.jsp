<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.epam.jwd.dao.model.order.Status" %>

<fmt:setLocale value="${not empty sessionScope.lang ? sessionScope.lang : 'en'}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="titleUserPage" var="titleUserPage"/>
<fmt:message bundle="${loc}" key="lUserPanel" var="lUserPanel"/>
<fmt:message bundle="${loc}" key="lAdminPanel" var="lAdminPanel"/>
<fmt:message bundle="${loc}" key="lOrders" var="lOrders"/>
<fmt:message bundle="${loc}" key="butLogout" var="butLogout"/>
<fmt:message bundle="${loc}" key="brand" var="brand"/>
<fmt:message bundle="${loc}" key="model" var="model"/>
<fmt:message bundle="${loc}" key="transmissionAuto" var="transmissionAuto"/>
<fmt:message bundle="${loc}" key="transAuto" var="transAuto"/>
<fmt:message bundle="${loc}" key="transMan" var="transMan"/>
<fmt:message bundle="${loc}" key="tPriceDay" var="tPriceDay"/>
<fmt:message bundle="${loc}" key="statusWaitPayment" var="statusWaitPayment"/>
<fmt:message bundle="${loc}" key="statusNew" var="statusNew"/>
<fmt:message bundle="${loc}" key="statusReady" var="statusReady"/>
<fmt:message bundle="${loc}" key="statusActive" var="statusActive"/>
<fmt:message bundle="${loc}" key="statusBlock" var="statusBlock"/>
<fmt:message bundle="${loc}" key="statusClose" var="statusClose"/>
<fmt:message bundle="${loc}" key="butPay" var="butPay"/>
<fmt:message bundle="${loc}" key="butCancelOrder" var="butCancelOrder"/>
<fmt:message bundle="${loc}" key="lPaid" var="lPaid"/>
<fmt:message bundle="${loc}" key="lNoOrders" var="lNoOrders"/>
<fmt:message bundle="${loc}" key="lDetails" var="lDetails"/>
<fmt:message bundle="${loc}" key="butCars" var="butCars"/>
<fmt:message bundle="${loc}" key="butPrevious" var="butPrevious"/>
<fmt:message bundle="${loc}" key="butNext" var="butNext"/>
<fmt:message bundle="${loc}" key="lStatus" var="lStatus"/>
<fmt:message bundle="${loc}" key="lDate" var="lDate"/>
<fmt:message bundle="${loc}" key="lCar" var="lCar"/>
<fmt:message bundle="${loc}" key="lRentalTime" var="lRentalTime"/>
<fmt:message bundle="${loc}" key="lRefusal" var="lRefusal"/>
<fmt:message bundle="${loc}" key="lAmount" var="lAmount"/>

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
                           href="${pageContext.request.contextPath}/controller?command=user_panel_page">
                            <span data-feather="home"></span>
                            ${lUserPanel}
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active"
                           href="${pageContext.request.contextPath}/controller?command=user_orders_page">
                            <span data-feather="file"></span>
                            ${lOrders}
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/controller?command=cars_page">
                            <span data-feather="truck"></span>
                            ${butCars}
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
            <c:if test="${not empty sessionScope.success}">
                <div class="alert alert-success alert-dismissible" style="margin-top: 10px">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                        ${sessionScope.success}
                    <c:remove var="success" scope="session"/>
                </div>
            </c:if>
            <h2>${lOrders}</h2>
            <c:choose>
                <c:when test="${not empty requestScope.orderList}">
                    <div class="table-responsive">
                        <table class="table table-striped table-sm">
                            <thead>
                            <tr>
                                <th>${lStatus}</th>
                                <th>${lDate}</th>
                                <th>${lCar}</th>
                                <th>${transmissionAuto}</th>
                                <th>${lRentalTime}</th>
                                <th>${lRefusal}</th>
                                <th>${lAmount}</th>
                                <th>${butPay}</th>
                                <th>${butCancelOrder}</th>
                            </thead>
                            <c:forEach var="order" items="${requestScope.orderList}">
                                <tbody>
                                <tr>
                                    <td>
                                        <c:choose>
                                            <c:when test="${order.status eq 1}">${statusWaitPayment}</c:when>
                                            <c:when test="${order.status eq 2}">${statusNew}</c:when>
                                            <c:when test="${order.status eq 3}">${statusReady}</c:when>
                                            <c:when test="${order.status eq 4}">${statusActive}</c:when>
                                            <c:when test="${order.status eq 5}">${statusBlock}</c:when>
                                            <c:when test="${order.status eq 6}">${statusClose}</c:when>
                                        </c:choose>
                                    </td>
                                    <td>${order.rentStartDtm}</td>
                                    <td>
                                        ${carList[order.carId-1].brand} ${carList[order.carId-1].model}
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${carList[order.carId-1].transmission eq 0}">${transMan}</c:when>
                                            <c:when test="${carList[order.carId-1].transmission eq 1}">${transAuto}</c:when>
                                        </c:choose>
                                    </td>
                                    <td>${order.rentalTime}</td>
                                    <td>${order.refusal}</td>
                                    <td>${order.currentSum}0</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${order.paymentStatus eq 0}"><a href="${pageContext.request.contextPath}/controller?command=pay&orderId=${order.id}">${butPay}</a></c:when>
                                            <c:otherwise>${lPaid}</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${order.status < Status.ACTIVE.id}">
                                                <c:set scope="session" var="orderId" value="${order.id}"/>
                                                <a href="${pageContext.request.contextPath}/controller?command=cancel_order">${butCancelOrder}</a>
                                            </c:when>
                                            <c:otherwise></c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                                </tbody>
                            </c:forEach>
                        </table>
                    </div>
                    <nav aria-label="Page navigation example">
                        <ul class="pagination justify-content-center">
                            <c:if test="${currentPage != 1}">
                                <li class="page-item">
                                    <a class="page-link" href="${pageContext.request.contextPath}/controller?command=user_orders_page&page=${currentPage - 1}" tabindex="-1">${butPrevious}</a>
                                </li>
                            </c:if>
                            <c:forEach begin="1" end="${requestScope.pages}" var="i">
                                <c:choose>
                                    <c:when test="${currentPage eq i}">
                                        <li class="page-item disabled"><a class="page-link" href="">${i}</a></li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/controller?command=user_orders_page&page=${i}">${i}</a></li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <c:if test="${currentPage lt requestScope.pages}">
                                <li class="page-item">
                                    <a class="page-link" href="${pageContext.request.contextPath}/controller?command=user_orders_page&page=${currentPage + 1}">${butNext}</a>
                                </li>
                            </c:if>
                        </ul>
                    </nav>
                </c:when>
                <c:otherwise>
                    <div>${lNoOrders}</div>
                </c:otherwise>
            </c:choose>
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

