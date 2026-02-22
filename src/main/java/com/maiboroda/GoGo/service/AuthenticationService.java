package com.maiboroda.GoGo.service;

import com.maiboroda.GoGo.dto.AuthenticationRequest;
import com.maiboroda.GoGo.dto.AuthenticationResponse;
import com.maiboroda.GoGo.dto.LoginRequest;

public interface AuthenticationService {
    AuthenticationResponse register(LoginRequest registerRequest);
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
