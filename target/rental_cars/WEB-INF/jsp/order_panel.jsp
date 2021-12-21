<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.epam.jwd.dao.model.account.Role" %>

<fmt:setLocale value="${not empty sessionScope.lang ? sessionScope.lang : 'en'}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="noInfoUser" var="noInfoUser"/>
<fmt:message bundle="${loc}" key="carIsBooking" var="carIsBooking"/>

<c:choose>
    <c:when test="${requestScope.car.available eq 0}">
        <c:set scope="session" var="error" value="${carIsBooking}"/>
        <c:redirect url="${pageContext.request.contextPath}/controller?command=user_panel_page"/>
    </c:when>
    <c:when test="${sessionScope.account.status eq 0}">
        <c:set var="error" scope="session" value="${noInfoUser}" />
        <c:redirect url="${pageContext.request.contextPath}/controller?command=user_panel_page"/>
    </c:when>
</c:choose>


<fmt:message bundle="${loc}" key="titleOrderPage" var="titleOrderPage"/>
<fmt:message bundle="${loc}" key="lUserPanel" var="lUserPanel"/>
<fmt:message bundle="${loc}" key="lAdminPanel" var="lAdminPanel"/>
<fmt:message bundle="${loc}" key="lOrders" var="lOrders"/>
<fmt:message bundle="${loc}" key="lCar" var="lCar"/>
<fmt:message bundle="${loc}" key="butLogout" var="butLogout"/>
<fmt:message bundle="${loc}" key="butRent" var="butRent"/>
<fmt:message bundle="${loc}" key="brand" var="brand"/>
<fmt:message bundle="${loc}" key="model" var="model"/>
<fmt:message bundle="${loc}" key="year" var="year"/>
<fmt:message bundle="${loc}" key="engineVolume" var="engineVolume"/>
<fmt:message bundle="${loc}" key="doors" var="doors"/>
<fmt:message bundle="${loc}" key="level" var="level"/>
<fmt:message bundle="${loc}" key="economy" var="economy"/>
<fmt:message bundle="${loc}" key="standard" var="standard"/>
<fmt:message bundle="${loc}" key="business" var="business"/>
<fmt:message bundle="${loc}" key="sport" var="sport"/>
<fmt:message bundle="${loc}" key="transmissionAuto" var="transmissionAuto"/>
<fmt:message bundle="${loc}" key="transAuto" var="transAuto"/>
<fmt:message bundle="${loc}" key="transMan" var="transMan"/>
<fmt:message bundle="${loc}" key="body" var="body"/>
<fmt:message bundle="${loc}" key="sedan" var="sedan"/>
<fmt:message bundle="${loc}" key="hatchback" var="hatchback"/>
<fmt:message bundle="${loc}" key="svu" var="svu"/>
<fmt:message bundle="${loc}" key="stationWagon" var="stationWagon"/>
<fmt:message bundle="${loc}" key="compartment" var="compartment"/>
<fmt:message bundle="${loc}" key="minivan" var="minivan"/>
<fmt:message bundle="${loc}" key="pickup" var="pickup"/>
<fmt:message bundle="${loc}" key="limousine" var="limousine"/>
<fmt:message bundle="${loc}" key="van" var="van"/>
<fmt:message bundle="${loc}" key="cabriolet" var="cabriolet"/>
<fmt:message bundle="${loc}" key="color" var="color"/>
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
<fmt:message bundle="${loc}" key="tPriceDay" var="tPriceDay"/>
<fmt:message bundle="${loc}" key="valPrice" var="valPrice"/>
<fmt:message bundle="${loc}" key="available" var="available"/>
<fmt:message bundle="${loc}" key="lCountDayRent" var="lCountDayRent"/>
<fmt:message bundle="${loc}" key="lCountDayRent" var="lCountDayRent"/>
<fmt:message bundle="${loc}" key="lCountDayRent" var="lCountDayRent"/>
<fmt:message bundle="${loc}" key="lTypeInsurance" var="lTypeInsurance"/>
<fmt:message bundle="${loc}" key="lInsuranceDesc1" var="lInsuranceDesc1"/>
<fmt:message bundle="${loc}" key="lInsuranceDesc2" var="lInsuranceDesc2"/>
<fmt:message bundle="${loc}" key="lInsuranceDesc3" var="lInsuranceDesc3"/>
<fmt:message bundle="${loc}" key="tPledge" var="tPledge"/>
<fmt:message bundle="${loc}" key="tInfoPledge" var="tInfoPledge"/>
<fmt:message bundle="${loc}" key="lNewOrder" var="lNewOrder"/>
<fmt:message bundle="${loc}" key="butBackToCars" var="butBackToCars"/>
<fmt:message bundle="${loc}" key="lPageNotAvailable" var="lPageNotAvailable"/>
<fmt:message bundle="${loc}" key="butCars" var="butCars"/>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="https://faviconka.ru/ico/1/faviconka.ru_1_103832.ico">
    <title>${titleOrderPage}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
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
                        <c:choose>
                            <c:when test="${sessionScope.account.role eq Role.USER}">
                                <li class="nav-item">
                                    <a class="nav-link"
                                       href="${pageContext.request.contextPath}/controller?command=user_panel_page&userId=${sessionScope.userId}">
                                        <span data-feather="home"></span>
                                            ${lUserPanel}
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link"
                                       href="${pageContext.request.contextPath}/controller?command=user_orders_page&userId=${sessionScope.userId}">
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
                            </c:when>
                            <c:when test="${sessionScope.account.role eq Role.ADMIN}">
                                <li>
                                    <a class="nav-link"
                                       href="${pageContext.request.contextPath}/controller?command=admin_panel_page">
                                        <span data-feather="home"></span>
                                            ${lAdminPanel}
                                    </a>
                                </li>
                            </c:when>
                        </c:choose>
                    </ul>
                </div>
            </nav>

            <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
                <c:choose>
                    <c:when test="${not empty requestScope.error}">
                        <div class="alert alert-danger" role="alert" style="margin-top: 10px">
                                ${error}<a href="${pageContext.request.contextPath}/controller?command=cars_page" class="alert-link">${butBackToCars}</a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <h2>${lNewOrder}</h2>
                        <table>
                            <tr>
                                <td class="fw-bolder">${lCar}</td>
                                <td>${car.brand} ${car.model}</td>
                            </tr>
                            <tr>
                                <td style="width: 210px" class="fw-bolder">${year}</td>
                                <td>${car.year}</td>
                            </tr>
                            <tr>
                                <td class="fw-bolder">${transmissionAuto}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${car.transmission eq 0}">${transMan}</c:when>
                                        <c:when test="${car.transmission eq 1}">${transAuto}</c:when>
                                    </c:choose>
                                </td>
                            </tr>
                            <tr>
                                <td class="fw-bolder">${body}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${car.body eq 1}">${sedan}</c:when>
                                        <c:when test="${car.body eq 2}">${hatchback}</c:when>
                                        <c:when test="${car.body eq 3}">${svu}</c:when>
                                        <c:when test="${car.body eq 4}">${stationWagon}</c:when>
                                        <c:when test="${car.body eq 5}">${compartment}</c:when>
                                        <c:when test="${car.body eq 6}">${minivan}</c:when>
                                        <c:when test="${car.body eq 7}">${pickup}</c:when>
                                        <c:when test="${car.body eq 8}">${limousine}</c:when>
                                        <c:when test="${car.body eq 9}">${van}</c:when>
                                        <c:when test="${car.body eq 10}">${cabriolet}</c:when>
                                    </c:choose>
                                </td>
                            </tr>
                            <tr>
                                <td class="fw-bolder">${color}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${car.color eq 'white'}">${white}</c:when>
                                        <c:when test="${car.color eq 'black'}">${black}</c:when>
                                        <c:when test="${car.color eq 'gray'}">${gray}</c:when>
                                        <c:when test="${car.color eq 'red'}">${red}</c:when>
                                        <c:when test="${car.color eq 'blue'}">${blue}</c:when>
                                        <c:when test="${car.color eq 'yellow'}">${yellow}</c:when>
                                        <c:when test="${car.color eq 'orange'}">${orange}</c:when>
                                        <c:when test="${car.color eq 'violet'}">${violet}</c:when>
                                        <c:when test="${car.color eq 'green'}">${green}</c:when>
                                        <c:when test="${car.color eq 'pink'}">${pink}</c:when>
                                        <c:when test="${car.color eq 'brown'}">${brown}</c:when>
                                    </c:choose>
                                </td>
                            </tr>
                            <tr>
                                <td class="fw-bolder">${doors}</td>
                                <td>${car.doors}</td>
                            </tr>
                            <tr>
                                <td class="fw-bolder">${level}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${car.level eq 1}">${economy}</c:when>
                                        <c:when test="${car.level eq 2}">${standard}</c:when>
                                        <c:when test="${car.level eq 3}">${business}</c:when>
                                        <c:when test="${car.level eq 4}">${sport}</c:when>
                                    </c:choose>
                                </td>
                            </tr>
                            <tr>
                                <td class="fw-bolder">${tPledge}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${car.level eq 1}">100.00 ${valPrice}</c:when>
                                        <c:when test="${car.level eq 2}">220.00 ${valPrice}</c:when>
                                        <c:when test="${car.level eq 3}">350.00 ${valPrice}</c:when>
                                        <c:when test="${car.level eq 4}">500.00 ${valPrice}</c:when>
                                    </c:choose>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" class="fw-lighter">${tInfoPledge}</td>
                            </tr>
                            <tr>
                                <td class="fw-bolder">${tPriceDay}</td>
                                <td>${priceDay}0 ${valPrice}</td>
                            </tr>
                            <form action="${pageContext.request.contextPath}/controller?command=new_order&carId=${car.id}"
                                  method="POST">
                                <tr>
                                    <td class="fw-bolder">${lCountDayRent}</td>
                                    <td><input type="number" name="day"></td>
                                </tr>
                                <tr>
                                    <td class="fw-bolder">${lTypeInsurance}</td>
                                    <td><select name="type">
                                        <optgroup>
                                            <option value="1">${lInsuranceDesc1}</option>
                                            <option value="2">${lInsuranceDesc2}</option>
                                            <option value="3">${lInsuranceDesc3}</option>
                                        </optgroup>
                                    </select></td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <button type="submit" class="btn btn-outline-dark">${butRent}</button>
                                    </td>
                                </tr>
                            </form>
                        </table>
                    </c:otherwise>
                </c:choose>
            </main>
        </div>
    </div>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
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

