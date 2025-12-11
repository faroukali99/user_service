package com.lidcoin.user_service.application.dto;

import com.lidcoin.user_service.domain.enums.UserRole;
import com.lidcoin.user_service.domain.enums.UserStatus;
import com.lidcoin.user_service.domain.enums.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class UserDTO {

    private Long id;

    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    @Size(min = 3, max = 50, message = "Le nom d'utilisateur doit contenir entre 3 et 50 caractères")
    private String username;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email;

    @NotBlank(message = "Le prénom est obligatoire")
    private String firstName;

    @NotBlank(message = "Le nom de famille est obligatoire")
    private String lastName;

    @Pattern(regexp = "^\\+[1-9][0-9]{7,14}$", message = "Format de téléphone invalide (E.164, ex: +228XXXXXXXX)")
    private String phoneNumber;

    private LocalDate dateOfBirth;
    private String nationalId;
    private UserType userType;
    private UserStatus status;
    private Boolean kycVerified;
    private LocalDateTime kycVerificationDate;
    private Integer kycLevel;
    private List<UserRole> roles;
    private LocalDateTime createdDate;
    private LocalDateTime lastLoginDate;
    private Boolean emailVerified;
    private Boolean twoFactorEnabled;

    // Champs organisationnels
    private String organizationName;
    private String organizationType;
    private String registrationNumber;
    private String taxId;
    private String address;
    private String city;
    private String country;

    // Constructeurs
    public UserDTO() {}

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getNationalId() { return nationalId; }
    public void setNationalId(String nationalId) { this.nationalId = nationalId; }

    public UserType getUserType() { return userType; }
    public void setUserType(UserType userType) { this.userType = userType; }

    public UserStatus getStatus() { return status; }
    public void setStatus(UserStatus status) { this.status = status; }

    public Boolean getKycVerified() { return kycVerified; }
    public void setKycVerified(Boolean kycVerified) { this.kycVerified = kycVerified; }

    public LocalDateTime getKycVerificationDate() { return kycVerificationDate; }
    public void setKycVerificationDate(LocalDateTime kycVerificationDate) { this.kycVerificationDate = kycVerificationDate; }

    public Integer getKycLevel() { return kycLevel; }
    public void setKycLevel(Integer kycLevel) { this.kycLevel = kycLevel; }

    public List<UserRole> getRoles() { return roles; }
    public void setRoles(List<UserRole> roles) { this.roles = roles; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public LocalDateTime getLastLoginDate() { return lastLoginDate; }
    public void setLastLoginDate(LocalDateTime lastLoginDate) { this.lastLoginDate = lastLoginDate; }

    public Boolean getEmailVerified() { return emailVerified; }
    public void setEmailVerified(Boolean emailVerified) { this.emailVerified = emailVerified; }

    public Boolean getTwoFactorEnabled() { return twoFactorEnabled; }
    public void setTwoFactorEnabled(Boolean twoFactorEnabled) { this.twoFactorEnabled = twoFactorEnabled; }

    public String getOrganizationName() { return organizationName; }
    public void setOrganizationName(String organizationName) { this.organizationName = organizationName; }

    public String getOrganizationType() { return organizationType; }
    public void setOrganizationType(String organizationType) { this.organizationType = organizationType; }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }

    public String getTaxId() { return taxId; }
    public void setTaxId(String taxId) { this.taxId = taxId; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
}