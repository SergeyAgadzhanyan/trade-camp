package com.tradecamp.stock.service;

import com.tradecamp.models.dto.StockDataDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StockService {
    List<StockDataDto> getStockData(String name, LocalDateTime from, LocalDateTime to);

    List<StockDataDto> getRandomStockData(int sum);
}
