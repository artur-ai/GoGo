package com.maiboroda.GoGo.controller;

import com.github.database.rider.junit5.api.DBRider;
import com.maiboroda.GoGo.AbstractIntegrationTest;
import com.maiboroda.GoGo.dto.AuthenticationRequest;
import com.maiboroda.GoGo.dto.CarRequestDto;
import com.maiboroda.GoGo.dto.LoginRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DBRider
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class AuthControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void cleanUsers() {
        jdbcTemplate.execute("DELETE FROM user_roles");
        jdbcTemplate.execute("DELETE FROM users");
    }

    @Test
    void testRegister_ShouldReturn201AndToken() throws Exception {
        LoginRequest request = new LoginRequest(
                "John", "Doe", "Mid",
                "john@test.com", "password123",
                "https://passport.url"
        );

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token", notNullValue()))
                .andExpect(jsonPath("$.firstName", is("John")));
    }

    @Test
    void testRegister_DuplicateEmail_ShouldFail() throws Exception {
        LoginRequest request = new LoginRequest(
                "John", "Doe", "Mid",
                "john@test.com", "password123",
                "https://passport.url"
        );

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testLogin_ShouldReturn200AndToken() throws Exception {
        LoginRequest registerRequest = new LoginRequest(
                "John", "Doe", "Mid",
                "login@test.com", "password123",
                "https://passport.url"
        );
        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)));

        AuthenticationRequest loginRequest = new AuthenticationRequest(
                "login@test.com", "password123"
        );

        mockMvc.perform(post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", notNullValue()))
                .andExpect(jsonPath("$.firstName", is("John")));
    }

    @Test
    void testLogin_WrongPassword_ShouldReturn401() throws Exception {
        AuthenticationRequest loginRequest = new AuthenticationRequest(
                "notexist@test.com", "wrongpassword"
        );

        mockMvc.perform(post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testRegister_EmptyEmail_ShouldReturn400() throws Exception {
        LoginRequest request = new LoginRequest(
                "John", "Doe", "Mid",
                "", "password123",
                "https://passport.url"
        );

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRegister_EmptyFirstName_ShouldReturn400() throws Exception {
        LoginRequest request = new LoginRequest(
                "", "Doe", "Mid",
                "john@test.com", "password123",
                "https://passport.url"
        );

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRegister_EmptyLastName_ShouldReturn400() throws Exception {
        LoginRequest request = new LoginRequest(
                "John", "", "Mid",
                "john@test.com", "password123",
                "https://passport.url"
        );

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRegister_EmptyPassword_ShouldReturn400() throws Exception {
        LoginRequest request = new LoginRequest(
                "John", "Doe", "Mid",
                "john@test.com", "",
                "https://passport.url"
        );

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRegister_EmptyPassport_ShouldReturn400() throws Exception {
        LoginRequest request = new LoginRequest(
                "John", "Doe", "Mid",
                "john@test.com", "password123",
                ""
        );

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRegister_TooLongFirstName_ShouldReturn400() throws Exception {
        LoginRequest request = new LoginRequest(
                "J".repeat(156), "Doe", "Mid",
                "john@test.com", "password123",
                "https://passport.url"
        );

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRegister_ResponseContainsTokenAndFirstName() throws Exception {
        LoginRequest request = new LoginRequest("Anna", "Smith",
                "Mid", "anna@test.com",
                "securepass", "https://passport.url"
        );

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token", notNullValue()))
                .andExpect(jsonPath("$.firstName", is("Anna")));
    }

    @Test
    void testLogin_NonExistentUser_ShouldReturn401() throws Exception {
        AuthenticationRequest loginRequest = new AuthenticationRequest(
                "nobody@test.com", "password123"
        );

        mockMvc.perform(post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testPublicEndpoint_WithoutToken_ShouldReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/cars")
                        .param("page", "0")
                        .param("size", "6"))
                .andExpect(status().isOk());
    }

    @Test
    void testProtectedEndpoint_WithValidToken_ShouldReturn201() throws Exception {
        LoginRequest registerRequest = new LoginRequest(
                "John", "Doe", "Mid",
                "auth@test.com", "password123",
                "https://passport.url"
        );

        String response = mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andReturn().getResponse().getContentAsString();

        String token = objectMapper.readTree(response).get("token").asText();

        CarRequestDto carRequest = new CarRequestDto("Dodge", "Dart", 2014, "Petrol", "2.4",
                new BigDecimal("15.50"), new BigDecimal("1200.00"), new BigDecimal("10.00"),
                "https://test.com/dodge.png");

        mockMvc.perform(post("/api/v1/cars")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carRequest)))
                .andExpect(status().isCreated());
    }
}