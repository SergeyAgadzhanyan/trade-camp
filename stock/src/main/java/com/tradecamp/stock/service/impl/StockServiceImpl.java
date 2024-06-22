package com.tradecamp.stock.service.impl;

import com.tradecamp.stock.mapper.StockDataMapper;
import com.tradecamp.stock.service.StockService;
import com.tradecamp.stock.storage.StockDataStorage;
import com.tradecamp.models.dto.StockDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.CandleInterval;
import ru.tinkoff.piapi.contract.v1.HistoricCandle;
import ru.tinkoff.piapi.core.InvestApi;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class StockServiceImpl implements StockService {
    @Value("${tinkoff.token}")
    private String token;
    private final StockDataStorage storage;
    private final StockDataMapper mapper;

    @Override
    public List<StockDataDto> getStockData(String name, LocalDateTime from, LocalDateTime to) {
        return storage.findByNameAndDateBetween(name, from, to, Sort.by("date")).stream()
                .map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<StockDataDto> getRandomStockData(int sum) {
        var api = InvestApi.create(token);
        //todo поменять дни на динамическое значение
        var from = Instant.now().minus(sum, ChronoUnit.DAYS);
        var to = Instant.now();

        List<HistoricCandle> candles;
        try {
            //todo поменять instrumentId на динамическое значение
            candles =
                    api.getMarketDataService().getCandles("e6123145-9665-43e0-8413-cd61b8aa9b13", from, to,
                            CandleInterval.CANDLE_INTERVAL_DAY).get();
        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException(e);
        }
        return candles.stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
