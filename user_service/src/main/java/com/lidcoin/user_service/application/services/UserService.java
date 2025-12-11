package com.lidcoin.user_service.application.services;

import com.lidcoin.user_service.application.dto.UserDTO;
import com.lidcoin.user_service.application.dtos.UserMapper;
import com.lidcoin.user_service.application.dto.UserRegistrationRequest;
import com.lidcoin.user_service.domain.enums.UserRole;
import com.lidcoin.user_service.domain.enums.UserStatus;
import com.lidcoin.user_service.domain.model.User;
import com.lidcoin.user_service.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO registerUser(UserRegistrationRequest request) {
        // Vérifier si l'utilisateur existe déjà
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Le nom d'utilisateur existe déjà");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("L'email existe déjà");
        }

        // Créer un nouvel utilisateur
        User user = userMapper.registrationRequestToEntity(request);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setStatus(UserStatus.PENDING_VERIFICATION);
        user.setEmailVerificationToken(UUID.randomUUID().toString());
        user.getRoles().add(UserRole.USER);
        user.setCreatedDate(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        // TODO: Envoyer un email de vérification

        return userMapper.toDTO(savedUser);
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID: " + id));
        return userMapper.toDTO(user);
    }

    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé: " + username));
        return userMapper.toDTO(user);
    }

    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'email: " + email));
        return userMapper.toDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID: " + id));

        // Mettre à jour les champs modifiables
        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setLastName(userDTO.getLastName());
        existingUser.setPhoneNumber(userDTO.getPhoneNumber());
        existingUser.setDateOfBirth(userDTO.getDateOfBirth());
        existingUser.setAddress(userDTO.getAddress());
        existingUser.setCity(userDTO.getCity());
        existingUser.setCountry(userDTO.getCountry());

        User updatedUser = userRepository.save(existingUser);
        return userMapper.toDTO(updatedUser);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID: " + id));

        // Soft delete
        user.setStatus(UserStatus.DELETED);
        userRepository.save(user);
    }

    public UserDTO verifyEmail(String token) {
        User user = userRepository.findByEmailVerificationToken(token)
                .orElseThrow(() -> new RuntimeException("Token de vérification invalide"));

        user.setEmailVerified(true);
        user.setEmailVerificationToken(null);
        user.setStatus(UserStatus.ACTIVE);

        User verifiedUser = userRepository.save(user);
        return userMapper.toDTO(verifiedUser);
    }

    public void updateKycStatus(Long userId, Integer kycLevel, Boolean verified) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID: " + userId));

        user.setKycLevel(kycLevel);
        user.setKycVerified(verified);

        if (verified) {
            user.setKycVerificationDate(LocalDateTime.now());
        }

        userRepository.save(user);
    }

    public void updateUserStatus(Long userId, UserStatus status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID: " + userId));

        user.setStatus(status);
        userRepository.save(user);
    }

    public void addRoleToUser(Long userId, UserRole role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID: " + userId));

        if (!user.getRoles().contains(role)) {
            user.getRoles().add(role);
            userRepository.save(user);
        }
    }

    public void removeRoleFromUser(Long userId, UserRole role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID: " + userId));

        user.getRoles().remove(role);
        userRepository.save(user);
    }

    public void recordLoginAttempt(String username, boolean success) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (success) {
                user.resetFailedLoginAttempts();
                user.setLastLoginDate(LocalDateTime.now());
            } else {
                user.incrementFailedLoginAttempts();
            }

            userRepository.save(user);
        }
    }

    public boolean isAccountLocked(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        return userOpt.map(user -> !user.isAccountNonLocked()).orElse(false);
    }
}