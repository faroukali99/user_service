package com.lidcoin.user_service.application.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PasswordResetRequest {

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email;

    // Constructeurs
    public PasswordResetRequest() {}

    public PasswordResetRequest(String email) {
        this.email = email;
    }

    // Getters et Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

class PasswordResetConfirmRequest {

    @NotBlank(message = "Le token est obligatoire")
    private String token;

    @NotBlank(message = "Le nouveau mot de passe est obligatoire")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
            message = "Le mot de passe doit contenir au moins une majuscule, une minuscule, un chiffre et un caractère spécial"
    )
    private String newPassword;

    // Constructeurs
    public PasswordResetConfirmRequest() {}

    // Getters et Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}