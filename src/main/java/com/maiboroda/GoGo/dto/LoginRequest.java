package com.maiboroda.GoGo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "FirstName can not be empty")
        @Size(max = 155, message = "Name must be less than 155 characters ")
        String firstName,

        @NotBlank(message = "LastName can not be empty")
        @Size(max = 155, message = "LastName must be less than 155 characters ")
        String lastName,

        @Size(max = 155, message = "MiddleName must be less than 155 characters ")
        String middleName,

        @NotBlank(message = "Email can not be empty")
        @Size(max = 155, message = "Email must be less than 155 characters ")
        String email,

        @NotBlank(message = "Password can not be empty")
        String password,

        @NotBlank(message = "Passport can not be empty")
        String passportUrl
) {
}
