package com.fastcampus.ch4.exception.notice;

public class DuplicateCategoryCodeException extends RuntimeException {
    public DuplicateCategoryCodeException() {
    }

    public DuplicateCategoryCodeException(String message) {
        super(message);
    }

    public DuplicateCategoryCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateCategoryCodeException(Throwable cause) {
        super(cause);
    }
}
