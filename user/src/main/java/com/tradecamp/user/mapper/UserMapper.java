package com.tradecamp.user.mapper;

import com.tradecamp.user.dto.UserDto;
import com.tradecamp.user.dto.UserDtoCreate;
import com.tradecamp.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserMapper {

//    public User toModel(UserDtoCreate u) {
//        return User.builder()
//                .id(null)
//                .name(u.getName())
//                .password(u.getPassword())
//                .build();
//    }
//
//    public UserDto toDto(User user) {
//        return UserDto.builder()
//                .name(user.getName())
//                .password(user.getPassword())
//                .build();
//    }
}
