package com.tradecamp.user.mapper;


import com.tradecamp.models.dto.UserDto;
import com.tradecamp.models.dto.UserDtoGet;
import com.tradecamp.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDtoGet toDtoGet(UserDto user);

    UserDtoGet toDtoGet(User user);

    UserDto toDto(User user);
}

