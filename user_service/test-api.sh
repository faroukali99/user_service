#!/bin/bash

# Script de test automatisé pour l'API User Service
# Usage: ./test-api.sh

BASE_URL="http://localhost:8081"
GREEN='\033[0;32m'
RED='\033[0;31m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}========================================${NC}"
echo -e "${BLUE}  Tests API User Service - LidCoin${NC}"
echo -e "${BLUE}========================================${NC}\n"

# Variables globales
USER_TOKEN=""
ADMIN_TOKEN=""
USER_ID=""
NEW_USER_ID=""

# Fonction pour afficher le résultat d'un test
check_result() {
    if [ $1 -eq 0 ]; then
        echo -e "${GREEN}✓ PASS${NC}: $2"
    else
        echo -e "${RED}✗ FAIL${NC}: $2"
    fi
}

# Fonction pour extraire une valeur JSON
extract_json() {
    echo "$1" | grep -o "\"$2\":[^,}]*" | sed 's/"[^"]*"://g' | tr -d '"'
}

echo -e "${BLUE}1. Tests de santé du service${NC}"
echo "-----------------------------------"

# Test 1: Health Check
response=$(curl -s -o /dev/null -w "%{http_code}" "$BASE_URL/api/health")
[ "$response" = "200" ]
check_result $? "Health Check"

# Test 2: Ready Check
response=$(curl -s -o /dev/null -w "%{http_code}" "$BASE_URL/api/health/ready")
[ "$response" = "200" ]
check_result $? "Ready Check"

# Test 3: Live Check
response=$(curl -s -o /dev/null -w "%{http_code}" "$BASE_URL/api/health/live")
[ "$response" = "200" ]
check_result $? "Live Check"

echo ""
echo -e "${BLUE}2. Tests d'authentification${NC}"
echo "-----------------------------------"

# Test 4: Login avec utilisateur valide
response=$(curl -s -X POST "$BASE_URL/api/auth/login" \
    -H "Content-Type: application/json" \
    -d '{"usernameOrEmail":"testuser","password":"Test@123"}')

USER_TOKEN=$(extract_json "$response" "token")
USER_ID=$(extract_json "$response" "userId")
[ -n "$USER_TOKEN" ]
check_result $? "Login utilisateur valide"

# Test 5: Login avec mot de passe invalide
response=$(curl -s -o /dev/null -w "%{http_code}" -X POST "$BASE_URL/api/auth/login" \
    -H "Content-Type: application/json" \
    -d '{"usernameOrEmail":"testuser","password":"WrongPassword"}')
[ "$response" = "401" ]
check_result $? "Login avec mot de passe invalide (devrait échouer)"

# Test 6: Login admin
response=$(curl -s -X POST "$BASE_URL/api/auth/login" \
    -H "Content-Type: application/json" \
    -d '{"usernameOrEmail":"admin","password":"Admin@123"}')

ADMIN_TOKEN=$(extract_json "$response" "token")
[ -n "$ADMIN_TOKEN" ]
check_result $? "Login administrateur"

# Test 7: Validation de token
response=$(curl -s -X POST "$BASE_URL/api/auth/validate?token=$USER_TOKEN")
valid=$(extract_json "$response" "valid")
[ "$valid" = "true" ]
check_result $? "Validation de token"

# Test 8: Validation de token invalide
response=$(curl -s -o /dev/null -w "%{http_code}" -X POST "$BASE_URL/api/auth/validate?token=invalid.token")
[ "$response" = "401" ]
check_result $? "Validation de token invalide (devrait échouer)"

echo ""
echo -e "${BLUE}3. Tests d'enregistrement d'utilisateur${NC}"
echo "-----------------------------------"

