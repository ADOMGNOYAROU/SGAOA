# 🎯 GUIDE DE COMPATIBILITÉ FRONTEND-BACKEND SGAOA

## ✅ **CONFIGURATION ACTUELLE**

### **Backend (Spring Boot)**
- **URL** : `http://localhost:8081`
- **API Base** : `/api`
- **Base de données** : MySQL `sgaoa_db`
- **Sécurité** : JWT Tokens
- **CORS** : Configuré pour `http://localhost:4200`

### **Frontend (Angular)**
- **URL** : `http://localhost:4200`
- **API URL** : `http://localhost:8081/api`
- **Authentification** : JWT avec localStorage
- **Framework** : Angular 19 + Material

---

## 🔧 **ÉTAPES POUR RENDRE COMPATIBLE**

### **1. Démarrer le Backend**

```bash
cd sgaoa-backend
./mvnw.cmd spring-boot:run
```

**Vérification :**
- Ouvrir `http://localhost:8081/api/auth/connexion` (POST)
- Doit retourner 401 (non authentifié) - Normal

### **2. Démarrer le Frontend**

```bash
cd sgaoa-frontend
npm install
ng serve
```

**Vérification :**
- Ouvrir `http://localhost:4200`
- Doit rediriger vers `/auth/login`

### **3. Test de Connexion**

#### **Identifiants de Test**
```json
{
  "email": "testmysql@example.com",
  "motDePasse": "password123"
}
```

#### **Étapes**
1. Aller sur `http://localhost:4200/auth/login`
2. Entrer les identifiants ci-dessus
3. Cliquer sur "Se connecter"
4. Doit rediriger vers le dashboard

---

## 🛠️ **POINTS DE COMPATIBILITÉ**

### **API Endpoints Disponibles**

#### **Authentification**
```http
POST /api/auth/inscription     # Inscription adoptant
POST /api/auth/connexion       # Connexion
GET  /api/auth/verifier-email  # Vérification email
POST /api/auth/reinitialiser-mot-de-passe  # Reset mot de passe
```

#### **Utilisateurs**
```http
GET    /api/comptes           # Lister utilisateurs
GET    /api/comptes/{id}      # Détails utilisateur
POST   /api/comptes           # Créer utilisateur
PUT    /api/comptes/{id}      # Modifier utilisateur
PATCH  /api/comptes/{id}/statut  # Changer statut
DELETE /api/comptes/{id}      # Supprimer utilisateur
```

#### **Orphelins** (Nouveau)
```http
GET    /api/orphelins          # Lister orphelins
GET    /api/orphelins/{id}     # Détails orphelin
POST   /api/orphelins          # Créer orphelin
PUT    /api/orphelins/{id}     # Modifier orphelin
GET    /api/orphelins/disponibles  # Orphelins disponibles
PATCH  /api/orphelins/{id}/statut  # Changer statut
DELETE /api/orphelins/{id}     # Supprimer orphelin
GET    /api/orphelins/search?q=query  # Rechercher
```

### **Mapping des Données**

#### **Utilisateur Response**
```json
{
  "id": 1,
  "nom": "TestMySQL",
  "prenom": "Verification",
  "email": "testmysql@example.com",
  "telephone": null,
  "role": "ADOPTANT",
  "statut": "EN_ATTENTE_VALIDATION",
  "emailVerifie": false,
  "dateCreation": "2026-05-09T15:22:47.487529",
  "dernierConnexion": null
}
```

#### **Auth Response**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
  "refreshToken": "refresh_token_here",
  "tokenType": "Bearer",
  "utilisateur": { ... }
}
```

---

## 🔐 **SÉCURITÉ**

### **JWT Tokens**
- **Header** : `Authorization: Bearer <token>`
- **Stockage** : localStorage
- **Expiration** : 24h (configurable)

### **Rôles et Permissions**
```typescript
enum Role {
  ADOPTANT = 'ADOPTANT',
  PRESIDENT_COMITE = 'PRESIDENT_COMITE',
  SECRETAIRE = 'SECRETAIRE',
  AGENT_SOCIAL = 'AGENT_SOCIAL',
  ADMINISTRATEUR = 'ADMINISTRATEUR'
}
```

### **Guards Angular**
- **AuthGuard** : Vérifie si connecté
- **RoleGuard** : Vérifie les permissions

---

## 📊 **TESTS DE COMPATIBILITÉ**

### **1. Test API Backend**
```bash
# Test connexion
curl -X POST http://localhost:8081/api/auth/connexion \
  -H "Content-Type: application/json" \
  -d '{"email":"testmysql@example.com","motDePasse":"password123"}'

# Test liste orphelins
curl -X GET http://localhost:8081/api/orphelins \
  -H "Authorization: Bearer <token>"
```

### **2. Test Frontend**
```typescript
// Dans le dashboard Angular
console.log('Token:', localStorage.getItem('token'));
console.log('User:', localStorage.getItem('utilisateur'));
```

### **3. Test Cross-Origin**
- Ouvrir les outils de développement Chrome
- Vérifier les requêtes réseau
- Pas d'erreurs CORS

---

## 🚨 **PROBLÈMES COMMUNS ET SOLUTIONS**

### **Problème 1 : CORS Error**
```
Access to fetch at 'http://localhost:8081/api/auth/connexion' 
from origin 'http://localhost:4200' has been blocked by CORS policy
```

**Solution :**
```java
// Dans SecurityConfig.java
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOriginPatterns(Arrays.asList("*"));
    configuration.setAllowedMethods(Arrays.asList("*"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true);
    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}
```

### **Problème 2 : 401 Unauthorized**
```
Status: 401 Unauthorized
```

**Solution :**
- Vérifier que le token est valide
- Ajouter `Authorization: Bearer <token>` header
- Vérifier l'expiration du token

### **Problème 3 : 403 Forbidden**
```
Status: 403 Forbidden
```

**Solution :**
- Vérifier les rôles de l'utilisateur
- Ajouter les préautorisations dans les contrôleurs
- Vérifier `@PreAuthorize` annotations

### **Problème 4 : Backend non démarré**
```
Connection refused: localhost:8081
```

**Solution :**
- Démarrer le backend : `./mvnw.cmd spring-boot:run`
- Vérifier que le port 8081 est libre
- Vérifier la configuration MySQL

---

## 📋 **CHECKLIST DE DÉPLOIEMENT**

### **Backend ✅**
- [ ] MySQL démarré sur localhost:3306
- [ ] Base `sgaoa_db` créée
- [ ] Application Spring Boot démarrée
- [ ] API accessible sur http://localhost:8081
- [ ] JWT configuration fonctionnelle
- [ ] CORS configuré pour Angular

### **Frontend ✅**
- [ ] Node.js installé
- [ ] Dependencies installées (`npm install`)
- [ ] Angular CLI fonctionnel
- [ ] Application démarrée (`ng serve`)
- [ ] Accès sur http://localhost:4200
- [ ] Routing configuré

### **Intégration ✅**
- [ ] Login fonctionnel
- [ ] Token JWT stocké
- [ ] Dashboard accessible
- [ ] API calls fonctionnels
- [ ] Pas d'erreurs CORS
- [ ] Navigation par rôle

---

## 🎯 **RESULTAT FINAL**

Une fois toutes les étapes complétées :

1. **Backend** : API REST sécurisée avec MySQL
2. **Frontend** : Application Angular moderne
3. **Communication** : HTTP + JWT
4. **Sécurité** : Rôles et permissions
5. **Base de données** : Persistance MySQL

**Les deux applications sont maintenant compatibles et fonctionnent ensemble !** 🚀
