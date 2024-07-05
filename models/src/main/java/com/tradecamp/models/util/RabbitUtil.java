package com.tradecamp.models.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradecamp.models.exception.BadRequestException;
import com.tradecamp.models.model.RabbitResposne;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;

import java.io.IOException;

@Slf4j
public class RabbitUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    public static String makeJsonError(String message, int code) {
        return "{ \"message\": " + message + "\", code\": " + code + "}";
    }

    public static <T> T fromMessageResponseToObject(Message message, Class<T> valueType) {

        RabbitResposne rabbitResposne = fromMessageToResponse(message);
        T result = getObjectClassFromResponse(valueType, rabbitResposne);
        log.info("Received object: {}", result);
        return result;
    }


    public static <T> T fromMessageRequesteToObject(Message message, Class<T> valueType) {
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

    public static RabbitResposne fromMessageToResponse(Message message) {
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

    public static RabbitResposne fromObjectToResponse(Object o) {
        RabbitResposne rabbitResposne;
        try {
            rabbitResposne = new RabbitResposne(objectMapper.writeValueAsString(o), 200);
        } catch (JsonProcessingException e) {
            log.error("Error while parsing Object", e);
            rabbitResposne = new RabbitResposne(e.getMessage(), 400);
        }
        return rabbitResposne;
    }

    public static Message fromResponseToMessage(RabbitResposne resposne) {
        try {
            return new Message(objectMapper.writeValueAsBytes(resposne));
        } catch (JsonProcessingException e) {
            return new Message(makeJsonError(resposne.getMessage(), 500).getBytes());
        }
    }

    private static <T> T getObjectClassFromResponse(Class<T> valueType, RabbitResposne rabbitResposne) {
        T result;
        if (rabbitResposne.getCode() == 200) {
            try {
                result = objectMapper.readValue(rabbitResposne.getMessage(), valueType);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else {
            log.error("Error from UserService: {}", rabbitResposne.getMessage());
            throw new BadRequestException(rabbitResposne.getMessage());
        }
        return result;
    }
}
