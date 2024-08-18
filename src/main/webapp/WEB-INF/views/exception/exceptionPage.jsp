<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false" %>
<html>
<head>
    <title>Exception</title>
    <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/cart.css'/>">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
    <script src="<c:url value='/js/cart.js'/>"></script>
</head>
<style>
    .error-container {
        width: 100%;
        max-width: 600px;
        margin: 50px auto;
        padding: 20px;
        background-color: white;
        border-radius: 5px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        text-align: center;
    }
    .error-message {
        margin-top: 20px;
        font-size: 18px;
        color: #333;
        margin-bottom: 20px;
    }
    .error-suggestion {
        font-size: 16px;
        color: #666;
        margin-bottom: 30px;
    }
    .home-link, .back-button {
        text-decoration: none;
        color: #007bff;
        font-weight: bold;
        border: 1px solid #007bff;
        padding: 10px 20px;
        border-radius: 5px;
        display: inline-block;
        margin-top: 10px;
        cursor: pointer;
    }
    .home-link:hover, .back-button:hover {
        background-color: #007bff;
        color: white;
    }
</style>
<body>
<header id="menu">
    <ul>
        <li id="logo">fastcampus</li>
        <li><a href="<c:url value='/'/>">Home</a></li>
        <%-- <li><a href="<c:url value='/board/list'/>">Board</a></li> --%>
        <%-- <li><a href="<c:url value='${loginOutLink}'/>">${loginOut}</a></li> --%>
        <%-- <li><a href="<c:url value='/register/add'/>">Sign in</a></li> --%>
        <li><a href=""><i class="fas fa-cart-plus"></i></a></li>
        <li><a href=""><i class="fa fa-search"></i></a></li>
    </ul>
</header>
<div class="error-container">
    <h1>무엇인가 문제가 발생했어요!</h1>
    <p class="error-message">${message}</p>
    <button class="home-link" onclick="location.href='<c:url value='/'/>'">이동 하기</button>
</div>
</body>
</html>
