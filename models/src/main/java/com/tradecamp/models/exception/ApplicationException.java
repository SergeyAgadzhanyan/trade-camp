package com.tradecamp.models.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
public class ApplicationException extends RuntimeException {
    private HttpStatusCode statusCode;

    public ApplicationException() {
    }

    public ApplicationException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
