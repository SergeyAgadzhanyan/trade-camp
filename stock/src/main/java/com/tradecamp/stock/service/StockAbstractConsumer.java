package com.tradecamp.stock.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradecamp.models.model.RabbitRequest;
import com.tradecamp.models.model.RabbitRequestType;
import com.tradecamp.models.model.RabbitResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StockAbstractConsumer<S extends StockService> {
    private final S service;
    private final ObjectMapper objectMapper;

    public String handleMessage(String message) {
        String response = "";
        int code;
        try {
            RabbitRequest rabbitRequest = objectMapper.readValue(message, RabbitRequest.class);
            switch (rabbitRequest.getType()) {
                case RabbitRequestType.STOCK_RANDOM:
                    response = objectMapper.writeValueAsString(service
                            .getRandomStockData(Integer.parseInt(rabbitRequest.getMessage())));
                    code = 200;
                    break;
                default:
                    throw new RuntimeException();
            }
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
            return "{message:\"\", error:\"500\"}";
        }

    }
}
