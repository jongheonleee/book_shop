<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false" %>
<html>
<head>
    <title>Order</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
    <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/order.css'/>">
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

<div class="order-container">
    <h2>주문상품 (총 ${orderItemList.size()} 개)</h2>

    <div class="order-items">
        <c:forEach var="item" items="${orderItemList}">
            <div class="order-item">
                <div class="item-image">
                    <img src="${item.repre_img}" alt="${item.book_title}">
                </div>
                <div class="item-details">
                    <p class="item-title">${item.book_title}</p>
                    <div class="item-price">
                        <p class="item-quantity">수량: ${item.item_quan}</p>
                        <p class="item-sale-price">
                            <span class="sale-price">${item.sale_pric * item.item_quan}원</span>
                            <span class="regular-price">${item.basic_pric * item.item_quan}원</span>
                        </p>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <div class="order-summary">
        <h2>결제 요약</h2>
        <div class="summary-section">
            <span class="label">상품 금액</span>
            <span class="amount">${orderDto.total_prod_pric}원</span>
        </div>
        <div class="summary-section">
            <span class="label">배송비</span>
            <span class="amount">${orderDto.delivery_fee}원</span>
        </div>
        <div class="summary-section discount-pay">
            <span class="label">상품 할인</span>
            <span class="amount">-${orderDto.total_disc_pric}원</span>
        </div>
        <div class="summary-section final-payment">
            <span class="label">최종 결제 금액</span>
            <span class="amount">${orderDto.total_ord_pric}원</span>
        </div>
        <a href="#" class="btn-pay">결제하기</a>
    </div>
</div>

</body>
</html>
