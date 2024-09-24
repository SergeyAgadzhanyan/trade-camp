package com.tradecamp.web.exception;

import com.tradecamp.models.exception.ApplicationException;
import com.tradecamp.models.exception.GlobalExceptionHandler;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ApplicationExceptionHandler extends GlobalExceptionHandler {
    @ExceptionHandler(ApplicationException.class)
    @Override
    protected ResponseEntity<String> handle(ApplicationException e) {
        return super.handle(e);
    }

    @ExceptionHandler(Exception.class)
    @Override
    protected ResponseEntity<String> handle(Exception e) {
        return super.handle(e);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @Override
    protected ResponseEntity<String> handle(ConstraintViolationException e) {
        return super.handle(e);
    }
}
