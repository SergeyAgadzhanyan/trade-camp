package com.example.backend.mapper;

import com.example.backend.dto.StockDataDto;
import com.example.backend.model.StockData;

public class StockDataMapper {
    public static StockDataDto toDto(StockData s) {
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
