package com.example.backend.service;

import com.example.backend.model.RabbitMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RabbitMQProducerService {
    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(RabbitMessage message) {
        rabbitTemplate.convertAndSend("stock_ex", message.getRoutingKey(), message.getMessage());
    }
}
