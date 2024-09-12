<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>공지사항 목록</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .notice-section {
            width: 80%;
            margin: 0 auto;
        }
        .notice-header {
            margin-top: 20px;
            font-size: 24px;
            font-weight: bold;
        }
        .filter-section {
            margin: 20px 0;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .filter-section select {
            margin-right: 10px;
        }
        .total-count {
            font-size: 16px;
            color: #555;
        }
        .table-container {
            margin-top: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        table th, table td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        table th {
            background-color: #f2f2f2;
            font-weight: bold;
        }
        table th.no, table th.cate, table td.no, table td.cate {
            text-align: center;
        }
        table th.no {
            width: 10%;
        }
        table th.cate {
            width: 15%;
        }
        table th.title {
            width: 70%;
        }

        table td a {
            color: black;
            text-decoration: none;
        }

        table td a:hover {
            color: #4CAF50;
        }

        .pagination {
            display: flex;
            justify-content: center;
            margin-bottom: 20px;
        }
        .pagination a {
            padding: 8px 16px;
            text-decoration: none;
            border: 1px solid #ccc;
            margin: 0 4px;
            border-radius: 4px;
            color: #333;
        }
        .pagination a.active {
            background-color: #4CAF50;
            color: white;
        }
        .pagination a.disabled {
            pointer-events: none;
            color: #ccc;
        }
    </style>
    <script>
        // 카테고리 선택 시, 페이지를 리로드하면서 선택된 카테고리를 쿼리 스트링에 추가
        function changeCategory() {
            const selectedCategory = document.getElementById("cateCode").value;
            const pageSize = '${ph.pageSize}'; // 현재 페이지 크기
            const page = '${ph.page}'; // 현재 페이지
            window.location.href = "?cateCode=" + selectedCategory + "&page=" + page + "&pageSize=" + pageSize;
        }
    </script>
</head>
<body>

<div class="notice-section">
    <!-- Notice Header -->
    <div class="notice-header">
        공지사항
    </div>

    <!-- 카테고리 선택 필터 및 총 게시글 수 -->
    <div class="filter-section">
        <div>
            <label for="cateCode">카테고리: </label>
            <select id="cateCode" name="cateCode" onchange="changeCategory()">
                <option value="ALL" ${cateCode == 'ALL' ? 'selected' : ''}>전체</option>
                <option value="CUSTSVC" ${cateCode == 'CUSTSVC' ? 'selected' : ''}>고객센터</option>
                <option value="OFFSTORE" ${cateCode == 'OFFSTORE' ? 'selected' : ''}>오프라인매장</option>
                <option value="ONLSTORE" ${cateCode == 'ONLSTORE' ? 'selected' : ''}>온라인매장</option>
                <option value="EBOOK" ${cateCode == 'EBOOK' ? 'selected' : ''}>EBOOK</option>
            </select>
        </div>
        <div class="total-count">
            총 ${totalCnt}건
        </div>
    </div>

    <!-- Notice Table -->
    <div class="table-container">
        <table>
            <thead>
            <tr>
                <th class="no">NO</th>
                <th class="cate">유형</th>
                <th class="title">공지제목</th>
            </tr>
            </thead>
            <tbody>
            <!-- 동적으로 공지사항 리스트 출력 -->
            <c:forEach var="notice" items="${notices}">
                <tr>
                    <td class="no">${notice.ntc_seq}</td>
                    <td class="cate">${notice.cate_name}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/cscenter/notice/detail/${notice.ntc_seq}">
                                ${notice.title}
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Pagination -->
    <div class="pagination">
        <!-- 이전 페이지 링크 -->
        <c:if test="${ph.page > 1}">
            <a href="?cateCode=${cateCode}&page=1&pageSize=${ph.pageSize}">&laquo; 처음</a>
            <a href="?cateCode=${cateCode}&page=${ph.page - 1}&pageSize=${ph.pageSize}">&lt; 이전</a>
        </c:if>
        <c:if test="${ph.page == 1}">
            <a class="disabled">&laquo; 처음</a>
            <a class="disabled">&lt; 이전</a>
        </c:if>

        <!-- 페이지 번호 링크 -->
        <c:forEach begin="${ph.beginPage}" end="${ph.endPage}" var="i">
            <a href="?cateCode=${cateCode}&page=${i}&pageSize=${ph.pageSize}" class="${i == ph.page ? 'active' : ''}">${i}</a>
        </c:forEach>

        <!-- 다음 페이지 링크 -->
        <c:if test="${ph.page < ph.totalPage}">
            <a href="?cateCode=${cateCode}&page=${ph.page + 1}&pageSize=${ph.pageSize}">다음 &gt;</a>
            <a href="?cateCode=${cateCode}&page=${ph.totalPage}&pageSize=${ph.pageSize}">끝 &raquo;</a>
        </c:if>
        <c:if test="${ph.page == ph.totalPage}">
            <a class="disabled">다음 &gt;</a>
            <a class="disabled">끝 &raquo;</a>
        </c:if>
    </div>

</div>

</body>
</html>