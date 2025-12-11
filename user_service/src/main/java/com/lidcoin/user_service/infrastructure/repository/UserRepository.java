package com.lidcoin.user_service.infrastructure.repository;

import com.lidcoin.user_service.domain.enums.UserStatus;
import com.lidcoin.user_service.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailVerificationToken(String token);

    Optional<User> findByPasswordResetToken(String token);

    List<User> findByStatus(UserStatus status);

    List<User> findByKycVerified(Boolean kycVerified);

    @Query("SELECT u FROM User u WHERE u.kycLevel >= :level")
    List<User> findByKycLevelGreaterThanEqual(@Param("level") Integer level);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.accountLocked = true AND u.accountLockedUntil < CURRENT_TIMESTAMP")
    List<User> findExpiredLockedAccounts();
}