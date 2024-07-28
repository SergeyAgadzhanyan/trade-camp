package com.tradecamp.web.exception;

public class GlobalException extends RuntimeException {
    public GlobalException() {
    }

    public GlobalException(String message) {
        super(message);
    }

    public GlobalException(ErrorMessages message) {
        super(message.getMessage());
    }
}
