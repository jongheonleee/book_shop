<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>fastcampus</title>
    <%--    <link rel="stylesheet" href="<c:url value='/css/item/menu.css'/>">--%>
    <link rel="stylesheet" href="<c:url value='/css/item/book.css'/>">
    <script src="<c:url value='/js/item/book.js'/>" defer></script>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script>
        // JSP 변수 값을 전역 변수로 설정
        var page = '${page}';
        var pageSize = '${pageSize}';
        var order_criteria = '${order_criteria}';
        var order_direction = '${order_direction}';

        let msg = "${msg}";
        if(msg == "WRT_ERR") alert("게시물 등록에 실패했습니다. 다시 시도해 주세요.");
    </script>
</head>
<body>
<div id="menu">
    <ul>
        <li id="logo">fastcampus</li>
        <li><a href="<c:url value='/'/>">Home</a></li>
        <li><a href="<c:url value='/book/list'/>">BookList</a></li>
        <li><a href="<c:url value='/login/login'/>">Login</a></li>
        <li><a href="<c:url value='/register/add'/>">Sign in</a></li>
    </ul>
</div>

<main>
    <form action="" id="form">
        <h2>상품 ${mode=="new" ? "등록" : "조회"}</h2>
        <!-- 버튼들을 가로로 정렬 -->
        <div class="button-group">
            <button type="button" id="writeBtn" class="btn">등록</button>
            <button type="button" id="modifyBtn" class="btn">수정</button>
            <button type="button" id="removeBtn" class="btn">삭제</button>
            <button type="button" id="listBtn" class="btn">목록</button>
        </div>

        <!-- 상단 정보 -->
        <section class="product-overview">
            <div class="product-image">
                <img id="imagePreview" src="${mode=='new' ? '' : bookDto.repre_img}" alt="책 표지" style="${mode=='new' ? 'display:none;' : ''}">
                <input id="imageUrl" name="repre_img" type="text" value="${mode=='new' ? '' : bookDto.repre_img}"
                       style="${mode=='new' ? 'display:block;' : 'display:none;'}"
                       placeholder="이미지 URL을 입력하세요"
                       oninput="updateImagePreview()">
            </div>
            <div class="product-details">
                <p><strong>책 제목: </strong><input type="text" name="title" value="${bookDto.title}" ${mode=="new" ? '' : 'readonly="readonly"'}></p>
                <p><strong>카테고리:</strong> <input type="text" name="whol_layr_name" value="${bookDto.whol_layr_name}" ${mode=="new" ? '' : 'readonly="readonly"'}></p>
                <p><strong>저자:</strong> <input type="text" name="wr_name" value="${bookDto.wr_name}" ${mode=="new" ? '' : 'readonly="readonly"'}> / <strong>번역가:</strong> <input type="text" name="trl_name" value="${bookDto.trl_name}" ${mode=="new" ? '' : 'readonly="readonly"'}></p>
                <p><strong>출판사:</strong> <input type="text" name="pub_name" value="${bookDto.pub_name}" ${mode=="new" ? '' : 'readonly="readonly"'}>
                <p><strong>종이책 정가:</strong> <input type="text" name="papr_pric" value="${bookDto.papr_pric}" ${mode=="new" ? '' : 'readonly="readonly"'}> / <strong>ebook 정가:</strong> <input type="text" name="e_pric" value="${bookDto.e_pric}" ${mode=="new" ? '' : 'readonly="readonly"'}></p>
                <p><strong>종이책 할인율:</strong> <input type="text" name="papr_disc" value="${bookDto.papr_disc}" ${mode=="new" ? '' : 'readonly="readonly"'}> / <strong>ebook 할인율:</strong> <input type="text" name="e_disc" value="${bookDto.e_disc}" ${mode=="new" ? '' : 'readonly="readonly"'}></p>
                <p><strong>종이책 할인가:</strong> <span id="papr_final_price">0원</span></p>
                <p><strong>ebook 할인가:</strong> <span id="e_final_price">0원</span></p>
                <p><strong>평점:</strong> <input type="text" name="rating" value="${bookDto.rating}" ${mode=="new" ? '' : 'readonly="readonly"'}></p>
                <p>
                    <strong>구매할 상품 유형:</strong>
                    <label>
                        <input type="radio" name="product_type" value="paper" checked> 종이책
                    </label>
                    <label>
                        <input type="radio" name="product_type" value="ebook"> ebook
                    </label>
                </p>
                <!-- 버튼들을 가로로 정렬 -->
                <div class="button-group">
                    <button type="button" class="buy-btn" id="purchaseBtn">구매하기</button>
                    <button type="button" class="buy-btn" id="cartBtn">장바구니</button>
                </div>
            </div>
        </section>

        <!-- 추가 정보 -->
        <section class="book-details">
            <h3>수상 내역/미디어 추천</h3>
            <p><textarea name="intro_awrad" ${mode=="new" ? '' : 'readonly="readonly"'}>${bookDto.intro_award}</textarea></p>

            <h3>도서 정보</h3>
            <p><textarea name="info" ${mode=="new" ? '' : 'readonly="readonly"'}>${bookDto.info}</textarea></p>

            <h3>목차</h3>
            <p><textarea name="cont" ${mode=="new" ? '' : 'readonly="readonly"'}>${bookDto.cont}</textarea></p>

            <h3>추천사</h3>
            <p><textarea name="rec" ${mode=="new" ? '' : 'readonly="readonly"'}>${bookDto.rec}</textarea></p>

            <h3>출판사 서평</h3>
            <p><textarea name="pub_review" ${mode=="new" ? '' : 'readonly="readonly"'}>${bookDto.pub_review}</textarea></p>

            <h3>ISBN, 발행/(출시)일자, 쪽수, 권수</h3>
            <p>ISBN: <input type="text" name="isbn" value="${bookDto.isbn}" ${mode=="new" ? '' : 'readonly="readonly"'}></p>
            <p>발행일자: <input type="text" name="pub_date" value="${bookDto.pub_date}" ${mode=="new" ? '' : 'readonly="readonly"'}></p>
            <p>쪽수: <input type="text" name="tot_page_num" value="${bookDto.tot_page_num}" ${mode=="new" ? '' : 'readonly="readonly"'}></p>
            <p>권수: <input type="text" name="tot_book_num" value="${bookDto.tot_book_num}" ${mode=="new" ? '' : 'readonly="readonly"'}></p>
        </section>
    </form>
</main>

<footer>
    <p>© 2024 OO문고. All rights reserved.</p>
</footer>
</body>
</html>
