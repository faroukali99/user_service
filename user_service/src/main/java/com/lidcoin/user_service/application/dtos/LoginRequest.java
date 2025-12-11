package com.lidcoin.user_service.application.dtos;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "Le nom d'utilisateur ou l'email est obligatoire")
    private String usernameOrEmail;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password;

    private String twoFactorCode;

    // Constructeurs
    public LoginRequest() {}

    public LoginRequest(String usernameOrEmail, String password) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
    }

    // Getters et Setters
    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTwoFactorCode() {
        return twoFactorCode;
    }

    public void setTwoFactorCode(String twoFactorCode) {
        this.twoFactorCode = twoFactorCode;
    }
}