# TechZone - Plateforme E-commerce

Plateforme e-commerce complète développée avec Spring Boot, spécialisée dans la vente de produits high-tech (PC, smartphones, accessoires).

## 📋 Description du projet

TechZone est une application web complète permettant la gestion d'une boutique en ligne avec :
- **Interface publique** : Catalogue de produits, panier, commandes
- **Espace utilisateur** : Gestion du compte, historique des commandes
- **Back-office admin** : Gestion des produits, catégories, commandes

## 🛠️ Stack technique

- **Backend** : Java 17, Spring Boot 3.2.0
- **Sécurité** : Spring Security, JWT (JSON Web Tokens)
- **Base de données** : H2 (développement)
- **ORM** : Spring Data JPA / Hibernate
- **Frontend** : Thymeleaf, HTML5, CSS3, JavaScript
- **Build** : Maven
- **Documentation API** : Swagger/OpenAPI

## 📁 Architecture du projet

```
src/main/java/com/techzone/
├── entity/          # Entités JPA (User, Role, Product, Category, Order, OrderLine)
├── repository/      # Interfaces Spring Data JPA
├── service/         # Logique métier
├── controller/
│   ├── api/        # Contrôleurs REST API
│   └── web/        # Contrôleurs MVC Thymeleaf
├── dto/            # Objets de transfert de données
├── security/       # Configuration Spring Security et filtres JWT
├── config/         # Configurations (Swagger, Security)
└── exception/       # Gestion globale des exceptions
```

## 🚀 Instructions de lancement

### Prérequis

- Java 17 ou supérieur
- Maven 3.6+

### Installation et exécution

1. **Cloner le projet** (si applicable)
   ```bash
   git clone <repository-url>
   cd TechZone
   ```

2. **Compiler et lancer l'application**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

3. **Accéder à l'application**
   - Application web : http://localhost:8080
   - Console H2 : http://localhost:8080/h2-console
     - JDBC URL: `jdbc:h2:mem:techzone`
     - Username: `sa`
     - Password: (vide)
   - Swagger UI : http://localhost:8080/swagger-ui.html

## 👥 Comptes de test

### Comptes ADMIN

| Email | Mot de passe | Rôle |
|-------|--------------|------|
| admin@techzone.com | password123 | ADMIN |
| admin2@techzone.com | password123 | ADMIN |

### Comptes USER

| Email | Mot de passe | Rôle |
|-------|--------------|------|
| user@techzone.com | password123 | USER |
| user2@techzone.com | password123 | USER |
| user3@techzone.com | password123 | USER |

## 📊 Modèle de données (MCD)

### Entités principales

- **User** : Utilisateurs de la plateforme (email, mot de passe hashé, nom, prénom, adresse)
- **Role** : Rôles système (ADMIN, USER)
- **Product** : Produits du catalogue (nom, description, prix, statut stock, promotion)
- **Category** : Catégories de produits
- **Order** : Commandes des utilisateurs
- **OrderLine** : Lignes de commande (produit, quantité, prix)

### Relations

- User ↔ Role : ManyToMany
- Category → Product : OneToMany
- User → Order : OneToMany
- Order → OrderLine : OneToMany
- Product → OrderLine : ManyToOne

## 🔐 Sécurité

### Authentification JWT

- **Durée de vie du token** : 30 minutes
- **Format** : Bearer token dans le header `Authorization: Bearer <token>`
- **Endpoints protégés** :
  - `/api/admin/**` : Requiert le rôle ADMIN
  - `/api/user/**` : Requiert le rôle USER ou ADMIN
  - `/admin/**` : Requiert le rôle ADMIN
  - `/user/**` : Requiert le rôle USER ou ADMIN

### Règles de mot de passe

- Minimum 8 caractères
- Hashage avec BCrypt

## 🎯 Fonctionnalités

### Interface publique

- ✅ Consultation du catalogue produits
- ✅ Filtrage par catégorie, promotion, statut stock
- ✅ Pagination (12 produits par page)
- ✅ Détail produit
- ✅ Panier pour invités (cookies, persistance 7 jours)
- ✅ Ajout/suppression produits du panier

### Espace utilisateur

- ✅ Inscription / Connexion
- ✅ Gestion du panier
- ✅ Passage de commande
- ✅ Historique des commandes
- ✅ Détail des commandes

### Back-office admin

- ✅ Dashboard avec statistiques
- ✅ Gestion CRUD des produits
- ✅ Gestion CRUD des catégories
- ✅ Visualisation de toutes les commandes
- ✅ Modification du statut des commandes

## 📡 API REST

### Endpoints publics

