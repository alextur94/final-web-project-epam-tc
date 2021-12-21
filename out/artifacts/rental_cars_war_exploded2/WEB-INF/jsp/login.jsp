<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="en"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="titleLoginPage" var="titleLoginPage"/>
<fmt:message bundle="${loc}" key="butMenu" var="butMenu"/>
<fmt:message bundle="${loc}" key="butHome" var="butHome"/>
<fmt:message bundle="${loc}" key="butCars" var="butCars"/>
<fmt:message bundle="${loc}" key="butContacts" var="butContacts"/>
<fmt:message bundle="${loc}" key="butAboutUs" var="butAboutUs"/>
<fmt:message bundle="${loc}" key="butLogin" var="butLogin"/>
<fmt:message bundle="${loc}" key="butCreateAcc" var="butCreateAcc"/>
<fmt:message bundle="${loc}" key="formLogTitle" var="formLogTitle"/>

<html>
<head>
    <title>${titleLoginPage}</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/my/loginForm.css">
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
<div>
    <div>
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
                        <li class="nav-item active"><a href="${pageContext.request.contextPath}/controller?command=show_login_page" class="nav-link">${butLogin}</a></li>
                    </ul>
                </div>
            </div>
        </nav>

        <div>
            <c:choose>
                <c:when test="${not empty requestScope.error}">
                    <p>${requestScope.error}</p>
                    <a href="${pageContext.request.contextPath}/controller?command=show_login_page">Try again</a>
                </c:when>
                <c:otherwise>
                    <div class="form-reg">
                        <div class="titleForm"><h3><span class="titleFormText">${formLogTitle}</span></h3></div>
                        <form action="${pageContext.request.contextPath}/controller?command=login" method="POST">
                            <div class="input-div">
                                <input class="form-control" type="text" name="login" placeholder="Login">
                            </div>
                            <div class="input-div">
                                <input class="form-control" type="password" name="password" placeholder="Password">
                            </div>
                            <button type="submit" class="btn btn-secondary" id="btnSubmit">Submit</button>
                            <div class="reg">
                                <a class="reg-ref" href="${pageContext.request.contextPath}/controller?command=show_registration_page">${butCreateAcc}</a>
                            </div>
                        </form>
                    </div>>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
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
