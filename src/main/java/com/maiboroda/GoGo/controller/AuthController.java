package com.maiboroda.GoGo.controller;

import com.maiboroda.GoGo.dto.AuthenticationResponse;
import com.maiboroda.GoGo.dto.LoginRequest;
import com.maiboroda.GoGo.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody LoginRequest registerRequest
    ) {
        log.info("Try register user: {}", registerRequest.email());
        AuthenticationResponse authenticationResponse = authenticationService.register(registerRequest);
        log.info("Successfully register user: {} ", registerRequest.email());
        return new ResponseEntity<>(authenticationResponse, HttpStatus.CREATED);
    }
}
