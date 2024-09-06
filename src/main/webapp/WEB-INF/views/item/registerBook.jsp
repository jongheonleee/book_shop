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
  <script src="<c:url value='/js/item/registerBook.js'/>" defer></script>
  <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
  <script>
    // JSP 변수 값을 전역 변수로 설정
    var page = '<c:out value="${bsc.page}"/>';
    var pageSize = '<c:out value="${bsc.pageSize}"/>';
    var order_criteria = '<c:out value="${bsc.order_criteria}"/>';
    var order_direction = '<c:out value="${bsc.order_direction}"/>';

    let msg = '<c:out value="${msg}"/>';
    if(msg == "WRT_ERR") alert("게시물 등록에 실패했습니다. 다시 시도해 주세요.");

    var cateList = '<c:out value="${cateListJson}"/>';
    <%--console.log("stringify: ", JSON.stringify(${cateListJson}));--%>
    <%--let cateList = JSON.parse(JSON.stringify(${cateListJson}));--%>
    // console.log(cateList);
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
        <img id="imagePreview" src="<c:out value="${mode=='new' ? '' : bookDto.repre_img}"/>" alt="책 표지" style="${mode=='new' ? 'display:none;' : ''}">
        <input id="imageUrl" name="repre_img" type="text" value="<c:out value="${mode=='new' ? '' : bookDto.repre_img}"/>"
               style="${mode=='new' ? 'display:block;' : 'display:none;'}"
               placeholder="이미지 URL을 입력하세요"
               oninput="updateImagePreview()">
      </div>
      <div class="product-details">
        <p><strong>책 제목: </strong><input type="text" name="title" value="<c:out value="${bookDto.title}"/>"
                                         <c:if test="${mode != 'new'}">readonly="readonly"</c:if>></p>
        <div class="select-container">
          <strong>카테고리: </strong>
          <select id="level1" onchange="updateSelect(2)">
<%--            <c:forEach var="categoryDto" items="${cateList}">--%>
<%--              <c:if test="${categoryDto.lev == 1}">--%>
<%--                <option value="${categoryDto.cate_num}">${categoryDto.name}</option>--%>
<%--              </c:if>--%>
<%--            </c:forEach>--%>
          </select>

          <!-- 두 번째 select -->
          <select id="level2" onchange="updateSelect(3)">
            <option value="">Select Level 2</option>
            <!-- JavaScript로 동적 업데이트 -->
          </select>

          <!-- 세 번째 select -->
          <select id="level3" onchange="updateSelect(4)">
            <option value="">Select Level 3</option>
            <!-- JavaScript로 동적 업데이트 -->
          </select>

          <!-- 네 번째 select -->
          <select id="level4">
            <option value="">Select Level 4</option>
            <!-- JavaScript로 동적 업데이트 -->
          </select>
        </div>
        <p><strong>저자:</strong> <input type="text" name="wr_name" value="<c:out value="${bookDto.wr_name}"/>"
                                       <c:if test="${mode != 'new'}">readonly="readonly"</c:if>> / <strong>번역가:</strong> <input type="text" name="trl_name" value="<c:out value="${bookDto.trl_name}"/>"
                                                                                                                                <c:if test="${mode != 'new'}">readonly="readonly"</c:if>></p>
        <p><strong>출판사:</strong> <input type="text" name="pub_name" value="<c:out value="${bookDto.pub_name}"/>"
                                        <c:if test="${mode != 'new'}">readonly="readonly"</c:if>></p>
        <p><strong>종이책 정가:</strong> <input type="text" name="papr_pric" value="<c:out value="${bookDto.papr_pric}"/>"
                                           <c:if test="${mode != 'new'}">readonly="readonly"</c:if>> / <strong>ebook 정가:</strong> <input type="text" name="e_pric" value="<c:out value="${bookDto.e_pric}"/>"
                                                                                                                                         <c:if test="${mode != 'new'}">readonly="readonly"</c:if>></p>
        <p><strong>종이책 할인율:</strong> <input type="text" name="papr_disc" value="<c:out value="${bookDto.papr_disc}"/>"
                                            <c:if test="${mode != 'new'}">readonly="readonly"</c:if>> / <strong>ebook 할인율:</strong> <input type="text" name="e_disc" value="<c:out value="${bookDto.e_disc}"/>"
                                                                                                                                           <c:if test="${mode != 'new'}">readonly="readonly"</c:if>></p>
        <p><strong>종이책 할인가:</strong> <span id="papr_final_price">0원</span></p>
        <p><strong>ebook 할인가:</strong> <span id="e_final_price">0원</span></p>
        <p><strong>평점:</strong> <input type="text" name="rating" value="<c:out value="${bookDto.rating}"/>"
                                       <c:if test="${mode != 'new'}">readonly="readonly"</c:if>></p>
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
      <p><textarea name="intro_awrad" <c:if test="${mode != 'new'}">readonly="readonly"</c:if>><c:out value="${bookDto.intro_award}"/></textarea></p>

      <h3>도서 정보</h3>
      <p><textarea name="info" <c:if test="${mode != 'new'}">readonly="readonly"</c:if>><c:out value="${bookDto.info}"/></textarea></p>

      <h3>목차</h3>
      <p><textarea name="cont" <c:if test="${mode != 'new'}">readonly="readonly"</c:if>><c:out value="${bookDto.cont}"/></textarea></p>

      <h3>추천사</h3>
      <p><textarea name="rec" <c:if test="${mode != 'new'}">readonly="readonly"</c:if>><c:out value="${bookDto.rec}"/></textarea></p>

      <h3>출판사 서평</h3>
      <p><textarea name="pub_review" <c:if test="${mode != 'new'}">readonly="readonly"</c:if>><c:out value="${bookDto.pub_review}"/></textarea></p>

      <h3>ISBN, 발행/(출시)일자, 쪽수, 권수</h3>
      <p>ISBN: <input type="text" name="isbn" value="<c:out value="${bookDto.isbn}"/>"
                      <c:if test="${mode != 'new'}">readonly="readonly"</c:if>></p>
      <p>발행일자: <input type="date" name="pub_date" value="<c:out value="${bookDto.pub_date}"/>"
                      <c:if test="${mode != 'new'}">readonly="readonly"</c:if>></p>
      <p>쪽수: <input type="text" name="tot_page_num" value="<c:out value="${bookDto.tot_page_num}"/>"
                    <c:if test="${mode != 'new'}">readonly="readonly"</c:if>></p>
      <p>권수: <input type="text" name="tot_book_num" value="<c:out value="${bookDto.tot_book_num}"/>"
                    <c:if test="${mode != 'new'}">readonly="readonly"</c:if>></p>
    </section>
  </form>
</main>

<footer>
  <p>© 2024 OO문고. All rights reserved.</p>
</footer>
</body>
</html>



