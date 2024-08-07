package com.fastcampus.ch4.exeption.qa;

import com.fastcampus.ch4.code.error.qa.ErrorCode;

public class QaInvalidValueException extends RuntimeException {

    private final ErrorCode code;

    public QaInvalidValueException(ErrorCode code) {
        this.code = code;
    }

    public ErrorCode getCode() {
        return code;
    }
}
