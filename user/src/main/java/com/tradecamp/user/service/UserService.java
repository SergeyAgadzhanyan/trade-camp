package com.tradecamp.user.service;

import com.tradecamp.models.dto.*;

public interface UserService {
    UserDtoGet create(UserDtoCreate userDtoCreate);
    UserDto find(UserDtoGet userDtoGet);
    void deleteByName(String name);
    TradeResultResponse setTradeResult(TradeResultRequest tradeResultRequest);

    TradeHistoryDtoResponse getLastTrade(String userName);
}
