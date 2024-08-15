$(document).ready(function() {
    // 수량 증가 버튼 클릭 이벤트
    $('.quantity-increase').on('click', function() {
        let quantityInput = $(this).siblings('.quantity-input');
        let quantity = parseInt(quantityInput.val());
        quantityInput.val(quantity + 1);

        updateTotalPrice($(this));
        updateSummary();
    });

    // 수량 감소 버튼 클릭 이벤트
    $('.quantity-decrease').on('click', function() {
        let quantityInput = $(this).siblings('.quantity-input');
        let quantity = parseInt(quantityInput.val());
        if (quantity > 1) {
            quantityInput.val(quantity - 1);
        }

        updateTotalPrice($(this));
        updateSummary();
    });

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

        $('.cart-item').each(function() {
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
});