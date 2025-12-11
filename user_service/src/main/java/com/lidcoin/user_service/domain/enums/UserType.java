package com.lidcoin.user_service.domain.enums;

/**
 * Énumération représentant les types d'utilisateurs dans le système.
 * Cette énumération est utilisée pour différencier les utilisateurs individuels
 * des organisations dans le système.
 */
public enum UserType {
    /**
     * Utilisateur individuel - Un utilisateur standard avec un compte personnel
     */
    INDIVIDUAL,
    
    /**
     * Organisation - Une entité telle qu'une banque ou une institution financière
     */
    ORGANIZATION
}