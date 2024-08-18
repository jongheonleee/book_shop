<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
            min-height: 100vh;
            margin: 0;
        }

        .container {
            background-color: white;
            padding: 2rem;
            border-radius: 8px;
            border: 1px solid #ddd;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
            box-sizing: border-box;
        }

        .container h2 {
            margin-bottom: 1rem;
            color: #333;
            text-align: center;
        }

        .container label {
            display: block;
            margin-bottom: 0.5rem;
            color: #555;
            font-weight: bold;
        }

        .container input[type="password"] {
            width: calc(100% - 2rem);
            padding: 0.75rem;
            margin-bottom: 1rem;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        .container input[type="submit"] {
            width: 100%;
            padding: 0.75rem;
            border: none;
            border-radius: 4px;
            background-color: #5cb85c;
            color: white;
            font-size: 1rem;
            cursor: pointer;
            margin-top: 1rem;
        }

        .container input[type="submit"]:hover {
            background-color: #4cae4c;
        }

        .message {
            margin-bottom: 1rem;
            color: red;
            font-size: 0.9rem;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>비밀번호 재설정</h2>
    <form action="${pageContext.request.contextPath}/reset-password" method="post">
        <input type="hidden" name="token" value="${token}">

        <label for="newPassword">새 비밀번호</label>
        <input type="password" id="newPassword" name="newPassword" required>

        <input type="submit" value="비밀번호 재설정">
    </form>
</div>
</body>
</html>
