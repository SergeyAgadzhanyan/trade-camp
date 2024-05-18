package com.tradecamp.web.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RabbitRequest {
    private final String message;
    private final String routingKey;
}
