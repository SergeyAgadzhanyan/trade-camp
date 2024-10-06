package com.tradecamp.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradecamp.models.dto.StockDataDto;
import com.tradecamp.models.model.RabbitRequest;
import com.tradecamp.models.model.RabbitResponse;
import com.tradecamp.stock.Application;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.tradecamp.models.model.RabbitRequestType.STOCK_RANDOM;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
class StockConsumerTest {
    @Autowired
    private StockConsumerImpl stockConsumer;
    private final static ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();


    @Test
    void getRandomData() throws JsonProcessingException {
        String stringResponse =
                stockConsumer.queueListener(objectMapper.writeValueAsString(RabbitRequest.builder()
                        .type(STOCK_RANDOM)
                        .message("100")
                        .build()));
        RabbitResponse response = objectMapper.readValue(stringResponse, RabbitResponse.class);
        List<StockDataDto> stockDates = objectMapper
                .readValue(response.getBody(), new TypeReference<List<StockDataDto>>() {
                });
        assertTrue("Список пустой", !stockDates.isEmpty());
    }


}
