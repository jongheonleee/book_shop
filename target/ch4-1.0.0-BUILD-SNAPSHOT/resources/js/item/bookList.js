// DOMContentLoaded 이벤트가 발생하면 initializeFilter 함수 호출
document.addEventListener("DOMContentLoaded", function() {
    initializeFilter();

    // 이벤트 핸들러 설정
    document.getElementById('bookFilter').addEventListener('change', function() {
        // 책 종류 변경 시, 해당 종류의 기본 정렬 기준을 설정
        filterSelection();
        updateOrderCriteriaOptions(); // 필터링 후 정렬 기준 업데이트
    });
});

// 페이지 로드 시 초기 필터 및 데이터 설정 함수
function initializeFilter() {
    filterSelection(); // 필터링 수행
    updateOrderCriteriaOptions(); // 정렬 기준 옵션 업데이트
    setDiscounts(); // 할인율 설정
    setPrices(); // 정가 설정
    setDiscountedPrices(); // 할인가 설정
    setSaleVolumes(); // 판매량 설정
}

// 글쓰기 페이지로 이동하는 함수
function goToWritePage() {
    window.location.href = "/ch4/book/write";
}

// 책 종류 필터를 적용하여 책 정보를 표시하는 함수
function filterSelection() {
    const filter = document.getElementById("bookFilter").value; // 선택된 필터 값 가져오기
    document.querySelectorAll('.info').forEach(el => {
        // 필터와 일치하는 책 정보는 표시하고, 나머지는 숨김
        el.style.display = el.classList.contains(filter) ? 'table-row' : 'none';
    });
}

// 정렬 기준 옵션을 업데이트하는 함수
function updateOrderCriteriaOptions() {
    const bookFilter = document.getElementById("bookFilter").value; // 선택된 책 종류
    const orderCriteriaSelect = document.getElementById("orderCriteria"); // 정렬 기준 셀렉트 박스
    const selectedOrderCriteria = orderCriteriaSelect.getAttribute('data-selected'); // 현재 선택된 옵션 값 가져오기

    // 옵션들 정의
    const options = {
        papr: [
            { value: 'book_reg_date', text: '상품등록일' },
            { value: 'papr_disc_pric', text: '종이책 할인가' },
            { value: 'sale_vol', text: '판매량' },
            { value: 'pub_date', text: '출시일' }
        ],
        ebook: [
            { value: 'book_reg_date', text: '상품등록일' },
            { value: 'e_disc_pric', text: 'eBook 할인가' },
            { value: 'sale_vol', text: '판매량' },
            { value: 'pub_date', text: '출시일' }
        ]
    };

    // 현재 선택된 책 종류에 맞는 옵션 가져오기
    const selectedOptions = options[bookFilter] || [];

    // // 기존 옵션 제거
    // while (orderCriteriaSelect.firstChild) {
    //     orderCriteriaSelect.removeChild(orderCriteriaSelect.firstChild);
    // }
    //
    // // 새로운 옵션 추가
    // selectedOptions.forEach(function(option) {
    //     var opt = document.createElement('option');
    //     opt.value = option.value;
    //     opt.textContent = option.text;
    //     // 현재 선택된 옵션 값과 일치하면 selected 속성 추가
    //     if (option.value === selectedOrderCriteria) {
    //         opt.selected = true;
    //     }
    //     orderCriteriaSelect.appendChild(opt);
    // });

    // 기존 옵션 제거 후 새 옵션 추가
    orderCriteriaSelect.innerHTML = selectedOptions.map(option =>
        `<option value="${option.value}" ${option.value === selectedOrderCriteria ? 'selected' : ''}>${option.text}</option>`
    ).join('');

    // 정렬 기준 업데이트 후, 선택된 값이 없는 경우 기본 옵션 설정
    if (!orderCriteriaSelect.value) {
        orderCriteriaSelect.value = selectedOptions[0]?.value || '';
    }
}

// "정렬하기" 버튼 클릭 시, 폼 제출 함수
function applySorting() {
    document.getElementById('filterForm').submit(); // 폼 제출
}

// 숫자를 정수로 포맷팅하는 함수
const formatNumberToInteger = value => Math.floor(value);

// 숫자를 천 단위로 구분하여 포맷팅하는 함수
const formatNumberWithCommas = value => value.toLocaleString();

// 할인율을 적용하여 할인된 가격을 계산하는 함수
const getDiscountedPrice = (price, discount) => Math.floor(price * (1 - discount / 100));


// 할인율을 설정하여 표시하는 함수
function setDiscounts() {
    document.querySelectorAll('.discount').forEach(element => {
        const discount = parseFloat(element.getAttribute('data-discount')); // 할인율 가져오기
        if (!isNaN(discount)) {
            // 할인율을 정수로 포맷팅
            element.innerText = `${formatNumberToInteger(discount)}%`;
        }
    });
}

// 정가를 포맷팅하여 표시하는 함수
function setPrices() {
    document.querySelectorAll('.price').forEach(element => {
        const price = parseFloat(element.getAttribute('data-price')); // 정가 가져오기
        if (!isNaN(price)) {
            // 정가를 천 단위로 포맷팅
            element.innerText = `${formatNumberWithCommas(price)}원`;
        }
    });
}

// 할인가를 계산하여 표시하는 함수
function setDiscountedPrices() {
    document.querySelectorAll('.discounted-price').forEach(element => {
        const price = parseFloat(element.getAttribute('data-price')); // 정가 가져오기
        const discount = parseFloat(element.getAttribute('data-discount')); // 할인율 가져오기
        if (!isNaN(price) && !isNaN(discount)) {
            // 할인 적용 후 할인가를 계산하고 포맷팅하여 표시
            const discountedPrice = getDiscountedPrice(price, discount);
            element.innerText = formatNumberWithCommas(discountedPrice) + "원";
        }
    });
}

// 판매량을 포맷팅하여 표시하는 함수
function setSaleVolumes() {
    document.querySelectorAll('.sale-volume').forEach(element => {
        const volume = parseFloat(element.getAttribute('data-sale-volume')); // 판매량 가져오기
        if (!isNaN(volume)) {
            // 판매량을 천 단위로 포맷팅
            element.innerText = formatNumberWithCommas(volume);
        }
    });
}

// 검색 기능 (실제 검색 로직 추가 필요)
function searchBooks() {
    const query = document.querySelector('.search-input').value;
    // 여기에 검색 관련 로직 추가
    alert('검색 기능이 구현되어야 합니다: ' + query);
}
