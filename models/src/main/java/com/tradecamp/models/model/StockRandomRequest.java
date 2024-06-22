package com.tradecamp.models.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Модель представляющая тело реквеста реббита при обращении в сервис stock за случайными данными. Sum представляет
 * собой число запрашиваемых данных
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockRandomRequest {
    private int sum;
}
