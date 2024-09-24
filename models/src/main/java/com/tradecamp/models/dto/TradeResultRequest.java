package com.tradecamp.models.dto;

import com.tradecamp.models.util.enums.Currency;
import com.tradecamp.models.util.enums.TradeOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradeResultRequest {
    private String stockName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Currency currency;
    private BigDecimal tradeResult;
    private String userName;
    private TradeOperation operation;
}
