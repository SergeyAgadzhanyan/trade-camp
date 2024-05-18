package com.tradecamp.user.service;

import com.tradecamp.user.dto.UserDto;
import com.tradecamp.user.dto.UserDtoCreate;
import com.tradecamp.user.exception.DbException;
import com.tradecamp.user.exception.ResourceNotFound;
import com.tradecamp.user.mapper.UserMapper;
import com.tradecamp.user.model.User;
import com.tradecamp.user.repository.UserRepository;
import com.tradecamp.user.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto create(UserDtoCreate u) {
        try {
            return userMapper.toDto(userRepository.save(userMapper.toModel(u)));
        } catch (DataIntegrityViolationException e) {
            throw new DbException(Messages.DATABASE_CONFLICT.getMessage());
        }
    }

    public UserDto find(UserDtoCreate u) {
        User user = userRepository.findByName(u.getName())
                .orElseThrow(() -> new ResourceNotFound(Messages.RESOURCE_NOT_FOUND.getMessage()));
        return userMapper.toDto(user);
    }

    public UserDto getMe(String userName) throws ResourceNotFound {
        return userMapper.toDto(userRepository.findByName(userName)
                .orElseThrow(() -> new ResourceNotFound(Messages.RESOURCE_NOT_FOUND.getMessage())));
    }

}
