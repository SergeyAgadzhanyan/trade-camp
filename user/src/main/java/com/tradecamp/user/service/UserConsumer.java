package com.tradecamp.user.service;

import com.tradecamp.models.dto.UserDtoGet;
import com.tradecamp.models.model.RabbitResposne;
import com.tradecamp.user.entity.User;
import com.tradecamp.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.tradecamp.models.util.RabbitUtil.*;
import static com.tradecamp.models.util.RabbitVar.*;


@Service
@EnableRabbit
@RequiredArgsConstructor
@Slf4j
public class UserConsumer {
    private final UserService userService;
    private final UserMapper userMapper;

    @RabbitListener(queues = USER_FIND_QUEUE)
    public Message findUser(Message message) {
        try {
            UserDtoGet userDtoGet = fromMessageRequesteToObject(message, UserDtoGet.class);
            User user = userService
                    .find(userDtoGet);
            RabbitResposne rabbitResposne = fromObjectToResponse(userMapper.toDto(user));
            return fromResponseToMessage(rabbitResposne);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Message(makeJsonError(new String(message.getBody()), 400).getBytes());
        }
    }


    @RabbitListener(queues = USER_CREATE_QUEUE)
    public Message create(Message message) {
        try {
            User user = fromMessageRequesteToObject(message, User.class);
            user = userService.create(user);
            RabbitResposne rabbitResposne = fromObjectToResponse(userMapper.toDto(user));
            return fromResponseToMessage(rabbitResposne);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Message(makeJsonError(new String(message.getBody()), 400).getBytes());
        }
    }

    @RabbitListener(queues = USER_DELETE_QUEUE)
    public Message delete(Message message) {
        try {
            UserDtoGet userDtoGet = fromMessageRequesteToObject(message, UserDtoGet.class);
            userService.delete(userDtoGet);
            RabbitResposne rabbitResposne = fromObjectToResponse(userDtoGet);
            return fromResponseToMessage(rabbitResposne);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Message(makeJsonError(new String(message.getBody()), 400).getBytes());
        }
    }

}

