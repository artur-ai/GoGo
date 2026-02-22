package com.maiboroda.GoGo.dto;

public record AuthenticationResponse(
        String token,
        String firstName
) {}
