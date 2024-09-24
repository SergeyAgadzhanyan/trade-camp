package com.tradecamp.models.util;

import org.springframework.util.StringUtils;

public enum Messages {
    USER_NOT_FOUND,
    RESOURCE_NOT_FOUND,
    DATABASE_CONFLICT,
    INTERNAL_SERVER_ERROR,
    INVALID_ARGUMENTS;

    public String getMessage() {
        return StringUtils.capitalize(this.name()
                .toLowerCase().replaceAll("_", " "));
    }
}
