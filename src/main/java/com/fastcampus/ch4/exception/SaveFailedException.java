package com.fastcampus.ch4.exception;

public class SaveFailedException extends RuntimeException {

    public SaveFailedException(String message) {
        super(message);
    }

    public SaveFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}