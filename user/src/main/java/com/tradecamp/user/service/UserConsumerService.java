package com.tradecamp.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradecamp.user.dto.UserDto;
import com.tradecamp.user.dto.UserDtoGet;
import com.tradecamp.user.model.RabbitResposne;
import com.tradecamp.user.utils.RabbitUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@EnableRabbit
@RequiredArgsConstructor
@Slf4j
public class UserConsumerService {
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "stock_qu")
    public Message findUser(Message message) {
        RabbitResposne rabbitResposne;
        log.info("Rabbit message : {}", message);
        try {
            UserDtoGet userDtoGet = objectMapper.readValue(message.getBody(), UserDtoGet.class);
            rabbitResposne = new RabbitResposne(objectMapper.writeValueAsString(userService
                    .find(userDtoGet)), 200);
        } catch (Exception e) {
            rabbitResposne = new RabbitResposne(e.getMessage(), 400);
        }

        try {
            return new Message(objectMapper.writeValueAsBytes(rabbitResposne));
        } catch (JsonProcessingException e) {
            return new Message(RabbitUtil.makeJsonError(e.getMessage(), 300).getBytes());
        }
    }
}
