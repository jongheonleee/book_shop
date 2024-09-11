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
        const baseUrl = action === 'purchase' ? "/ch4/order/orderView" : "/ch4/cart/add";
        // const fullUrl = `${baseUrl}?product_type=${selectedProductType}&isbn=${isbnValue}`;
        const fullUrl = `${baseUrl}`;

        const form = $('#form');
        if (form.length) {
            // 장바구니
            if (action === 'cart') {
                form.attr({
                    action : fullUrl,
                    method : "post"
                }).submit();
            }

            // 구매하기 -> 주문/결제 페이지
            if (action === 'purchase') {
                const purchaseRequestBody = {
                    'delivery_fee': 0,
                    'orderItemDtoList': [
                        {
                            isbn: $('input[name="isbn"]').val(),
                            'prod_type_code': $('input[name=\'product_type\']').val(),
                            'item_quan': 1
                        }
                    ]
                }
                submitForm(purchaseRequestBody)
            }
        }
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

    // 수정 버튼
    $('modifyBtn').on("click", () => {
        // 1. 읽기 상태이면 수정 상태로 변경
        const form = $('#form');
        let isReadOnly = $("input[name=wr_name]")
        // 2. 수정 상태이면 수정된 내용을 서버로 전송

        const cateNum = getCateNum();

        // cate_num을 폼 데이터에 추가
        $('#form').append(`<input type="hidden" name="cate_num" value="${cateNum}">`);

        if (form.length) {
            const actionUrl = `/ch4/book/write`;
            form.attr({
                action: actionUrl,
                method: "post"
            }).submit();
        }
    });
});

// 레벨에 맞춰 select 박스를 업데이트하는 함수
function updateSelect(level) {
    const prevLevel = level - 1;
    let selectedValue = "";
    for (let i = 1; i <= prevLevel; i++) {
        selectedValue += document.getElementById(`level${i}`).value;
    }
    const nextSelect = document.getElementById(`level${level}`);;

    // 선택된 값이 없으면 다음 레벨 select 초기화
    if (!selectedValue) {
        nextSelect.style.display = 'none'; // 선택된 값이 없으면 select 박스를 숨김
        return;
    }

    // cateList에서 앞자리가 일치하는 옵션 필터링
    const options = cateList.filter(categoryDto => {
        return categoryDto.lev == level && categoryDto.cate_num.startsWith(selectedValue);
        console.log(`Checking ${categoryDto.cate_num} with level ${categoryDto.lev}`);
    });

    // 새 옵션으로 select 박스를 업데이트
    nextSelect.innerHTML = options.map(categoryDto => {
        return `<option value="${categoryDto.cur_layr_num}">${categoryDto.name}</option>`;
    }).join('');

    // 옵션이 있으면 다음 셀렉트 박스를 표시함
    if (options.length > 0) {
        nextSelect.style.display = 'block';
    } else {
        nextSelect.style.display = 'none';
    }

    // 만약 마지막 카테고리라면 다음 셀렉트 박스를 숨김
    if (options.some(categoryDto => categoryDto.last_cate_chk === 'Y')) {
        if (level < 4) {
            document.getElementById(`level${level + 1}`).style.display = 'none';
        }
    }
}

// JSON 데이터를 사용하여 동적으로 콘텐츠를 생성
$(document).ready(function () {
    const container = $('#level1');
    cateList.forEach(categoryDto => {
        if (categoryDto.lev == 1) {
            container.append(`<option value="${categoryDto.cur_layr_num}">${categoryDto.name}</option>`);
        }
    });

    // 첫 번째 셀렉트 박스의 첫 번째 옵션을 자동으로 선택
    const firstOptionValue = $('#level1 option').eq(0).val(); // 첫 번째 옵션이 <option value="">Select Level 1</option>일 경우 두 번째 옵션 선택
    $('#level1').val(firstOptionValue);
    // 선택된 첫 번째 옵션에 따라 다음 레벨 셀렉트 박스 업데이트
    updateSelect(2);

    // 두 번째 셀렉트 박스의 첫 번째 옵션을 자동으로 선택
    const secondOptionValue = $('#level2 option').eq(0).val(); // 첫 번째 옵션이 <option value="">Select Level 2</option>일 경우 두 번째 옵션 선택
    $('#level2').val(secondOptionValue);
    // 선택된 두 번째 옵션에 따라 다음 레벨 셀렉트 박스 업데이트
    updateSelect(3);

    // 세 번째 셀렉트 박스의 첫 번째 옵션을 자동으로 선택하고 마지막 카테고리 여부 확인
    const thirdOptionValue = $('#level3 option').eq(0).val(); // 첫 번째 옵션이 <option value="">Select Level 3</option>일 경우 두 번째 옵션 선택
    $('#level3').val(thirdOptionValue);
    updateSelect(4);
});

function getCateNum() {
    // 각 레벨의 select 박스에서 선택된 값을 가져옵니다.
    const level1Value = document.getElementById('level1').value;
    const level2Value = document.getElementById('level2').value;
    const level3Value = document.getElementById('level3').value;
    const level4Value = document.getElementById('level4').value;

    // 선택된 값들을 합쳐 하나의 문자열로 만듭니다.
    const combinedCateNum = `${level1Value}${level2Value}${level3Value}${level4Value}`;

    console.log('Combined Cate Num:', combinedCateNum); // 디버깅을 위한 로그

    return combinedCateNum;
}

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