package com.example.backend.service;

import com.example.backend.dto.UserDto;
import com.example.backend.dto.UserDtoCreate;
import com.example.backend.exception.DbException;
import com.example.backend.exception.ResourceNotFound;
import com.example.backend.mapper.UserMapper;
import com.example.backend.model.User;
import com.example.backend.storage.UserStorage;
import com.example.backend.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage storage;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;

    public UserDto create(UserDtoCreate u) {
        try {
            return mapper.toDto(storage.save(mapper.toModel(u)));
        } catch (DataIntegrityViolationException e) {
            throw new DbException(Messages.DATABASE_CONFLICT.getMessage());
        }
    }

    public UserDto find(UserDtoCreate u) {
        User user = storage.findByName(u.getName())
                .orElseThrow(() -> new ResourceNotFound(Messages.RESOURCE_NOT_FOUND.getMessage()));

        if (!encoder.matches(u.getPassword(), user.getPassword()))
            throw new ResourceNotFound(Messages.RESOURCE_NOT_FOUND.getMessage());
        return mapper.toDto(user);
    }
}
