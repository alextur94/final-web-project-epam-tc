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
    <title>${titleAdminPanelPage}</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/my/carNewForm.css">
    <link href="https://fonts.googleapis.com/css?family=Poppins:200,300,400,500,600,700,800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/open-iconic-bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/animate.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/owl.carousel.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/owl.theme.default.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/magnific-popup.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/aos.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/ionicons.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap-datepicker.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jquery.timepicker.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/flaticon.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/icomoon.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body style="background-image: url('${pageContext.request.contextPath}/assets/images/bg_1.jpg'); background-size: 100%;" >

    <nav class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light" id="ftco-navbar">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/controller?command=show_main_page">Car<span>Rent</span></a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#ftco-nav" aria-controls="ftco-nav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="oi oi-menu"></span> ${butMenu}
            </button>

            <div class="collapse navbar-collapse" id="ftco-nav">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item"><a href="${pageContext.request.contextPath}/controller?command=show_main_page" class="nav-link">${butHome}</a></li>
                    <li class="nav-item"><a href="${pageContext.request.contextPath}/controller?command=show_cars_page" class="nav-link">${butCars}</a></li>
                    <li class="nav-item"><a href="${pageContext.request.contextPath}/controller?command=show_contacts_page" class="nav-link">${butContacts}</a></li>
                    <li class="nav-item"><a href="${pageContext.request.contextPath}/controller?command=show_about_us_page" class="nav-link">${butAboutUs}</a></li>
                    <c:choose>
                        <c:when test="${empty sessionScope.userLogin}">
                            <li class="nav-item"><a href="${pageContext.request.contextPath}/controller?command=show_login_page" class="nav-link">${butLogin}</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item active"><a href="${pageContext.request.contextPath}/controller?command=show_user_account" class="nav-link">${butAccount}</a></li>
                            <li class="nav-item"><a href="${pageContext.request.contextPath}/controller?command=logout" class="nav-link">${butLogout}</a></li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container">
        <c:choose>
            <c:when test="${not empty requestScope.error}">
                <p>${requestScope.error}</p>
                <a href="${pageContext.request.contextPath}/controller?command=show_login_page">Try again</a>
            </c:when>
            <c:otherwise>
              <div class="car-new-form">
                  <form action="${pageContext.request.contextPath}/controller?command=new_car" method="POST">
                      <label for="brand">${brand}: </label><input type="text" id="brand" name="brand"> </br>
                      <label for="model">${model}: </label><input type="text" id="model" name="model"> </br>
                      <label for="year">${year}: </label><input type="number" id="year" name="year"> </br>
                      <label>${level}: </label>
                        <select name="level">
                            <optgroup>
                          <option value="1">${economy}</option>
                          <option value="2">${standard}</option>
                          <option value="3">${business}</option>
                          <option value="4">${sport}</option>
                            </optgroup>
                         </select> </br>
                      <label>${body}: </label>
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
                      </select> </br>
                      <label for="engine_volume">${engineVolume}: </label><input type="number" id="engine_volume" name="engine_volume"> </br>
                      <label for="trans">${transmissionAuto}</label><input type="checkbox" id="trans" name="transmission"> </br>
                      <label for="doors">${doors}: </label><input type="number" id="doors" name="doors"> </br>
                      <label>${color}: </label>
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
                      </select> </br>
                      <label for="available">${available}</label><input type="checkbox" id="available" name="available" checked> </br>
                      <label for="price">PRICE<!!!>: </label><input type="number" id="price" name="price_per_day"> </br>
                      <button type="submit">Save car</button>
                  </form>
              </div>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- loader -->
    <div id="ftco-loader" class="show fullscreen"><svg class="circular" width="48px" height="48px"><circle class="path-bg" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke="#eeeeee"/><circle class="path" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke-miterlimit="10" stroke="#F96D00"/></svg></div>

    <script src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/jquery-migrate-3.0.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/popper.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/jquery.easing.1.3.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/jquery.waypoints.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/jquery.stellar.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/owl.carousel.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/jquery.magnific-popup.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/aos.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/jquery.animateNumber.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/bootstrap-datepicker.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/jquery.timepicker.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/scrollax.min.js"></script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBVWaKrjvy3MaE7SQ74_uJiULgl1JY0H2s&sensor=false"></script>
    <script src="${pageContext.request.contextPath}/assets/js/google-map.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/main.js"></script>

</body>
</html>
