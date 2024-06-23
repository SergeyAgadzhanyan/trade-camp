package com.tradecamp.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradecamp.models.dto.StockDataDto;
import com.tradecamp.models.model.StockRandomRequest;
import com.tradecamp.models.util.RabbitUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.tradecamp.models.util.RabbitVar.STOCK_EXCHANGE;
import static com.tradecamp.models.util.RabbitVar.STOCK_ROUTING_KEY_RANDOM;

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
            StockRandomRequest stockRandomRequest = StockRandomRequest.builder().sum(sum).build();
            Message receive = rabbitTemplate.sendAndReceive(STOCK_EXCHANGE, STOCK_ROUTING_KEY_RANDOM,
                    new Message(objectMapper.writeValueAsString(stockRandomRequest).getBytes()));
            return RabbitUtil.fromMessageResponseToObject(receive, (Class<List<StockDataDto>>) (Object) List.class);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
