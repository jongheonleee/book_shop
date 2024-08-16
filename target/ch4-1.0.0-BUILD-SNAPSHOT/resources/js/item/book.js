// DOMContentLoaded 이벤트가 발생하면, HTML 문서의 모든 요소들이 로드된 후 아래 코드를 실행합니다.
document.addEventListener('DOMContentLoaded', function() {

    // 가격과 할인율을 입력받는 input 요소들을 선택합니다.
    const inputs = {
        paprPrice: $('input[name="papr_pric"]'),
        paprDisc: $('input[name="papr_disc"]'),
        ePrice: $('input[name="e_pric"]'),
        eDisc: $('input[name="e_disc"]'),
        imageUrl: $('#imageUrl'),
        imagePreview: $('#imagePreview')
    };

    // 최종 가격을 계산하고 업데이트하는 함수입니다.
    function calculatePrices() {
        const paprPric = parseFloat(inputs.paprPrice.val()) || 0;
        const paprDisc = parseFloat(inputs.paprDisc.val()) || 0;
        const ePric = parseFloat(inputs.ePrice.val()) || 0;
        const eDisc = parseFloat(inputs.eDisc.val()) || 0;

        const paprFinalPrice = paprPric * (1 - paprDisc / 100);
        const eFinalPrice = ePric * (1 - eDisc / 100);

        $('#papr_final_price').text(Math.floor(paprFinalPrice).toLocaleString('ko-KR') + '원');
        $('#e_final_price').text(Math.floor(eFinalPrice).toLocaleString('ko-KR') + '원');
    }

    // 이미지 미리보기 업데이트 함수
    function updateImagePreview() {
        var url = inputs.imageUrl.val();
        var imagePreview = inputs.imagePreview;

        if (url) {
            imagePreview.attr('src', url);
            imagePreview.show(); // 이미지가 보이도록 설정
        } else {
            imagePreview.hide(); // URL이 비어있으면 이미지 숨기기
        }
    }

    // 입력 필드의 oninput 이벤트에 함수들을 바인딩
    inputs.paprPrice.on('input', calculatePrices);
    inputs.paprDisc.on('input', calculatePrices);
    inputs.ePrice.on('input', calculatePrices);
    inputs.eDisc.on('input', calculatePrices);
    inputs.imageUrl.on('input', updateImagePreview);

    calculatePrices(); // 최종 가격을 계산하고 업데이트
    updateImagePreview(); // 초기 미리보기 업데이트
});


// jQuery로 문서 준비 이벤트 처리
$(function() {

    // 구매하기, 장바구니 버튼 눌렸을 때 반응하는 함수
    const handlePurchaseOrCart = (action) => {
        const selectedProductType = $('input[name="product_type"]:checked').val();
        const isbnValue = $('input[name="isbn"]').val();
        // 주문파트와 연결할 때 URL만 바꾸면됨
        const url = action === 'purchase' ? "<c:url value='/book/list'/>" : "<c:url value='/book/list'/>";

        location.href = `${url}?product_type=${selectedProductType}&isbn=${isbnValue}`;
    };

    // 구매하기 버튼
    $('#purchaseBtn').on("click", () => handlePurchaseOrCart('purchase'));
    // 장바구니 버튼
    $('#cartBtn').on("click", () => handlePurchaseOrCart('cart'));

    // 목록 버튼
    $('#listBtn').on("click", () => {
        const url = "/ch4/book/list";
        const fullUrl = `${url}?page=${page}&pageSize=${pageSize}&order_criteria=${order_criteria}&order_direction=${order_direction}`;
        location.href = fullUrl;
    });

    // 삭제 버튼
    $('#removeBtn').on("click", () => {
        if (!confirm("정말로 삭제하시겠습니까?")) return;
        const form = $('#form');
        if (form.length) {
            const actionUrl = `/ch4/book/remove?page=${page}&pageSize=${pageSize}&order_criteria=${order_criteria}&order_direction=${order_direction}`;
            form.attr({
                action: actionUrl,
                method: "post"
            }).submit();
        }
    });

    // 등록 버튼
    // 폼에 있는 input, textarea태그 값들 전송
    $('#writeBtn').on("click", () => {
        const form = $('#form');
        if (form.length) {
            const actionUrl = `/ch4/book/write`;
            form.attr({
                action: actionUrl,
                method: "post"
            }).submit();
        }
    });
});