<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false"%>
<c:set var="loginId" value="${pageContext.request.getSession(false)==null ? '' : pageContext.request.session.getAttribute('id')}"/>
<c:set var="loginOutLink" value="${loginId=='' ? '/login/login' : '/login/logout'}"/>
<c:set var="loginOut" value="${loginId=='' ? 'Login' : 'ID='+=loginId}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>교보문고</title>
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
    <style>
      .container {
        background-color: white;
        border-radius: 12px;
        padding: 15px 20px;
        display: flex;
        align-items: center;
        gap: 10px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
      }

      .dropdown, .date-input, .search-button {
        border-radius: 8px;
        padding: 8px 12px;
        border: 1px solid #ccc;
        font-size: 14px;
      }

      .dropdown {
        cursor: pointer;
        border: 1px solid #000;
      }

      .date-input {
        display: flex;
        align-items: center;
        justify-content: space-between;
        background-color: #f7f7f7;
        border: 1px solid #ccc;
      }

      .search-button {
        background-color: white;
        color: #3b50a6;
        border: 1px solid #3b50a6;
        cursor: pointer;
      }

      .search-button:hover {
        background-color: #eef1ff;
      }

      .inquiry-button .icon {
        margin-right: 6px;
      }

      .note {
        font-size: 14px;
        color: #666;
        margin-bottom: 10px;
      }

      .tabs {
        display: flex;
        border-bottom: 1px solid #ccc;
      }

      .tab {
        flex: 1;
        padding: 12px 0;
        text-align: center;
        cursor: pointer;
        color: #888;
        border: 1px solid transparent;
        border-bottom: none;
        transition: color 0.3s ease;
      }

      .tab.active {
        color: #000;
        border: 1px solid #000;
        border-bottom: 1px solid #fff;
        font-weight: bold;
      }

      .tab-content {
        border: 1px solid #ccc;
        padding: 20px;
        display: none;
      }

      .tab-content.active {
        display: block;
      }


      .inquiry-button {
        background-color: #4a4fb3; /* 버튼 배경 색상 */
        color: white; /* 텍스트 색상 */
        border: none; /* 테두리 제거 */
        border-radius: 8px; /* 테두리 반경 */
        padding: 10px 20px; /* 패딩 */
        margin: 20px;
        display: flex; /* 아이템들을 수평 정렬 */
        align-items: center; /* 아이콘과 텍스트를 수직 정렬 */
        font-size: 16px; /* 텍스트 크기 */
        font-weight: 500; /* 텍스트 두께 */
        cursor: pointer; /* 커서 포인터로 변경 */
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* 그림자 효과 */
        transition: background-color 0.3s ease; /* 호버 시 배경색 전환 효과 */
      }

      .inquiry-button:hover {
        background-color: #3b50a6; /* 호버 시 배경 색상 */
      }

      .inquiry-button .icon {
        margin-right: 8px; /* 아이콘과 텍스트 간격 */
      }


      .board-container {
        width: 100%;
        max-width: 100%;
        background-color: #fff;
        padding: 20px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        border-radius: 5px;
      }

      h1 {
        text-align: center;
        margin-bottom: 20px;
        font-size: 24px;
      }

      .board {
        width: 100%;
        border-collapse: collapse;
        margin-bottom: 20px;
      }

      .board th, .board td {
        border: 1px solid #ddd;
        padding: 10px;
        text-align: center;
      }

      .board th {
        background-color: #f8f8f8;
      }

      .board tbody tr:hover {
        background-color: #f1f1f1;
      }

      .board a {
        color: #007bff;
        text-decoration: none;
      }

      .board a:hover {
        text-decoration: underline;
      }

      .pagination {
        text-align: center;
        margin-top: 10px;
      }

      .pagination a {
        color: #007bff;
        padding: 8px 16px;
        text-decoration: none;
        border: 1px solid #ddd;
        margin: 0 4px;
        border-radius: 4px;
      }

      .pagination a.active {
        background-color: #007bff;
        color: white;
        border: 1px solid #007bff;
      }

      .pagination a:hover:not(.active) {
        background-color: #ddd;
      }

      .search-input {
        display: none;
        padding: 5px 10px;
        font-size: 16px;
        border: 1px solid #ccc;
        border-radius: 4px;
        margin-left: 10px;
      }

      .right-align {
        margin-left: 42.5%;
        padding: 10px;
      }


    </style>
    <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
