<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
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

        .container {
            width : 50%;
            margin : auto;
        }

        .writing-header {
            position: relative;
            margin: 20px 0 0 0;
            padding-bottom: 10px;
            border-bottom: 1px solid #323232;
        }

        input {
            width: 100%;
            height: 35px;
            margin: 5px 0px 10px 0px;
            border: 1px solid #e9e8e8;
            padding: 8px;
            background: #f8f8f8;
            outline-color: #e6e6e6;
        }

        textarea {
            width: 100%;
            background: #f8f8f8;
            margin: 5px 0px 10px 0px;
            border: 1px solid #e9e8e8;
            resize: none;
            padding: 8px;
            outline-color: #e6e6e6;
        }

        .frm {
            width:100%;
        }
        .btn {
            background-color: rgb(236, 236, 236); /* Blue background */
            border: none; /* Remove borders */
            color: black; /* White text */
            padding: 6px 12px; /* Some padding */
            font-size: 16px; /* Set a font size */
            cursor: pointer; /* Mouse pointer on hover */
            border-radius: 5px;
        }

        .btn:hover {
            text-decoration: underline;
        }

        /* 기본 스타일을 유지하면서 게시글 노출 여부를 조정하는 CSS */
        .container {
            width: 50%;
            margin: auto;
        }

        .writing-header {
            position: relative;
            margin: 20px 0 0 0;
            padding-bottom: 10px;
            border-bottom: 1px solid #323232;
        }

        input {
            width: 100%;
            height: 35px;
            margin: 5px 0 10px 0;
            border: 1px solid #e9e8e8;
            padding: 8px;
            background: #f8f8f8;
            outline-color: #e6e6e6;
        }

        textarea {
            width: 100%;
            background: #f8f8f8;
            margin: 5px 0 10px 0;
            border: 1px solid #e9e8e8;
            resize: none;
            padding: 8px;
            outline-color: #e6e6e6;
        }

        .frm {
            width: 100%;
        }

        .btn {
            background-color: rgb(236, 236, 236); /* Button background color */
            border: none; /* Remove borders */
            color: black; /* Text color */
            padding: 6px 12px; /* Padding */
            font-size: 16px; /* Font size */
            cursor: pointer; /* Pointer cursor on hover */
            border-radius: 5px; /* Rounded corners */
        }

        .btn:hover {
            text-decoration: underline; /* Underline text on hover */
        }

        /* Flexbox를 사용하여 카테고리와 체크박스를 같은 줄에 배치 */
        .options-container {
            display: flex;
            align-items: center; /* 수직 정렬 */
            justify-content: space-between; /* 아이템 사이의 간격을 균등하게 분배 */
            margin-bottom: 10px; /* 아래쪽 여백 조정 */
        }

        .options-container .search-option {
            margin-right: 20px; /* 카테고리와 체크박스 사이의 여백 */
        }

        .checkbox-container {
            display: flex;
            align-items: center; /* 체크박스와 레이블을 수직으로 정렬 */
        }

        .checkbox-container input {
            margin-right: 5px; /* 체크박스와 레이블 사이의 여백 */
            transform: scale(0.5); /* 체크박스 크기 조절 */
        }

    </style>
</head>
<body>
<div id="menu">
    <ul>
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
    if(msg=="WRT_ERR") alert("게시물 등록에 실패하였습니다. 다시 시도해 주세요.");
    if(msg=="MOD_ERR") alert("게시물 수정에 실패하였습니다. 다시 시도해 주세요.");
</script>
<div class="container">
    <h2 class="writing-header">게시판 ${mode=="new" ? "글쓰기" : "읽기"}</h2>
    <form id="form" class="frm" action="" method="post">
        <!-- 카테고리 선택과 체크박스가 같은 줄에 위치하도록 감싸는 div 추가 -->
        <div class="options-container">
