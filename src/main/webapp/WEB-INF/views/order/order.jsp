<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false" %>
<html>
<head>
    <title>Order</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
    <script src="https://js.tosspayments.com/v2/standard"></script>
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
    </div>
</div>
<div class="order-container">
    <div class="order-summary">
        <!-- 결제 UI -->
        <div id="payment-method"></div>
        <!-- 이용약관 UI -->
        <div id="agreement"></div>
        <button id="payment-button" class="btn-pay">결제하기</button>
    </div>
</div>
<script type="text/javascript">
    // JSP에서 cartItemList를 JavaScript 변수로 할당
    var orderProductList = [];

    <c:forEach var="item" items="${orderItemList}">
        orderProductList.push({
            isbn: "${item.isbn}",
            prodTypeCode: "${item.prod_type_code}",
            itemQuan: "${item.item_quan}"
        });
    </c:forEach>
</script>
<script src="<c:url value='/js/order.js'/>"></script>
<script>
    main();

    async function main() {
        const button = document.getElementById("payment-button");
        // ------  결제위젯 초기화 ------
        const clientKey = "test_gck_docs_Ovk5rk1EwkEbP0W43n07xlzm";
        const tossPayments = TossPayments(clientKey);
        // 회원 결제
        const customerKey = "mCEp_QbYxElNaPblVNaOy";
        const widgets = tossPayments.widgets({
            customerKey,
        });

        // ------ 주문의 결제 금액 설정 ------
        await widgets.setAmount({
            currency: "KRW",
            value: ${orderDto.total_ord_pric},
        });

        await Promise.all([
            // ------  결제 UI 렌더링 ------
            widgets.renderPaymentMethods({
                selector: "#payment-method",
                variantKey: "DEFAULT",
            }),
            // ------  이용약관 UI 렌더링 ------
            widgets.renderAgreement({ selector: "#agreement", variantKey: "AGREEMENT" }),
        ]);

        // ------ '결제하기' 버튼 누르면 결제창 띄우기 ------
        button.addEventListener("click", async function () {
            orderViewDto = await requestCreateOrder()
                .then((res) => {
                    return res.json();
                });

            // TODO : 주문 번호 최소 6자 이상으로 인해 앞에 "orderId" 를 추가
            await widgets.requestPayment({
                orderId: "orderId" + orderViewDto['orderDto']['ord_seq'],
                orderName: orderViewDto['orderProductDtoList'][0]['book_title'] + " 외 " + (orderViewDto['orderProductDtoList'].length - 1) +"건",
                successUrl: window.location.origin + "/success.html",
                failUrl: window.location.origin + "/fail.html",
                customerEmail: "test@gmail.com",
                customerName: orderViewDto['orderDto']['cust_id'],
                customerMobilePhone: "01012345678",
            });
        });
    }

    async function requestCreateOrder() {
        const orderItemList = orderProductList.map(orderItem => {
            return {
                isbn: orderItem.isbn,
                'prod_type_code': orderItem.prodTypeCode,
                'item_quan': orderItem.itemQuan
            };
        });

        const requestBody = {
            'delivery_fee': 0,
            'orderItemDtoList': orderItemList
        };

        return fetch('/ch4/order/order', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(requestBody),
        });
    }
</script>
</body>
</html>
