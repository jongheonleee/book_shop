package com.fastcampus.ch4.code.error.qa;

import org.springframework.http.HttpStatus;

public enum QaErrorCode implements ErrorCode {

    // http 코드 400 : 사용자가 잘못된 값 입력해서 요청한 경우
    INVALID_BLANK_INPUT(HttpStatus.BAD_REQUEST, "공백을 입력할 수 없습니다."),
    INVALID_VALUE_INPUT(HttpStatus.BAD_REQUEST, "잘못된 값을 입력하였습니다. 입력값을 다시 확인해주세요"),
    // http 코드 409 : 요청한 값과 서버의 값 간의 충돌이 발생한 경우
    DUPLICATED_KEY(HttpStatus.CONFLICT, "중복된 키 값이 존재합니다.");


    private final HttpStatus httpStatus;
    private final String message;

    private QaErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
