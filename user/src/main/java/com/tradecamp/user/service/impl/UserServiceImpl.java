package com.tradecamp.user.service.impl;

import com.tradecamp.models.dto.UserDto;
import com.tradecamp.models.dto.UserDtoCreate;
import com.tradecamp.models.dto.UserDtoGet;
import com.tradecamp.models.exception.ResourceNotFound;
import com.tradecamp.user.mapper.UserMapper;
import com.tradecamp.user.repository.UserRepository;
import com.tradecamp.user.service.UserService;
import com.tradecamp.user.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserDtoGet create(UserDtoCreate userDtoCreate) {
        return userMapper.toDtoGet(userRepository.save(userMapper.toEntity(userDtoCreate)));
    }

    @Transactional
    public UserDto find(UserDtoGet userDtoGet) {
        return userMapper.toDto(userRepository.findByName(userDtoGet.getName())
                .orElseThrow(() -> new ResourceNotFound(Messages.RESOURCE_NOT_FOUND.getMessage())));
    }

    @Transactional
    public void deleteByName(String name) {
        userRepository.deleteByName(name);
    }
}
