package com.tradecamp.models.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.tradecamp.models.exception.BadRequestException;
import com.tradecamp.models.model.RabbitResposne;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class RabbitUtil {
    private final ObjectMapper objectMapper;
    private final Gson gson;

    public static String makeJsonError(int code) {
        return "{ \"message\": " + "\" Internal error \","  + "\"code\": " + code + "}";
    }

    public <T> T fromMessageResponseToObject(Message message, Class<T> valueType) {
        T result;
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

    public <T> T fromMessageRequesteToObject(Message message, Class<T> valueType) {
        T result;
        if (message != null && message.getBody() != null) {
            try {
                result = objectMapper.readValue(message.getBody(), valueType);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            log.error("Received request has wrong format: {}", message);
            throw new BadRequestException("Response from rabbit has wrong format or empty body");
        }
        log.info("Received object: {}", result);
        return result;
    }

    public RabbitResposne fromMessageToResponse(Message message) {
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

    public RabbitResposne fromObjectToResponse(Object o) {
        RabbitResposne rabbitResposne;
        try {
            rabbitResposne = new RabbitResposne(objectMapper.writeValueAsString(o), 200);
        } catch (JsonProcessingException e) {
            log.error("Error while parsing Object", e);
            rabbitResposne = new RabbitResposne(e.getMessage(), 400);
        }
        return rabbitResposne;
    }

    public Message fromResponseToMessage(RabbitResposne resposne) {
        try {
            return new Message(objectMapper.writeValueAsBytes(resposne));
        } catch (JsonProcessingException e) {
            return new Message(makeJsonError(300).getBytes());
        }
    }
}
