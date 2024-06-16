package com.tradecamp.user.utils;

public class RabbitUtil {
    public static final String USER_FIND_QUEUE = "user_find";
    public static final String USER_CREATE_QUEUE = "user_create";
    public static final String USER_DELETE_QUEUE = "user_delete";

    public static String makeJsonError(String message, int code) {
        return "{ \"message\": \"" + message + "\", \"code\": " + code + "}";
    }
}
