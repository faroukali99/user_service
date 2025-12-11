package com.lidcoin.user_service.infrastructure.config;

import com.lidcoin.user_service.domain.enums.UserRole;
import com.lidcoin.user_service.domain.enums.UserStatus;
import com.lidcoin.user_service.domain.enums.UserType;
import com.lidcoin.user_service.domain.model.User;
import com.lidcoin.user_service.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Vérifier si des utilisateurs existent déjà
        if (userRepository.count() == 0) {
            createDefaultUsers();
        }
    }

    private void createDefaultUsers() {
        // Créer un super admin
        User superAdmin = new User();
        superAdmin.setUsername("superadmin");
        superAdmin.setEmail("superadmin@lidcoin.com");
        superAdmin.setPasswordHash(passwordEncoder.encode("Admin@123"));
        superAdmin.setFirstName("Super");
        superAdmin.setLastName("Admin");
        superAdmin.setPhoneNumber("+22890000000");
        superAdmin.setUserType(UserType.INDIVIDUAL);
        superAdmin.setStatus(UserStatus.ACTIVE);
        superAdmin.setEmailVerified(true);
        superAdmin.setKycVerified(true);
        superAdmin.setKycLevel(3);
        superAdmin.setKycVerificationDate(LocalDateTime.now());
        superAdmin.getRoles().add(UserRole.SUPER_ADMIN);
        superAdmin.getRoles().add(UserRole.ADMIN);
        superAdmin.setCreatedDate(LocalDateTime.now());
        userRepository.save(superAdmin);

        // Créer un admin
        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@lidcoin.com");
        admin.setPasswordHash(passwordEncoder.encode("Admin@123"));
        admin.setFirstName("Admin");
        admin.setLastName("User");
        admin.setPhoneNumber("+22890000001");
        admin.setUserType(UserType.INDIVIDUAL);
        admin.setStatus(UserStatus.ACTIVE);
        admin.setEmailVerified(true);
        admin.setKycVerified(true);
        admin.setKycLevel(3);
        admin.setKycVerificationDate(LocalDateTime.now());
        admin.getRoles().add(UserRole.ADMIN);
        admin.setCreatedDate(LocalDateTime.now());
        userRepository.save(admin);

        // Créer un utilisateur test
        User testUser = new User();
        testUser.setUsername("testuser");
        testUser.setEmail("test@lidcoin.com");
        testUser.setPasswordHash(passwordEncoder.encode("Test@123"));
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setPhoneNumber("+22890000002");
        testUser.setDateOfBirth(LocalDate.of(1990, 1, 1));
        testUser.setUserType(UserType.INDIVIDUAL);
        testUser.setStatus(UserStatus.ACTIVE);
        testUser.setEmailVerified(true);
        testUser.setKycVerified(false);
        testUser.setKycLevel(0);
        testUser.getRoles().add(UserRole.USER);
        testUser.setCreatedDate(LocalDateTime.now());
        userRepository.save(testUser);

        // Créer un trader
        User trader = new User();
        trader.setUsername("trader");
        trader.setEmail("trader@lidcoin.com");
        trader.setPasswordHash(passwordEncoder.encode("Trader@123"));
        trader.setFirstName("Trader");
        trader.setLastName("Pro");
        trader.setPhoneNumber("+22890000003");
        trader.setUserType(UserType.INDIVIDUAL);
        trader.setStatus(UserStatus.ACTIVE);
        trader.setEmailVerified(true);
        trader.setKycVerified(true);
        trader.setKycLevel(2);
        trader.setKycVerificationDate(LocalDateTime.now());
        trader.getRoles().add(UserRole.USER);
        trader.getRoles().add(UserRole.TRADER);
        trader.setCreatedDate(LocalDateTime.now());
        userRepository.save(trader);

        // Créer une organisation (banque)
        User bank = new User();
        bank.setUsername("commercial_bank");
        bank.setEmail("bank@example.com");
        bank.setPasswordHash(passwordEncoder.encode("Bank@123"));
        bank.setFirstName("Commercial");
        bank.setLastName("Bank");
        bank.setPhoneNumber("+22890000004");
        bank.setUserType(UserType.ORGANIZATION);
        bank.setOrganizationName("Banque Commerciale du Togo");
        bank.setOrganizationType("COMMERCIAL_BANK");
        bank.setRegistrationNumber("BCT-2024-001");
        bank.setTaxId("TG123456789");
        bank.setAddress("123 Avenue de la Libération");
        bank.setCity("Lomé");
        bank.setCountry("Togo");
        bank.setStatus(UserStatus.ACTIVE);
        bank.setEmailVerified(true);
        bank.setKycVerified(true);
        bank.setKycLevel(3);
        bank.setKycVerificationDate(LocalDateTime.now());
        bank.getRoles().add(UserRole.MERCHANT);
        bank.getRoles().add(UserRole.API_USER);
        bank.setCreatedDate(LocalDateTime.now());
        userRepository.save(bank);

        System.out.println("=================================================");
        System.out.println("Utilisateurs par défaut créés avec succès:");
        System.out.println("- Super Admin: superadmin / Admin@123");
        System.out.println("- Admin: admin / Admin@123");
        System.out.println("- Test User: testuser / Test@123");
        System.out.println("- Trader: trader / Trader@123");
        System.out.println("- Bank: commercial_bank / Bank@123");
        System.out.println("=================================================");
    }
}