package com.tradecamp.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradecamp.models.dto.TradeResultRequest;
import com.tradecamp.models.dto.UserDtoCreate;
import com.tradecamp.models.dto.UserDtoGet;
import com.tradecamp.models.exception.ApplicationException;
import com.tradecamp.models.model.RabbitRequest;
import com.tradecamp.models.model.RabbitRequestType;
import com.tradecamp.models.model.RabbitResponse;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class UserAbstractConsumer<S extends UserService> {
    private final S service;
    private final ObjectMapper objectMapper;

    public String handleMessage(String request) {
        String response = "";
        int code;
        try {
            RabbitRequest rabbitRequest = objectMapper.readValue(request, RabbitRequest.class);
            code = switch (rabbitRequest.getType()) {
                case RabbitRequestType.USER_CREATE -> {
                    response = objectMapper.writeValueAsString(service
                            .create(objectMapper.readValue(rabbitRequest.getMessage(), UserDtoCreate.class)));
                    yield 201;
                }
                case USER_FIND -> {
                    response = objectMapper.writeValueAsString(
                            service.find(objectMapper
                                    .readValue(rabbitRequest.getMessage(), UserDtoGet.class)));
                    yield 200;
                }
                case USER_DELETE -> {
                    service.deleteByName(rabbitRequest.getMessage());
                    yield 204;
                }
                case USER_SET_TRADE_RESULT -> {
                    response = objectMapper.writeValueAsString(service.setTradeResult(objectMapper
                            .readValue(rabbitRequest.getMessage(), TradeResultRequest.class)));
                    yield 200;
                }
                default -> throw new RuntimeException();
            };
        } catch (ApplicationException e) {
            code = 404;
        } catch (Exception e) {
            code = 500;
        }
        RabbitResponse rabbitResponse = RabbitResponse.builder()
                .code(code)
                .body(response)
                .build();
        try {
            return objectMapper.writeValueAsString(rabbitResponse);
        } catch (JsonProcessingException e) {
            return "{request:\"\", error:\"500\"}";
        }

    }
}
