package com.example.backend.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RabbitMessage {
    private final String message;
    private final String routingKey;
}
