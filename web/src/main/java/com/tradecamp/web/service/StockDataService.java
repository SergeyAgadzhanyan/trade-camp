package com.tradecamp.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradecamp.models.dto.StockDataDto;
import com.tradecamp.models.exception.ApplicationException;
import com.tradecamp.models.model.RabbitRequest;
import com.tradecamp.models.model.RabbitRequestType;
import com.tradecamp.models.model.RabbitResponse;
import com.tradecamp.models.util.Messages;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.tradecamp.models.util.RabbitVar.STOCK_EXCHANGE;
import static com.tradecamp.models.util.RabbitVar.STOCK_ROUTING_KEY;

@Service
@RequiredArgsConstructor
@Log4j2
public class StockDataService {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public List<StockDataDto> getStockData(String name, LocalDateTime from, LocalDateTime to) {
//        return storage.findByNameAndDateBetween(name, from, to, SpringDataWebProperties.Sort.by("date")).stream()
//                .map(mapper::toDto).collect(Collectors.toList());
//        RabbitMessage rabbitMessage = new RabbitMessage("")
//        rabbitTemplate.convertAndSend("stock_ex", message.getRoutingKey(), message.getMessage());

        return null;
    }

    public List<StockDataDto> getRandomStockData(int sum) {
        try {
            RabbitRequest request = RabbitRequest.builder()
                    .type(RabbitRequestType.STOCK_RANDOM)
                    .message(String.valueOf(sum)).build();

            String response = (String) rabbitTemplate.convertSendAndReceive(STOCK_EXCHANGE,
                    STOCK_ROUTING_KEY,
                    objectMapper.writeValueAsString(request));
            RabbitResponse rabbitResponse = objectMapper.readValue(response, RabbitResponse.class);
            if (rabbitResponse.getCode() != 200) {
                throw new ApplicationException(Messages.INTERNAL_SERVER_ERROR.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return objectMapper.readValue(rabbitResponse.getBody(), new TypeReference<List<StockDataDto>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