</head>
<body>
<!-- 메뉴 영역 -->
<div id="menu">
    <ul>
        <li id="logo">교보문고</li>
        <li><a href="<c:url value='/'/>">Home</a></li>
        <li><a href="<c:url value='/qa/list'/>">QA</a></li>
        <%--        <li><a href="<c:url value='${loginOutLink}'/>">${loginOut}</a></li>--%>
        <li><a href="<c:url value='/register/add'/>">Sign in</a></li>
    </ul>
</div>

<!-- 기간 검색 칸 -->
<div id="search-area" class="container">
    <form id="search-period-form" method="get" class="container">
        <select class="dropdown">
            <option value="3">3개월</option>
            <option value="6">6개월</option>
            <option value="12">1년</option>
        </select>

        <div class="date-input">
            <input type="date" value="2024-05-08">
        </div>
        <span>~</span>
        <div class="date-input">
            <input type="date" value="2024-08-08">
        </div>

        <button id="search-period-button" class="search-button">조회</button>
    </form>

    <div id="search-title" class="right-align container">
        <input type="text" id="search-title-input" class="search-title-input" placeholder="Search...">
        <button id="search-title-button" class="search-button">
            <i class="fa fa-search"></i>
        </button>
    </div>
</div>


<%--<div class="note">--%>
<%--    • 1:1문의내역 조회는 최대 3년까지 가능합니다.--%>
<%--</div>--%>

<!-- 상태별 구분 칸 -->
<div id="tabs" class="container">
    <div class="tab active" data-tab="tab-1">전체</div>
    <div class="tab" data-tab="tab-2">준비중</div>
    <div class="tab" data-tab="tab-3">처리중</div>
    <div class="tab" data-tab="tab-4">답변완료</div>
</div>

<!-- 문의글 목록-->
<%--<div id="list" style="text-align: center">--%>
<div class="board-container">
    <h1> 1 : 1 문의(Q&A)</h1>
    <table class="board">
        <thead>
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>카테고리</th>
                <th>상태</th>
                <th>날짜</th>
    <%--            <th>작성자</th>--%>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="qaDto" items="${selected}">
                <tr>
                    <td>${qaDto.qa_num}</td>
                    <td><a href="<c:url value='/qa/${qaDto.qa_num}?page=${ph.page}&pageSize=${ph.pageSize}'/>">${qaDto.title}</a></td>
                    <td>${qaDto.cate_name}</td>
                    <td>${qaDto.chk_repl}</td>
                    <td>${qaDto.created_at}</td>
                </tr>
            </c:forEach>

        <!-- 추가 게시글 -->
        </tbody>
    </table>
    <div class="pagination">
<%--        <c:if test="${ph.totalCnt == 0}">--%>
<%--            <div>문의글 없음</div>--%>
<%--        </c:if>--%>

        <c:if test="${ph.totalCnt != null && ph.totalCnt != 0}">
            <c:if test="${ph.prev}">
                <a class="page" href="<c:url value="/qa/list?page=${ph.page-1}&pageSize=${ph.pageSize}"/>">&lt;</a>
            </c:if>

            <c:forEach var="i" begin="${ph.beginPage}" end="${ph.endPage}">
                <a class="page" href="<c:url value="/qa/list?page=${i}&pageSize=${ph.pageSize}"/>"> ${i} </a>
            </c:forEach>

            <c:if test="${ph.next}">
                <a class="page" href="<c:url value="/qa/list?page=${ph.page+1}&pageSize=${ph.pageSize}"/>">&gt;</a>
            </c:if>
        </c:if>
    </div>
</div>

<!-- 문의 작성 버튼 -->
<button class="inquiry-button">
    <span class="icon">✏️</span> 1:1문의하기
</button>

<script>
  const SEARCH_KEYWORD_PERIOD = 'period';
  const SEARCH_KEYWORD_TITLE = 'title';

  $(document).ready(function() {
    // 기간 검색 버튼 핸들러
    $('#search-period-button').click(function (e) {
      // 선택한 옵션의 값을 조회
      const selectedOption = $('.dropdown').val();
      alert(selectedOption);
      // 서버에 get 파라미터로 요청
      location.href = 'http://localhost:8080/ch4/qa/search?option=' + SEARCH_KEYWORD_PERIOD + '&period=' + selectedOption;
      alert(location.href);
    });

    // // 제목 검색 버튼 핸들러
    $('#search-title-button').click(function (e) {
      // 입력값 조회
      const titleKeyword = document.getElementById('search-title-input').value;
      // 서버에 get 파라미터로 요청
      location.href = 'http://localhost:8080/ch4/qa/search?option=' + SEARCH_KEYWORD_TITLE + '&titleKeyword=' + titleKeyword;
    });
  });
</script>

</body>
</html>
