package com.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RabbitMQProducerService {
    private final RabbitTemplate rabbitTemplate;


}
