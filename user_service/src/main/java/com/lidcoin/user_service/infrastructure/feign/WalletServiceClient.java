package com.lidcoin.user_service.infrastructure.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "wallet-service", url = "${wallet-service.url:http://localhost:8083}")
public interface WalletServiceClient {

    @PostMapping("/api/wallets/create")
    ResponseEntity<Map<String, Object>> createWallet(@RequestBody Map<String, Object> walletData);

    @GetMapping("/api/wallets/user/{userId}")
    ResponseEntity<Map<String, Object>> getWalletByUserId(@PathVariable Long userId);

    @GetMapping("/api/wallets/{walletId}/balance")
    ResponseEntity<Map<String, Object>> getWalletBalance(@PathVariable String walletId);

    @PostMapping("/api/wallets/{walletId}/freeze")
    ResponseEntity<Map<String, Object>> freezeWallet(@PathVariable String walletId);

    @PostMapping("/api/wallets/{walletId}/unfreeze")
    ResponseEntity<Map<String, Object>> unfreezeWallet(@PathVariable String walletId);
}