- `POST /api/auth/register` - Inscription
- `POST /api/auth/login` - Connexion
- `GET /api/products` - Liste des produits (avec pagination et filtres)
- `GET /api/products/{id}` - Détail d'un produit
- `GET /api/cart` - Récupérer le panier
- `POST /api/cart/add` - Ajouter au panier
- `DELETE /api/cart/remove/{productId}` - Retirer du panier
- `PUT /api/cart/update` - Mettre à jour la quantité

### Endpoints USER (authentification requise)

- `GET /api/user/orders` - Liste des commandes de l'utilisateur
- `GET /api/user/orders/{id}` - Détail d'une commande

### Endpoints ADMIN (authentification requise)

- `POST /api/admin/products` - Créer un produit
- `PUT /api/admin/products/{id}` - Modifier un produit
- `DELETE /api/admin/products/{id}` - Supprimer un produit
- `GET /api/admin/categories` - Liste des catégories
- `POST /api/admin/categories` - Créer une catégorie
- `PUT /api/admin/categories/{id}` - Modifier une catégorie
- `DELETE /api/admin/categories/{id}` - Supprimer une catégorie
- `GET /api/admin/orders` - Liste de toutes les commandes
- `GET /api/admin/orders/{id}` - Détail d'une commande
- `PUT /api/admin/orders/{id}/status` - Modifier le statut d'une commande

## 🎨 Design et UX

- Design moderne avec système de couleurs cohérent
- Responsive design (mobile-first)
- Animations et transitions fluides
- Indicateurs visuels de statut (🟢 en stock, 🔴 rupture)
- Messages de feedback utilisateur

## 📝 Fonctionnalités avancées implémentées

1. **Swagger/OpenAPI** : Documentation interactive de l'API accessible sur `/swagger-ui.html`
2. **Logs structurés** : Configuration logback avec logs pour authentification et commandes
3. **Design amélioré** : Interface moderne avec CSS personnalisé, animations, responsive

## 🧪 Tests

Les données de test sont chargées automatiquement au démarrage via `data.sql` :
- 2 comptes ADMIN
- 3 comptes USER
- 5 catégories
- 18 produits variés
- 4 commandes de test

## 📚 Documentation API

La documentation Swagger est accessible après le lancement de l'application :
- **URL** : http://localhost:8080/swagger-ui.html
- **OpenAPI JSON** : http://localhost:8080/v3/api-docs

## 🤖 Utilisation de l'intelligence artificielle

Ce projet a été développé avec l'assistance de **Cursor AI** (modèle Auto) pour :
- Génération de code backend (entités, services, contrôleurs)
- Création de l'interface frontend (templates Thymeleaf, CSS)
- Configuration de la sécurité JWT
- Documentation et structure du projet

## 📦 Collection Postman

Une collection Postman est disponible dans le fichier `TechZone.postman_collection.json` avec :
- Tous les endpoints de l'API
- Exemples de requêtes avec JWT
- Variables d'environnement pour le token

## 🔄 Diagramme de classes UML

```
┌─────────────┐
│    User     │
├─────────────┤
│ id          │
│ email       │
│ password    │
│ nom         │
│ prenom      │
│ adresse     │
└──────┬──────┘
       │
       │ ManyToMany
       │
┌──────▼──────┐      ┌─────────────┐
│ User_Roles │      │    Role     │
└─────────────┘      ├─────────────┤
                     │ id          │
                     │ name        │
                     └─────────────┘

┌─────────────┐      ┌─────────────┐
│  Category   │      │   Product   │
├─────────────┤      ├─────────────┤
│ id          │◄─────┤ id          │
│ nom         │      │ nom         │
│ description │      │ description │
└─────────────┘      │ prix        │
                     │ stockStatus │
                     │ promotion   │
                     └──────┬───────┘
                            │
                            │ ManyToOne
                            │
                     ┌──────▼───────┐
                     │ OrderLine    │
                     ├──────────────┤
                     │ id          │
                     │ quantity    │
                     │ prixUnitaire│
                     └──────┬───────┘
                            │
                            │ ManyToOne
                            │
                     ┌──────▼───────┐
                     │    Order     │
                     ├──────────────┤
                     │ id          │
                     │ date        │
                     │ statut      │
                     │ total       │
                     └──────┬───────┘
                            │
                            │ ManyToOne
                            │
                     ┌──────▼───────┐
                     │    User      │
                     └──────────────┘
```

## 📄 Licence

Ce projet est développé dans le cadre d'un projet académique.

## 👨‍💻 Auteurs

Développé par un groupe de 3 étudiants pour la soutenance du 30/01/2025.

---

**Note** : Pour toute question ou problème, consultez la documentation Swagger ou les logs de l'application.
