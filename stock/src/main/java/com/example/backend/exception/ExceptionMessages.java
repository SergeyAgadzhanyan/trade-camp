package com.example.backend.exception;

import org.springframework.util.StringUtils;

public enum ExceptionMessages {
    NAME_LENGTH_INVALID;

    public String getMessage() {
        return StringUtils.capitalize(this.name()
                .toLowerCase().replaceAll("_", " "));
    }
}
