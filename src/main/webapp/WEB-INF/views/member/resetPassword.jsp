<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>비밀번호 재설정</title>
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
        .reset-password-form {
            background-color: white;
            padding: 25px;
            border-radius: 10px;
            border: 2px solid #e0e0e0;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
            width: 320px;
        }
        .reset-password-form h2 {
            margin-bottom: 20px;
            color: #333;
            text-align: center;
        }
        .reset-password-form label {
            display: block;
            margin-bottom: 5px;
            color: #555;
        }
        .reset-password-form input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .reset-password-form button {
            width: 100%;
            padding: 10px;
            background-color: #5cb85c;
            border: none;
            color: white;
            font-size: 16px;
            border-radius: 4px;
            cursor: pointer;
        }
        .reset-password-form button:hover {
            background-color: #4cae4c;
        }
        .error {
            color: red;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
<div class="reset-password-form">
    <h2>비밀번호 재설정</h2>
    <form action="${pageContext.request.contextPath}/member/reset-password" method="POST">
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        <c:if test="${not empty message}">
            <div class="message">${message}</div>
        </c:if>
        <input type="hidden" name="token" value="${token}">

        <label for="newPassword">새 비밀번호</label>
        <input type="password" id="newPassword" name="newPassword" required>

        <button type="submit">비밀번호 재설정</button>
    </form>
</div>
</body>
</html>
