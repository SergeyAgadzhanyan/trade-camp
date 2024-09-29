package com.tradecamp.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradecamp.models.dto.TradeResultRequest;
import com.tradecamp.models.dto.UserDtoCreate;
import com.tradecamp.models.dto.UserDtoGet;
import com.tradecamp.models.model.RabbitRequest;
import com.tradecamp.models.model.RabbitRequestType;
import com.tradecamp.models.model.RabbitResponse;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.tradecamp.models.util.RabbitVar.USER_QUEUE;


@Component
@AllArgsConstructor
public class UserConsumer {
    private final UserService service;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = USER_QUEUE)
    public String handleMessage(String request) {
        String response = "";
        String error = null;
        try {
            RabbitRequest rabbitRequest = objectMapper.readValue(request, RabbitRequest.class);
            switch (rabbitRequest.getType()) {
                case RabbitRequestType.USER_CREATE -> response = objectMapper.writeValueAsString(service
                        .create(objectMapper.readValue(rabbitRequest.getMessage(), UserDtoCreate.class)));


                case USER_FIND -> response = objectMapper.writeValueAsString(
                        service.find(objectMapper
                                .readValue(rabbitRequest.getMessage(), UserDtoGet.class)));

                case USER_DELETE -> service.deleteByName(rabbitRequest.getMessage());

                case USER_SET_TRADE_RESULT ->
                        response = objectMapper.writeValueAsString(service.setTradeResult(objectMapper
                                .readValue(rabbitRequest.getMessage(), TradeResultRequest.class)));

                case USER_GET_LAST_TRADE_RESULT ->
                        response = objectMapper.writeValueAsString(service.getLastTrade(rabbitRequest.getMessage()));


                default -> throw new RuntimeException();
            }
            ;
        } catch (Exception e) {
            error = e.getMessage();
        }
        RabbitResponse rabbitResponse = RabbitResponse.builder()
                .error(error)
                .body(response)
                .build();
        try {
            return objectMapper.writeValueAsString(rabbitResponse);
        } catch (JsonProcessingException e) {
            return "{request:\"\", error:\"500\"}";
        }

    }
}
