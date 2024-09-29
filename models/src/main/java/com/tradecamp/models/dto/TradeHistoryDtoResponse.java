package com.tradecamp.models.dto;


import com.tradecamp.models.util.enums.Currency;
import com.tradecamp.models.util.enums.TradeOperation;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeHistoryDtoResponse {
    private String userName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Currency currency;
    private TradeOperation operation;
    private BigDecimal tradeResult;
    private BigDecimal scoreResult;
}
