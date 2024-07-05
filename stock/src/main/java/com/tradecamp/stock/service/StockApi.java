package com.tradecamp.stock.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.core.InvestApi;

@Service
@Getter
public class StockApi {
    private final InvestApi api;

    public StockApi(@Value("${tinkoff.token}") String token) {
        api = InvestApi.create(token);
    }
}
