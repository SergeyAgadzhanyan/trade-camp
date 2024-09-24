package com.tradecamp.user.mapper;


import com.tradecamp.models.dto.UserDto;
import com.tradecamp.models.dto.UserDtoCreate;
import com.tradecamp.models.dto.UserDtoGet;
import com.tradecamp.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

@Mapper
@Service
public interface UserMapper {
    UserDtoGet toDtoGet(UserDto user);

    UserDtoGet toDtoGet(User user);

    UserDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "startScore", constant = "10000")
    User toEntity(UserDtoCreate userDto);
}

