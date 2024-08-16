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

      .category-container {
        /* background-color: #f8f8f8; */
        padding: 3px;
        font-size: 5px;
        border-radius: 1px;
        /* box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); */
        text-align: left;

      }

      label {
        font-size: 16px;
        margin-right: 10px;
      }

      select {
        padding: 10px;
        font-size: 16px;
        border: 1px solid #ccc;
        border-radius: 5px;
        cursor: pointer;
      }

      select:focus {
        outline: none;
        border-color: #007bff;
        box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
      }
    </style>
</head>
<body>
<div id="menu">
    <ul>
        <li id="logo">서점📚</li>
        <li><a href="<c:url value='/'/>">Home</a></li>
        <li><a href="<c:url value='/qa/list'/>">QA</a></li>
        <li><a href="<c:url value='/register/add'/>">Sign in</a></li>
    </ul>
</div>
<script>
  let msg = "${msg}";
  if(msg=="WRT_ERR") alert("게시물 등록에 실패하였습니다. 다시 시도해 주세요.");
  if(msg=="MOD_ERR") alert("게시물 수정에 실패하였습니다. 다시 시도해 주세요.");
</script>
<div class="container">
    <h2 class="writing-header">1:1 문의글 수정 </h2>
    <form id="form" class="frm" action="" method="post">
        <!-- 카테고리 -->
        <div class="category-container">
            <label for="category-select">1:1문의 유형 선택:</label>

            <select class="category-select">
                <c:forEach var="category" items="${categories}">
                    <option name="cate_code" value="${category.code}">${category.code_name}</option>
                </c:forEach>
            </select>
        </div>


        <!-- 제목 & 내용-->
        <input name="user_id" value="${qa.user_id}" type="hidden">
        <input id="category" name="qa_cate_num" type="hidden" placeholder="${qa.qa_cate_num}" value="${qa.qa_cate_num}" >
        <input name="title" type="text" placeholder="${qa.title}" value="${qa.title}"><br>
        <textarea name="content" rows="20" placeholder="${qa.content}" ></textarea><br>
        <button type="button" id="writeBtn" class="btn btn-write"><i class="fa fa-pencil"></i> 수정</button>
        <button type="button" id="listBtn" class="btn btn-list"><i class="fa fa-bars"></i> 목록</button>
    </form>
</div>
<script>
  $(document).ready(function(){
    let formCheck = function() {
      let form = document.getElementById("form");

      const selectedOption = $('.category-select').val();
      $("#category").val(selectedOption);


      if(form.title.value==="") {
        alert("제목을 입력해 주세요.");
        form.title.focus();
        return false;
      }

      if(form.content.value==="") {
        alert("내용을 입력해 주세요.");
        form.content.focus();
        return false;
      }
      return true;
    }


    $("#writeBtn").on("click", function(){
      let form = $("#form");

      form.attr("action", "<c:url value='/qa/modify'/>");
      form.attr("method", "post");

      if(formCheck())
        form.submit();
    });

  });
</script>
</body>
</html>