<%--            <!-- 카테고리 선택하는 칸 -->--%>
<%--            <select class="search-option" name="faq_catg_code">--%>
<%--                <c:forEach var="faqCateDto" items="${faqCateList}">--%>
<%--                    <option value="${faqCateDto.cate_code}"> ${faqCateDto.name} </option>--%>
<%--                </c:forEach>--%>
<%--            </select>--%>

            <!-- 카테고리 선택하는 칸 -->
            <select class="search-option" name="faq_catg_code">
                <c:forEach var="faqCateDto" items="${faqCateList}">
                    <option value="${faqCateDto.cate_code}"
                            <c:if test="${faqCateDto.cate_code eq faqDto.faq_catg_code}">selected</c:if>>
                            ${faqCateDto.name}
                    </option>
                </c:forEach>
            </select>

            <!-- 게시글 노출 여부 -->
    <c:if test="${role eq 'admin'}">
        <div class="checkbox-container">
            <label>
                <input type="checkbox" id="checkbox" name="dsply_chk" value="Y"
                       <c:if test="${faqDto.dsply_chk eq 'Y'}">checked</c:if>> display check
            </label>
        </div>
    </c:if>
        </div>

        <input type="hidden" name="faq_seq" value="${faqDto.faq_seq}">

        <input name="title" type="text" value="${faqDto.title}" placeholder=" 제목을 입력해 주세요." ${mode=="new" ? "" : "readonly='readonly'"}><br>
        <textarea name="cont" rows="20" placeholder=" 내용을 입력해 주세요." ${mode=="new" ? "" : "readonly='readonly'"}>${faqDto.cont}</textarea><br>

        <c:if test="${role eq 'admin'}">
            <c:if test="${mode eq 'new'}">
                <button type="button" id="writeBtn" class="btn btn-write"><i class="fa fa-pencil"></i> 등록</button>
            </c:if>
            <c:if test="${mode ne 'new'}">
                <button type="button" id="writeNewBtn" class="btn btn-write"><i class="fa fa-pencil"></i> 글쓰기</button>
            </c:if>
<%--            <c:if test="${faqDto.reg_id eq loginId}">--%>
                <button type="button" id="modifyBtn" class="btn btn-modify"><i class="fa fa-edit"></i> 수정</button>
                <button type="button" id="removeBtn" class="btn btn-remove"><i class="fa fa-trash"></i> 삭제</button>
<%--            </c:if>--%>
        </c:if>

<%--        <c:if test="${mode eq 'new'}">--%>
<%--            <button type="button" id="writeBtn" class="btn btn-write"><i class="fa fa-pencil"></i> 등록</button>--%>
<%--        </c:if>--%>
<%--        <c:if test="${mode ne 'new'}">--%>
<%--            <button type="button" id="writeNewBtn" class="btn btn-write"><i class="fa fa-pencil"></i> 글쓰기</button>--%>
<%--        </c:if>--%>
<%--        <c:if test="${faqDto.reg_id eq loginId}">--%>
<%--            <button type="button" id="modifyBtn" class="btn btn-modify"><i class="fa fa-edit"></i> 수정</button>--%>
<%--            <button type="button" id="removeBtn" class="btn btn-remove"><i class="fa fa-trash"></i> 삭제</button>--%>
<%--        </c:if>--%>

        <button type="button" id="listBtn" class="btn btn-list"><i class="fa fa-bars"></i> 목록</button>
    </form>


</div>
<script>
    $(document).ready(function(){
        let formCheck = function() {
            let form = document.getElementById("form");
            if(form.title.value=="") {
                alert("제목을 입력해 주세요.");
                form.title.focus();
                return false;
            }

            if(form.cont.value=="") {
                alert("내용을 입력해 주세요.");
                form.cont.focus();
                return false;
            }
            return true;
        }

        $("#writeNewBtn").on("click", function(){
            location.href="<c:url value='/cscenter/faq/write'/>";
        });

        $("#writeBtn").on("click", function(){
            let form = $("#form");
            form.attr("action", "<c:url value='/cscenter/faq/write'/>");
            form.attr("method", "post");

            if(formCheck())
                form.submit();
        });

        $("#modifyBtn").on("click", function(){
            let form = $("#form");
            let isReadonly = $("input[name=title]").attr('readonly');

            // 1. 읽기 상태이면, 수정 상태로 변경
            if(isReadonly=='readonly') {
                $(".writing-header").html("게시판 수정");
                $("input[name=title]").attr('readonly', false);
                $("textarea").attr('readonly', false);
                $("#modifyBtn").html("<i class='fa fa-pencil'></i> 등록");
                return;
            }

            // 2. 수정 상태이면, 수정된 내용을 서버로 전송
            form.attr("action", "<c:url value='/cscenter/faq/modify/${faqDto.faq_seq}'/>");
            form.attr("method", "post");
            if(formCheck())
                form.submit();
        });

        $("#removeBtn").on("click", function(){
            if(!confirm("정말로 삭제하시겠습니까?")) return;

            let form = $("#form");
            form.attr("action", "<c:url value='/cscenter/faq/remove/${faqDto.faq_seq}'/>");
            form.attr("method", "post");
            form.submit();
        });

        $("#listBtn").on("click", function(){
            location.href="<c:url value='/cscenter/faq/list/00'/>";
        });
    });
</script>
</body>
</html>