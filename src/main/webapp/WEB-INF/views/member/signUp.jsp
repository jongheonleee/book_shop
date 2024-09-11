<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원 가입</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            overflow: auto;
        }

        .container {
            background-color: white;
            padding: 2rem;
            border-radius: 8px;
            border: 1px solid #ddd;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 600px;
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
        .container input[type="password"],
        .container input[type="email"],
        .container input[type="tel"] {
            width: calc(100% - 2rem);
            padding: 0.75rem;
            margin-bottom: 1rem;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        .container .checkbox-group {
            display: flex;
            flex-direction: column;
            margin-bottom: 1rem;
        }

        .container .checkbox-group input[type="checkbox"] {
            margin-right: 8px;
        }

        .container .term-content {
            max-height: 150px;
            overflow-y: auto;
            border: 1px solid #ddd;
            border-radius: 4px;
            padding: 0.5rem;
            background-color: #f9f9f9;
            margin-top: 0.5rem;
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

        .validation-info {
            font-size: 0.8rem;
            color: #666;
            margin-bottom: 1rem;
        }

        .checkbox-label {
            display: flex;
            align-items: center;
        }

        .term-item {
            margin-bottom: 1rem;
        }

        .term-name {
            font-weight: bold;
            margin-bottom: 0.5rem;
        }

        .term-content {
            font-size: 0.9rem;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>회원 가입</h2>

    <!-- 오류 및 성공 메시지 표시 -->
    <c:if test="${not empty errorMessage}">
        <div class="message">${errorMessage}</div>
    </c:if>
    <c:if test="${not empty message}">
        <div class="message">${message}</div>
    </c:if>

    <!-- 회원 가입 폼 -->
    <form action="${pageContext.request.contextPath}/signup" method="post" onsubmit="return validateForm()">
        <label for="id">아이디</label>
        <input type="text" id="id" name="id" required>
        <div class="validation-info">아이디는 5자 이상 20자 이하로, 영문자와 숫자만 사용할 수 있습니다.</div>

        <label for="password">비밀번호</label>
        <input type="password" id="password" name="pswd" required>
        <div class="validation-info">비밀번호는 8자 이상 20자 이하로, 대문자, 소문자, 숫자, 특수문자를 각각 최소 1개 포함해야 합니다.</div>

        <label for="passwordConfirm">비밀번호 확인</label>
        <input type="password" id="passwordConfirm" name="passwordConfirm" required>
        <div class="validation-info">비밀번호 확인 필드가 비밀번호와 일치해야 합니다.</div>

        <label for="phone">전화번호</label>
        <input type="tel" id="phone" name="phnNumb" required pattern="\d{10,15}" title="전화번호는 10자 이상 15자 이하로 입력해주세요.">
        <div class="validation-info">전화번호는 10자 이상 15자 이하로 입력해 주세요.</div>

        <label for="email">이메일</label>
        <input type="email" id="email" name="email" required>
        <div class="validation-info">유효한 이메일 주소를 입력해 주세요.</div>

        <!-- 주소 입력 -->
        <label for="address">주소지</label>
        <input type="text" id="address" name="address" required placeholder="주소를 입력해주세요.">
        <div class="validation-info">주소를 입력해 주세요.</div>

        <!-- 필수 약관 -->
        <div class="checkbox-group">
            <c:forEach items="${terms}" var="term">
                <c:if test="${term.required == 'Y'}">
                    <div class="term-item">
                        <input type="checkbox" id="term_${term.termId}" name="requiredTermAgreedTermIds" value="${term.termId}" required>
                        <label for="term_${term.termId}" class="checkbox-label">
                            <div class="term-name">${term.termName} (필수)</div>
                            <div class="term-content">${term.termContent}</div>
                        </label>
                    </div>
                </c:if>
            </c:forEach>
        </div>

        <!-- 선택 약관 -->
        <div class="checkbox-group">
            <c:forEach items="${terms}" var="term">
                <c:if test="${term.required == 'N'}">
                    <div class="term-item">
                        <input type="checkbox" id="term_${term.termId}" name="optionalTermAgreedTermIds" value="${term.termId}">
                        <label for="term_${term.termId}" class="checkbox-label">
                            <div class="term-name">${term.termName} (선택)</div>
                            <div class="term-content">${term.termContent}</div>
                        </label>
                    </div>
                </c:if>
            </c:forEach>
        </div>

        <input type="submit" value="회원 가입">
    </form>
</div>

<script>
    function validateForm() {
        var password = document.getElementById("password").value;
        var passwordConfirm = document.getElementById("passwordConfirm").value;
        if (password !== passwordConfirm) {
            alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
            return false;
        }
        return true;
    }
</script>

</body>
</html>
