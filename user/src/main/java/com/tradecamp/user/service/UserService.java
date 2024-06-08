package com.tradecamp.user.service;

import com.tradecamp.user.dto.UserDto;
import com.tradecamp.user.dto.UserDtoCreate;
import com.tradecamp.user.dto.UserDtoGet;
import com.tradecamp.user.exception.DbException;
import com.tradecamp.user.exception.ResourceNotFound;
import com.tradecamp.user.mapper.UserMapperInterface;
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
    private final UserMapperInterface userMapperInterface;

    public UserDto create(UserDtoCreate u) {
        try {
            return userMapperInterface.toDto(userRepository.save(userMapperInterface.toUser(u)));
        } catch (DataIntegrityViolationException e) {
            throw new DbException(Messages.DATABASE_CONFLICT.getMessage());
        }
    }

    public UserDto find(UserDtoGet userDtoGet) {
        User user = userRepository.findByName(userDtoGet.getName())
                .orElseThrow(() -> new ResourceNotFound(Messages.RESOURCE_NOT_FOUND.getMessage()));
        return userMapperInterface.toDto(user);
    }


}
