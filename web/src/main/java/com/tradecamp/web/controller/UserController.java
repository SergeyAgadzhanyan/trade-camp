package com.tradecamp.web.controller;

import com.tradecamp.web.dto.UserDto;
import com.tradecamp.web.dto.UserDtoCreate;
import com.tradecamp.web.dto.UserDtoGet;
import com.tradecamp.web.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Validated

public class UserController {
    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserDto create(@Valid @RequestBody UserDtoCreate u) {
        return userService.create(u);
    }

    @GetMapping
    public UserDto find(@Valid @RequestBody UserDtoGet u) {
        return userService.find(u);
    }

    @GetMapping("/me")
    public UserDto getMe() {
        return userService.getMe();
    }
}
