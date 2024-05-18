package com.tradecamp.user.utils;

import org.springframework.util.StringUtils;

public enum Messages {
    USER_NOT_FOUND,
    RESOURCE_NOT_FOUND,
    DATABASE_CONFLICT,
    INVALID_ARGUMENTS;

    public String getMessage() {
        return StringUtils.capitalize(this.name()
                .toLowerCase().replaceAll("_", " "));
    }
}
