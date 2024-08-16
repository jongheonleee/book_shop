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
    <h2>장바구니</h2>
    <c:choose>
        <c:when test="${empty itemList}">
            <div class="empty-cart-container">
                <div class="empty-cart-message">
                    <p class="message">장바구니에 담긴 상품이 없습니다.</p>
                    <p class="sub-message">로그인하고 다양한 혜택을 받아보세요!</p>
                    <button class="login-button" onclick="location.href='<c:url value='/login'/>';">로그인하기</button>
                    <button class="btn-later">돌아가기</button>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <c:forEach var="item" items="${itemList}">
                <div class="cart-item">
                    <div class="item-image">
                        <img src="${item.img_url}" alt="${item.book_title}">
                    </div>
                    <div class="item-details">
                        <p class="item-title">${item.book_title}</p>
                        <p class="item-sale-price">
                            <span class="discount">${item.bene_perc * 100}% </span>
                            <span class="sale-price">${item.salePrice}원 </span>
                            <span class="regular-price">${item.basicPrice}원 </span>
                            <span class="reward-points">(${item.point_pric}P)</span>
                        </p>
                        <p class="item-total-price">
                            <span class="label">총 판매가: <c:out
                                    value="${(item.basicPrice - item.bene_pric) * item.item_quan}"/>원</span>
                        </p>
                    </div>
                    <div class="item-quantity">
                        <button class="quantity-decrease" data-isPlus="${false}" data-cartSeq="${item.cart_seq}"
                                data-isbn="${item.isbn}" data-prodTypeCode="${item.prod_type_code}">-
                        </button>
                        <input class="quantity-input" type="text" value="${item.item_quan}" readonly>
                        <button class="quantity-increase" data-isPlus="${true}" data-cartSeq="${item.cart_seq}"
                                data-isbn="${item.isbn}" data-prodTypeCode="${item.prod_type_code}">+
                        </button>
                    </div>
                    <div class="item-remove">
                        <button class="remove-button">X</button>
                    </div>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>

<div class="cart-summary">
    <c:choose>
        <c:when test="${empty itemList}">
            <div class="summary-content">
                <div class="summary-section">
                    <p class="amount">장바구니를 추가해주세요!</p>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="summary-header">
                <button class="btn-later">나중에 주문</button>
                <button class="btn-order">주문하기</button>
            </div>
            <div class="summary-content">
                <div class="summary-section total-product-amount">
                    <span class="label">총 상품금액</span>
                    <span class="amount">${salePrice}원</span>
                    <p class="detail">${salePrice}원에서 ${salePrice}원 즉시할인</p>
                </div>
                <div class="summary-symbol">-</div>
                <div class="summary-section total-discount-amount">
                    <span class="label">총 할인금액</span>
                    <span class="amount">${salePrice}원</span>
                </div>
                <div class="summary-symbol">=</div>
                <div class="summary-section final-payment-amount">
                    <span class="label">최종 결제금액</span>
                    <span class="amount">${salePrice}원</span>
                </div>
            </div>
            <div class="summary-footer">
                <p class="point">총 예상적립 포인트: ${point_pric}원</p>
            </div>
        </c:otherwise>
    </c:choose>
</div>


</body>
</html>