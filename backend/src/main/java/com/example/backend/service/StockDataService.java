package com.example.backend.service;

import com.example.backend.dto.StockDataDto;
import com.example.backend.enums.Symbols;
import com.example.backend.mapper.StockDataMapper;
import com.example.backend.storage.StockDataStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockDataService {
    private final StockDataStorage storage;
    private final StockDataMapper mapper;

    public List<StockDataDto> getStockData(String name, LocalDateTime from, LocalDateTime to) {
        return storage.findByNameAndDateBetween(name, from, to, Sort.by("date")).stream()
                .map(mapper::toDto).collect(Collectors.toList());
    }

    public List<StockDataDto> getRandomStockData(int sum) {
        return storage.getRandomData(Symbols.TSLA.name(), sum).stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
