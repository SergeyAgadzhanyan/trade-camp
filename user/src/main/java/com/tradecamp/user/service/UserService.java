package com.tradecamp.user.service;

import com.tradecamp.models.dto.UserDto;
import com.tradecamp.models.dto.UserDtoCreate;
import com.tradecamp.models.dto.UserDtoGet;

public interface UserService {
    UserDtoGet create(UserDtoCreate userDtoCreate);

    UserDto find(UserDtoGet userDtoGet);


    void deleteByName(String name);
}
