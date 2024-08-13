
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

      .notice-container {
        width: 100%;
        height: 500px;
        background-color: #fff;
        padding: 20px;
        border-radius: 10px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      }

      .answer-container {
        width: 100%;
        height: 200px;
        background-color: lightgray;
        padding: 20px;
        border-radius: 10px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      }

      h1 {
        font-size: 24px;
        margin-bottom: 10px;
      }

      .notice-info {
        font-size: 14px;
        color: #555;
        margin-bottom: 20px;
      }

      .notice-content {
        font-size: 16px;
        line-height: 1.5;
        margin-bottom: 30px;
      }

      .notice-content p {
        margin-bottom: 10px;
      }

      .notice-content ul {
        list-style-type: disc;
        margin: 10px 0 10px 20px;
      }

      .notice-content ul li {
        margin-bottom: 5px;
      }

      .notice-navigation {
        display: flex;
        justify-content: space-between;
        font-size: 14px;
      }

      .notice-navigation .nav-item {
        display: flex;
        align-items: center;
      }

      .notice-navigation .nav-item span {
        font-weight: bold;
        margin-right: 10px;
      }

      .notice-navigation a {
        color: #007bff;
        text-decoration: none;
      }

      .notice-navigation a:hover {
        text-decoration: underline;
      }
    </style>
</head>
<body>
<div id="menu">
    <ul>
        <li id="logo">서점📚</li>
        <li><a href="<c:url value='/'/>">Home</a></li>
        <li><a href="<c:url value='/qa/list'/>">QA</a></li>
        <%--        <li><a href="<c:url value='${loginOutLink}'/>">${loginOut}</a></li>--%>
        <li><a href="<c:url value='/register/add'/>">Sign in</a></li>
    </ul>
</div>
<script>
  let msg = "${msg}";
  if(msg=="WRT_ERR") alert("게시물 등록에 실패하였습니다. 다시 시도해 주세요.");
  if(msg=="MOD_ERR") alert("게시물 수정에 실패하였습니다. 다시 시도해 주세요.");
</script>
<div class="notice-container">
    <h1>${qa.title}</h1>
    <div class="notice-info">
        <span> 문의 유형 : ${qa.cate_name}</span> | <span> 문의 처리 상태 : ${qa.stat_name}</span> | <span> 답변 여부 : ${qa.chk_repl}</span>
    </div>
    <div class="notice-content">
        ${qa.content}
    </div>
</div>

<div class="answer-container">
    <div class="notice-navigation">
        <h1>안녕하세요! 해당 문의글에 대한 답변입니다.</h1>

        <div class="nav-item">
            <span>운영자1</span>
            <span>작성일자 : 2024.05.07</span>
        </div>
    </div>
    <div>"성공은 영원하지 않으며, 실패도 치명적이지 않다. 중요한 것은 계속 나아가는 용기다."</div>
</div>


<%--<div class="container">--%>
<%--    <h2 class="writing-header">문의글 ${mode=="new" ? "글쓰기" : "읽기"}</h2>--%>
<%--    <form id="form2" class="frm" action="" method="post">--%>
<%--        <input type="hidden" name="bno" value="${qa.qa_num}">--%>

<%--        <h1>${qa.title}</h1>--%>
<%--        <div>--%>
<%--            <span>${qa.cate_name}</span>--%>
<%--            <span>${qa.stat_name}</span>--%>
<%--            <span>${qa.chk_repl}</span>--%>
<%--        </div>--%>

<%--        <p>--%>
<%--            ${qa.content}--%>
<%--        </p>--%>

<%--        <p>--%>
<%--            문의 글에 대한 답변--%>
<%--        </p>--%>
<%--    </form>--%>


<%--    <form id="form" class="frm" action="" method="post">--%>
<%--        <input type="hidden" name="bno" value="${boardDto.bno}">--%>

<%--        <input name="title" type="text" value="${qa.title}" placeholder="  제목을 입력해 주세요." ${mode=="new" ? "" : "readonly='readonly'"}><br>--%>
<%--        <textarea name="content" rows="20" placeholder=" 내용을 입력해 주세요." ${mode=="new" ? "" : "readonly='readonly'"}>${qa.content}</textarea><br>--%>

<%--        <c:if test="${mode eq 'new'}">--%>
<%--            <button type="button" id="writeBtn" class="btn btn-write"><i class="fa fa-pencil"></i> 등록</button>--%>
<%--        </c:if>--%>
<%--        <c:if test="${mode ne 'new'}">--%>
<%--            <button type="button" id="writeNewBtn" class="btn btn-write"><i class="fa fa-pencil"></i> 글쓰기</button>--%>
<%--        </c:if>--%>
<%--        <c:if test="${boardDto.writer eq loginId}">--%>
<%--            <button type="button" id="modifyBtn" class="btn btn-modify"><i class="fa fa-edit"></i> 수정</button>--%>
<%--            <button type="button" id="removeBtn" class="btn btn-remove"><i class="fa fa-trash"></i> 삭제</button>--%>
<%--        </c:if>--%>
<%--        <button type="button" id="listBtn" class="btn btn-list"><i class="fa fa-bars"></i> 목록</button>--%>
<%--    </form>--%>
<%--</div>--%>
<script>
  $(document).ready(function(){
    let formCheck = function() {
      let form = document.getElementById("form");
      if(form.title.value=="") {
        alert("제목을 입력해 주세요.");
        form.title.focus();
        return false;
      }

      if(form.content.value=="") {
        alert("내용을 입력해 주세요.");
        form.content.focus();
        return false;
      }
      return true;
    }

    $("#writeNewBtn").on("click", function(){
      location.href="<c:url value='/board/write'/>";
    });

    $("#writeBtn").on("click", function(){
      let form = $("#form");
      form.attr("action", "<c:url value='/board/write'/>");
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
      form.attr("action", "<c:url value='/board/modify${searchCondition.queryString}'/>");
      form.attr("method", "post");
      if(formCheck())
        form.submit();
    });

    $("#removeBtn").on("click", function(){
      if(!confirm("정말로 삭제하시겠습니까?")) return;

      let form = $("#form");
      form.attr("action", "<c:url value='/board/remove${searchCondition.queryString}'/>");
      form.attr("method", "post");
      form.submit();
    });

    $("#listBtn").on("click", function(){
      location.href="<c:url value='/board/list${searchCondition.queryString}'/>";
    });
  });
</script>
</body>
</html>
