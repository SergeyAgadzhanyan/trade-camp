package com.tradecamp.user.mapper;


import com.tradecamp.models.dto.TradeHistoryDtoResponse;
import com.tradecamp.models.dto.TradeResultRequest;
import com.tradecamp.models.dto.TradeResultResponse;
import com.tradecamp.user.entity.TradeHistory;
import com.tradecamp.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Mapper
@Service
public interface TradeHistoryMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "scoreResult", expression = "java(lastScore.add(dto.getTradeResult()))")
    @Mapping(target = "user", source = "user")
    TradeHistory toTradeHistory(TradeResultRequest dto, User user, BigDecimal lastScore);

    @Mapping(target = "score", source = "scoreResult")
    @Mapping(target = "startScore", expression = "java(history.getUser().getStartScore())")
    TradeResultResponse toTradeResultResponse(TradeHistory history);

    @Mapping(target = "userName", source = "user.name")
    TradeHistoryDtoResponse toTradeHistoryResponseDto(TradeHistory entity);
}

