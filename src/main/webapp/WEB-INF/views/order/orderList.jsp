<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>주문/배송 목록</title>
    <link rel="stylesheet" href="<c:url value='/css/orderList.css'/>">
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

<main>
    <div class="order-summary-container">
        <div class="order-summary-header">
            <h2>주문/배송 목록</h2>
            <span class="recent-info">*최근 6개월 주문내역 입니다.</span>
            <button class="detail-button">상세조회 <i class="fas fa-calendar-alt"></i></button>
        </div>

        <div class="order-status-container">
            <div class="status-box">
                <strong>주문내역</strong>
                <p>주문/배송안내 <i class="fas fa-question-circle"></i></p>
            </div>
            <div class="status-box">
                <strong>${empty orderList ? 0 : orderCount.orderWaitCount}</strong>
                <p>준비중</p>
            </div>
            <div class="status-box">
                <strong>${empty orderList ? 0 : orderCount.deliveryDoingCount}</strong>
                <p>배송중</p>
            </div>
            <div class="status-box">
                <strong>${empty orderList ? 0 : orderCount.deliveryDoneCount}</strong>
                <p>배송완료</p>
            </div>
            <div class="status-box">
                <strong>${empty orderList ? 0 : 0}</strong>
                <p>취소</p>
            </div>
            <div class="status-box">
                <strong>${empty orderList ? 0 : 0}</strong>
                <p>교환/반품</p>
            </div>
        </div>
        <c:choose>
            <c:when test="${empty orderList}">
                <div class="order-item-container">
                    <div class="no-orders-container">
                        <div class="no-orders-icon">
                            <i class="fas fa-exclamation-circle"></i>
                        </div>
                        <p class="no-orders-message">해당 기간에 주문한 상품이 없습니다.</p>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach var="order" items="${orderList}">
                    <div class="order-item-container">
                        <div class="order-header">
                            <span>${order.orderDto.created_at}(${order.orderDto.ord_seq})</span>
                            <a href="<c:url value='/order/detail/${order.orderDto.ord_seq}'/>" class="detail-link">상세보기</a>
                        </div>

                        <c:forEach var="item" items="${order.orderProductDtoList}">
                            <div class="order-item">
                                <div class="item-image">
                                    <img src="${item.repre_img}" alt="${item.book_title}">
                                </div>
                                <div class="item-details">
                                    <p class="item-title">[${item.prod_type_code == 'paper' ? '국내도서' : 'EBOOK'}] ${item.book_title}</p>
                                    <p class="item-quantity">수량: ${item.item_quan}</p>
                                </div>
                                <div class="item-price">
                                    <p>${item.ord_pric}원</p>
                                </div>
                            </div>
                        </c:forEach>

                        <div class="order-footer">
                            <div class="shipping-info">
                                <span class="status">${order.orderDto.deli_stat_name}</span>
<%--                                <span class="date">${order.shippingDate}</span>--%>
<%--                                <span class="tracking-number">${order.trackingNumber}</span>--%>
                            </div>
                            <div class="order-actions">
                                <button class="btn-review">리뷰 작성</button>
                                <button class="btn-tracking">배송조회</button>
                                <button class="btn-receipt">문장수집</button>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>

    </div>
</main>

<footer>
    <p>© 2024 OO문고. All rights reserved.</p>
</footer>
</body>
</html>