package com.tradecamp.web.controller;

import com.tradecamp.models.dto.*;
import com.tradecamp.web.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    public UserDtoGet create(@Valid @RequestBody UserDtoCreate userDtoCreate) {
        return userService.create(userDtoCreate);
    }

    @GetMapping
    public UserDto get(@RequestParam @NotBlank @Size(min = 3) String name) {
        return userService.getByName(name);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@RequestParam @NotBlank @Size(min = 3) String name) {
        userService.deleteByName(name);
    }

    @PostMapping("/stat")
    public TradeResultResponse setStat(@RequestBody TradeResultRequest tradeResultRequest) {
        return userService.setTradeResult(tradeResultRequest);
    }

}
