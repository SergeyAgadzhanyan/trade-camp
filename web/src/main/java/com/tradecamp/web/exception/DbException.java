package com.tradecamp.web.exception;

public class DbException extends RuntimeException {
    public DbException() {
    }

    public DbException(String message) {
        super(message);
    }
}
