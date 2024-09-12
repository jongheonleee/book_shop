<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>프로필 업데이트 결과</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .result-message {
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            text-align: center;
        }
        .result-message h1 {
            color: #333;
        }
        .result-message p {
            font-size: 16px;
            margin-top: 20px;
        }
        .result-message a {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }
        .result-message a:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>

<div class="result-message">
    <h1>프로필 업데이트 결과</h1>
    <p>${message}</p>
    <a href="<c:url value='/editProfile' />">프로필로 돌아가기</a>
</div>

</body>
</html>
