package com.tradecamp.web.service;

import com.tradecamp.models.dto.StockDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class StockDataService {
    private final RabbitTemplate rabbitTemplate;

    public List<StockDataDto> getStockData(String name, LocalDateTime from, LocalDateTime to) {
//        return storage.findByNameAndDateBetween(name, from, to, SpringDataWebProperties.Sort.by("date")).stream()
//                .map(mapper::toDto).collect(Collectors.toList());
//        RabbitMessage rabbitMessage = new RabbitMessage("")
//        rabbitTemplate.convertAndSend("stock_ex", message.getRoutingKey(), message.getMessage());

        return null;
    }

    public List<StockDataDto> getRandomStockData(int sum) {
//        var api = InvestApi.create(token);
//        var from = Instant.now().minus(30, ChronoUnit.DAYS);
//        var to = Instant.now();
//
//        List<HistoricCandle> candles;
//        try {
//            candles =
//                    api.getMarketDataService().getCandles("e6123145-9665-43e0-8413-cd61b8aa9b13", from, to,
//                            CandleInterval.CANDLE_INTERVAL_DAY).get();
//        } catch (Exception e) {
//            log.error(e);
//            throw new RuntimeException(e);
//        }
//        return candles.stream().map(mapper::toDto).collect(Collectors.toList());
        return null;
    }
}
