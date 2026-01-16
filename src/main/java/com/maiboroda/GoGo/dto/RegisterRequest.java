package com.maiboroda.GoGo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "FirstName can not be empty")
    @Size(max = 155, message = "Name must be less than 155 characters ")
    private String firstName;

    @NotBlank(message = "LastName can not be empty")
    @Size(max = 155, message = "LastName must be less than 155 characters ")
    private String lastName;

    @NotBlank(message = "MiddleName can not be empty")
    @Size(max = 155, message = "MiddleName must be less than 155 characters ")
    private String middleName;

    @NotBlank(message = "Email can not be empty")
    @Size(max = 155, message = "Email must be less than 155 characters ")
    private String email;

    @NotBlank(message = "Password can not be empty")
    private String password;

    @NotBlank(message = "Passport can not be empty")
    private String passportUrl;



}
