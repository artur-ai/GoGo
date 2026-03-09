package com.maiboroda.GoGo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maiboroda.GoGo.dto.AuthenticationRequest;
import com.maiboroda.GoGo.dto.AuthenticationResponse;
import com.maiboroda.GoGo.entity.User;
import com.maiboroda.GoGo.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            AuthenticationRequest authRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), AuthenticationRequest.class);
            log.info("Try to login user: {}", authRequest.email());

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    authRequest.email(),
                    authRequest.password()
            );

            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException exception) {
            log.error("Failed to parse authentication request", exception);
            throw new RuntimeException("Failed to parse authentication request", exception);
        }

    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException {
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();
        String email = userDetails.getUsername();
        String token = jwtService.generateToken(userDetails);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found after authentication"));

        AuthenticationResponse authenticationResponse = new AuthenticationResponse(token, user.getFirstName());
        log.info("Successfully authenticate user: {}", email);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(
                new ObjectMapper().writeValueAsString(authenticationResponse)
        );
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed
    ) throws IOException {
        log.error("Authentication failed: {}", failed.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"error\": \"Invalid email or password\"}");
    }
}
