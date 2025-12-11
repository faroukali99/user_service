package com.lidcoin.user_service.application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    @GetMapping
    public ResponseEntity<?> health() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "user-service");
        health.put("timestamp", LocalDateTime.now());

        return ResponseEntity.ok(health);
    }

    @GetMapping("/ready")
    public ResponseEntity<?> ready() {
        Map<String, Object> readiness = new HashMap<>();
        readiness.put("status", "READY");
        readiness.put("service", "user-service");

        return ResponseEntity.ok(readiness);
    }

    @GetMapping("/live")
    public ResponseEntity<?> live() {
        Map<String, Object> liveness = new HashMap<>();
        liveness.put("status", "ALIVE");
        liveness.put("service", "user-service");

        return ResponseEntity.ok(liveness);
    }
}