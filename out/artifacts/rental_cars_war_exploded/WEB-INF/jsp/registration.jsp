<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${not empty sessionScope.lang ? sessionScope.lang : 'en'}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="titleRegistrationPage" var="titleRegistrationPage"/>
<fmt:message bundle="${loc}" key="butLogin" var="butLogin"/>
<fmt:message bundle="${loc}" key="loginAccepted" var="loginAccepted"/>
<fmt:message bundle="${loc}" key="loginNotAccepted" var="loginNotAccepted"/>
<fmt:message bundle="${loc}" key="emailValid" var="emailValid"/>
<fmt:message bundle="${loc}" key="emailNotValid" var="emailNotValid"/>
<fmt:message bundle="${loc}" key="passValid" var="passValid"/>
<fmt:message bundle="${loc}" key="passNotValid" var="passNotValid"/>
<fmt:message bundle="${loc}" key="formRegTitle" var="formRegTitle"/>
<fmt:message bundle="${loc}" key="butCreate" var="butCreate"/>
<fmt:message bundle="${loc}" key="butMenu" var="butMenu"/>

<html>
<head>
    <title>${titleRegistrationPage}</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <script src="${pageContext.request.contextPath}/assets/js/my/regForm.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/my/regForm.css">
    <link href="https://fonts.googleapis.com/css?family=Poppins:200,300,400,500,600,700,800&display=swap"
          rel="stylesheet">
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
<body style="background-image: url('${pageContext.request.contextPath}/assets/images/bg_1.jpg'); background-size: 100%;">
<div>
    <div>
        <nav class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light" id="ftco-navbar">
            <div class="container">
                <a class="navbar-brand"
                   href="${pageContext.request.contextPath}/controller?command=main_page">Car<span>Rent</span></a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#ftco-nav"
                        aria-controls="ftco-nav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="oi oi-menu"></span> ${butMenu}
                </button>
            </div>
        </nav>
        <div>
            <div>
                <div class="form-reg">
                    <form class="" action="${pageContext.request.contextPath}/controller?command=registration"
                          method="POST" id="myForm">
                        <div class="titleForm"><h3><span class="titleFormText">${formRegTitle}</span></h3></div>
                        <div class="form-group">
                            <input type="text" class="form-control" name="login" id="input1"
                                   autocomplete="no" minlength="3" maxlength="20" placeholder="login" required>
                            <div class="valid-feedback">${loginAccepted}</div>
                            <div class="invalid-feedback">${loginNotAccepted}</div>
                        </div>
                        <div class="form-group">
                            <input type="text" class="form-control"
                                   pattern="^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$"
                                   autocomplete="no" minlength="5" name="email" id="input2" placeholder="email">
                            <div class="valid-feedback">${emailValid}</div>
                            <div class="invalid-feedback">${emailNotValid}</div>
                        </div>
                        <div class="form-group">
                            <input type="text" class="form-control"
                                   pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}$"
                                   name="password" minlength="6" id="input3" placeholder="password">
                            <div class="valid-feedback">${passValid}</div>
                            <div class="invalid-feedback">${passNotValid}</div>
                        </div>
                        <div>
                            <button type="submit" class="btn btn-secondary" id="btnSubmit">${butCreate}</button>
                        </div>
                        <c:if test="${not empty sessionScope.error}">
                            <div class="alert alert-danger alert-dismissible" style="margin-top: 10px">
                                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                    ${sessionScope.error}
                                <c:remove var="error" scope="session"/>
                            </div>
                        </c:if>
                        <div class="log-ref">
                            <a class="log-ref-a"
                               href="${pageContext.request.contextPath}/controller?command=login_page">${butLogin}</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- loader -->
<div id="ftco-loader" class="show fullscreen">
    <svg class="circular" width="48px" height="48px">
        <circle class="path-bg" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke="#eeeeee"/>
        <circle class="path" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke-miterlimit="10"
                stroke="#F96D00"/>
    </svg>
</div>


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
