package com.example.backend.controller;

import com.example.backend.dto.UserDto;
import com.example.backend.dto.UserDtoCreate;
import com.example.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Validated
public class UserController {
    private final UserService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserDto create(@Valid @RequestBody UserDtoCreate u) {
        return service.create(u);
    }
    @GetMapping
    public UserDto find(@Valid @RequestBody UserDtoCreate u){
        return service.find(u);
    }

    @GetMapping("/me")
    public UserDto getMe(Principal principal) {
        return UserDto.builder()
                .name(principal.getName()).build();
    }
}
