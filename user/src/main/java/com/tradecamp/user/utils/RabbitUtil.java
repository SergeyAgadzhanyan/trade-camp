package com.tradecamp.user.utils;

public class RabbitUtil {
    public static String makeJsonError(String message, int code){
        return "{ \"message\": \"" + message +  "\", \"code\": " + code + "}";
    }
}
