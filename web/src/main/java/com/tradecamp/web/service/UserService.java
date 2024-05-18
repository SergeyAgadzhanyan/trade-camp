package com.tradecamp.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradecamp.web.dto.UserDto;
import com.tradecamp.web.dto.UserDtoCreate;
import com.tradecamp.web.dto.UserDtoGet;
import com.tradecamp.web.model.RabbitRequest;
import com.tradecamp.web.utils.RabbitUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final ObjectMapper objectMapper;

    private final RabbitTemplate rabbitTemplate;
    private final RabbitUtil rabbitUtil;


    public UserDto create(UserDtoCreate u) {
//        try {
//            return mapper.toDto(userRepository.save(mapper.toModel(u)));
//        } catch (DataIntegrityViolationException e) {
//            throw new DbException(Messages.DATABASE_CONFLICT.getMessage());
//        }
        return null;
    }

    public UserDto find(UserDtoCreate u) {
//        User user = userRepository.findByName(u.getName())
//                .orElseThrow(() -> new ResourceNotFound(Messages.RESOURCE_NOT_FOUND.getMessage()));
//
//        if (!encoder.matches(u.getPassword(), user.getPassword()))
//            throw new ResourceNotFound(Messages.RESOURCE_NOT_FOUND.getMessage());
//        return mapper.toDto(user);
        return null;
    }

    public UserDto getMe() {
        UserDtoGet userDtoGet = new UserDtoGet("testname");
        String message;
        try {
            message = objectMapper.writeValueAsString(userDtoGet);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        RabbitRequest request = new RabbitRequest(message, "stock");
        Message messageFromRm = rabbitTemplate.sendAndReceive("stock_ex", request.getRoutingKey(),
                new Message(request.getMessage().getBytes()));
        return rabbitUtil.convertToUserDto(messageFromRm);
    }
}
