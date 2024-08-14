<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
    <title>CART</title>
    <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/cart.css'/>">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="<c:url value='/js/cart.js'/>"></script>
</head>
<body>
<header id="menu">
    <ul>
        <li id="logo">fastcampus</li>
        <li><a href="<c:url value='/'/>">Home</a></li>
        <%-- <li><a href="<c:url value='/board/list'/>">Board</a></li> --%>
        <%-- <li><a href="<c:url value='${loginOutLink}'/>">${loginOut}</a></li> --%>
        <%-- <li><a href="<c:url value='/register/add'/>">Sign in</a></li> --%>
        <li><a href=""><i class="fa fa-search"></i></a></li>
    </ul>
</header>
<div class="cart-container">
    <h2>교보문고/바로드림</h2>
    <c:forEach var="item" items="${cartItems}">
        <div class="cart-item">
            <div class="item-image">
                <img src="${item.imageUrl}" alt="${item.bookTitle}">
            </div>
            <div class="item-details">
                <p class="item-title">${item.bookTitle}</p>
                <p class="item-sale-price">
                    <span class="discount">${item.discount}% </span>
                    <span class="sale-price">${item.salePrice}원 </span>
                    <span class="regular-price">${item.regularPrice}원 </span>
                    <span class="reward-points">(${item.rewardPoints}P)</span>
                </p>
            </div>
            <div class="item-quantity">
                <button class="quantity-decrease">-</button>
                <input class="quantity-input" type="text" value="${item.quantity}" readonly>
                <button class="quantity-increase">+</button>
            </div>
            <div class="item-remove">
                <button class="remove-button">X</button>
            </div>
        </div>
    </c:forEach>

    <div class="cart-item">
        <div class="item-image">
            <img src="https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9791193866009.jpg" alt="어른들을 위한 두뇌 피트니스">
        </div>
        <div class="item-details">
            <p class="item-title">어른들을 위한 두뇌 피트니스</p>
            <p class="item-sale-price">
                <span class="discount">10 % </span>
                <span class="sale-price">13500 원 </span>
                <span class="regular-price">15000 원 </span>
                <span class="reward-points">(1500 P)</span>
            </p>
        </div>
        <div class="item-quantity">
            <button class="quantity-decrease">-</button>
            <input class="quantity-input" type="text" value="1" readonly>
            <button class="quantity-increase">+</button>
        </div>
        <div class="item-remove">
            <button class="remove-button">X</button>
        </div>
    </div>

    <div class="cart-item">
        <div class="item-image">
            <img src="https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788994492032.jpg" alt="자바의 정석 3판">
        </div>
        <div class="item-details">
            <p class="item-title">자바의 정석 3판</p>
            <p class="item-sale-price">
                <span class="discount">10 % </span>
                <span class="sale-price">24300 원 </span>
                <span class="regular-price">27000 원 </span>
                <span class="reward-points">(2700 P)</span>
            </p>
        </div>
        <div class="item-quantity">
            <button class="quantity-decrease">-</button>
            <input class="quantity-input" type="text" value="1" readonly>
            <button class="quantity-increase">+</button>
        </div>
        <div class="item-remove">
            <button class="remove-button">X</button>
        </div>
    </div>

</div>

<div class="cart-summary">
    <div class="summary-header">
        <button class="btn-later">나중에 주문</button>
        <button class="btn-order">주문하기</button>
    </div>
    <div class="summary-content">
        <div class="summary-section total-product-amount">
            <span class="label">총 상품금액</span>
            <span class="amount">${totalProductAmount}원</span>
            <p class="detail">${originalPrice}원에서 ${discountAmount}원 즉시할인</p>
        </div>
        <div class="summary-symbol">-</div>
        <div class="summary-section total-discount-amount">
            <span class="label">총 할인금액</span>
            <span class="amount">${totalDiscountAmount}원</span>
        </div>
        <div class="summary-symbol">=</div>
        <div class="summary-section final-payment-amount">
            <span class="label">최종 결제금액</span>
            <span class="amount">${finalPaymentAmount}원</span>
        </div>
    </div>
    <div class="summary-footer">
        <p class="point">기본적립 포인트: ${basicPoints}원</p>
        <p class="point">추가적립 포인트: ${additionalPoints}원</p>
        <p class="point">마니아 포인트: ${maniaPoints}원</p>
        <p class="point">총 예상적립 포인트: ${totalPoints}원</p>
    </div>

    <div class="summary-content">
        <div class="summary-section total-product-amount">
            <span class="label">총 상품금액</span>
            <span class="amount">${13500 + 24300}원</span>
            <p class="detail">${15000 + 27000}원에서 ${1500 + 2700}원 즉시할인</p>
        </div>
        <div class="summary-symbol">-</div>
        <div class="summary-section total-discount-amount">
            <span class="label">총 할인금액</span>
            <span class="amount">${1500 + 2700} 원</span>
        </div>
        <div class="summary-symbol">=</div>
        <div class="summary-section final-payment-amount">
            <span class="label">최종 결제금액</span>
            <span class="amount">${13500 + 24300} 원</span>
        </div>
    </div>
</div>
</body>
</html>ㄴ