# Test 9: Enregistrement d'un nouvel utilisateur
TIMESTAMP=$(date +%s)
response=$(curl -s -X POST "$BASE_URL/api/users/register" \
    -H "Content-Type: application/json" \
    -d "{
        \"username\":\"testuser$TIMESTAMP\",
        \"email\":\"testuser$TIMESTAMP@example.com\",
        \"password\":\"Test@123\",
        \"firstName\":\"Test\",
        \"lastName\":\"User\",
        \"phoneNumber\":\"+22890123456\",
        \"dateOfBirth\":\"1990-01-01\",
        \"userType\":\"INDIVIDUAL\"
    }")

success=$(extract_json "$response" "success")
NEW_USER_ID=$(extract_json "$response" "id")
[ "$success" = "true" ]
check_result $? "Enregistrement d'un nouvel utilisateur"

# Test 10: Enregistrement avec username existant
response=$(curl -s -o /dev/null -w "%{http_code}" -X POST "$BASE_URL/api/users/register" \
    -H "Content-Type: application/json" \
    -d '{
        "username":"testuser",
        "email":"another@example.com",
        "password":"Test@123",
        "firstName":"Test",
        "lastName":"User",
        "userType":"INDIVIDUAL"
    }')
[ "$response" = "400" ]
check_result $? "Enregistrement avec username existant (devrait échouer)"

# Test 11: Enregistrement avec email invalide
response=$(curl -s -o /dev/null -w "%{http_code}" -X POST "$BASE_URL/api/users/register" \
    -H "Content-Type: application/json" \
    -d '{
        "username":"newuser",
        "email":"invalid-email",
        "password":"Test@123",
        "firstName":"Test",
        "lastName":"User",
        "userType":"INDIVIDUAL"
    }')
[ "$response" = "400" ]
check_result $? "Enregistrement avec email invalide (devrait échouer)"

echo ""
echo -e "${BLUE}4. Tests de gestion des utilisateurs${NC}"
echo "-----------------------------------"

# Test 12: Récupérer un utilisateur par ID
response=$(curl -s -o /dev/null -w "%{http_code}" -X GET "$BASE_URL/api/users/$USER_ID" \
    -H "Authorization: Bearer $USER_TOKEN")
[ "$response" = "200" ]
check_result $? "Récupérer un utilisateur par ID"

# Test 13: Récupérer un utilisateur sans authentification
response=$(curl -s -o /dev/null -w "%{http_code}" -X GET "$BASE_URL/api/users/$USER_ID")
[ "$response" = "401" ]
check_result $? "Récupérer un utilisateur sans authentification (devrait échouer)"

# Test 14: Récupérer un utilisateur par username
response=$(curl -s -o /dev/null -w "%{http_code}" -X GET "$BASE_URL/api/users/username/testuser" \
    -H "Authorization: Bearer $USER_TOKEN")
[ "$response" = "200" ]
check_result $? "Récupérer un utilisateur par username"

# Test 15: Récupérer un utilisateur par email
response=$(curl -s -o /dev/null -w "%{http_code}" -X GET "$BASE_URL/api/users/email/test@lidcoin.com" \
    -H "Authorization: Bearer $USER_TOKEN")
[ "$response" = "200" ]
check_result $? "Récupérer un utilisateur par email"

# Test 16: Récupérer tous les utilisateurs
response=$(curl -s -X GET "$BASE_URL/api/users" \
    -H "Authorization: Bearer $USER_TOKEN")
count=$(echo "$response" | grep -o "\"id\"" | wc -l)
[ "$count" -gt 0 ]
check_result $? "Récupérer tous les utilisateurs"

# Test 17: Mettre à jour un utilisateur
response=$(curl -s -o /dev/null -w "%{http_code}" -X PUT "$BASE_URL/api/users/$USER_ID" \
    -H "Authorization: Bearer $USER_TOKEN" \
    -H "Content-Type: application/json" \
    -d '{
        "firstName":"Updated",
        "lastName":"Name",
        "phoneNumber":"+22899999999"
    }')
[ "$response" = "200" ]
check_result $? "Mettre à jour un utilisateur"

