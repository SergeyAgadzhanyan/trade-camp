package com.tradecamp.web.service;


import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@EnableRabbit
public class StockConsumerService {

//    @RabbitListener(queues = "stock_qu")
//    public void handleMessage(String message) {
//        System.out.printf("Received from myQueue : %s ", new String(message.getBytes()));
//    }
}
