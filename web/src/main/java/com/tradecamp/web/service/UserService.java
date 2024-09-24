package com.tradecamp.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradecamp.models.dto.*;
import com.tradecamp.models.exception.ApplicationException;
import com.tradecamp.models.model.RabbitRequest;
import com.tradecamp.models.model.RabbitRequestType;
import com.tradecamp.models.model.RabbitResponse;
import com.tradecamp.models.util.Messages;
import com.tradecamp.web.configuration.MyUserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.tradecamp.models.util.RabbitVar.USER_EXCHANGE;
import static com.tradecamp.models.util.RabbitVar.USER_ROUTING_KEY;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final ObjectMapper objectMapper;

    private final RabbitTemplate rabbitTemplate;
    private final PasswordEncoder passwordEncoder;


    public UserDtoGet create(UserDtoCreate userDtoCreate) {
        try {
            userDtoCreate.setPassword(passwordEncoder.encode(userDtoCreate.getPassword()));
            RabbitRequest request = RabbitRequest.builder()
                    .type(RabbitRequestType.USER_CREATE)
                    .message(objectMapper.writeValueAsString(userDtoCreate))
                    .build();
            String response = (String) rabbitTemplate.convertSendAndReceive(USER_EXCHANGE, USER_ROUTING_KEY,
                    objectMapper.writeValueAsString(request));
            RabbitResponse rabbitResponse = objectMapper.readValue(response, RabbitResponse.class);
            if (rabbitResponse.getCode() != 201) {
                throw new ApplicationException(Messages.INTERNAL_SERVER_ERROR.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return objectMapper.readValue(rabbitResponse.getBody(), UserDtoGet.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public UserDto getByName(String name) {
        return find(UserDtoGet.builder().name(name).build());
    }

    private UserDto find(UserDtoGet userDtoGet) {
        try {
            RabbitRequest request = RabbitRequest.builder()
                    .type(RabbitRequestType.USER_FIND)
                    .message(objectMapper.writeValueAsString(userDtoGet)).build();
            String response = (String) rabbitTemplate.convertSendAndReceive(USER_EXCHANGE, USER_ROUTING_KEY,
                    objectMapper.writeValueAsString(request));
            RabbitResponse rabbitResponse = objectMapper.readValue(response, RabbitResponse.class);
            if (rabbitResponse.getCode() != 200) {
                throw new ApplicationException(Messages.INTERNAL_SERVER_ERROR.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return objectMapper.readValue(rabbitResponse.getBody(), UserDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteByName(String name) {
        delete(UserDtoGet.builder().name(name).build());
    }

    private void delete(UserDtoGet userDtoGet) {
        try {
            RabbitRequest request = RabbitRequest.builder()
                    .type(RabbitRequestType.USER_DELETE)
                    .message(objectMapper.writeValueAsString(userDtoGet)).build();

            String response = (String) rabbitTemplate.convertSendAndReceive(USER_EXCHANGE, USER_ROUTING_KEY,
                    objectMapper.writeValueAsString(request));

            RabbitResponse rabbitResponse = objectMapper.readValue(response, RabbitResponse.class);

            if (rabbitResponse.getCode() != 204) {
                throw new ApplicationException(Messages.INTERNAL_SERVER_ERROR.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public TradeResultResponse setTradeResult(TradeResultRequest tradeResultRequest) {
        try {
            MyUserPrincipal currentUser = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            tradeResultRequest.setUserName(currentUser.getUsername());
            RabbitRequest request = RabbitRequest.builder()
                    .type(RabbitRequestType.USER_SET_TRADE_RESULT)
                    .message(objectMapper.writeValueAsString(tradeResultRequest))
                    .build();
            String response = (String) rabbitTemplate.convertSendAndReceive(USER_EXCHANGE, USER_ROUTING_KEY,
                    objectMapper.writeValueAsString(request));
            RabbitResponse rabbitResponse = objectMapper.readValue(response, RabbitResponse.class);
            if (rabbitResponse.getCode() != 200) {
                throw new ApplicationException(Messages.INTERNAL_SERVER_ERROR.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return objectMapper.readValue(rabbitResponse.getBody(), TradeResultResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
