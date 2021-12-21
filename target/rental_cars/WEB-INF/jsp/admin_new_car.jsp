<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${not empty sessionScope.lang ? sessionScope.lang : 'en'}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="lAdminPanel" var="lAdminPanel"/>
<fmt:message bundle="${loc}" key="titleCarPage" var="titleCarPage"/>
<fmt:message bundle="${loc}" key="lOrdersNew" var="lOrdersNew"/>
<fmt:message bundle="${loc}" key="lOrdersReady" var="lOrdersReady"/>
<fmt:message bundle="${loc}" key="lOrdersActive" var="lOrdersActive"/>
<fmt:message bundle="${loc}" key="lOrdersClose" var="lOrdersClose"/>
<fmt:message bundle="${loc}" key="lNewCar" var="lNewCar"/>
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
<fmt:message bundle="${loc}" key="tPriceDay" var="tPriceDay"/>
<fmt:message bundle="${loc}" key="butSaveCar" var="butSaveCar"/>
<fmt:message bundle="${loc}" key="butLogout" var="butLogout"/>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="https://faviconka.ru/ico/1/faviconka.ru_1_103832.ico">
    <title>${titleCarPage}</title>
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
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link"
                               href="${pageContext.request.contextPath}/controller?command=admin_ready_orders_page">
                                <span data-feather="file"></span>
                                ${lOrdersReady}
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link"
                               href="${pageContext.request.contextPath}/controller?command=admin_active_orders_page">
                                <span data-feather="file"></span>
                                ${lOrdersActive}
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link"
                               href="${pageContext.request.contextPath}/controller?command=admin_close_orders_page">
                                <span data-feather="file"></span>
                                ${lOrdersClose}
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active"
                               href="${pageContext.request.contextPath}/controller?command=admin_close_orders_page">
                                <span data-feather="truck"></span>
                                ${lNewCar}
                            </a>
                        </li>
                    </ul>
                </div>
            </nav>

            <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
                <div>
                    <c:if test="${not empty sessionScope.error}">
                        <div class="alert alert-danger alert-dismissible" style="margin-top: 10px">
                            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                ${sessionScope.error}
                            <c:remove var="error" scope="session"/>
                        </div>
                    </c:if>
                </div>
                <div style="float: left;">
                <form action="${pageContext.request.contextPath}/controller?command=new_car" method="POST">
                    <table>
                        <tr>
                            <td class="fw-bolder" width="200px">${brand}</td>
                            <td><input type="text" id="brand" name="brand"></td>
                        </tr>
                        <tr>
                            <td class="fw-bolder">${model}</td>
                            <td><input type="text" id="model" name="model"></td>
                        </tr>
                        <tr>
                            <td class="fw-bolder">${year}</td>
                            <td><input type="number" id="year" name="year"/></td>
                        </tr>
                        <tr>
                            <td class="fw-bolder">${level}</td>
                            <td>
                                <select name="level">
                                    <optgroup>
                                        <option value="1">${economy}</option>
                                        <option value="2">${standard}</option>
                                        <option value="3">${business}</option>
                                        <option value="4">${sport}</option>
                                    </optgroup>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td class="fw-bolder">${body}</td>
                            <td>
                                <select name="body">
                                    <optgroup>
                                        <option value="1">${sedan}</option>
                                        <option value="2">${hatchback}</option>
                                        <option value="3">${svu}</option>
                                        <option value="4">${station_wagon}</option>
                                        <option value="5">${compartment}</option>
                                        <option value="6">${minivan}</option>
                                        <option value="7">${pickup}</option>
                                        <option value="8">${limousine}</option>
                                        <option value="9">${van}</option>
                                        <option value="10">${cabriolet}</option>
                                    </optgroup>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td class="fw-bolder">${engineVolume}</td>
                            <td><input type="number" id="engine_volume" name="engine_volume"></td>
                        </tr>
                        <tr>
                            <td class="fw-bolder">${transmissionAuto}</td>
                            <td><input type="checkbox" id="trans" name="transmission"></td>
                        </tr>
                        <tr>
                            <td class="fw-bolder">${doors}</td>
                            <td><input type="number" id="doors" name="doors"></td>
                        </tr>
                        <tr>
                            <td class="fw-bolder">${color}</td>
                            <td>
                                <select name="color">
                                    <optgroup>
                                        <option value="white">${white}</option>
                                        <option value="black">${black}</option>
                                        <option value="gray">${gray}</option>
                                        <option value="red">${red}</option>
                                        <option value="blue">${blue}</option>
                                        <option value="yellow">${yellow}</option>
                                        <option value="orange">${orange}</option>
                                        <option value="violet">${violet}</option>
                                        <option value="green">${green}</option>
                                        <option value="pink">${pink}</option>
                                        <option value="brown">${brown}</option>
                                    </optgroup>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td class="fw-bolder">${available}</td>
                            <td><input type="checkbox" id="available" name="available" checked></td>
                        </tr>
                        <tr>
                            <td class="fw-bolder">${tPriceDay}</td>
                            <td><input type="number" id="price" name="price_per_day"></td>
                        </tr>
                        <td></td>
                        <td>
                            <button type="submit" class="btn btn-outline-dark" id="btnSubmit">${butSaveCar}</button>
                        </td>
                    </table>
                </form>
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

