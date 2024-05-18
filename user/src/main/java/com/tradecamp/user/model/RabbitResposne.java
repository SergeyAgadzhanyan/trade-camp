package com.tradecamp.user.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RabbitResposne {
    private final String message;
    private final int code;
}
