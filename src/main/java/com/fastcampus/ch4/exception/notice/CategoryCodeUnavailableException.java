package com.fastcampus.ch4.exception.notice;

public class CategoryCodeUnavailableException extends RuntimeException {
    public CategoryCodeUnavailableException() {
    }

    public CategoryCodeUnavailableException(String message) {
        super(message);
    }

    public CategoryCodeUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public CategoryCodeUnavailableException(Throwable cause) {
        super(cause);
    }
}
