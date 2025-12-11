package com.lidcoin.user_service.application.services;

import com.lidcoin.user_service.application.dtos.LoginRequest;
import com.lidcoin.user_service.domain.model.User;
import com.lidcoin.user_service.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public AuthenticationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    public Map<String, Object> authenticate(LoginRequest loginRequest) {
        // Trouver l'utilisateur par username ou email
        Optional<User> userOpt = userRepository.findByUsername(loginRequest.getUsernameOrEmail());

        if (userOpt.isEmpty()) {
            userOpt = userRepository.findByEmail(loginRequest.getUsernameOrEmail());
        }

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Identifiants invalides");
        }

        User user = userOpt.get();

        // Vérifier si le compte est verrouillé
        if (!user.isAccountNonLocked()) {
            throw new RuntimeException("Compte verrouillé. Réessayez plus tard.");
        }

        // Vérifier le statut du compte
        if (!user.getStatus().canLogin()) {
            throw new RuntimeException("Compte inactif ou suspendu");
        }

        // Vérifier le mot de passe
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            userService.recordLoginAttempt(user.getUsername(), false);
            throw new RuntimeException("Identifiants invalides");
        }

        // Vérifier la 2FA si activée
        if (user.getTwoFactorEnabled()) {
            if (loginRequest.getTwoFactorCode() == null || loginRequest.getTwoFactorCode().isEmpty()) {
                throw new RuntimeException("Code 2FA requis");
            }
            // TODO: Implémenter la vérification du code 2FA
        }

        // Enregistrer la connexion réussie
        userService.recordLoginAttempt(user.getUsername(), true);

        // Générer un token simple (remplacer par JWT plus tard)
        String simpleToken = UUID.randomUUID().toString();

        String roles = user.getRoles().stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));

        return Map.of(
                "success", true,
                "userId", user.getId(),
                "username", user.getUsername(),
                "email", user.getEmail(),
                "roles", roles,
                "token", simpleToken,
                "message", "Authentification réussie"
        );
    }

    public void requestPasswordReset(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            // Ne pas révéler si l'email existe ou non pour des raisons de sécurité
            return;
        }

        User user = userOpt.get();
        String resetToken = UUID.randomUUID().toString();

        user.setPasswordResetToken(resetToken);
        user.setPasswordResetTokenExpiry(LocalDateTime.now().plusHours(24));

        userRepository.save(user);

        // TODO: Envoyer l'email avec le lien de réinitialisation
        System.out.println("Password reset token for " + email + ": " + resetToken);
    }

    public void resetPassword(String token, String newPassword) {
        Optional<User> userOpt = userRepository.findByPasswordResetToken(token);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Token de réinitialisation invalide");
        }

        User user = userOpt.get();

        if (user.getPasswordResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token de réinitialisation expiré");
        }

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        user.setPasswordResetToken(null);
        user.setPasswordResetTokenExpiry(null);

        userRepository.save(user);
    }

    public void changePassword(Long userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (!passwordEncoder.matches(oldPassword, user.getPasswordHash())) {
            throw new RuntimeException("Ancien mot de passe incorrect");
        }

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public Map<String, Object> validateToken(String token) {
        // Validation simple (à remplacer par JWT)
        return Map.of(
                "valid", true,
                "token", token,
                "message", "Token valide (validation simple)"
        );
    }
}