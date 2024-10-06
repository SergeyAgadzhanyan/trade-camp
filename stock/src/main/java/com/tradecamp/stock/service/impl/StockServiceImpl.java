package com.tradecamp.stock.service.impl;

import com.tradecamp.models.dto.StockDataDto;
import com.tradecamp.stock.mapper.StockDataMapper;
import com.tradecamp.stock.service.StockApi;
import com.tradecamp.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.CandleInterval;
import ru.tinkoff.piapi.contract.v1.HistoricCandle;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class StockServiceImpl implements StockService {
    private final StockDataMapper mapperMapStruct;
    private final StockApi stockApi;

    @Override
    public List<StockDataDto> getRandomStockData(int sum) {
        RandomRange randomRange = getRandomRange(sum);
        List<HistoricCandle> candles;
        try {
            //todo поменять instrumentId на динамическое значение
            candles =
                    stockApi.getApi().getMarketDataService().getCandles("e6123145-9665-43e0-8413-cd61b8aa9b13", randomRange.from(), randomRange.to(),
                            CandleInterval.CANDLE_INTERVAL_DAY).get();
        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException(e);
        }
        return candles.stream().map(mapperMapStruct::toDto).collect(Collectors.toList());
    }

    private static RandomRange getRandomRange(int sum) {
        Random random = new Random();
        int toDay = random.nextInt(1000 + sum);
        int fromDay = toDay + sum;
        Instant now = Instant.now();
        return new RandomRange(now.minus(fromDay, ChronoUnit.DAYS), now.minus(toDay, ChronoUnit.DAYS));
    }

    private record RandomRange(Instant from, Instant to) {
    }
}
