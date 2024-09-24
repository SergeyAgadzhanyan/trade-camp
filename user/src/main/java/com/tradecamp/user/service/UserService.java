package com.tradecamp.user.service;

import com.tradecamp.models.dto.*;
import jakarta.transaction.Transactional;

public interface UserService {
    UserDtoGet create(UserDtoCreate userDtoCreate);
    UserDto find(UserDtoGet userDtoGet);
    void deleteByName(String name);
    TradeResultResponse setTradeResult(TradeResultRequest tradeResultRequest);
}
