package com.tradecamp.user.service.impl;

import com.tradecamp.models.dto.*;
import com.tradecamp.models.exception.ApplicationException;
import com.tradecamp.models.util.Messages;
import com.tradecamp.user.entity.TradeHistory;
import com.tradecamp.user.entity.User;
import com.tradecamp.user.mapper.TradeHistoryMapper;
import com.tradecamp.user.mapper.UserMapper;
import com.tradecamp.user.repository.TradeHistoryRepository;
import com.tradecamp.user.repository.UserRepository;
import com.tradecamp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TradeHistoryRepository tradeHistoryRepository;
    private final TradeHistoryMapper tradeHistoryMapper;

    @Transactional
    public UserDtoGet create(UserDtoCreate userDtoCreate) {
        return userMapper.toDtoGet(userRepository.save(userMapper.toEntity(userDtoCreate)));
    }

    @Transactional
    public UserDto find(UserDtoGet userDtoGet) {
        return userMapper.toDto(userRepository.findByName(userDtoGet.getName())
                .orElseThrow(() -> new ApplicationException(Messages.RESOURCE_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND)));
    }

    @Transactional
    public void deleteByName(String name) {
        userRepository.deleteByName(name);
    }

    @Override
    @Transactional
    public TradeResultResponse setTradeResult(TradeResultRequest tradeResultRequest) {
        User user = userRepository.findByName(tradeResultRequest.getUserName())
                .orElseThrow(() -> new ApplicationException(Messages.RESOURCE_NOT_FOUND.getMessage(),
                        HttpStatus.NOT_FOUND));
        Optional<TradeHistory> lastTrade = tradeHistoryRepository.findLast();
        BigDecimal lastScore = lastTrade.isPresent() ? lastTrade.get().getScoreResult() : user.getStartScore();

        TradeHistory tradeHistory = tradeHistoryMapper.toTradeHistory(tradeResultRequest,
                user, lastScore);
        tradeHistory = tradeHistoryRepository.save(tradeHistory);
        return tradeHistoryMapper.toTradeResultResponse(tradeHistory);
    }
}
