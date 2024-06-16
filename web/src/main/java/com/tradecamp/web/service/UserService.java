package com.tradecamp.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradecamp.models.dto.UserDtoCreate;
import com.tradecamp.models.dto.UserDtoGet;
import com.tradecamp.models.exception.BadRequestException;
import com.tradecamp.models.model.RabbitResposne;
import com.tradecamp.models.model.User;
import com.tradecamp.web.utils.WebRabbitUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.tradecamp.models.util.RabbitVar.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final ObjectMapper objectMapper;

    private final RabbitTemplate rabbitTemplate;
    private final WebRabbitUtil webRabbitUtil;
    private final PasswordEncoder passwordEncoder;


    public User create(UserDtoCreate userDtoCreate) {
        userDtoCreate.setPassword(passwordEncoder.encode(userDtoCreate.getPassword()));
        try {
            Message messageFromRm = rabbitTemplate.sendAndReceive(EXCHANGE_USER, ROUTING_KEY_CREATE_USER,
                    new Message(objectMapper.writeValueAsString(userDtoCreate).getBytes()));
            return webRabbitUtil.convertMessage(messageFromRm, User.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public User getByName(String name) {
        return find(UserDtoGet.builder().name(name).build());
    }

    public User find(UserDtoGet userDtoGet) {
        try {
            Message messageFromRm = rabbitTemplate.sendAndReceive(EXCHANGE_USER, ROUTING_KEY_FIND_USER,
                    new Message(objectMapper.writeValueAsString(userDtoGet).getBytes()));
            return webRabbitUtil.convertMessage(messageFromRm, User.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteByName(String name) {
        delete(UserDtoGet.builder().name(name).build());
    }

    private void delete(UserDtoGet userDtoGet) {
        try {
            Message messageFromRm = rabbitTemplate.sendAndReceive(EXCHANGE_USER, ROUTING_KEY_DELETE_USER,
                    new Message(objectMapper.writeValueAsString(userDtoGet).getBytes()));
            RabbitResposne rabbitResposne = webRabbitUtil.convertMessageToResponse(messageFromRm);
            if (rabbitResposne.getCode() != 200) {
                throw new BadRequestException(rabbitResposne.getMessage());
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
