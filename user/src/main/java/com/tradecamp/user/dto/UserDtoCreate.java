package com.tradecamp.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDtoCreate {
    @NotBlank
    @Size(min = 3)
    private String name;
    @NotBlank
    @Size(min = 4)
    private String password;
}
