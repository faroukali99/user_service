package com.lidcoin.user_service.application.controller;

import com.lidcoin.user_service.application.dto.UserDTO;
import com.lidcoin.user_service.application.dto.UserRegistrationRequest;
import com.lidcoin.user_service.application.services.UserService;
import com.lidcoin.user_service.domain.enums.UserRole;
import com.lidcoin.user_service.domain.enums.UserStatus;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        try {
            UserDTO userDTO = userService.registerUser(request);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Utilisateur créé avec succès");
            response.put("user", userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            UserDTO userDTO = userService.getUserById(id);
            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        try {
            UserDTO userDTO = userService.getUserByUsername(username);
            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        try {
            UserDTO userDTO = userService.getUserByEmail(email);
            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        try {
            UserDTO updatedUser = userService.updateUser(id, userDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Utilisateur mis à jour avec succès");
            response.put("user", updatedUser);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Utilisateur supprimé avec succès");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestParam String token) {
        try {
            UserDTO userDTO = userService.verifyEmail(token);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Email vérifié avec succès");
            response.put("user", userDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PutMapping("/{id}/kyc")
    public ResponseEntity<?> updateKycStatus(
            @PathVariable Long id,
            @RequestParam Integer kycLevel,
            @RequestParam Boolean verified) {
        try {
            userService.updateKycStatus(id, kycLevel, verified);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Statut KYC mis à jour avec succès");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateUserStatus(
            @PathVariable Long id,
            @RequestParam UserStatus status) {
        try {
            userService.updateUserStatus(id, status);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Statut utilisateur mis à jour avec succès");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping("/{id}/roles")
    public ResponseEntity<?> addRole(
            @PathVariable Long id,
            @RequestParam UserRole role) {
        try {
            userService.addRoleToUser(id, role);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Rôle ajouté avec succès");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}/roles")
    public ResponseEntity<?> removeRole(
            @PathVariable Long id,
            @RequestParam UserRole role) {
        try {
            userService.removeRoleFromUser(id, role);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Rôle retiré avec succès");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}