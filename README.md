# Portail de mise en relation

## Description
Cette application est une API RESTful construite avec Spring Boot 3. \
Elle utilise Spring Security pour l’authentification et l’autorisation, et Swagger pour la documentation de l’API à l’aide d’OpenAPI 3.

## Table des matières
1. [Prérequis](#prérequis)
2. [Installation](#installation)
3. [Utilisation](#utilisation)
4. [Configuration](#configuration)
5. [Endpoints](#endpoints)
6. [Authentification](#authentification)
7. [Tables de la base de données](#tables-de-la-base-de-données)
8. [Documentation de l’API](#documentation-de-lapi)

## Prérequis
Java 17 ou version supérieure

## Installation
Clonez ce dépôt : git clone https://github.com/Ahmed-OCR/back

## Utilisation
Accédez au répertoire du projet : cd back \
Construction du projet : `mvn clean install` \
Exécutez l’application : `mvn spring-boot:run` ou via votre IDE.

## Configuration
Le fichier application.properties contient les propriétés de configuration de l’application (ex: Context path, DbPort, serverPort etc...) \
Vous pouvez personnaliser les paramètres de sécurité dans la classe SecurityConfiguration (Clé token)

## Endpoints
- L’API expose les endpoints suivants :
  - `/api/auth/register` (POST): Création de compte
  - `/api/auth/login` (POST): Connexion à l'application
  - `/api/auth/me` (GET): Identifie l'utilisateur
  - `/api/rentals/` (GET): Renvoie la liste de toutes les locations
  - `/api/rentals/:id` (GET): Renvoie une location
  - `/api/rentals/` (PUT): Mise à jour d'une location
  - `/api/rentals` (POST): Création d'une location
  - `/api/messages` (POST): Création d'un message pour une location
  - `/api/user/:id` (GET): Renvoie les informations sur l'utilisateur connecté

## Authentification
L’authentification se fait via JWT (JSON Web Tokens).

## Tables de la base de données
- Liste des tables
  - `users` : Stocke les informations sur les utilisateurs (nom, e-mail, etc.).
  - `rentals` : Contient les détails des locations.
  - `messages` : Enregistre les messages liés aux locations
  
- Relations:
  - Il n'est pas possible d'ajouter une location si le user n'existe pas
  - Il n'est pas possible d'ajouter un message si une location et/ou un user n'existe(nt) pas

![Bdd](/bdd.png "Base de données")

## Documentation de l’API
Accédez à la documentation de l’API via Swagger UI : `/api/swagger-ui/index.html`