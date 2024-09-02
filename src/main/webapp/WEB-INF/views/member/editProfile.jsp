<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>마이페이지</title>
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
        .mypage-form {
            background-color: white;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
            width: 500px;
        }
        .mypage-form h2 {
            margin-bottom: 40px;
            color: #333;
            font-size: 28px;
            text-align: center;
        }
        .mypage-form label {
            display: block;
            margin-bottom: 10px;
            color: #555;
            font-weight: bold;
        }
        .mypage-form input[type="text"],
        .mypage-form input[type="password"],
        .mypage-form input[type="email"],
        .mypage-form input[type="tel"] {
            width: calc(100% - 24px);
            padding: 14px;
            margin-bottom: 30px;
            border: 1px solid #ccc;
            border-radius: 6px;
            box-sizing: border-box;
        }
        .mypage-form .section-title {
            font-size: 20px;
            margin-bottom: 25px;
            color: #444;
            border-bottom: 2px solid #ccc;
            padding-bottom: 10px;
        }
        .mypage-form .button-container {
            display: flex;
            justify-content: space-between;
            gap: 20px;
        }
        .mypage-form button {
            flex: 1;
            padding: 16px;
            background-color: #5cb85c;
            border: none;
            color: white;
            font-size: 18px;
            border-radius: 6px;
            cursor: pointer;
            margin-top: 20px;
        }
        .mypage-form button:hover {
            background-color: #4cae4c;
        }
        .mypage-form .back-button {
            background-color: #6c757d;
        }
        .mypage-form .back-button:hover {
            background-color: #5a6268;
        }
        .mypage-form .address-button {
            display: block;
            width: 100%;
            padding: 14px;
            background-color: #007bff;
            border: none;
            color: white;
            font-size: 16px;
            border-radius: 6px;
            cursor: pointer;
            text-align: center;
            margin-top: 10px;
        }
        .mypage-form .address-button:hover {
            background-color: #0056b3;
        }
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
        <input type="password" id="new-password" name="new-password">

        <label for="confirm-new-password">새 비밀번호 확인</label>
        <input type="password" id="confirm-new-password" name="confirm-new-password">

        <div class="section-title">연락처 정보 수정</div>

        <label for="email">이메일</label>
        <input type="email" id="email" name="email">

        <label for="phone">전화번호</label>
        <input type="tel" id="phone" name="phnNumb">

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

<script>
    document.getElementById('mypage-form').addEventListener('submit', function(e) {
        e.preventDefault(); // 기본 제출 방지

        // // 현재 비밀번호와 서버에서 저장된 비밀번호가 일치하는지 확인
        // const currentPassword = document.getElementById('current-password').value;
        // const storedPassword = "userStoredPassword"; // 실제 서버에서 가져오는 비밀번호
        //
        // if (currentPassword !== storedPassword) {
        //     alert('현재 비밀번호가 일치하지 않습니다.');
        //     return;
        // }
        //
        // // 새 비밀번호 확인
        // const newPassword = document.getElementById('new-password').value;
        // const confirmNewPassword = document.getElementById('confirm-new-password').value;
        //
        // if (newPassword !== '' || confirmNewPassword !== '') {
        //     if (newPassword !== confirmNewPassword) {
        //         alert('새 비밀번호가 일치하지 않습니다.');
        //         return;
        //     }
        // }

<%--        // 이메일과 전화번호가 하나라도 입력되면 그 값만 전송 가능--%>
<%--        const email = document.getElementById('email').value;--%>
<%--        const phone = document.getElementById('phone').value;--%>
<%--        const address = document.getElementById('address').value;--%>

<%--        const data = {--%>
<%--            currentPassword: currentPassword || null,--%>
<%--            newPassword: newPassword || null,--%>
<%--            email: email || null,--%>
<%--            phone: phone || null,--%>
<%--            address: address || null--%>
<%--        };--%>

<%--        console.log('수정된 정보:', data);--%>

<%--        // 실제 서버로 제출할 경우 여기에 AJAX 요청 등을 사용--%>
<%--        alert('수정이 완료되었습니다.');--%>
<%--    });--%>
<%--</script>--%>

        const form = new FormData(this);
        const data = {
            'current-password': form.get('current-password') || null,
            'new-password': form.get('new-password') || null,
            'confirm-new-password': form.get('confirm-new-password') || null,
            'email': form.get('email') || null,
            'phone': form.get('phone') || null,
            'address': form.get('address') || null
        };

        // AJAX 요청을 사용하여 서버에 데이터 전송
        fetch('<c:url value="/updateProfile" />', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams(data)
        }).then(response => response.text())
            .then(result => {
                alert('수정이 완료되었습니다.');
            }).catch(error => {
            console.error('Error:', error);
        });
    });
</script>

</body>
</html>
