package com.lidcoin.user_service.infrastructure.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "blockchain-service", url = "${blockchain-service.url:http://localhost:8082}")
public interface BlockchainServiceClient {

    @PostMapping("/api/blockchain/register-user")
    ResponseEntity<Map<String, Object>> registerUserOnBlockchain(@RequestBody Map<String, Object> userData);

    @GetMapping("/api/blockchain/user/{userId}")
    ResponseEntity<Map<String, Object>> getUserBlockchainInfo(@PathVariable Long userId);

    @PostMapping("/api/blockchain/verify-kyc")
    ResponseEntity<Map<String, Object>> verifyKycOnBlockchain(@RequestBody Map<String, Object> kycData);

    @GetMapping("/api/blockchain/transactions/user/{userId}")
    ResponseEntity<Map<String, Object>> getUserTransactions(@PathVariable Long userId);
}