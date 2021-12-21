<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.epam.jwd.dao.model.account.Role"%>

<fmt:setLocale value="en"/>
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

<html>
<head>
    <title>${titleMainPage}</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
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
<body>

    <nav class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light" id="ftco-navbar">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/controller?command=show_main_page">Car<span>Rent</span></a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#ftco-nav" aria-controls="ftco-nav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="oi oi-menu"></span> ${butMenu}
            </button>

            <div class="collapse navbar-collapse" id="ftco-nav">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item active"><a href="${pageContext.request.contextPath}/controller?command=show_main_page" class="nav-link">${butHome}</a></li>
                    <li class="nav-item"><a href="${pageContext.request.contextPath}/controller?command=show_cars_page" class="nav-link">${butCars}</a></li>
                    <li class="nav-item"><a href="${pageContext.request.contextPath}/controller?command=show_contacts_page" class="nav-link">${butContacts}</a></li>
                    <li class="nav-item"><a href="${pageContext.request.contextPath}/controller?command=show_about_us_page" class="nav-link">${butAboutUs}</a></li>
                    <c:choose>
                        <c:when test="${empty sessionScope.userLogin}">
                            <li class="nav-item"><a href="${pageContext.request.contextPath}/controller?command=show_login_page" class="nav-link">${butLogin}</a></li>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${sessionScope.userRole eq Role.USER}">
                                    <li class="nav-item"><a href="${pageContext.request.contextPath}/controller?command=user_panel_page" class="nav-link">${butAccount}</a></li>
                                </c:when>
                                <c:otherwise>
                                    <li class="nav-item"><a href="${pageContext.request.contextPath}/controller?command=admin_panel_page" class="nav-link">${butAccount}</a></li>
                                </c:otherwise>
                            </c:choose>
                            <li class="nav-item"><a href="${pageContext.request.contextPath}/controller?command=logout" class="nav-link">${butLogout}</a></li>
                        </c:otherwise>
                    </c:choose>
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
                        <c:url value="/controller" var="setRuLocale">
                            <c:param name="command" value="set_locale"/>
                            <c:param name="returnPage" value="${pageContext.request.queryString}"/>
                            <c:param name="sessionLocale" value="ru"/>
                        </c:url>
                        <c:url value="/controller" var="setEnLocale">
                            <c:param name="command" value="set_locale"/>
                            <c:param name="returnPage" value="${pageContext.request.queryString}"/>
                            <c:param name="sessionLocale" value="en"/>
                        </c:url>
                        <a href="${setRuLocale}" class="">rus</a>
                        <a href="${setEnLocale}" class="">eng</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <section class="ftco-section ftco-no-pt bg-light">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-12 heading-section text-center ftco-animate mb-5">
                    <span class="subheading">${ourOffer}</span>
                    <h2 class="mb-2">${mostPopular}</h2>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="carousel-car owl-carousel">
                        <div class="item">
                            <div class="car-wrap rounded ftco-animate">
                                <div class="img rounded d-flex align-items-end" style="background-image: url(${pageContext.request.contextPath}/assets/images/car-1.jpg);">
                                </div>
                                <div class="text">
                                    <h2 class="mb-0"><a href="#">Mercedes Grand Sedan</a></h2>
                                    <div class="d-flex mb-3">
                                        <span class="cat">Cheverolet</span>
                                        <p class="price ml-auto">$500 <span>/day</span></p>
                                    </div>
                                    <p class="d-flex mb-0 d-block"><a href="#" class="btn btn-primary py-2 mr-1">Book now</a> <a href="#" class="btn btn-secondary py-2 ml-1">Details</a></p>
                                </div>
                            </div>
                        </div>
                        <div class="item">
                            <div class="car-wrap rounded ftco-animate">
                                <div class="img rounded d-flex align-items-end" style="background-image: url(${pageContext.request.contextPath}/assets/images/car-2.jpg);">
                                </div>
                                <div class="text">
                                    <h2 class="mb-0"><a href="#">Mercedes Grand Sedan</a></h2>
                                    <div class="d-flex mb-3">
                                        <span class="cat">Cheverolet</span>
                                        <p class="price ml-auto">$500 <span>/day</span></p>
                                    </div>
                                    <p class="d-flex mb-0 d-block"><a href="#" class="btn btn-primary py-2 mr-1">Book now</a> <a href="#" class="btn btn-secondary py-2 ml-1">Details</a></p>
                                </div>
                            </div>
                        </div>
                        <div class="item">
                            <div class="car-wrap rounded ftco-animate">
                                <div class="img rounded d-flex align-items-end" style="background-image: url(${pageContext.request.contextPath}/assets/images/car-3.jpg);">
                                </div>
                                <div class="text">
                                    <h2 class="mb-0"><a href="#">Mercedes Grand Sedan</a></h2>
                                    <div class="d-flex mb-3">
                                        <span class="cat">Cheverolet</span>
                                        <p class="price ml-auto">$500 <span>/day</span></p>
                                    </div>
                                    <p class="d-flex mb-0 d-block"><a href="#" class="btn btn-primary py-2 mr-1">Book now</a> <a href="#" class="btn btn-secondary py-2 ml-1">Details</a></p>
                                </div>
                            </div>
                        </div>
                        <div class="item">
                            <div class="car-wrap rounded ftco-animate">
                                <div class="img rounded d-flex align-items-end" style="background-image: url(${pageContext.request.contextPath}/assets/images/car-4.jpg);">
                                </div>
                                <div class="text">
                                    <h2 class="mb-0"><a href="#">Mercedes Grand Sedan</a></h2>
                                    <div class="d-flex mb-3">
                                        <span class="cat">Cheverolet</span>
                                        <p class="price ml-auto">$500 <span>/day</span></p>
                                    </div>
                                    <p class="d-flex mb-0 d-block"><a href="#" class="btn btn-primary py-2 mr-1">Book now</a> <a href="#" class="btn btn-secondary py-2 ml-1">Details</a></p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <footer class="ftco-footer ftco-bg-dark ftco-section">
        <div class="container">
            <div class="row mb-5">
                <div class="col-md">
                    <div class="ftco-footer-widget mb-4">
                        <h2 class="ftco-heading-2"><a href="#" class="logo">Car<span>book</span></a></h2>
                        <p>Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                        <ul class="ftco-footer-social list-unstyled float-md-left float-lft mt-5">
                            <li class="ftco-animate"><a href="#"><span class="icon-twitter"></span></a></li>
                            <li class="ftco-animate"><a href="#"><span class="icon-facebook"></span></a></li>
                            <li class="ftco-animate"><a href="#"><span class="icon-instagram"></span></a></li>
                        </ul>
                    </div>
                </div>
                <div class="col-md">
                    <div class="ftco-footer-widget mb-4 ml-md-5">
                        <h2 class="ftco-heading-2">Information</h2>
                        <ul class="list-unstyled">
                            <li><a href="#" class="py-2 d-block">About</a></li>
                            <li><a href="#" class="py-2 d-block">Services</a></li>
                            <li><a href="#" class="py-2 d-block">Term and Conditions</a></li>
                            <li><a href="#" class="py-2 d-block">Best Price Guarantee</a></li>
                            <li><a href="#" class="py-2 d-block">Privacy &amp; Cookies Policy</a></li>
                        </ul>
                    </div>
                </div>
                <div class="col-md">
                    <div class="ftco-footer-widget mb-4">
                        <h2 class="ftco-heading-2">Customer Support</h2>
                        <ul class="list-unstyled">
                            <li><a href="#" class="py-2 d-block">FAQ</a></li>
                            <li><a href="#" class="py-2 d-block">Payment Option</a></li>
                            <li><a href="#" class="py-2 d-block">Booking Tips</a></li>
                            <li><a href="#" class="py-2 d-block">How it works</a></li>
                            <li><a href="#" class="py-2 d-block">Contact Us</a></li>
                        </ul>
                    </div>
                </div>
                <div class="col-md">
                    <div class="ftco-footer-widget mb-4">
                        <h2 class="ftco-heading-2">Have a Questions?</h2>
                        <div class="block-23 mb-3">
                            <ul>
                                <li><span class="icon icon-map-marker"></span><span class="text">203 Fake St. Mountain View, San Francisco, California, USA</span></li>
                                <li><a href="#"><span class="icon icon-phone"></span><span class="text">+2 392 3929 210</span></a></li>
                                <li><a href="#"><span class="icon icon-envelope"></span><span class="text">info@yourdomain.com</span></a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12 text-center">

                    <p><!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                        Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | This template is made with <i class="icon-heart color-danger" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank">Colorlib</a>
                        <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. --></p>
                </div>
            </div>
        </div>
    </footer>



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
