<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.epam.jwd.dao.model.account.Role"%>

<fmt:setLocale value="${not empty sessionScope.lang ? sessionScope.lang : 'en'}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="titleMainPage" var="titleMainPage"/>
<fmt:message bundle="${loc}" key="butMenu" var="butMenu"/>
<fmt:message bundle="${loc}" key="butHome" var="butHome"/>
<fmt:message bundle="${loc}" key="butCars" var="butCars"/>
<fmt:message bundle="${loc}" key="butContacts" var="butContacts"/>
<fmt:message bundle="${loc}" key="butAboutUs" var="butAboutUs"/>
<fmt:message bundle="${loc}" key="butLogin" var="butLogin"/>
<fmt:message bundle="${loc}" key="butLogout" var="butLogout"/>
<fmt:message bundle="${loc}" key="butAccount" var="butAccount"/>
<fmt:message bundle="${loc}" key="slogan" var="slogan"/>
<fmt:message bundle="${loc}" key="sloganText" var="sloganText"/>
<fmt:message bundle="${loc}" key="ourOffer" var="ourOffer"/>
<fmt:message bundle="${loc}" key="mostPopular" var="mostPopular"/>
<fmt:message bundle="${loc}" key="ruLocale" var="ruLocale"/>
<fmt:message bundle="${loc}" key="enLocale" var="enLocale"/>

<html>
<head>
    <title>${titleMainPage}</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://fonts.googleapis.com/css?family=Poppins:200,300,400,500,600,700,800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/my/local-but.css">
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
<body>

    <nav class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light" id="ftco-navbar">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/controller?command=main_page">Car<span>Rent</span></a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#ftco-nav" aria-controls="ftco-nav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="oi oi-menu"></span> ${butMenu}
            </button>

            <div class="collapse navbar-collapse" id="ftco-nav">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item active"><a href="${pageContext.request.contextPath}/controller?command=main_page" class="nav-link">${butHome}</a></li>
                    <li class="nav-item"><a href="${pageContext.request.contextPath}/controller?command=cars_page" class="nav-link">${butCars}</a></li>
                    <c:choose>
                        <c:when test="${empty sessionScope.userLogin}">
                            <li class="nav-item"><a href="${pageContext.request.contextPath}/controller?command=login_page" class="nav-link">${butLogin}</a></li>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${sessionScope.account.role eq Role.USER}">
                                    <li class="nav-item"><a href="${pageContext.request.contextPath}/controller?command=user_panel_page" class="nav-link">${butAccount}</a></li>
                                </c:when>
                                <c:otherwise>
                                    <li class="nav-item">
                                        <a href="${pageContext.request.contextPath}/controller?command=admin_orders_page" class="nav-link">${butAccount}</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                            <li class="nav-item"><a href="${pageContext.request.contextPath}/controller?command=logout" class="nav-link">${butLogout}</a></li>
                        </c:otherwise>
                    </c:choose>
                    <div class="block-local">
                        <div class="but-local"><a href="${pageContext.request.contextPath}/controller?command=locale&lang=ru" style="color: #FFFFFF">${ruLocale}</a></div>
                        <div class="but-local"><a href="${pageContext.request.contextPath}/controller?command=locale&lang=en" style="color: #FFFFFF">${enLocale}</a></div>
                    </div>
                </ul>
            </div>
        </div>
    </nav>

    <div class="hero-wrap ftco-degree-bg" style="background-image: url('${pageContext.request.contextPath}/assets/images/bg_1.jpg');" data-stellar-background-ratio="0.5">
        <div class="overlay"></div>
        <div class="container">
            <div class="row no-gutters slider-text justify-content-start align-items-center justify-content-center">
                <div class="col-lg-8 ftco-animate">
                    <div class="text w-100 text-center mb-md-5 pb-md-5">
                        <h1 class="mb-4">${slogan}</h1>
                        <p style="font-size: 18px;">${sloganText}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>

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
