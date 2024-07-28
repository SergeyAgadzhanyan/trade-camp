package com.tradecamp.user.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradecamp.user.service.UserAbstractConsumer;
import com.tradecamp.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.tradecamp.models.util.RabbitVar.USER_QUEUE;


@Service
@EnableRabbit
@Slf4j
public class UserConsumerImpl extends UserAbstractConsumer<UserService> {

    public UserConsumerImpl(UserService service, ObjectMapper objectMapper) {
        super(service, objectMapper);
    }

    @RabbitListener(queues = USER_QUEUE)
    public String queueListener(String request) {
        return handleMessage(request);
    }
}

