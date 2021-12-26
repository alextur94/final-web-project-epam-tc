<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${not empty sessionScope.lang ? sessionScope.lang : 'en'}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="titleUserPage" var="titleUserPage"/>
<fmt:message bundle="${loc}" key="lUserPanel" var="lUserPanel"/>
<fmt:message bundle="${loc}" key="butLogout" var="butLogout"/>
<fmt:message bundle="${loc}" key="butChangePass" var="butChangePass"/>
<fmt:message bundle="${loc}" key="butBalance" var="butBalance"/>
<fmt:message bundle="${loc}" key="butSave" var="butSave"/>
<fmt:message bundle="${loc}" key="lName" var="lName"/>
<fmt:message bundle="${loc}" key="lSurName" var="lSurName"/>
<fmt:message bundle="${loc}" key="lLogin" var="lLogin"/>
<fmt:message bundle="${loc}" key="lEmail" var="lEmail"/>
<fmt:message bundle="${loc}" key="lPhone" var="lPhone"/>
<fmt:message bundle="${loc}" key="lDocumentId" var="lDocumentId"/>
<fmt:message bundle="${loc}" key="lDriveLicense" var="lDriveLicense"/>
<fmt:message bundle="${loc}" key="lBalance" var="lBalance"/>
<fmt:message bundle="${loc}" key="lAddress" var="lAddress"/>
<fmt:message bundle="${loc}" key="lChangePassword" var="lChangePassword"/>
<fmt:message bundle="${loc}" key="lAttMessage" var="lAttMessage"/>
<fmt:message bundle="${loc}" key="lOrders" var="lOrders"/>
<fmt:message bundle="${loc}" key="lInfoAccount" var="lInfoAccount"/>
<fmt:message bundle="${loc}" key="lStatusAcc" var="lStatusAcc"/>
<fmt:message bundle="${loc}" key="lStatusNotActive" var="lStatusNotActive"/>
<fmt:message bundle="${loc}" key="lStatusActive" var="lStatusActive"/>
<fmt:message bundle="${loc}" key="lBalanceChange" var="lBalanceChange"/>
<fmt:message bundle="${loc}" key="valPrice" var="valPrice"/>
<fmt:message bundle="${loc}" key="butCars" var="butCars"/>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="https://faviconka.ru/ico/1/faviconka.ru_1_103832.ico">
    <title>${titleUserPage}</title>
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
                    <li class="nav-item">
                        <a class="nav-link active"
                           href="${pageContext.request.contextPath}/controller?command=user_panel_page">
                            <span data-feather="home"></span>
                            ${lUserPanel}
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link"
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
                        ${sessionScope.error}
                    <c:remove var="error" scope="session"/>
                </div>
            </c:if>
            <c:if test="${not empty sessionScope.success}">
                <div class="alert alert-success alert-dismissible" style="margin-top: 10px">
                        ${sessionScope.success}
                    <c:remove var="success" scope="session"/>
                </div>
            </c:if>
            <h2>${lInfoAccount}</h2>
            <c:choose>
                <c:when test="${account.status eq 1}">
                    <table>
                        <tr>
                            <td class="fw-bolder" width="200px">${lStatusAcc}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${account.status eq 0}">${lStatusNotActive}</c:when>
                                    <c:when test="${account.status eq 1}">${lStatusActive}</c:when>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <td class="fw-bolder">${lName}</td>
                            <td>${account.name}</td>
                        </tr>
                        <tr>
                            <td class="fw-bolder">${lSurName}</td>
                            <td>${account.surname}</td>
                        </tr>
                        <tr>
                            <td class="fw-bolder">${lLogin}</td>
                            <td>${sessionScope.userLogin}</td>
                        </tr>
                        <tr>
                            <td class="fw-bolder">${lEmail}</td>
                            <td>${account.email}</td>
                        </tr>
                        <tr>
                            <td class="fw-bolder">${lPhone}</td>
                            <td>${account.phone}</td>
                        </tr>
                        <tr>
                            <td class="fw-bolder">${lDocumentId}</td>
                            <td>${account.documentId}</td>
                        </tr>
                        <tr>
                            <td class="fw-bolder">${lDriveLicense}</td>
                            <td>${account.driveLicenseNumber}</td>
                        </tr>
                        <tr>
                            <td class="fw-bolder">${lAddress}</td>
                            <td>${account.address}</td>
                        </tr>
                        <tr>
                            <td class="fw-bolder">${lBalance}</td>
                            <td>${account.balance}${valPrice}</td>
                        </tr>
                        <form action="${pageContext.request.contextPath}/controller?command=change_password" method="POST">
                            <tr>
                                <td class="fw-bolder">${lChangePassword}</td>
                                <td><input type="password" name="password"></td>
                            </tr>
                                <td></td>
                                <td>
                                    <button type="submit" class="btn btn-outline-dark" id="btnSubmit">${butChangePass}</button>
                                </td>
                            </tr>
                        </form>
                        <form action="${pageContext.request.contextPath}/controller?command=change_balance" method="POST">
                            <tr>
                                <td class="fw-bolder">${lBalanceChange}</td>
                                <td><input type="number" name="change_balance"></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td>
                                    <button type="submit" class="btn btn-outline-dark">${butBalance}</button>
                                </td>
                            </tr>
                        </form>
                    </table>
                </c:when>
                <c:otherwise>
                    <table>
                        <p>${lAttMessage}</p>
                        <form action="${pageContext.request.contextPath}/controller?command=save_account"
                              method="POST">
                            <tr>
                                <td class="fw-bolder">${lName}</td>
                                <td><input type="text" name="name" required></td>
                            </tr>
                            <tr>
                                <td class="fw-bolder">${lSurName}</td>
                                <td><input type="text" name="surname" required></td>
                            </tr>
                            <tr>
                                <td class="fw-bolder">${lPhone}</td>
                                <td><input type="text" name="phone" required></td>
                            </tr>
                            <tr>
                                <td class="fw-bolder">${lDocumentId}</td>
                                <td><input type="text" name="documentId" required></td>
                            </tr>
                            <tr>
                                <td class="fw-bolder">${lDriveLicense}</td>
                                <td><input type="text" name="driveLicenseNumber" required></td>
                            </tr>
                            <tr>
                                <td class="fw-bolder">${lAddress}</td>
                                <td><textarea type="text" name="address" maxlength="200" cols="25" rows="6"></textarea></td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <button type="submit" class="btn btn-outline-dark">${butSave}</button>
                                </td>
                            </tr>
                        </form>
                    </table>
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


