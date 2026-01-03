package com.lidcoin.user_service.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Objet de transfert de données pour la demande de réinitialisation de mot de passe.
 * Utilisé pour capturer l'adresse email de l'utilisateur qui souhaite réinitialiser son mot de passe.
 * L'email est validé pour s'assurer qu'il n'est pas vide et qu'il suit un format valide.
 */
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

/**
 * Objet de transfert de données pour la confirmation de réinitialisation de mot de passe.
 * Contient le token de réinitialisation et le nouveau mot de passe.
 * Le mot de passe est validé pour s'assurer qu'il respecte les critères de sécurité requis.
 */
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