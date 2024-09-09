<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
            overflow: auto;
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
            overflow: auto;
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

        .container input[type="text"],
        .container input[type="password"] {
            width: calc(100% - 2rem);
            padding: 0.75rem;
            margin-bottom: 1rem;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        .container .checkbox-group {
            display: flex;
            align-items: center;
            margin-bottom: 1rem;
        }

        .container .checkbox-group input[type="checkbox"] {
            margin-right: 8px;
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

        .links {
            text-align: center;
            margin-top: 1rem;
        }

        .links a {
            color: #007bff;
            text-decoration: none;
        }

        .links a:hover {
            text-decoration: underline;
        }
    </style>
    <script>
        function handleLogin(event) {
            event.preventDefault();  // 폼 기본 동작 방지

            // 폼 데이터 수집
            const formData = new FormData(event.target);
            const id = formData.get('id');
            const password = formData.get('pswd');
            const toURL = formData.get('toURL');

            // 로그인 요청 보내기
            fetch('${pageContext.request.contextPath}/member/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: new URLSearchParams({
                    id: id,
                    pswd: password,
                    toURL: toURL  // toURL을 함께 전송
                }),

            }).then(response => response.json())
                .then(data => {
                    const token = data.token;
                    const redirectUrl = data.redirectUrl;

                    if (token) {
                        // JWT 토큰을 로컬 스토리지에 저장
                        localStorage.setItem('token', token);
                    }
                    if (redirectUrl) {
                        // 리다이렉트할 URL로 이동
                        window.location.href = redirectUrl;
                    } else {
                        alert("로그인 실패했습니다");
                        window.location.href = '/ch4/member/login';
                    }
                }).catch(error => console.error('Error:', error));
        }
    </script>
</head>
<body>
<div class="container">
    <h2>로그인</h2>

    <!-- 오류 및 성공 메시지 표시 -->
    <c:if test="${not empty errorMessage}">
        <div class="message">${errorMessage}</div>
    </c:if>
    <c:if test="${not empty message}">
        <div class="message">${message}</div>
    </c:if>

    <!-- 로그인 폼 -->
    <form onsubmit="handleLogin(event)">
        <label for="id">아이디</label>
        <input type="text" id="id" name="id" required pattern="[A-Za-z0-9]{5,20}" title="아이디는 5자 이상 20자 이하, 영문자와 숫자만 가능합니다.">

        <label for="password">비밀번호</label>
        <input type="password" id="password" name="pswd" required pattern="(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}" title="비밀번호는 8자 이상 20자 이하로, 대문자, 소문자, 숫자, 특수문자가 각각 최소 1개 포함되어야 합니다.">

        <div class="checkbox-group">
            <input type="checkbox" id="rememberMe" name="rememberMe">
            <label for="rememberMe">아이디 저장</label>
        </div>

        <input type="submit" value="로그인">
        <input type="hidden" name="toURL" value="${param.toURL}">

        <div class="links">
            <a href="${pageContext.request.contextPath}/findId">아이디 찾기</a> |
            <a href="${pageContext.request.contextPath}/findPassword">비밀번호 찾기</a>
        </div>
    </form>
</div>
</body>
</html>