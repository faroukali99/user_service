package com.lidcoin.user_service.application.dtos;

import com.lidcoin.user_service.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public com.lidcoin.user_service.application.dto.UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        com.lidcoin.user_service.application.dto.UserDTO dto = new com.lidcoin.user_service.application.dto.UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setNationalId(user.getNationalId());
        dto.setUserType(user.getUserType());
        dto.setStatus(user.getStatus());
        dto.setKycVerified(user.getKycVerified());
        dto.setKycVerificationDate(user.getKycVerificationDate());
        dto.setKycLevel(user.getKycLevel());
        dto.setRoles(user.getRoles());
        dto.setCreatedDate(user.getCreatedDate());
        dto.setLastLoginDate(user.getLastLoginDate());
        dto.setEmailVerified(user.getEmailVerified());
        dto.setTwoFactorEnabled(user.getTwoFactorEnabled());
        dto.setOrganizationName(user.getOrganizationName());
        dto.setOrganizationType(user.getOrganizationType());
        dto.setRegistrationNumber(user.getRegistrationNumber());
        dto.setTaxId(user.getTaxId());
        dto.setAddress(user.getAddress());
        dto.setCity(user.getCity());
        dto.setCountry(user.getCountry());

        return dto;
    }

    public User toEntity(com.lidcoin.user_service.application.dto.UserDTO dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setNationalId(dto.getNationalId());
        user.setUserType(dto.getUserType());
        user.setStatus(dto.getStatus());
        user.setKycVerified(dto.getKycVerified());
        user.setKycVerificationDate(dto.getKycVerificationDate());
        user.setKycLevel(dto.getKycLevel());
        user.setRoles(dto.getRoles());
        user.setCreatedDate(dto.getCreatedDate());
        user.setLastLoginDate(dto.getLastLoginDate());
        user.setEmailVerified(dto.getEmailVerified());
        user.setTwoFactorEnabled(dto.getTwoFactorEnabled());
        user.setOrganizationName(dto.getOrganizationName());
        user.setOrganizationType(dto.getOrganizationType());
        user.setRegistrationNumber(dto.getRegistrationNumber());
        user.setTaxId(dto.getTaxId());
        user.setAddress(dto.getAddress());
        user.setCity(dto.getCity());
        user.setCountry(dto.getCountry());

        return user;
    }

    public User registrationRequestToEntity(com.lidcoin.user_service.application.dto.UserRegistrationRequest request) {
        if (request == null) {
            return null;
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setNationalId(request.getNationalId());
        user.setUserType(request.getUserType());
        user.setOrganizationName(request.getOrganizationName());
        user.setOrganizationType(request.getOrganizationType());
        user.setRegistrationNumber(request.getRegistrationNumber());
        user.setTaxId(request.getTaxId());
        user.setAddress(request.getAddress());
        user.setCity(request.getCity());
        user.setCountry(request.getCountry());

        return user;
    }
}