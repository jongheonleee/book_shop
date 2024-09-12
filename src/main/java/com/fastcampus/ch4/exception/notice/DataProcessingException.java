package com.fastcampus.ch4.exception.notice;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException() {
    }

    public DataProcessingException(String message) {
        super(message);
    }

    public DataProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataProcessingException(Throwable cause) {
        super(cause);
    }
}
