<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>비밀번호 찾기 결과</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .result-container {
            background-color: white;
            padding: 25px;
            border-radius: 10px;
            border: 2px solid #e0e0e0;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
            width: 320px;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
        }
        .result-container h2 {
            margin-bottom: 20px;
            color: #333;
            text-align: center;
        }
        .result-container .message {
            color: #333;
            margin-bottom: 15px;
            text-align: center; /* 중앙 정렬 */
        }
        .result-container .error {
            color: red;
            margin-bottom: 15px;
            text-align: center; /* 중앙 정렬 */
        }
        .result-container a {
            display: block;
            text-align: center;
            color: #5cb85c;
            text-decoration: none;
            margin-top: 15px;
        }
        .result-container a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="result-container">
    <h2>비밀번호 찾기 결과</h2>
    <c:if test="${not empty message}">
        <div class="message">${message}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
    <a href="${pageContext.request.contextPath}/member/login">로그인 페이지로 돌아가기</a>
</div>
</body>
</html>
