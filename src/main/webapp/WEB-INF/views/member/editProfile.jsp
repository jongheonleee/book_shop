<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>마이페이지</title>
    <style>
        /* 스타일 생략 */
    </style>
</head>
<body>

<div class="mypage-form">
    <h2>마이페이지</h2>
    <form id="mypage-form" action="<c:url value='/updateProfile' />" method="POST">
        <div class="section-title">비밀번호 변경</div>

        <label for="current-password">현재 비밀번호</label>
        <input type="password" id="current-password" name="current-password" required>

        <label for="new-password">새 비밀번호</label>
        <input type="password" id="new-password" name="pswd">

        <label for="confirm-new-password">새 비밀번호 확인</label>
        <input type="password" id="confirm-new-password" name="confirm-new-password">

        <div class="section-title">연락처 정보 수정</div>

        <label for="email">이메일</label>
        <input type="email" id="email" name="email">

        <label for="phnNumb">전화번호</label>
        <input type="tel" id="phnNumb" name="phnNumb">

        <div class="section-title">주소지 수정</div>

        <label for="address">주소</label>
        <input type="text" id="address" name="address" placeholder="배송지 주소를 입력하세요">

        <div class="button-container">
            <button type="submit">정보 수정</button>
            <button type="button" class="back-button" onclick="window.history.back();">되돌아가기</button>
        </div>
        <button type="button" class="address-button" onclick="window.location.href='/address-management';">주소지 관리</button>
    </form>
</div>

</body>
</html>
