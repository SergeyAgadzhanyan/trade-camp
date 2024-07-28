package com.tradecamp.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradecamp.models.dto.UserDto;
import com.tradecamp.models.dto.UserDtoCreate;
import com.tradecamp.models.dto.UserDtoGet;
import com.tradecamp.models.model.RabbitRequest;
import com.tradecamp.models.model.RabbitResponse;
import com.tradecamp.user.Application;
import com.tradecamp.user.entity.User;
import com.tradecamp.user.repository.UserRepository;
import com.tradecamp.user.service.impl.UserConsumerImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.tradecamp.models.model.RabbitRequestType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
class UserConsumerImplTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserConsumerImpl userConsumerImpl;
    private final static String ADMIN_NAME = "admin";
    private final static String NEW_USER_CREATE_NAME = "newUser";
    private final static String ADMIN_PASS = "admin";
    private final static ObjectMapper objectMapper = new ObjectMapper();
    private UserDtoGet userDtoGet;
    private UserDtoCreate userDtoCreate;

    @BeforeEach
    void setUp() {
        userDtoGet = UserDtoGet.builder()
                .name(ADMIN_NAME).build();

        userDtoCreate = UserDtoCreate.builder()
                .name(NEW_USER_CREATE_NAME)
                .password(ADMIN_PASS)
                .build();

        userRepository.save(User.builder()
                .id(1L)
                .name(ADMIN_NAME)
                .password(ADMIN_PASS)
                .build());

    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void findUser() throws JsonProcessingException {
        String stringResponse =
                userConsumerImpl.queueListener(objectMapper.writeValueAsString(RabbitRequest.builder()
                        .type(USER_FIND)
                        .message(objectMapper.writeValueAsString(userDtoGet))
                        .build()));
        RabbitResponse response = objectMapper.readValue(stringResponse, RabbitResponse.class);
        UserDto userDto = objectMapper.readValue(response.getBody(), UserDto.class);
        assertEquals(userDto.getName(), ADMIN_NAME);
    }

    @Test
    void create() throws JsonProcessingException {
        userDtoCreate.setName(NEW_USER_CREATE_NAME);

        userConsumerImpl.queueListener(objectMapper.writeValueAsString(RabbitRequest.builder()
                .type(USER_CREATE)
                .message(objectMapper.writeValueAsString(userDtoCreate))
                .build()));

        userDtoGet.setName(NEW_USER_CREATE_NAME);
        String stringResponse =
                userConsumerImpl.queueListener(objectMapper.writeValueAsString(RabbitRequest.builder()
                        .type(USER_FIND)
                        .message(objectMapper.writeValueAsString(userDtoGet))
                        .build()));

        RabbitResponse response = objectMapper.readValue(stringResponse, RabbitResponse.class);
        UserDto userDto = objectMapper.readValue(response.getBody(), UserDto.class);
        assertEquals(userDto.getName(), NEW_USER_CREATE_NAME);
    }

    @Test
    void delete() throws JsonProcessingException {

        userDtoCreate.setName(NEW_USER_CREATE_NAME);

        userConsumerImpl.queueListener(objectMapper.writeValueAsString(RabbitRequest.builder()
                .type(USER_CREATE)
                .message(objectMapper.writeValueAsString(userDtoCreate))
                .build()));

        userConsumerImpl.queueListener(objectMapper.writeValueAsString(RabbitRequest.builder()
                .type(USER_DELETE)
                .message(NEW_USER_CREATE_NAME)
                .build()));

        userDtoGet.setName(NEW_USER_CREATE_NAME);
        String stringResponse =
                userConsumerImpl.queueListener(objectMapper.writeValueAsString(RabbitRequest.builder()
                        .type(USER_FIND)
                        .message(objectMapper.writeValueAsString(userDtoGet))
                        .build()));

        RabbitResponse response = objectMapper.readValue(stringResponse, RabbitResponse.class);
        assertEquals(404, response.getCode());
    }

}
