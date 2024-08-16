<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>회원가입 축하</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      text-align: center;
      margin: 0;
      padding: 0;
      background-color: #f0f0f0;
    }
    .container {
      background-color: #fff;
      border-radius: 10px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      max-width: 600px;
      margin: 50px auto;
      padding: 20px;
    }
    .message {
      font-size: 1.5em;
      margin-bottom: 20px;
    }
    .button {
      display: inline-block;
      padding: 10px 20px;
      font-size: 1em;
      color: #fff;
      background-color: #007BFF;
      border: none;
      border-radius: 5px;
      text-decoration: none;
      margin: 10px;
    }
    .button:hover {
      background-color: #0056b3;
    }
  </style>
</head>
<body>
<div class="container">
  <div class="message">회원가입을 축하드립니다!</div>
  <a href="${pageContext.request.contextPath}/member/login" class="button">로그인 하시겠습니까?</a>
  <br>
  <a href="${pageContext.request.contextPath}/home" class="button">메인 페이지로 돌아가기</a>
</div>
</body>
</html>
