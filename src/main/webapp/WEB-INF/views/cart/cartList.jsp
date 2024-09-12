<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false" %>
<html>
<head>
    <title>CART</title>
    <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/cart.css'/>">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
</head>
<body>
<header id="menu">
    <ul>
        <li id="logo">fastcampus</li>
        <li><a href="<c:url value='/'/>">Home</a></li>
        <li><a href="<c:url value='/qa/list'/>">QA</a></li>
        <li><a href="<c:url value='/book/list'/>">Board</a></li>
        <li><a href="<c:url value='/cscenter/faq/list/00'/>">FAQ</a></li>
        <li><a href="<c:url value='${loginOutLink}'/>">${loginOut}</a></li>
        <li><a href="<c:url value='/signup'/>">Sign up</a></li>
        <li><a href="<c:url value='/cart/list'/>"><i class="fas fa-cart-plus"></i></a></li>
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
                    <button class="login-button" onclick="location.href='<c:url value='/member/login'/>'">로그인하기</button>
                    <button class="btn-later" onclick="location.href='<c:url value='/'/>'">돌아가기</button>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <c:forEach var="item" items="${itemList}">
                <div class="cart-item">
                    <div class="item-image">
                        <img src="${item.img_url}" alt="${item.title}">
                    </div>
                    <div class="item-details">
                        <p class="item-title">${item.title}</p>
                        <p class="item-sale-price">
                            <span class="discount"><fmt:formatNumber type="number" maxFractionDigits="0"
                                                                     value="${item.bene_perc * 100}"/>% </span>

                            <span class="sale-price">${item.salePrice}원 </span>
                            <span class="regular-price">${item.basicPrice}원 </span>
                            <span class="reward-points">(${item.point_pric}P)</span>
                        </p>
                        <p class="item-total-price">
                            <span class="label">총 판매가: <c:out
                                    value="${(item.basicPrice - item.bene_pric) * item.item_quan}"/>원</span>
                        </p>
                    </div>
                    <form class="item-quantity" action="<c:url value="/cart/product/quantity"/>" method="POST">
                        <input type="hidden" name="cartSeq" value="${item.cart_seq}">
                        <input type="hidden" name="isbn" value="${item.isbn}">
                        <input type="hidden" name="prodTypeCode" value="${item.prod_type_code}">
                        <button type="submit" class="quantity-decrease">-</button>
                        <input class="quantity-input" type="text" name="itemQuantity" value="${item.item_quan}"
                               readonly>
                        <button type="submit" class="quantity-increase">+</button>
                    </form>
                    <form class="item-remove" action="<c:url value="/cart/product/delete"/>" method="POST">
                        <input type="hidden" name="cartSeq" value="${item.cart_seq}">
                        <input type="hidden" name="isbn" value="${item.isbn}">
                        <input type="hidden" name="prodTypeCode" value="${item.prod_type_code}">
                        <button class="remove-button">X</button>
                    </form>
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
                <button class="btn-later" onclick="location.href='<c:url value='/book/list'/>'">나중에 주문</button>
                <button class="btn-order">주문하기</button>
            </div>
            <div class="summary-content">
                <div class="summary-section total-product-amount">
                    <span class="label">총 상품금액</span>
                    <span class="amount">${salePrice}원</span>
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

<script type="text/javascript">
    // JSP에서 cartItemList를 JavaScript 변수로 할당
    var cartItemList = [];

    <c:forEach var="item" items="${itemList}">
        cartItemList.push({
            cartSeq: "${item.cart_seq}",
            isbn: "${item.isbn}",
            prodTypeCode: "${item.prod_type_code}",
            title: "${item.title}",
            salePrice: "${item.salePrice}",
            basicPrice: "${item.basicPrice}",
            itemQuan: "${item.item_quan}"
        });
    </c:forEach>
</script>
<script src="<c:url value='/js/cart.js'/>"></script>

</body>
</html>