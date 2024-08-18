<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%-- 기본값 설정 --%>
<%
    String orderCriteria = request.getParameter("order_criteria") != null ? request.getParameter("order_criteria") : "book_reg_date";
    String orderDirection = request.getParameter("order_direction") != null ? request.getParameter("order_direction") : "ASC";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>fastcampus</title>
    <link rel="stylesheet" href="<c:url value='/css/item/bookList.css'/>">
    <script src="<c:url value='/js/item/bookList.js'/>" defer></script>
</head>
<body onload="initializeFilter()"> <!-- 페이지 로드 시 초기 필터 설정 -->
<div id="menu">
    <ul>
        <li id="logo">fastcampus</li>
        <li><a href="<c:url value='/'/>">Home</a></li>
        <li><a href="<c:url value='/book/list'/>">BookList</a></li>
        <li><a href="<c:url value='/login/login'/>">Login</a></li>
        <li><a href="<c:url value='/register/add'/>">Sign in</a></li>
    </ul>
</div>
<script>
    let msg = "<c:out value='${msg}'/>";
    if(msg == "WRT_OK") alert("성공적으로 등록되었습니다.");
    if(msg == "DEL_OK") alert("성공적으로 삭제되었습니다.");
    if(msg == "DEL_ERR") alert("삭제에 실패했습니다.");
</script>

