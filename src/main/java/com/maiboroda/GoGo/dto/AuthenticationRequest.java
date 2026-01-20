package com.maiboroda.GoGo.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(
        @NotBlank(message = "Email can not be empty")
        String email,

        @NotBlank(message = "Password can not be empty")
        String password
) {
}
