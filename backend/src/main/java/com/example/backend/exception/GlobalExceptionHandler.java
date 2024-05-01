package com.example.backend.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BadRequestException handleMethodArgumentNotValidException(ConstraintViolationException ex) {
        log.warn("Получен статус 404 Not found {}", ex.getMessage(), ex);
        return new BadRequestException(ex.getMessage());
    }
    @ExceptionHandler(ResourceNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RuntimeException handleResourceNotFound(ResourceNotFound ex) {
        log.warn("Получен статус 404 Not found {}", ex.getMessage(), ex);
        return new BadRequestException(ex.getMessage());
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Exception handleEx(Exception ex) {
        return new Exception(ex.getMessage());
    }
}
