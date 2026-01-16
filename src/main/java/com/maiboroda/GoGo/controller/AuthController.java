package com.maiboroda.GoGo.controller;

import com.maiboroda.GoGo.dto.AuthenticationRequest;
import com.maiboroda.GoGo.dto.AuthenticationResponse;
import com.maiboroda.GoGo.dto.RegisterRequest;
import com.maiboroda.GoGo.entity.User;
import com.maiboroda.GoGo.service.AuthenticationService;
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
            @RequestBody RegisterRequest registerRequest
    ) {
        log.info("Try register user: {}", registerRequest.getEmail());
        AuthenticationResponse authenticationResponse = authenticationService.register(registerRequest);
        log.info("Successfully register user: {} ", registerRequest.getEmail());
        return new ResponseEntity<>(authenticationResponse, HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authentication(
            @RequestBody AuthenticationRequest authenticationRequest
            ) {
        log.info("Try to login user: {}", authenticationRequest.getEmail());
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest);
        log.info("Successfully authenticate user: {}", authenticationRequest.getEmail());
        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }

}
