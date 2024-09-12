package com.fastcampus.ch4.exception.notice;

public class InvalidPostException extends RuntimeException {
    public InvalidPostException() {
    }

    public InvalidPostException(String message) {
        super(message);
    }

    public InvalidPostException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPostException(Throwable cause) {
        super(cause);
    }
}
