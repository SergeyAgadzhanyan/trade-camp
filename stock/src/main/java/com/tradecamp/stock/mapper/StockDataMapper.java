package com.tradecamp.stock.mapper;

import com.google.protobuf.Timestamp;
import com.tradecamp.models.dto.StockDataDto;
import com.tradecamp.stock.entity.StockData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.piapi.contract.v1.HistoricCandle;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Mapper(componentModel = "spring")
public interface StockDataMapper {
    @Mapping(target = "name", constant = "APPLE")
    @Mapping(target = "open", source = "candle.open.units")
    @Mapping(target = "close", source = "candle.close.units")
    @Mapping(target = "low", source = "candle.low.units")
    @Mapping(target = "high", source = "candle.high.units")
    @Mapping(target = "date", expression = "java(getDate(candle.getTime()))")
    StockDataDto toDto(HistoricCandle candle);

    default LocalDateTime getDate(Timestamp timestamp) {
        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(timestamp.getSeconds() * 1000), ZoneId.of("UTC"));
    }

    StockDataDto toDto(StockData s);
}
