<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>오류</title>
</head>
<body>
<div class="container">
    <h2>오류</h2>
    <p>${error}</p>
    <a href="${pageContext.request.contextPath}/member/login">로그인 페이지로 돌아가기</a>
</div>
</body>
</html>
