<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>비밀번호 재설정</title>
</head>
<body>
<h2>비밀번호 재설정</h2>

<form action="${pageContext.request.contextPath}/member/enter-token" method="post">
  <div>
    <label for="token">토큰:</label>
    <input type="text" id="token" name="token" required />
  </div>
  <div>
    <label for="newPassword">새 비밀번호:</label>
    <input type="password" id="newPassword" name="newPassword" required />
  </div>
  <button type="submit">비밀번호 재설정</button>
</form>

<c:if test="${not empty error}">
  <p style="color: red;">${error}</p>
</c:if>
</body>
</html>
