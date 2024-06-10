package com.tradecamp.web.exception;

import com.tradecamp.models.exception.BadRequestException;
import com.tradecamp.models.exception.ResourceNotFound;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler({ConstraintViolationException.class, BadRequestException.class})
    public ResponseEntity<String> handleMethodArgumentNotValidException(RuntimeException ex) {
        log.warn("Получен статус 400 {}", ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFound ex) {
        log.warn("Получен статус 404 Not found {}", ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Exception handleEx(Exception ex) {
        return new Exception(ex.getMessage());
    }
}
