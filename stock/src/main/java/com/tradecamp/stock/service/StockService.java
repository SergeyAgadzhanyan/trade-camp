package com.tradecamp.stock.service;

import com.tradecamp.models.dto.StockDataDto;

import java.util.List;

public interface StockService {
    List<StockDataDto> getRandomStockData(int sum);
}
