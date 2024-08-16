<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ page session="true"%>
<c:set var="loginId" value="${sessionScope.id}"/>
<c:set var="loginOutLink" value="${loginId=='' ? '/login/login' : '/login/logout'}"/>
<c:set var="loginOut" value="${loginId=='' ? 'Login' : 'ID='+=loginId}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>fastcampus</title>
    <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: "Noto Sans KR", sans-serif;
        }

        a {
            text-decoration: none;
            color: black;
        }
        button,
        input {
            border: none;
            outline: none;
        }

        .container {
            display: flex;
            justify-content: space-between;
            width: 80%;
            margin: 0 auto;
        }

        .board-container {
            width: 75%;
            /* border: 1px solid black; */
        }

        .sidebar {
            width: 20%;
            border-left: 1px solid #ddd;
            padding-left: 20px;
        }

        .search-container {
            background-color: rgb(253, 253, 250);
            width: 100%;
            height: 110px;
            border: 1px solid #ddd;
            margin-top: 10px;
            margin-bottom: 30px;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .search-form {
            height: 37px;
            display: flex;
        }

        .search-option {
            width: 100px;
            height: 100%;
            outline: none;
            margin-right: 5px;
            border: 1px solid #ccc;
            color: gray;
        }

        .search-option > option {
            text-align: center;
        }

        .search-input {
            color: gray;
            background-color: white;
            border: 1px solid #ccc;
            height: 100%;
            width: 300px;
            font-size: 15px;
            padding: 5px 7px;
        }

        .search-input::placeholder {
            color: gray;
        }

        .search-button {
            width: 20%;
            height: 100%;
            background-color: rgb(22, 22, 22);
            color: rgb(209, 209, 209);
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 15px;
        }

        .search-button:hover {
            color: rgb(165, 165, 165);
        }

        table {
            border-collapse: collapse;
            width: 100%;
            border-top: 2px solid rgb(39, 39, 39);
        }

        tr:nth-child(even) {
            background-color: #f0f0f070;
        }

        th,
        td {
            width: 300px;
            text-align: center;
            padding: 10px 12px;
            border-bottom: 1px solid #ddd;
        }

        td {
            color: rgb(53, 53, 53);
        }

        .no {
            width: 150px;
        }

        .title {
            width: 50%;
        }

        td.title {
            text-align: left;
        }

        td.category {
            text-align: left;
        }

        td.viewcnt {
            text-align: right;
        }

        td.title:hover {
            text-decoration: underline;
        }

        .paging {
            color: black;
            width: 100%;
            align-items: center;
        }

        .page {
            color: black;
            padding: 6px;
            margin-right: 10px;
        }

        .paging-active {
            background-color: rgb(216, 216, 216);
            border-radius: 5px;
            color: rgb(24, 24, 24);
        }

        .paging-container {
            width: 100%;
            height: 70px;
            display: flex;
            margin-top: 50px;
            margin: auto;
        }

        .header-container {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }

        .total-count {
            font-size: 16px;
            color: #333;
        }

        .write-button {
            /* 버튼의 스타일을 유지하기 위한 추가 스타일 */
        }

        .btn-write {
            background-color: rgb(236, 236, 236);
            border: none;
            color: black;
            padding: 6px 12px;
            font-size: 16px;
            cursor: pointer;
            border-radius: 5px;
            margin-left: 30px;
        }

        .btn-write:hover {
            text-decoration: underline;
        }

        .category-button {
            background-color: #e0e0e0;
            border: none;
            border-radius: 5px;
            color: #333;
            font-size: 14px;
            padding: 10px 20px;
            margin: 5px;
            cursor: pointer;
            transition: background-color 0.3s, color 0.3s, box-shadow 0.3s;
        }

        .category-button:hover {
            background-color: #d0d0d0;
            color: #000;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }

        .category-button:active {
            background-color: #b0b0b0;
        }

        .sidebar-item {
            margin-bottom: 10px;
        }

        .sidebar-item a {
            text-decoration: none;
            color: #333;
            display: block;
            padding: 10px;
            border-radius: 5px;
            transition: background-color 0.3s, color 0.3s;
        }

        .sidebar-item a:hover {
            background-color: #f0f0f0;
            color: #000;
        }
    </style>
</head>
<body>
<div id="menu">
    <ul id="menu-list">
        <li id="logo">fastcampus</li>
        <li><a href="<c:url value='/'/>">Home</a></li>
        <li><a href="<c:url value='/cscenter/faq/list/00'/>">FAQ</a></li>
        <li><a href="<c:url value='${loginOutLink}'/>">${loginOut}</a></li>
        <li><a href="<c:url value='/register/add'/>">Sign in</a></li>
        <li><a href=""><i class="fa fa-search"></i></a></li>
    </ul>
</div>

<script>
    let msg = "${msg}";
    if(msg=="LIST_ERR")  alert("게시물 목록을 가져오는데 실패했습니다. 다시 시도해 주세요.");
    if(msg=="READ_ERR")  alert("삭제되었거나 없는 게시물입니다.");
    if(msg=="DEL_AUTH_ERR")   alert("삭제 권한이 없습니다.");
    if(msg=="DEL_NOT_FOUND_ERR")   alert("이미 삭제되었거나 없는 게시물입니다.");

    if(msg=="DEL_OK")    alert("성공적으로 삭제되었습니다.");
    if(msg=="WRT_OK")    alert("성공적으로 등록되었습니다.");
    if(msg=="MOD_OK")    alert("성공적으로 수정되었습니다.");
</script>

<div class="container">
    <div class="board-container">
        <div class="search-container">
            <form action="<c:url value="/cscenter/faq/search"/>" class="search-form" method="get">
                <select class="search-option" name="option">
                    <option value="A" ${option=='A' ? "selected" : ""}>제목+내용</option>
                    <option value="T" ${option=='T' ? "selected" : ""}>제목만</option>
                </select>

                <input type="text" name="keyword" class="search-input" type="text" value="${param.keyword}" placeholder="검색어를 입력해주세요">
                <input type="submit" class="search-button" value="검색">
            </form>
        </div>

        <c:if test="${not empty faqCateList}">
            <div style="text-align:center">
                <c:forEach var="faqCateDto" items="${faqCateList}">
                    <a href="<c:url value='/cscenter/faq/list/${faqCateDto.cate_code}'/>">
                        <button class="category-button">${faqCateDto.name}</button>
                    </a>
                </c:forEach>
            </div>
        </c:if>

        <div class="header-container">
            <div class="total-count">
                <span>총 ${totalCnt} 건</span>
            </div>
            <c:if test="${role == 'admin'}">
                <div class="write-button">
                    <a href="<c:url value='/cscenter/faq/write'/>">
                        <button class="btn-write">글쓰기</button>
                    </a>
                </div>
            </c:if>
        </div>

        <table>
            <tr>
                <th class="category">카테고리</th>
                <th class="title">제목</th>
            </tr>
            <c:forEach var="faqDto" items="${faqList}">
                <tr>
                    <td class="category">${faqDto.catg_name}</td>
                    <td class="title">
                        <a href="<c:url value='/cscenter/faq/detail/${faqDto.faq_seq}'/>">${faqDto.title}</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <br>

        <div class="paging-container">
            <div class="paging">
                <c:if test="${totalCnt==null || totalCnt==0}">
                    <div> 게시물이 없습니다. </div>
                </c:if>
                <!-- Paging code should be uncommented as needed -->
            </div>
        </div>
    </div>

    <div class="sidebar">
        <c:if test="${not empty faqCateTotalList}">
            <div>
                <c:forEach var="faqCateDto" items="${faqCateTotalList}">
                    <div class="sidebar-item">
                        <a href="<c:url value='/cscenter/faq/list/${faqCateDto.cate_code}'/>">${faqCateDto.name}</a>
                    </div>
                </c:forEach>
            </div>
        </c:if>
    </div>
</div>
</body>
</html>
