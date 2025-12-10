package com.lidcoin.user_service.domain.enums;

public enum UserStatus {
    /**
     * Compte actif et opérationnel
     */
    ACTIVE("Active", "User account is active and operational"),

    /**
     * Compte inactif (temporairement désactivé par l'utilisateur)
     */
    INACTIVE("Inactive", "User account is temporarily inactive"),

    /**
     * Compte suspendu (par l'administration)
     */
    SUSPENDED("Suspended", "User account has been suspended"),

    /**
     * Compte banni (violation des termes)
     */
    BANNED("Banned", "User account has been permanently banned"),

    /**
     * En attente de vérification
     */
    PENDING_VERIFICATION("Pending Verification", "User account is awaiting verification"),

    /**
     * Compte supprimé (soft delete)
     */
    DELETED("Deleted", "User account has been deleted");

    private final String displayName;
    private final String description;

    UserStatus(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return this == ACTIVE;
    }

    public boolean canLogin() {
        return this == ACTIVE || this == INACTIVE;
    }
}

