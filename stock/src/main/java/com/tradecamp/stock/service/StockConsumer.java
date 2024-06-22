package com.tradecamp.stock.service;

import com.tradecamp.models.dto.StockDataDto;
import com.tradecamp.models.model.RabbitResposne;
import com.tradecamp.models.model.StockRandomRequest;
import com.tradecamp.models.util.RabbitUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.tradecamp.models.util.RabbitUtil.*;
import static com.tradecamp.models.util.RabbitVar.STOCK_RANDOM_QUEUE;

@Service
@EnableRabbit
@RequiredArgsConstructor
@Slf4j
public class StockConsumer {
    private final StockService stockService;
    public List<StockDataDto> getStockData(String name, LocalDateTime from, LocalDateTime to) {
        //todo Реализовать метод
        return List.of();
    }

    @RabbitListener(queues = STOCK_RANDOM_QUEUE)
    public Message getRandomStockData(Message message) {
        try {
            StockRandomRequest stockRandomRequest = fromMessageRequesteToObject(message, StockRandomRequest.class);
            List<StockDataDto> randomStockData = stockService
                    .getRandomStockData(stockRandomRequest.getSum());
            RabbitResposne rabbitResposne = fromObjectToResponse(randomStockData);
            return fromResponseToMessage(rabbitResposne);
        } catch (Exception e) {
            return new Message(makeJsonError(400).getBytes());
        }
    }

}
