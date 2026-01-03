package com.lidcoin.user_service.application.services;

import com.lidcoin.user_service.domain.model.User;
import com.lidcoin.user_service.infrastructure.feign.BlockchainServiceClient;
import com.lidcoin.user_service.infrastructure.feign.WalletServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserIntegrationService {

    @Autowired
    private WalletServiceClient walletServiceClient;

    @Autowired
    private BlockchainServiceClient blockchainServiceClient;

    /**
     * Crée un wallet pour un utilisateur nouvellement enregistré
     */
    public Map<String, Object> createUserWallet(User user) {
        try {
            Map<String, Object> walletData = new HashMap<>();
            walletData.put("userId", user.getId());
            walletData.put("username", user.getUsername());
            walletData.put("email", user.getEmail());
            walletData.put("userType", user.getUserType());

            ResponseEntity<Map<String, Object>> response = walletServiceClient.createWallet(walletData);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création du wallet: " + e.getMessage());
        }
    }

    /**
     * Enregistre un utilisateur sur la blockchain
     */
    public Map<String, Object> registerUserOnBlockchain(User user) {
        try {
            Map<String, Object> userData = new HashMap<>();
            userData.put("userId", user.getId());
            userData.put("username", user.getUsername());
            userData.put("email", user.getEmail());
            userData.put("userType", user.getUserType());
            userData.put("kycVerified", user.getKycVerified());
            userData.put("kycLevel", user.getKycLevel());

            ResponseEntity<Map<String, Object>> response =
                    blockchainServiceClient.registerUserOnBlockchain(userData);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'enregistrement sur la blockchain: " + e.getMessage());
        }
    }

    /**
     * Vérifie le KYC sur la blockchain
     */
    public Map<String, Object> verifyKycOnBlockchain(Long userId, Integer kycLevel, Boolean verified) {
        try {
            Map<String, Object> kycData = new HashMap<>();
            kycData.put("userId", userId);
            kycData.put("kycLevel", kycLevel);
            kycData.put("verified", verified);

            ResponseEntity<Map<String, Object>> response =
                    blockchainServiceClient.verifyKycOnBlockchain(kycData);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la vérification KYC sur la blockchain: " + e.getMessage());
        }
    }

    /**
     * Gèle le wallet d'un utilisateur
     */
    public Map<String, Object> freezeUserWallet(String walletId) {
        try {
            ResponseEntity<Map<String, Object>> response = walletServiceClient.freezeWallet(walletId);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du gel du wallet: " + e.getMessage());
        }
    }

    /**
     * Dégèle le wallet d'un utilisateur
     */
    public Map<String, Object> unfreezeUserWallet(String walletId) {
        try {
            ResponseEntity<Map<String, Object>> response = walletServiceClient.unfreezeWallet(walletId);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du dégel du wallet: " + e.getMessage());
        }
    }

    /**
     * Récupère le wallet d'un utilisateur
     */
    public Map<String, Object> getUserWallet(Long userId) {
        try {
            ResponseEntity<Map<String, Object>> response = walletServiceClient.getWalletByUserId(userId);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération du wallet: " + e.getMessage());
        }
    }

    /**
     * Récupère les transactions blockchain d'un utilisateur
     */
    public Map<String, Object> getUserBlockchainTransactions(Long userId) {
        try {
            ResponseEntity<Map<String, Object>> response =
                    blockchainServiceClient.getUserTransactions(userId);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des transactions: " + e.getMessage());
        }
    }
}