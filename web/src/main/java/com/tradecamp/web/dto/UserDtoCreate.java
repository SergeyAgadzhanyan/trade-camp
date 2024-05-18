package com.tradecamp.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoCreate {
    @NotBlank
    @Size(min = 3)
    private String name;
    @NotBlank
    @Size(min = 4)
    private String password;
}
