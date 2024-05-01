package com.example.backend.mapper;

import com.example.backend.dto.UserDto;
import com.example.backend.dto.UserDtoCreate;
import com.example.backend.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserMapper {
    private final PasswordEncoder encoder;


    public User toModel(UserDtoCreate u) {
        return User.builder()
                .id(null)
                .name(u.getName())
                .password(encoder.encode(u.getPassword()))
                .build();
    }

    public UserDto toDto(User user) {
        return UserDto.builder()
                .name(user.getName())
                .build();
    }
}
