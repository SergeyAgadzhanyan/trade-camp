package com.example.backend.exception;

public class DbException extends RuntimeException {
    public DbException() {
    }

    public DbException(String message) {
        super(message);
    }
}
