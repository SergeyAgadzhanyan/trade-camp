package com.tradecamp.user.mapper;

import com.tradecamp.user.dto.UserDto;
import com.tradecamp.user.dto.UserDtoCreate;
import com.tradecamp.user.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapperInterface {
    User toUser(UserDtoCreate u);

    UserDto toDto(User user);
}