echo ""
echo -e "${BLUE}5. Tests des opérations administrateur${NC}"
echo "-----------------------------------"

# Test 18: Mettre à jour le statut KYC (admin)
response=$(curl -s -o /dev/null -w "%{http_code}" -X PUT "$BASE_URL/api/users/$USER_ID/kyc?kycLevel=2&verified=true" \
    -H "Authorization: Bearer $ADMIN_TOKEN")
[ "$response" = "200" ]
check_result $? "Mettre à jour le statut KYC (admin)"

# Test 19: Mettre à jour le statut KYC (user) - devrait échouer
response=$(curl -s -o /dev/null -w "%{http_code}" -X PUT "$BASE_URL/api/users/$USER_ID/kyc?kycLevel=2&verified=true" \
    -H "Authorization: Bearer $USER_TOKEN")
[ "$response" = "403" ]
check_result $? "Mettre à jour le statut KYC (user - devrait échouer)"

# Test 20: Mettre à jour le statut utilisateur (admin)
response=$(curl -s -o /dev/null -w "%{http_code}" -X PUT "$BASE_URL/api/users/$USER_ID/status?status=ACTIVE" \
    -H "Authorization: Bearer $ADMIN_TOKEN")
[ "$response" = "200" ]
check_result $? "Mettre à jour le statut utilisateur (admin)"

# Test 21: Ajouter un rôle (admin)
response=$(curl -s -o /dev/null -w "%{http_code}" -X POST "$BASE_URL/api/users/$USER_ID/roles?role=TRADER" \
    -H "Authorization: Bearer $ADMIN_TOKEN")
[ "$response" = "200" ]
check_result $? "Ajouter un rôle (admin)"

# Test 22: Ajouter un rôle (user) - devrait échouer
response=$(curl -s -o /dev/null -w "%{http_code}" -X POST "$BASE_URL/api/users/$USER_ID/roles?role=ADMIN" \
    -H "Authorization: Bearer $USER_TOKEN")
[ "$response" = "403" ]
check_result $? "Ajouter un rôle (user - devrait échouer)"

# Test 23: Retirer un rôle (admin)
response=$(curl -s -o /dev/null -w "%{http_code}" -X DELETE "$BASE_URL/api/users/$USER_ID/roles?role=TRADER" \
    -H "Authorization: Bearer $ADMIN_TOKEN")
[ "$response" = "200" ]
check_result $? "Retirer un rôle (admin)"

echo ""
echo -e "${BLUE}6. Tests de réinitialisation de mot de passe${NC}"
echo "-----------------------------------"

# Test 24: Demande de réinitialisation de mot de passe
response=$(curl -s -o /dev/null -w "%{http_code}" -X POST "$BASE_URL/api/auth/password-reset/request" \
    -H "Content-Type: application/json" \
    -d '{"email":"test@lidcoin.com"}')
[ "$response" = "200" ]
check_result $? "Demande de réinitialisation de mot de passe"

# Test 25: Changement de mot de passe
response=$(curl -s -o /dev/null -w "%{http_code}" -X POST "$BASE_URL/api/auth/password/change?userId=$USER_ID&oldPassword=Test@123&newPassword=NewPass@123" \
    -H "Authorization: Bearer $USER_TOKEN")
[ "$response" = "200" ] || [ "$response" = "400" ]  # Peut échouer si déjà changé
check_result $? "Changement de mot de passe"

echo ""
echo -e "${BLUE}========================================${NC}"
echo -e "${BLUE}  Résumé des tests${NC}"
echo -e "${BLUE}========================================${NC}"
echo -e "Base URL: $BASE_URL"
echo -e "User Token: ${USER_TOKEN:0:20}..."
echo -e "Admin Token: ${ADMIN_TOKEN:0:20}..."
echo -e "User ID: $USER_ID"
echo ""
echo -e "${GREEN}Tests terminés!${NC}"