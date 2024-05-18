package com.tradecamp.web.controller;


import com.tradecamp.web.dto.StockDataDto;
import com.tradecamp.web.service.RabbitMQProducerService;
import com.tradecamp.web.service.StockDataService;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/data")
@Validated
public class StockDataController {
    StockDataService service;

    @GetMapping
    public List<StockDataDto> getData(@RequestParam @Size(min = 3, max = 6) String name,
                                      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                      @RequestParam @Past LocalDateTime from,
                                      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                      @RequestParam @PastOrPresent LocalDateTime to) {
        return service.getStockData(name, from, to);
    }
    @GetMapping("/test")
    public String test(){
        return "OK";
    }

    @GetMapping("/random")
    public List<StockDataDto> getRandomData(@RequestParam @Min(100) @Max(200) Integer sum) {
        return service.getRandomStockData(sum);
    }

}
