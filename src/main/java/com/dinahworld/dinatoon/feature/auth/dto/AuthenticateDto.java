package com.dinahworld.dinatoon.feature.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record AuthenticateDto(
        @NotNull
        @Schema(title = "email", description = "Email address of the user")
        String email,

        @NotNull
        @Schema(title = "password", description = "Password of the user")
        String password
) {
}