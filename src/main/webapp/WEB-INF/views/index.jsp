<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<c:set var="loginId"
       value="${pageContext.request.getSession(false)==null ? '' : pageContext.request.session.getAttribute('id')}"/>
<c:set var="loginOutLink" value="${loginId=='' ? '/member/login' : '/member/logout'}"/>
<c:set var="loginOut" value="${loginId=='' ? 'Login' : 'ID='+=loginId}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>fastcampus</title>
    <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>

    <!-- 챗봇의 CSS -->
    <style>
        .chat-container {
            position: fixed;
            bottom: 20px;
            right: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 300px;
            max-height: 1000px;
            padding: 10px;
            display: flex;
            flex-direction: column;
            z-index: 10000; /* z-index 값을 높게 설정 */
        }

        .chat-container.minimized {
            height: 50px;
            overflow: hidden;
        }

        .chat-header {
            background-color: #007bff;
            color: white;
            padding: 10px;
            border-radius: 8px 8px 0 0;
            text-align: center;
            cursor: pointer;
        }

        .messages {
            flex-grow: 1;
            overflow-y: auto;
            margin-bottom: 10px;
            max-height: 600px;
        }

        .message {
            margin: 5px 0;
            padding: 8px;
            border-radius: 8px;
            max-width: 80%;
            word-wrap: break-word;
        }

        .user {
            background-color: #e1ffc7;
            align-self: flex-end;
            text-align: right;
        }

        .bot {
            background-color: #d0e7ff;
            align-self: flex-start;
            text-align: left;
        }

        .input-form {
            display: flex;
        }

        .input-form input[type="text"] {
            flex: 1;
            padding: 5px;
            border-radius: 5px;
            border: 1px solid #ccc;
            margin-right: 5px;
            font-size: 14px;
        }

        .input-form button {
            padding: 5px 10px;
            border: none;
            border-radius: 5px;
            background-color: #007bff;
            color: white;
            font-size: 14px;
            cursor: pointer;
        }

        .button-group {
            display: flex;
            justify-content: space-between;
        }

        .button-group button {
            flex: 1;
            margin-right: 5px;
            padding: 5px;
            font-size: 14px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            background-color: #e0e0e0;
            color: black;
        }

        .button-group button.active {
            background-color: #007bff;
            color: white;
        }

        .button-group button:last-child {
            margin-right: 0;
        }

        .book-block, .faq-block {
            border: 1px solid #d0e7ff;
            background-color: #f9f9f9;
            padding: 10px;
            border-radius: 8px;
            margin-bottom: 10px;
        }

        .book-block h4, .faq-block h4 {
            margin: 0;
            font-size: 1.1em;
            color: #007bff;
        }

        .book-block p, .faq-block p {
            margin: 5px 0;
            font-size: 0.9em;
        }
    </style>
</head>
<body>
<div id="menu">
    <ul>
        <li id="logo">fastcampus</li>
        <li><a href="<c:url value='/'/>">Home</a></li>
        <li><a href="<c:url value='/qa/list'/>">QA</a></li>
        <li><a href="<c:url value='/book/list'/>">Board</a></li>
        <li><a href="<c:url value='/cscenter/faq/list/00'/>">FAQ</a></li>
        <li><a href="<c:url value='${loginOutLink}'/>">${loginOut}</a></li>
        <li><a href="<c:url value='/signup'/>">Sign up</a></li>
        <li><a href="<c:url value='/cart/list'/>"><i class="fas fa-cart-plus"></i></a></li>
        <li><a href="<c:url value='/order/list'/>">주문목록</a></li>
        <li><a href=""><i class="fa fa-search"></i></a></li>
    </ul>
</div>

<div style="text-align:center">
    <h1>This is HOME</h1>
    <h1>This is HOME</h1>
    <h1>This is HOME</h1>
</div>

<!-- 챗봇 추가 -->

<div class="chat-container minimized" id="chat-container">
    <div class="chat-header" onclick="toggleChat()">챗봇</div>
    <div class="messages" id="messages"></div>

    <form class="input-form" onsubmit="sendMessage(event)">
        <input type="text" id="user_input" placeholder="채팅을 입력하세요 : " required/>
        <button type="submit">전송</button>
    </form>
</div>

<script>
    function toggleChat() {
        var chatContainer = document.getElementById('chat-container');
        chatContainer.classList.toggle('minimized');
    }

    function sendMessage(event) {
        event.preventDefault();
        const userInput = document.getElementById('user_input').value;

        // 로컬 스토리지에서 JWT 토큰 가져오기
        const token = localStorage.getItem('token');  // 'token'이 로컬 스토리지에 저장된 이름
        console.log("저장된 토큰: " + token)
        fetch('http://127.0.0.1:8000/chat', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': token  // JWT 토큰을 Authorization 헤더에 추가
            },
            credentials: 'include',
            body: JSON.stringify({
                user_input: userInput
            }),
        })
            .then(response => response.json())
            .then(data => {
                const messages = document.getElementById('messages');

                // 사용자 메시지 추가
                const userMessage = document.createElement('div');
                userMessage.classList.add('message', 'user');
                userMessage.textContent = "👤: " + userInput;
                messages.appendChild(userMessage);

                // 봇의 응답 처리
                if (data['bot_response']) {
                    const botMessage = document.createElement('div');
                    botMessage.classList.add('message', 'bot');

                    // 일반 텍스트 응답 처리
                    botMessage.innerHTML = "🤖: " + data['bot_response'] + "<br>";

                    if (data.content.length > 0) {
                        data.content.forEach(content => {
                            if (content['reply_type'] === "book") {
                                console.log(content);
                                botMessage.innerHTML +=
                                    "<div class='book-block'>" +
                                    "<h4>" + content.title + "</h4>" +
                                    "<p><strong>ISBN:</strong> " + content.isbn + "</p>" +
                                    "<p><strong>출판사:</strong> " + content['pub_name'] + "</p>" +
                                    "<p><strong>출판일:</strong> " + content['pub_date'] + "</p>" +
                                    "<p><strong>판매 상태:</strong> " + content['sale_stat'] + "</p>" +
                                    "<p><strong>판매량:</strong> " + content['sale_vol'] + "</p>" +
                                    "<p><strong>종이책 가격:</strong> " + content['papr_pric'] + "원</p>" +
                                    "<p><strong>전자책 가격:</strong> " + content['e_pric'] + "원</p>" +
                                    "<p><strong>판매 회사:</strong> " + content['sale_com'] + "</p>" +
                                    "<p><strong>출판사 리뷰:</strong> " + content['pub_review'] + "</p>" +
                                    "</div>";
                            } else if (content['reply_type'] === "faq") {
                                botMessage.innerHTML +=
                                    "<div class='faq-block'>" +
                                    "<h4>" + content.title + "</h4>" +
                                    "<p>" + content['content'] + "</p>" +
                                    "<p><strong>조회수:</strong> " + content['view_cnt'] + "</p>" +
                                    "</div>";
                            }

                        })
                    }

                    messages.appendChild(botMessage);
                }

                // 메시지 추가 후 스크롤을 맨 아래로 이동
                messages.scrollTop = messages.scrollHeight;

                // 입력 필드 초기화
                document.getElementById('user_input').value = '';
            })
            .catch(error => console.error('Error:', error));
    }
</script>

</body>
</html>