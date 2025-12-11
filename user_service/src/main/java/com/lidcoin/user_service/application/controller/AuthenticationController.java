package com.lidcoin.user_service.application.controller;

import com.lidcoin.user_service.application.dtos.LoginRequest;
import com.lidcoin.user_service.application.dtos.PasswordResetRequest;
import com.lidcoin.user_service.application.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Map<String, Object> response = authenticationService.authenticate(loginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    @PostMapping("/password-reset/request")
    public ResponseEntity<?> requestPasswordReset(@Valid @RequestBody PasswordResetRequest request) {
        try {
            authenticationService.requestPasswordReset(request.getEmail());
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Si cet email existe, un lien de réinitialisation a été envoyé");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping("/password-reset/confirm")
    public ResponseEntity<?> confirmPasswordReset(
            @RequestParam String token,
            @RequestParam String newPassword) {
        try {
            authenticationService.resetPassword(token, newPassword);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Mot de passe réinitialisé avec succès");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping("/password/change")
    public ResponseEntity<?> changePassword(
            @RequestParam Long userId,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        try {
            authenticationService.changePassword(userId, oldPassword, newPassword);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Mot de passe changé avec succès");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestParam String token) {
        try {
            Map<String, Object> response = authenticationService.validateToken(token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }
}