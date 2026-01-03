package com.lidcoin.user_service.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lidcoin.user_service.application.dto.LoginRequest;
import com.lidcoin.user_service.application.dto.PasswordResetRequest;
import com.lidcoin.user_service.application.services.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthenticationService authenticationService;

    private String validToken;

    @BeforeEach
    public void setup() {
        // Créer un token valide pour les tests
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsernameOrEmail("testuser");
        loginRequest.setPassword("Test@123");

        try {
            Map<String, Object> response = authenticationService.authenticate(loginRequest);
            validToken = (String) response.get("token");
        } catch (Exception e) {
            // Ignorer si l'utilisateur n'existe pas encore
        }
    }

    @Test
    public void testLoginWithValidCredentials() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsernameOrEmail("testuser");
        loginRequest.setPassword("Test@123");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.userId").exists())
                .andExpect(jsonPath("$.roles").exists());
    }

    @Test
    public void testLoginWithInvalidPassword() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsernameOrEmail("testuser");
        loginRequest.setPassword("WrongPassword");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void testLoginWithNonExistentUser() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsernameOrEmail("nonexistent");
        loginRequest.setPassword("Test@123");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Identifiants invalides"));
    }

    @Test
    public void testLoginWithEmail() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsernameOrEmail("test@lidcoin.com");
        loginRequest.setPassword("Test@123");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    public void testLoginWithMissingUsername() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("Test@123");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testLoginWithMissingPassword() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsernameOrEmail("testuser");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPasswordResetRequest() throws Exception {
        PasswordResetRequest request = new PasswordResetRequest();
        request.setEmail("test@lidcoin.com");

        mockMvc.perform(post("/api/auth/password-reset/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void testPasswordResetRequestWithInvalidEmail() throws Exception {
        PasswordResetRequest request = new PasswordResetRequest();
        request.setEmail("invalid-email");

        mockMvc.perform(post("/api/auth/password-reset/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testValidateValidToken() throws Exception {
        if (validToken != null) {
            mockMvc.perform(post("/api/auth/validate")
                            .param("token", validToken))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.valid").value(true))
                    .andExpect(jsonPath("$.username").exists())
                    .andExpect(jsonPath("$.userId").exists())
                    .andExpect(jsonPath("$.roles").exists());
        }
    }

    @Test
    public void testValidateInvalidToken() throws Exception {
        mockMvc.perform(post("/api/auth/validate")
                        .param("token", "invalid.token.here"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    public void testChangePassword() throws Exception {
        if (validToken != null) {
            mockMvc.perform(post("/api/auth/password/change")
                            .header("Authorization", "Bearer " + validToken)
                            .param("userId", "3")
                            .param("oldPassword", "Test@123")
                            .param("newPassword", "NewPass@123"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success").value(true));
        }
    }

    @Test
    public void testChangePasswordWithWrongOldPassword() throws Exception {
        if (validToken != null) {
            mockMvc.perform(post("/api/auth/password/change")
                            .header("Authorization", "Bearer " + validToken)
                            .param("userId", "3")
                            .param("oldPassword", "WrongPassword")
                            .param("newPassword", "NewPass@123"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }
    }
}