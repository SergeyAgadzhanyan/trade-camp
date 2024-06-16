package com.tradecamp.models.util;

public class RabbitUtil {
    public static String makeJsonError(String message, int code) {
        return "{ \"message\": \"" + message + "\", \"code\": " + code + "}";
    }
}
