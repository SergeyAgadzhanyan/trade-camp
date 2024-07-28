package com.tradecamp.stock.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradecamp.stock.service.StockAbstractConsumer;
import com.tradecamp.stock.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.tradecamp.models.util.RabbitVar.STOCK_QUEUE;

@Service
@EnableRabbit
@Slf4j
public class StockConsumerImpl extends StockAbstractConsumer<StockService> {

    public StockConsumerImpl(StockService service, ObjectMapper objectMapper) {
        super(service, objectMapper);
    }

    @RabbitListener(queues = STOCK_QUEUE)
    public String queueListener(String message) {
        return handleMessage(message);
    }

}
