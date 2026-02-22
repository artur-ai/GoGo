package com.maiboroda.GoGo.security;

import com.maiboroda.GoGo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {
        CustomAuthenticationFilter authFilter = new CustomAuthenticationFilter(
                authenticationManager,
                jwtService,
                userRepository
        );
        authFilter.setFilterProcessesUrl("/api/v1/auth/authenticate");

        httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/cars/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/cars/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/cars/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/cars/**").authenticated()
                        .requestMatchers("/", "/index.html", "/login.html", "/register.html", "/catalog-car.html").permitAll()
                        .requestMatchers("/style.css", "/auth.js", "/index.js", "/catalog.js", "/catalog.css").permitAll()
                        .requestMatchers("/js/**", "/css/**", "/images/**", "/static/**").permitAll()
                        .anyRequest().authenticated()
                )

                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(authFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
