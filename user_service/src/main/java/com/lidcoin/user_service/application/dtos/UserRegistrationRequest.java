package com.lidcoin.user_service.application.dto;

import com.lidcoin.user_service.domain.enums.UserType;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class UserRegistrationRequest {

    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    @Size(min = 3, max = 50, message = "Le nom d'utilisateur doit contenir entre 3 et 50 caractères")
    private String username;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
            message = "Le mot de passe doit contenir au moins une majuscule, une minuscule, un chiffre et un caractère spécial"
    )
    private String password;

    @NotBlank(message = "Le prénom est obligatoire")
    private String firstName;

    @NotBlank(message = "Le nom de famille est obligatoire")
    private String lastName;

    @Pattern(regexp = "^\\+[1-9][0-9]{7,14}$", message = "Format de téléphone invalide (E.164, ex: +228XXXXXXXX)")
    private String phoneNumber;

    private LocalDate dateOfBirth;
    private String nationalId;

    @NotNull(message = "Le type d'utilisateur est obligatoire")
    private UserType userType;

    // Champs pour les organisations
    private String organizationName;
    private String organizationType;
    private String registrationNumber;
    private String taxId;
    private String address;
    private String city;
    private String country;

    // Getters et Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

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