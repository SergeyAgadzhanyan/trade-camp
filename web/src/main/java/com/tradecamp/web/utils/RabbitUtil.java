package com.tradecamp.web.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradecamp.models.exception.BadRequestException;
import com.tradecamp.models.model.RabbitResposne;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Service
public class RabbitUtil {

    public static String ROUTING_KEY_FIND_USER = "find";
    public static String ROUTING_KEY_CREATE_USER = "create";
    public static String ROUTING_KEY_DELETE_USER = "delete";
    public static String EXCHANGE_USER = "user_ex";
    private final ObjectMapper objectMapper;

    public <T> T convertMessage(Message message, Class<T> valueType) {
        T result = null;
        if (message != null && message.getBody() != null) {
            try {
                RabbitResposne rabbitResposne = objectMapper.readValue(message.getBody(), RabbitResposne.class);
                if (rabbitResposne.getCode() == 200) {
                    result = objectMapper.readValue(rabbitResposne.getMessage(), valueType);
                } else {
                    log.error("Error from UserService: {}", rabbitResposne.getMessage());
                    throw new BadRequestException(rabbitResposne.getMessage());
                }
            } catch (IOException e) {
                log.error("Error while parsing UserDto", e);
                throw new RuntimeException(e);
            }
        } else {
            log.error("Received request has wrong format: {}", message);
            throw new BadRequestException("Response from rabbit has wrong format or empty body");
        }
        log.info("Received object: {}", result);
        return result;
    }

    public RabbitResposne convertMessageToResponse(Message message) {
        if (message != null && message.getBody() != null) {
            try {
                return objectMapper.readValue(message.getBody(), RabbitResposne.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            log.error("Received request has wrong format: {}", message);
            throw new BadRequestException("Response from rabbit has wrong format or empty body");
        }
    }


}
