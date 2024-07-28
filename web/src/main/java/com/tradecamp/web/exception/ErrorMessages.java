package com.tradecamp.web.exception;

public enum ErrorMessages {
    RESOURCE_NOT_FOUND("Resource not found"),
    INTERNAL_SERVER_ERROR("Internal Server Error");


    private final String message;

    private ErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
