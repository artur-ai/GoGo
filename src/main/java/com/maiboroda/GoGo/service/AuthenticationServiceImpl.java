package com.maiboroda.GoGo.service;

import com.maiboroda.GoGo.dto.AuthenticationRequest;
import com.maiboroda.GoGo.dto.AuthenticationResponse;
import com.maiboroda.GoGo.dto.LoginRequest;
import com.maiboroda.GoGo.entity.User;
import com.maiboroda.GoGo.mapper.UserMapper;
import com.maiboroda.GoGo.repository.UserRepository;
import com.maiboroda.GoGo.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.maiboroda.GoGo.entity.Role.ROLE_USER;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final UserDetailsService userDetailsService;

    @Override
    public AuthenticationResponse register(LoginRequest registerRequest) {
        User user = userMapper.toEntity(registerRequest);

        user.setPassword(passwordEncoder.encode(registerRequest.password()));
        user.setRoles(Set.of(ROLE_USER));

        userRepository.save(user);
        log.info("Successfully save user {} to datA base", user.getFirstName());

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String jwtToken = jwtService.generateToken(userDetails);

        return new AuthenticationResponse(jwtToken, user.getFirstName());
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.email(),
                        authenticationRequest.password()
                )
        );

        User user = userRepository.findByEmail(authenticationRequest.email())
                .orElseThrow();
        log.info("Successfully find user {} in data base", user.getEmail());

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String jwtToken = jwtService.generateToken(userDetails);

        return new AuthenticationResponse(jwtToken, user.getFirstName());
    }
}
