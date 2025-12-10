package com.lidcoin.user_service.infrastructure.security;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "security-service", url = "${security-service.url:http://localhost:8081}")
public interface SecurityServiceClient {

    /**
     * Valider un token JWT
     */
    @PostMapping("/api/auth/validate")
    Map<String, Object> validateToken(@RequestParam String token);

    /**
     * Générer un token JWT pour un utilisateur
     */
    @PostMapping("/api/auth/generate-token")
    Map<String, Object> generateToken(
            @RequestParam String userId,
            @RequestParam String username,
            @RequestParam String roles
    );
}