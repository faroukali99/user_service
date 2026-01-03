# User Service - LidCoin Project

## Description
Service de gestion des utilisateurs pour la plateforme LidCoin. Ce microservice est responsable de l'authentification, de l'autorisation et de la gestion des profils utilisateurs.

## Fonctionnalités

- Inscription des utilisateurs
- Authentification (JWT)
- Gestion des profils utilisateurs
- Réinitialisation de mot de passe
- Gestion des rôles et permissions

## Prérequis

- Java 17 ou supérieur
- Maven 3.8+
- Base de données (configurée dans `application.properties`)
- Spring Boot 3.x

## Installation

1. Cloner le dépôt :
   ```bash
   git clone [URL_DU_DEPOT]
   cd user_service
   ```

2. Configurer la base de données dans `src/main/resources/application.properties`

3. Construire le projet :
   ```bash
   mvn clean install
   ```

4. Lancer l'application :
   ```bash
   mvn spring-boot:run
   ```

## API Endpoints

- `POST /api/auth/register` - Enregistrement d'un nouvel utilisateur
- `POST /api/auth/login` - Connexion utilisateur
- `GET /api/users/me` - Récupérer son profil
- `PUT /api/users/me` - Mettre à jour son profil
- `POST /api/auth/reset-password` - Demande de réinitialisation de mot de passe

## Structure du projet

```
src/
├── main/
│   ├── java/com/lidcoin/user_service/
│   │   ├── application/    # Couche application (DTOs, Services, Controllers)
│   │   ├── domain/         # Modèles de domaine et repositories
│   │   ├── infrastructure/ # Configuration et sécurité
│   │   └── UserServiceApplication.java
│   └── resources/          # Fichiers de configuration et propriétés
└── test/                   # Tests unitaires et d'intégration
```

## Configuration

Les principales configurations sont disponibles dans `application.properties` :
- Configuration de la base de données
- JWT secret et expiration
- Paramètres SMTP pour les emails

## Tests

Pour exécuter les tests :
```bash
mvn test
```

## Licence

LidCoin
