package com.dinahworld.dinatoon.dto;

import jakarta.validation.constraints.NotEmpty;

public record UserAuthDto(
        String username,
        String email,
        @NotEmpty(message = "password must not be empty")
        String password
) {
}