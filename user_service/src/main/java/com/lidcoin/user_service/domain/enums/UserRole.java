package com.lidcoin.user_service.domain.enums;

public enum UserRole {
    /**
     * Utilisateur standard
     */
    USER("User", "Standard user with basic permissions", 1),

    /**
     * Trader vérifié
     */
    TRADER("Trader", "Verified trader with enhanced trading permissions", 2),

    /**
     * Validateur de blockchain
     */
    VALIDATOR("Validator", "Blockchain validator node operator", 3),

    /**
     * Administrateur
     */
    ADMIN("Administrator", "System administrator with elevated privileges", 10),

    /**
     * Super administrateur
     */
    SUPER_ADMIN("Super Administrator", "Super administrator with full system access", 100),

    /**
     * Modérateur
     */
    MODERATOR("Moderator", "Community moderator", 5),

    /**
     * Support client
     */
    SUPPORT("Support", "Customer support agent", 4),

    /**
     * Marchand
     */
    MERCHANT("Merchant", "Merchant with payment gateway access", 3),

    /**
     * Utilisateur API
     */
    API_USER("API User", "User with API access", 2);

    private final String displayName;
    private final String description;
    private final int level;

    UserRole(String displayName, String description, int level) {
        this.displayName = displayName;
        this.description = description;
        this.level = level;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public int getLevel() {
        return level;
    }

    public boolean hasHigherLevelThan(UserRole other) {
        return this.level > other.level;
    }

    public boolean isAdmin() {
        return this == ADMIN || this == SUPER_ADMIN;
    }

    public boolean canManageUsers() {
        return this == ADMIN || this == SUPER_ADMIN || this == MODERATOR;
    }
}