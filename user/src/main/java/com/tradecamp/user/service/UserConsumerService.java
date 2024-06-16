package com.tradecamp.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradecamp.models.dto.UserDtoGet;
import com.tradecamp.models.model.RabbitResposne;
import com.tradecamp.models.model.User;
import com.tradecamp.user.utils.RabbitUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.tradecamp.user.utils.RabbitUtil.*;

@Service
@EnableRabbit
@RequiredArgsConstructor
@Slf4j
public class UserConsumerService {
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = USER_FIND_QUEUE)
    public Message findUser(Message message) {
        RabbitResposne rabbitResposne;
        log.info("Rabbit message for find user: {}", message);
        try {
            UserDtoGet userDtoGet = objectMapper.readValue(message.getBody(), UserDtoGet.class);
            rabbitResposne = new RabbitResposne(objectMapper.writeValueAsString(userService
                    .find(userDtoGet)), 200);
        } catch (Exception e) {
            rabbitResposne = new RabbitResposne(e.getMessage(), 400);
        }

        return getMessage(rabbitResposne);
    }


    @RabbitListener(queues = USER_CREATE_QUEUE)
    public Message create(Message message) {
        RabbitResposne rabbitResposne;
        log.info("Rabbit message for create user: {}", message);
        try {
            User user = objectMapper.readValue(message.getBody(), User.class);
            rabbitResposne = new RabbitResposne(objectMapper.writeValueAsString(userService
                    .create(user)), 200);
        } catch (Exception e) {
            rabbitResposne = new RabbitResposne(e.getMessage(), 400);
        }

        try {
            return new Message(objectMapper.writeValueAsBytes(rabbitResposne));
        } catch (JsonProcessingException e) {
            return new Message(RabbitUtil.makeJsonError(e.getMessage(), 300).getBytes());
        }
    }

    @RabbitListener(queues = USER_DELETE_QUEUE)
    public Message delete(Message message) {
        RabbitResposne rabbitResposne;
        log.info("Rabbit message for delete user: {}", message);
        try {
            UserDtoGet user = objectMapper.readValue(message.getBody(), UserDtoGet.class);
            userService.delete(user);
            rabbitResposne = new RabbitResposne(objectMapper.writeValueAsString(user), 200);
        } catch (Exception e) {
            rabbitResposne = new RabbitResposne(e.getMessage(), 400);
        }

        try {
            return new Message(objectMapper.writeValueAsBytes(rabbitResposne));
        } catch (JsonProcessingException e) {
            return new Message(RabbitUtil.makeJsonError(e.getMessage(), 300).getBytes());
        }
    }

    private Message getMessage(RabbitResposne rabbitResposne) {
        try {
            return new Message(objectMapper.writeValueAsBytes(rabbitResposne));
        } catch (JsonProcessingException e) {
            return new Message(RabbitUtil.makeJsonError(e.getMessage(), 300).getBytes());
        }
    }
}

