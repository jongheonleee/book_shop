<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>공지사항 상세</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .notice-container {
            width: 80%;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        .notice-header {
            text-align: center;
            margin-bottom: 20px;
        }
        .notice-category, .notice-title, .notice-content {
            margin-bottom: 20px;
        }
        .notice-category {
            font-weight: bold;
            color: #555;
        }
        .notice-title {
            font-size: 24px;
            font-weight: bold;
        }
        .notice-content {
            white-space: pre-wrap;
            line-height: 1.6;
        }
    </style>
</head>
<body>
<div class="notice-container">
    <div class="notice-header">
        <h2>공지사항 상세 페이지</h2>
    </div>

    <div class="notice-category">
        <span>${notice.cate_name}</span>
    </div>

    <div class="notice-title">
        <span>${notice.title}</span>
    </div>

    <div class="notice-content">
        <p>${notice.cont}</p>
    </div>
</div>
</body>
</html>