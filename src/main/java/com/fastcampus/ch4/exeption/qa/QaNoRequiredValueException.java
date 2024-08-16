package com.fastcampus.ch4.exeption.qa;

import com.fastcampus.ch4.code.error.qa.ErrorCode;

public class QaNoRequiredValueException extends RuntimeException {

    private final ErrorCode code;


    public QaNoRequiredValueException(ErrorCode code) {
        this.code = code;
    }

    public ErrorCode getCode() {
        return code;
    }
}