<div class="container">

    <div class="search-container">
        <form action="<c:url value='/book/list'/>" class="search-form" method="get">
            <select id="searchOption" class="search-option" name="option" data-selected="<c:out value='${param.option}'/>">
                <option value="T" ${param.option == 'T' ? 'selected' : ''}>제목</option>
                <option value="W" ${param.option == 'W' ? 'selected' : ''}>저자</option>
                <option value="P" ${param.option == 'P' ? 'selected' : ''}>출판사</option>
            </select>

            <input type="hidden" name="bookFilter" value="<c:out value='${param.bookFilter}'/>">
            <input type="hidden" name="order_criteria" value="<c:out value='${ph.bsc.order_criteria}'/>">
            <input type="hidden" name="order_direction" value="<c:out value='${ph.bsc.order_direction}'/>">

            <input type="text" name="keyword" class="search-input" value="<c:out value='${ph.bsc.keyword}'/>" placeholder="검색어를 입력해주세요">
            <input type="submit" class="btn btn-search" value="검색">
        </form>
    </div>

    <!-- 필터 폼과 버튼들을 포함한 컨테이너 -->
    <div class= "filter-container">
        <form id="filterForm" action="<c:url value='/book/list'/>" method="get" class="form-filter">
            <div class="filter-group">
                <label for="bookFilter">책 종류 선택:</label>
                <select id="bookFilter" name="bookFilter">
                    <option value="papr" ${param.bookFilter == 'papr' ? 'selected' : ''}>종이책</option>
                    <option value="ebook" ${param.bookFilter == 'ebook' ? 'selected' : ''}>eBook</option>
                </select>
            </div>

            <div class="filter-group">
                <label for="orderCriteria">정렬 기준:</label>
                <select id="orderCriteria" name="order_criteria" data-selected="<c:out value='${param.order_criteria}'/>">
                    <option value="book_reg_date" ${param.order_criteria == 'book_reg_date' ? 'selected' : ''}>상품등록일</option>
                    <option value="papr_disc_pric" ${param.order_criteria == 'papr_disc_pric' ? 'selected' : ''}>종이책 할인가</option>
                    <option value="e_disc_pric" ${param.order_criteria == 'e_disc_pric' ? 'selected' : ''}>eBook 할인가</option>
                    <option value="sale_vol" ${param.order_criteria == 'sale_vol' ? 'selected' : ''}>판매량</option>
                    <option value="pub_date" ${param.order_criteria == 'pub_date' ? 'selected' : ''}>출시일</option>
                </select>
            </div>

            <div class="filter-group">
                <label for="orderDirection">정렬 방향:</label>
                <select id="orderDirection" name="order_direction">
                    <option value="ASC" ${param.order_direction == 'ASC' ? 'selected' : ''}>오름차순</option>
                    <option value="DESC" ${param.order_direction == 'DESC' ? 'selected' : ''}>내림차순</option>
                </select>
            </div>

            <!-- 페이지 번호와 페이지 크기를 유지하기 위한 hidden input 추가 -->
            <input type="hidden" name="page" value="<c:out value='${ph.bsc.page}'/>">
            <input type="hidden" name="pageSize" value="<c:out value='${ph.bsc.pageSize}'/>">
            <input type="hidden" name="option" value="<c:out value='${ph.bsc.option}'/>">
            <input type="hidden" name="keyword" value="<c:out value='${ph.bsc.keyword}'/>">

            <!-- 정렬 버튼 추가 -->
            <button type="submit"  class="btn btn-sort">정렬하기</button>
            <!-- 글쓰기 버튼 추가 -->
            <button type="button" onclick="goToWritePage()" class="btn btn-write">상품등록</button>
        </form>
    </div>

    <!-- 정렬 필터 폼을 테이블 우측 상단에 배치 -->

    <div class="table-container">
        <table class="styled-table">
            <thead>
            <tr>
                <th>표지</th>
                <th>제목</th>
                <th>저자</th>
                <th>출판사</th>
                <th>할인율</th>
                <th>정가</th>
                <th>할인가</th>
                <th>도서 판매 코멘트</th>
                <th>상품 등록일</th>
                <th>판매량</th>
                <th>출시일</th>
                <th>평점</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="bookDto" items="${list}">
                <c:if test="${bookDto.papr_pric != null}">
                    <tr class="info papr">
                        <td><a href="<c:url value='/book/read${ph.bsc.queryString}&isbn=${bookDto.isbn}'/>"><img src="<c:out value='${bookDto.repre_img}'/>" alt="Book Cover" /></a></td>
                        <td><a href="<c:url value='/book/read${ph.bsc.queryString}&isbn=${bookDto.isbn}'/>"><c:out value='${bookDto.title}'/></a></td>
                        <td><c:out value='${bookDto.wr_name}'/></td>
                        <td><c:out value='${bookDto.pub_name}'/></td>
                        <td class="discount" data-discount="<c:out value='${bookDto.papr_disc}'/>"></td>
                        <td class="price" data-price="<c:out value='${bookDto.papr_pric}'/>"></td>
                        <td class="discounted-price" data-price="<c:out value='${bookDto.papr_pric}'/>" data-discount="<c:out value='${bookDto.papr_disc}'/>"></td>
                        <td><c:out value='${bookDto.sale_com}'/></td>
                        <td><fmt:formatDate value="${bookDto.book_reg_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td class="sale-volume" data-sale-volume="<c:out value='${bookDto.sale_vol}'/>"></td>
                        <td><c:out value='${bookDto.pub_date}'/></td>
                        <td><c:out value='${bookDto.rating}'/></td>
                    </tr>
                </c:if>
                <c:if test="${bookDto.e_pric != null}">
                    <tr class="info ebook">
                        <td><a href="<c:url value='/book/read${ph.bsc.queryString}&isbn=${bookDto.isbn}'/>"><img src="<c:out value='${bookDto.repre_img}'/>" alt="Book Cover" /></a></td>
                        <td><a href="<c:url value='/book/read${ph.bsc.queryString}&isbn=${bookDto.isbn}'/>"><c:out value='${bookDto.title}'/></a></td>
                        <td><c:out value='${bookDto.wr_name}'/></td>
                        <td><c:out value='${bookDto.pub_name}'/></td>
                        <td class="discount" data-discount="<c:out value='${bookDto.e_disc}'/>"></td>
                        <td class="price" data-price="<c:out value='${bookDto.e_pric}'/>"></td>
                        <td class="discounted-price" data-price="<c:out value='${bookDto.e_pric}'/>" data-discount="<c:out value='${bookDto.e_disc}'/>"></td>
                        <td><c:out value='${bookDto.sale_com}'/></td>
                        <td><fmt:formatDate value="${bookDto.book_reg_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td class="sale-volume" data-sale-volume="<c:out value='${bookDto.sale_vol}'/>"></td>
                        <td><c:out value='${bookDto.pub_date}'/></td>
                        <td><c:out value='${bookDto.rating}'/></td>
                    </tr>
                </c:if>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="pagination">
        <c:if test="${ph.showPrev}">
            <a href="<c:url value='/book/list${ph.bsc.getQueryString(ph.beginPage-1)}'/>">&lt;</a>
        </c:if>
        <c:forEach var="i" begin="${ph.beginPage}" end="${ph.endPage}">
            <a href="<c:url value='/book/list${ph.bsc.getQueryString(i)}'/>"><c:out value='${i}'/></a>
        </c:forEach>
        <c:if test="${ph.showNext}">
            <a href="<c:url value='/book/list${ph.bsc.getQueryString(ph.endPage+1)}'/>">&gt;</a>
        </c:if>
    </div>
</div>
</body>
</html>
