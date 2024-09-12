package com.fastcampus.ch4.exception.notice;

public class AutoIncrementOverflowException extends RuntimeException {
    public AutoIncrementOverflowException() {}

    public AutoIncrementOverflowException(String message) {}

    public AutoIncrementOverflowException(String message, Throwable cause) {
        super(message, cause);
    }

    public AutoIncrementOverflowException(Throwable cause) {
        super(cause);
    }
}
