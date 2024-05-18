package com.tradecamp.web.service;

import com.tradecamp.web.model.RabbitRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RabbitMQProducerService {
    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(RabbitRequest message) {
        rabbitTemplate.convertAndSend("stock_ex", message.getRoutingKey(), message.getMessage());
    }
}
