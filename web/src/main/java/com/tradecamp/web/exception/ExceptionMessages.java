package com.tradecamp.web.exception;

import org.springframework.util.StringUtils;

public enum ExceptionMessages {
    NAME_LENGTH_INVALID,
    ERROR_500;

    public String getMessage() {
        return StringUtils.capitalize(this.name()
                .toLowerCase().replaceAll("_", " "));
    }
}
