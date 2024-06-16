package com.tradecamp.models.util;

public class RabbitVar {
    public static String ROUTING_KEY_FIND_USER = "find";
    public static String ROUTING_KEY_CREATE_USER = "create";
    public static String ROUTING_KEY_DELETE_USER = "delete";
    public static String EXCHANGE_USER = "user_ex";
    public static final String USER_FIND_QUEUE = "user_find";
    public static final String USER_CREATE_QUEUE = "user_create";
    public static final String USER_DELETE_QUEUE = "user_delete";
}
