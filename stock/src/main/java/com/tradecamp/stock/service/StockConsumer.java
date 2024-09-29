package com.tradecamp.stock.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradecamp.models.model.RabbitRequest;
import com.tradecamp.models.model.RabbitRequestType;
import com.tradecamp.models.model.RabbitResponse;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.tradecamp.models.util.RabbitVar.STOCK_QUEUE;

@Component
@AllArgsConstructor
public class StockConsumer {
    private final StockService service;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = STOCK_QUEUE)
    public String handleMessage(String message) {
        String response = "";
        String error = null;
        try {
            RabbitRequest rabbitRequest = objectMapper.readValue(message, RabbitRequest.class);
            switch (rabbitRequest.getType()) {
                case RabbitRequestType.STOCK_RANDOM:
                    response = objectMapper.writeValueAsString(service
                            .getRandomStockData(Integer.parseInt(rabbitRequest.getMessage())));
                    break;
                default:
                    throw new RuntimeException();
            }
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
            return "{message:\"\", error:\"500\"}";
        }

    }
}
