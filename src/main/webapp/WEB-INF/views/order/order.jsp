<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false" %>
<html>
<head>
    <title>Order</title>
    <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
</head>
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

<div class="container">
    <div class="order-items">
        <h2>주문상품 (총 <%= request.getAttribute("totalItems") %>개)</h2>
        <div class="item">
            <img src="book1.jpg" alt="도서 1">
            <div class="item-details">
                <strong>[국내도서]어른을 위한 두뇌 피트니스</strong>
                <p>1개</p>
            </div>
            <div class="item-price">15,120원</div>
        </div>
        <div class="item">
            <img src="book2.jpg" alt="도서 2">
            <div class="item-details">
                <strong>[국내도서]켄트 벡의 Tidy First?</strong>
                <p>2개</p>
            </div>
            <div class="item-price">39,600원</div>
        </div>
        <div class="item">
            <img src="book3.jpg" alt="도서 3">
            <div class="item-details">
                <strong>[국내도서]추천 시스템</strong>
                <p>1개</p>
            </div>
            <div class="item-price">36,000원</div>
        </div>
    </div>

    <div class="order-summary">
        <h2>결제 요약</h2>
        <div class="summary-row">
            <span>상품 금액</span>
            <span>100,800원</span>
        </div>
        <div class="summary-row">
            <span>배송비</span>
            <span>0원</span>
        </div>
        <div class="summary-row">
            <span>상품 할인</span>
            <span>-10,080원</span>
        </div>
        <div class="summary-row">
            <span>포인트 사용</span>
            <span>0원</span>
        </div>
        <div class="summary-row">
            <span class="total-price">최종 결제 금액</span>
            <span class="total-price">90,720원</span>
        </div>
        <a href="#" class="btn-pay">결제하기</a>
    </div>
</div>

</body>
</html>
