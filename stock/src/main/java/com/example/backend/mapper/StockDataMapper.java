package com.example.backend.mapper;

import com.example.backend.dto.StockDataDto;
import com.example.backend.model.StockData;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.contract.v1.HistoricCandle;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class StockDataMapper {
    public StockDataDto toDto(HistoricCandle candle) {
        return StockDataDto.builder()
                .name("APPLE")
                .open(String.valueOf(candle.getOpen().getUnits()))
                .close(String.valueOf(candle.getClose().getUnits()))
                .low(String.valueOf(candle.getLow().getUnits()))
                .high(String.valueOf(candle.getHigh().getUnits()))
                .date(LocalDateTime.ofInstant(Instant.ofEpochMilli(candle.getTime().getSeconds() * 1000), ZoneId.of(
                        "UTC")))
                .volume(candle.getVolume())
                .build();
    }

    public StockDataDto toDto(StockData s) {
        return StockDataDto.builder()
                .name(s.getName())
                .open(s.getOpen())
                .close(s.getClose())
                .low(s.getLow())
                .high(s.getHigh())
                .date(s.getDate())
                .volume(s.getVolume())
                .build();
    }
}
