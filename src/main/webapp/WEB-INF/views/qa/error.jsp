<%--
  Created by IntelliJ IDEA.
  User: qwefghnm1212
  Date: 24. 8. 8.
  Time: 오후 12:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>서점📚</title>
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
    <title>Title</title>
    <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
</head>
<body>
    <div id="menu">
        <ul>
            <li id="logo">서점📚</li>
            <li><a href="<c:url value='/'/>">Home</a></li>
            <li><a href="<c:url value='/qa/list'/>">QA</a></li>
            <%--        <li><a href="<c:url value='${loginOutLink}'/>">${loginOut}</a></li>--%>
            <li><a href="<c:url value='/register/add'/>">Sign in</a></li>
        </ul>
    </div>

    <h1>글 등록에 실패했습니다!</h1>
    <h2>${errorMsg}</h2>
</body>
</html>
