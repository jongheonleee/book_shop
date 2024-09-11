$(document).ready(function () {
    // 수량 증가 버튼 클릭 이벤트
    $('.quantity-increase').on('click', function () {
        let quantityInput = $(this).siblings('.quantity-input');
        let quantity = parseInt(quantityInput.val());

        let cartSeq = $(this).data('cartSeq');
        let isbn = $(this).data('isbn');
        let prodCodeType = $(this).data('prod_code_type');

        quantityInput.val(quantity + 1);
        updateTotalPrice($(this));
        updateSummary();
    });

    // 수량 감소 버튼 클릭 이벤트
    $('.quantity-decrease').on('click', function () {
        let quantityInput = $(this).siblings('.quantity-input');
        let quantity = parseInt(quantityInput.val());
        if (quantity > 1) {
            quantityInput.val(quantity - 1);
        }

        updateTotalPrice($(this));
        updateSummary();
    });

    $('.btn-order').on('click', function () {
        const orderItemList = cartItemList.map(cartItem => {
            return {
                isbn: cartItem.isbn,
                'prod_type_code': cartItem.prodTypeCode,
                'item_quan': cartItem.itemQuan
            };
        });

        const requestBody = {
            'delivery_fee': 0,
            'orderItemDtoList': orderItemList
        };

        submitForm(requestBody);
    });

    // requestBody 데이터를 form으로 전송하는 함수
    function submitForm(requestBody) {
        // 새로운 form element를 생성
        const form = document.createElement('form');
        form.method = 'POST';
        form.action = '/ch4/order/order'; // 서버에서 처리할 URL

        // delivery_fee 필드를 form에 추가
        const deliveryFeeInput = document.createElement('input');
        deliveryFeeInput.type = 'hidden';
        deliveryFeeInput.name = 'delivery_fee';
        deliveryFeeInput.value = requestBody.delivery_fee;
        form.appendChild(deliveryFeeInput);

        // orderItemDtoList의 각 항목을 form에 추가
        requestBody.orderItemDtoList.forEach((item, index) => {
            // isbn 필드
            const isbnInput = document.createElement('input');
            isbnInput.type = 'hidden';
            isbnInput.name = `orderItemDtoList[${index}].isbn`;
            isbnInput.value = item.isbn;
            form.appendChild(isbnInput);

            // prodCodeType 필드
            const prodCodeInput = document.createElement('input');
            prodCodeInput.type = 'hidden';
            prodCodeInput.name = `orderItemDtoList[${index}].prod_type_code`;
            prodCodeInput.value = item.prod_type_code;
            form.appendChild(prodCodeInput);

            // quantity 필드
            const quantityInput = document.createElement('input');
            quantityInput.type = 'hidden';
            quantityInput.name = `orderItemDtoList[${index}].item_quan`;
            quantityInput.value = item.item_quan;
            form.appendChild(quantityInput);
        });

        // form을 body에 추가하고 전송
        document.body.appendChild(form);
        form.submit();
    }

    // 총 판매가 업데이트 함수
    function updateTotalPrice(element) {
        let cartItem = element.closest('.cart-item');
        let quantity = parseInt(cartItem.find('.quantity-input').val());
        let regularPrice = parseFloat(cartItem.find('.regular-price').text().replace('원', '').replace(',', ''));
        let discountPercentage = parseFloat(cartItem.find('.discount').text().replace('%', '')) / 100;
        let salePrice = regularPrice * (1 - discountPercentage);

        let totalPrice = salePrice * quantity;

        cartItem.find('.item-total-price .label').html('총 판매가: ' + totalPrice.toLocaleString() + '원');
    }

    // 요약 정보 업데이트 함수
    function updateSummary() {
        let totalProductAmount = 0;
        let totalDiscountAmount = 0;
        let finalPaymentAmount = 0;
        let totalPoints = 0;

        $('.cart-item').each(function () {
            let quantity = parseInt($(this).find('.quantity-input').val());
            let regularPrice = parseFloat($(this).find('.regular-price').text().replace('원', '').replace(',', ''));
            let discountPercentage = parseFloat($(this).find('.discount').text().replace('%', '')) / 100;
            let salePrice = regularPrice * (1 - discountPercentage);
            let discountAmount = regularPrice * discountPercentage;

            totalProductAmount += regularPrice * quantity;
            totalDiscountAmount += discountAmount * quantity;
            finalPaymentAmount += salePrice * quantity;
            totalPoints += parseFloat($(this).find('.reward-points').text().replace('P', '').replace('(', '').replace(')', '')) * quantity;
        });

        $('.total-product-amount .amount').text(totalProductAmount.toLocaleString() + '원');
        $('.total-discount-amount .amount').text(totalDiscountAmount.toLocaleString() + '원');
        $('.final-payment-amount .amount').text(finalPaymentAmount.toLocaleString() + '원');
        $('.summary-footer .point').text('총 예상적립 포인트: ' + totalPoints.toLocaleString() + '원');
    }

    // 페이지 로드 시 요약 정보 초기화
    updateSummary();
})
;