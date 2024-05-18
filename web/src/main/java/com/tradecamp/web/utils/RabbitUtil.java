package com.tradecamp.web.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradecamp.web.dto.UserDto;
import com.tradecamp.web.model.RabbitResposne;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Service
public class RabbitUtil {
    private final ObjectMapper objectMapper;

    public UserDto convertToUserDto(Message messageFromRm) {
        UserDto userDto = null;
        if (messageFromRm != null && messageFromRm.getBody() != null) {
            try {
                RabbitResposne rabbitResposne = objectMapper.readValue(messageFromRm.getBody(), RabbitResposne.class);
                if (rabbitResposne.getCode() == 200) {
                    userDto = objectMapper.readValue(rabbitResposne.getMessage(), UserDto.class);
                } else {
                    log.error("Error from UserService: {}", rabbitResposne.getMessage());
                }
            } catch (IOException e) {
                log.error("Error while parsing UserDto", e);
            }
        } else {
            log.error("Received request has wrong format: {}", messageFromRm);
        }
        log.info("Received user: {}", userDto);
        return userDto;
    }
}
