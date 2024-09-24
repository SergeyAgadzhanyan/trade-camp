package com.tradecamp.user.entity;


import com.tradecamp.models.util.enums.Currency;
import com.tradecamp.models.util.enums.TradeOperation;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "trade_history")
public class TradeHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @Enumerated(EnumType.STRING)
    private TradeOperation operation;
    private BigDecimal tradeResult;
    private BigDecimal scoreResult;
}
