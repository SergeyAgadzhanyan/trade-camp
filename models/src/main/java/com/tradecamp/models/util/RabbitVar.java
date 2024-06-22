package com.tradecamp.models.util;

public class RabbitVar {
    //KEYS
    public static String USER_ROUTING_KEY_FIND = "find";
    public static String USER_ROUTING_KEY_CREATE = "create";
    public static String USER_ROUTING_KEY_DELETE = "delete";
    public static String STOCK_ROUTING_KEY_RANDOM = "random";
    //QUEUES
    public static final String USER_FIND_QUEUE = "user_find";
    public static final String USER_CREATE_QUEUE = "user_create";
    public static final String USER_DELETE_QUEUE = "user_delete";
    public static final String STOCK_RANDOM_QUEUE = "stock_random";
    //EXCHANGES
    public static String USER_EXCHANGE = "user_ex";
    public static String STOCK_EXCHANGE = "stock_ex";
}
