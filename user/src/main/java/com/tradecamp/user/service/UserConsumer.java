package com.tradecamp.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradecamp.models.dto.UserDtoGet;
import com.tradecamp.models.model.RabbitResposne;
import com.tradecamp.models.model.entity.User;
import com.tradecamp.models.util.RabbitUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.tradecamp.models.util.RabbitUtil.makeJsonError;
import static com.tradecamp.models.util.RabbitVar.*;


@Service
@EnableRabbit
@RequiredArgsConstructor
@Slf4j
public class UserConsumer {
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final RabbitUtil rabbitUtil;

    @RabbitListener(queues = USER_FIND_QUEUE)
    public Message findUser(Message message) {
        try {
            UserDtoGet userDtoGet = rabbitUtil.fromMessageRequesteToObject(message, UserDtoGet.class);
            User user = userService
                    .find(userDtoGet);
            RabbitResposne rabbitResposne = rabbitUtil.fromObjectToResponse(user);
            return rabbitUtil.fromResponseToMessage(rabbitResposne);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Message(makeJsonError(400).getBytes());
        }
    }


    @RabbitListener(queues = USER_CREATE_QUEUE)
    public Message create(Message message) {
        try {
            User user = rabbitUtil.fromMessageRequesteToObject(message, User.class);
            user = userService.create(user);
            RabbitResposne rabbitResposne = rabbitUtil.fromObjectToResponse(user);
            return rabbitUtil.fromResponseToMessage(rabbitResposne);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Message(makeJsonError(400).getBytes());
        }
    }

    @RabbitListener(queues = USER_DELETE_QUEUE)
    public Message delete(Message message) {
        try {

            UserDtoGet user = rabbitUtil.fromMessageRequesteToObject(message, UserDtoGet.class);
            userService.delete(user);
            RabbitResposne rabbitResposne = rabbitUtil.fromObjectToResponse(user);
            return rabbitUtil.fromResponseToMessage(rabbitResposne);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Message(makeJsonError(400).getBytes());
        }
    }

}

