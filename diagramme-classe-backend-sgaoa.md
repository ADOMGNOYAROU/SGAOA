# Diagramme de Classes Backend - SGAOA

## Description du Diagramme de Classes Backend

Le diagramme de classes backend représente la structure statique du serveur SGAOA en montrant les entités JPA, services, contrôleurs et repositories. Ce diagramme est optimisé pour l'implémentation Spring Boot.

## Schéma du Diagramme de Classes Backend

```mermaid
classDiagram
    %% Package Entités (Entities)
    class Utilisateur {
        <<Entity>>
        <<Table("utilisateurs")>>
        -Long id
        -String nom
        -String prenom
        -String email
        -String motDePasse
        -String telephone
        -Role role
        -StatutCompte statut
        -Boolean emailVerifie
        -LocalDateTime dateCreation
        -LocalDateTime dateModification
        -LocalDateTime dernierConnexion
        -String tokenVerificationEmail
        -String tokenReinitialisation
        -LocalDateTime expirationTokenReinitialisation
        
        +getId() Long
        +getNom() String
        +setNom(String) void
        +getPrenom() String
        +setPrenom(String) void
        +getEmail() String
        +setEmail(String) void
        +getMotDePasse() String
        +setMotDePasse(String) void
        +getRole() Role
        +setRole(Role) void
        +getStatut() StatutCompte
        +setStatut(StatutCompte) void
        +isEmailVerifie() Boolean
        +setEmailVerifie(Boolean) void
        +getDateCreation() LocalDateTime
        +setDateCreation(LocalDateTime) void
        +isAccountNonExpired() boolean
        +isAccountNonLocked() boolean
        +isCredentialsNonExpired() boolean
        +isEnabled() boolean
        +getAuthorities() Collection~GrantedAuthority~
    }
    
    class Role {
        <<Enum>>
        ADOPTANT
        PRESIDENT_COMITE
        SECRETAIRE
        AGENT_SOCIAL
        ADMINISTRATEUR
    }
    
    class StatutCompte {
        <<Enum>>
        EN_ATTENTE_VALIDATION
        ACTIF
        INACTIF
        SUSPENDU
    }
    
    class Adoptant {
        <<Entity>>
        <<Table("adoptants")>>
        -Long id
        -String profession
        -String adresse
        -String situationFamiliale
        -Integer nombreEnfants
        -BigDecimal revenuMensuel
        -Long utilisateurId
        
        +getId() Long
        +getProfession() String
        +setProfession(String) void
        +getAdresse() String
        +setAdresse(String) void
        +getSituationFamiliale() String
        +setSituationFamiliale(String) void
        +getNombreEnfants() Integer
        +setNombreEnfants(Integer) void
        +getRevenuMensuel() BigDecimal
        +setRevenuMensuel(BigDecimal) void
        +getUtilisateurId() Long
        +setUtilisateurId(Long) void
    }
    
    class Orphelin {
        <<Entity>>
        <<Table("orphelins")>>
        -Long id
        -String nom
        -String prenom
        -LocalDate dateNaissance
        -String lieuNaissance
        -String sexe
        -String etatSante
        -String historiqueMedical
        -String situationFamiliale
        -StatutOrphelin statut
        
        +getId() Long
        +getNom() String
        +setNom(String) void
        +getPrenom() String
        +setPrenom(String) void
        +getDateNaissance() LocalDate
        +setDateNaissance(LocalDate) void
        +getLieuNaissance() String
        +setLieuNaissance(String) void
        +getSexe() String
        +setSexe(String) void
        +getEtatSante() String
        +setEtatSante(String) void
        +getHistoriqueMedical() String
        +setHistoriqueMedical(String) void
        +getSituationFamiliale() String
        +setSituationFamiliale(String) void
        +getStatut() StatutOrphelin
        +setStatut(StatutOrphelin) void
    }
    
    class StatutOrphelin {
        <<Enum>>
        DISPONIBLE
        EN_PROCESSUS_ADOPTION
        ADOPTÉ
        INDISPONIBLE
    }
    
    class DemandeAdoption {
        <<Entity>>
        <<Table("demandes_adoption")>>
        -Long id
        -String reference
        -LocalDate dateDepot
        -StatutDemande statut
        -String motifRejet
        -LocalDate dateDecision
        -Long adoptantId
        -Long orphelinId
        
        +getId() Long
        +getReference() String
        +setReference(String) void
        +getDateDepot() LocalDate
        +setDateDepot(LocalDate) void
        +getStatut() StatutDemande
        +setStatut(StatutDemande) void
        +getMotifRejet() String
        +setMotifRejet(String) void
        +getDateDecision() LocalDate
        +setDateDecision(LocalDate) void
        +getAdoptantId() Long
        +setAdoptantId(Long) void
        +getOrphelinId() Long
        +setOrphelinId(Long) void
    }
    
    class StatutDemande {
        <<Enum>>
        EN_ATTENTE
        EN_INSTRUCTION
        ENQUETE_SOCIALE
        COMITE_EXAMEN
        APPROUVÉE
        REJETÉE
        COMPLÉTÉE
    }
    
    class EnqueteSociale {
        <<Entity>>
        <<Table("enquetes_sociales")>>
        -Long id
        -LocalDate dateVisite
        -String lieuVisite
        -String rapportVisite
        -String conclusion
        -String recommandation
        -Long agentSocialId
        -Long demandeAdoptionId
        
        +getId() Long
        +getDateVisite() LocalDate
        +setDateVisite(LocalDate) void
        +getLieuVisite() String
        +setLieuVisite(String) void
        +getRapportVisite() String
        +setRapportVisite(String) void
        +getConclusion() String
        +setConclusion(String) void
        +getRecommandation() String
        +setRecommandation(String) void
        +getAgentSocialId() Long
        +setAgentSocialId(Long) void
        +getDemandeAdoptionId() Long
        +setDemandeAdoptionId(Long) void
    }
    
    class Document {
        <<Entity>>
        <<Table("documents")>>
        -Long id
        -String nom
        -String type
        -String cheminFichier
        -Long taille
        -LocalDate dateDepot
        -String statut
        -Long utilisateurId
        -Long demandeAdoptionId
        -Long orphelinId
        -Long enqueteSocialeId
        
        +getId() Long
        +getNom() String
        +setNom(String) void
        +getType() String
        +setType(String) void
        +getCheminFichier() String
        +setCheminFichier(String) void
        +getTaille() Long
        +setTaille(Long) void
        +getDateDepot() LocalDate
        +setDateDepot(LocalDate) void
        +getStatut() String
        +setStatut(String) void
        +getUtilisateurId() Long
        +setUtilisateurId(Long) void
        +getDemandeAdoptionId() Long
        +setDemandeAdoptionId(Long) void
        +getOrphelinId() Long
        +setOrphelinId(Long) void
        +getEnqueteSocialeId() Long
        +setEnqueteSocialeId(Long) void
    }
    
    %% Package DTOs (Data Transfer Objects)
    class UtilisateurResponse {
        -Long id
        -String nom
        -String prenom
        -String email
        -String telephone
        -Role role
        -StatutCompte statut
        -Boolean emailVerifie
        -LocalDateTime dateCreation
        -LocalDateTime dernierConnexion
        
        +getId() Long
        +getNom() String
        +getPrenom() String
        +getEmail() String
        +getTelephone() String
        +getRole() Role
        +getStatut() StatutCompte
        +isEmailVerifie() Boolean
        +getDateCreation() LocalDateTime
        +getDernierConnexion() LocalDateTime
    }
    
    class AuthResponse {
        -String accessToken
        -String refreshToken
        -String tokenType
        -UtilisateurResponse utilisateur
        
        +getAccessToken() String
        +setAccessToken(String) void
        +getRefreshToken() String
        +setRefreshToken(String) void
        +getTokenType() String
        +setTokenType(String) void
        +getUtilisateur() UtilisateurResponse
        +setUtilisateur(UtilisateurResponse) void
    }
    
    class InscriptionRequest {
        -String nom
        -String prenom
        -String email
        -String motDePasse
        -String telephone
        -String profession
        -String adresse
        
        +getNom() String
        +setNom(String) void
        +getPrenom() String
        +setPrenom(String) void
        +getEmail() String
        +setEmail(String) void
        +getMotDePasse() String
        +setMotDePasse(String) void
        +getTelephone() String
        +setTelephone(String) void
        +getProfession() String
        +setProfession(String) void
        +getAdresse() String
        +setAdresse(String) void
    }
    
    class LoginRequest {
        -String email
        -String motDePasse
        
        +getEmail() String
        +setEmail(String) void
        +getMotDePasse() String
        +setMotDePasse(String) void
    }
    
    %% Package Repositories
    class UtilisateurRepository {
        <<Repository>>
        <<JpaRepository>>
        +findByEmail(String email) Optional~Utilisateur~
        +existsByEmail(String email) boolean
        +existsByTelephone(String telephone) boolean
        +findByRole(Role role) List~Utilisateur~
        +findByStatut(StatutCompte statut) List~Utilisateur~
    }
    
    class AdoptantRepository {
        <<Repository>>
        <<JpaRepository>>
        +findByUtilisateurId(Long utilisateurId) Optional~Adoptant~
        +findByProfession(String profession) List~Adoptant~
    }
    
    class OrphelinRepository {
        <<Repository>>
        <<JpaRepository>>
        +findByStatut(StatutOrphelin statut) List~Orphelin~
        +findByNomContaining(String nom) List~Orphelin~
        +findDisponibles() List~Orphelin~
    }
    
    class DemandeAdoptionRepository {
        <<Repository>>
        <<JpaRepository>>
        +findByAdoptantId(Long adoptantId) List~DemandeAdoption~
        +findByOrphelinId(Long orphelinId) List~DemandeAdoption~
        +findByStatut(StatutDemande statut) List~DemandeAdoption~
        +findByReference(String reference) Optional~DemandeAdoption~
    }
    
    class EnqueteSocialeRepository {
        <<Repository>>
        <<JpaRepository>>
        +findByAgentSocialId(Long agentSocialId) List~EnqueteSociale~
        +findByDemandeAdoptionId(Long demandeId) List~EnqueteSociale~
        +findByDateVisiteBetween(LocalDate debut, LocalDate fin) List~EnqueteSociale~
    }
    
    class DocumentRepository {
        <<Repository>>
        <<JpaRepository>>
        +findByUtilisateurId(Long utilisateurId) List~Document~
        +findByDemandeAdoptionId(Long demandeId) List~Document~
        +findByType(String type) List~Document~
        +findByStatut(String statut) List~Document~
    }
    
    %% Package Services
    class UtilisateurService {
        <<Service>>
        -UtilisateurRepository utilisateurRepository
        -PasswordEncoder passwordEncoder
        -AuthenticationManager authenticationManager
        -JwtService jwtService
        
        +inscrireAdoptant(InscriptionRequest request) UtilisateurResponse
        +connecter(LoginRequest request) AuthResponse
        +creerCompte(CreerCompteRequest request) UtilisateurResponse
        +modifierCompte(Long id, CreerCompteRequest request) UtilisateurResponse
        +changerStatutCompte(Long id, StatutCompte statut) void
        +supprimerCompte(Long id) void
        +listerTousLesComptes() List~UtilisateurResponse~
        +obtenirCompteParId(Long id) UtilisateurResponse
        +verifierEmail(String token) boolean
        +reinitialiserMotDePasse(String email) void
    }
    
    class AdoptantService {
        <<Service>>
        -AdoptantRepository adoptantRepository
        -UtilisateurRepository utilisateurRepository
        
        +obtenirProfilAdoptant(Long adoptantId) AdoptantResponse
        +modifierProfilAdoptant(Long id, AdoptantRequest request) AdoptantResponse
        +listerAdoptants() List~AdoptantResponse~
        +obtenirStatistiquesAdoptants() StatistiquesAdoptants
    }
    
    class OrphelinService {
        <<Service>>
        -OrphelinRepository orphelinRepository
        -DocumentRepository documentRepository
        
        +creerOrphelin(OrphelinRequest request) OrphelinResponse
        +modifierOrphelin(Long id, OrphelinRequest request) OrphelinResponse
        +obtenirOrphelinParId(Long id) OrphelinResponse
        +listerOrphelins() List~OrphelinResponse~
        +listerOrphelinsDisponibles() List~OrphelinResponse~
        +changerStatutOrphelin(Long id, StatutOrphelin statut) void
        +supprimerOrphelin(Long id) void
    }
    
    class DemandeAdoptionService {
        <<Service>>
        -DemandeAdoptionRepository demandeAdoptionRepository
        -AdoptantRepository adoptantRepository
        -OrphelinRepository orphelinRepository
        -EnqueteSocialeRepository enqueteRepository
        -NotificationService notificationService
        
        +creerDemande(DemandeRequest request) DemandeAdoptionResponse
        +modifierDemande(Long id, DemandeRequest request) DemandeAdoptionResponse
        +validerDemande(Long id) void
        +rejeterDemande(Long id, String motif) void
        +obtenirDemandeParId(Long id) DemandeAdoptionResponse
        +listerDemandes() List~DemandeAdoptionResponse~
        +listerDemandesParStatut(StatutDemande statut) List~DemandeAdoptionResponse~
        +obtenirDemandesAdoptant(Long adoptantId) List~DemandeAdoptionResponse~
    }
    
    class EnqueteSocialeService {
        <<Service>>
        -EnqueteSocialeRepository enqueteRepository
        -AgentSocialService agentSocialService
        
        +creerEnquete(EnqueteRequest request) EnqueteSocialeResponse
        +modifierEnquete(Long id, EnqueteRequest request) EnqueteSocialeResponse
        +validerEnquete(Long id) void
        +obtenirEnqueteParId(Long id) EnqueteSocialeResponse
        +listerEnquetesAgent(Long agentId) List~EnqueteSocialeResponse~
        +listerEnquetesDemande(Long demandeId) List~EnqueteSocialeResponse~
    }
    
    class DocumentService {
        <<Service>>
        -DocumentRepository documentRepository
        -FileStorageService fileStorageService
        
        +uploaderDocument(MultipartFile file, Long entityId, String entityType) DocumentResponse
        +telechargerDocument(Long documentId) Resource
        +supprimerDocument(Long documentId) void
        +listerDocumentsEntite(Long entityId, String entityType) List~DocumentResponse~
        +validerDocument(Long documentId) void
    }
    
    %% Package Controllers
    class AuthController {
        <<RestController>>
        <<RequestMapping("/api/auth")>>
        -UtilisateurService utilisateurService
        
        +@PostMapping("/inscription") inscrireAdoptant(@RequestBody InscriptionRequest request) ResponseEntity~UtilisateurResponse~
        +@PostMapping("/connexion") connecter(@RequestBody LoginRequest request) ResponseEntity~AuthResponse~
        +@GetMapping("/verifier-email") verifierEmail(@RequestParam String token) ResponseEntity~String~
        +@PostMapping("/reinitialiser-mot-de-passe") reinitialiserMotDePasse(@RequestParam String email) ResponseEntity~String~
    }
    
    class UtilisateurController {
        <<RestController>>
        <<RequestMapping("/api/comptes")>>
        -UtilisateurService utilisateurService
        
        +@GetMapping("") listerTousLesComptes() ResponseEntity~List~UtilisateurResponse~~
        +@GetMapping("/{id}") obtenirCompteParId(@PathVariable Long id) ResponseEntity~UtilisateurResponse~
        +@PostMapping("") creerCompte(@RequestBody CreerCompteRequest request) ResponseEntity~UtilisateurResponse~
        +@PutMapping("/{id}") modifierCompte(@PathVariable Long id, @RequestBody CreerCompteRequest request) ResponseEntity~UtilisateurResponse~
        +@PatchMapping("/{id}/statut") changerStatutCompte(@PathVariable Long id, @RequestBody StatutRequest request) ResponseEntity~Void~
        +@DeleteMapping("/{id}") supprimerCompte(@PathVariable Long id) ResponseEntity~Void~
    }
    
    class AdoptantController {
        <<RestController>>
        <<RequestMapping("/api/adoptants")>>
        -AdoptantService adoptantService
        
        +@GetMapping("") listerAdoptants() ResponseEntity~List~AdoptantResponse~~
        +@GetMapping("/{id}") obtenirProfilAdoptant(@PathVariable Long id) ResponseEntity~AdoptantResponse~
        +@PutMapping("/{id}") modifierProfilAdoptant(@PathVariable Long id, @RequestBody AdoptantRequest request) ResponseEntity~AdoptantResponse~
        +@GetMapping("/statistiques") obtenirStatistiquesAdoptants() ResponseEntity~StatistiquesAdoptants~
    }
    
    class OrphelinController {
        <<RestController>>
        <<RequestMapping("/api/orphelins")>>
        -OrphelinService orphelinService
        
        +@GetMapping("") listerOrphelins() ResponseEntity~List~OrphelinResponse~~
        +@GetMapping("/{id}") obtenirOrphelinParId(@PathVariable Long id) ResponseEntity~OrphelinResponse~
        +@PostMapping("") creerOrphelin(@RequestBody OrphelinRequest request) ResponseEntity~OrphelinResponse~
        +@PutMapping("/{id}") modifierOrphelin(@PathVariable Long id, @RequestBody OrphelinRequest request) ResponseEntity~OrphelinResponse~
        +@GetMapping("/disponibles") listerOrphelinsDisponibles() ResponseEntity~List~OrphelinResponse~~
        +@PatchMapping("/{id}/statut") changerStatutOrphelin(@PathVariable Long id, @RequestBody StatutRequest request) ResponseEntity~Void~
        +@DeleteMapping("/{id}") supprimerOrphelin(@PathVariable Long id) ResponseEntity~Void~
    }
    
    class DemandeAdoptionController {
        <<RestController>>
        <<RequestMapping("/api/demandes-adoption")>>
        -DemandeAdoptionService demandeService
        
        +@GetMapping("") listerDemandes() ResponseEntity~List~DemandeAdoptionResponse~~
        +@GetMapping("/{id}") obtenirDemandeParId(@PathVariable Long id) ResponseEntity~DemandeAdoptionResponse~
        +@PostMapping("") creerDemande(@RequestBody DemandeRequest request) ResponseEntity~DemandeAdoptionResponse~
        +@PutMapping("/{id}") modifierDemande(@PathVariable Long id, @RequestBody DemandeRequest request) ResponseEntity~DemandeAdoptionResponse~
        +@PatchMapping("/{id}/valider") validerDemande(@PathVariable Long id) ResponseEntity~Void~
        +@PatchMapping("/{id}/rejeter") rejeterDemande(@PathVariable Long id, @RequestBody RejetRequest request) ResponseEntity~Void~
        +@GetMapping("/adoptant/{adoptantId}") obtenirDemandesAdoptant(@PathVariable Long adoptantId) ResponseEntity~List~DemandeAdoptionResponse~~
    }
    
    %% Package Security
    class JwtService {
        -String secretKey
        -long jwtExpiration
        -long refreshExpiration
        
        +extractToken(String token) String
        +extractEmail(String token) String
        +extractExpiration(String token) Date
        +generateToken(UserDetails userDetails) String
        +generateRefreshToken(UserDetails userDetails) String
        +isTokenValid(String token, UserDetails userDetails) boolean
        +isTokenExpired(String token) boolean
    }
    
    class JwtAuthenticationFilter {
        <<Component>>
        -JwtService jwtService
        -UserDetailsService userDetailsService
        
        +doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) void
    }
    
    class SecurityConfig {
        <<Configuration>>
        <<EnableWebSecurity>>
        -UserDetailsService userDetailsService
        -JwtAuthenticationFilter jwtAuthFilter
        
        +securityFilterChain(HttpSecurity http) SecurityFilterChain
        +authenticationProvider() AuthenticationProvider
        +authenticationManager(AuthenticationConfiguration config) AuthenticationManager
        +passwordEncoder() PasswordEncoder
        +userDetailsService() UserDetailsService
    }
    
    %% Relations entre les classes
    Utilisateur "1" --> "0..1" Adoptant : a
    Utilisateur "1" --> "0..1" AgentSocial : est
    
    Adoptant "1" --> "0..*" DemandeAdoption : dépose
    Orphelin "1" --> "0..*" DemandeAdoption : concerne
    DemandeAdoption "1" --> "0..*" EnqueteSociale : nécessite
    
    Document "1" --> "0..1" Utilisateur : appartient
    Document "1" --> "0..1" DemandeAdoption : appartient
    Document "1" --> "0..1" Orphelin : appartient
    Document "1" --> "0..1" EnqueteSociale : appartient
    
    %% Relations Service-Repository
    UtilisateurService --> UtilisateurRepository
    AdoptantService --> AdoptantRepository
    OrphelinService --> OrphelinRepository
    DemandeAdoptionService --> DemandeAdoptionRepository
    EnqueteSocialeService --> EnqueteSocialeRepository
    DocumentService --> DocumentRepository
    
    %% Relations Controller-Service
    AuthController --> UtilisateurService
    UtilisateurController --> UtilisateurService
    AdoptantController --> AdoptantService
    OrphelinController --> OrphelinService
    DemandeAdoptionController --> DemandeAdoptionService
    
    %% Relations Security
    JwtAuthenticationFilter --> JwtService
    JwtAuthenticationFilter --> UserDetailsService
    SecurityConfig --> JwtAuthenticationFilter
    SecurityConfig --> UserDetailsService
```

## Description Textuelle des Classes Backend

### Package Entités JPA

#### Utilisateur
Entité principale implémentant UserDetails pour Spring Security.

**Annotations JPA :**
- `@Entity` : Entité JPA
- `@Table("utilisateurs")` : Table MySQL
- `@UserDetails` : Intégration Spring Security

**Fonctionnalités clés :**
- Authentification via email/mot de passe
- Gestion des rôles et statuts
- Tokens de vérification email

#### Adoptant, Orphelin, DemandeAdoption
Entités métier avec relations JPA :
- `@ManyToOne` et `@OneToMany` pour les associations
- `@JoinColumn` pour les clés étrangères
- Validation des données avec `@Valid`

### Package DTOs (Data Transfer Objects)

#### UtilisateurResponse, AuthResponse
DTOs pour les réponses API avec sélectivité des données.

#### InscriptionRequest, LoginRequest
DTOs pour les requêtes avec validation `@NotBlank`, `@Email`.

### Package Repositories

#### Interfaces JpaRepository
- Méthodes de base CRUD automatiques
- Méthodes personnalisées avec dérivations de noms
- `@Query` pour les requêtes complexes

### Package Services

#### Logique métier avec @Service
- Validation des règles métier
- Gestion des transactions `@Transactional`
- Mapping entre entités et DTOs

### Package Controllers

#### API REST avec @RestController
- Endpoints RESTful respectant les conventions
- Validation avec `@Valid`
- Gestion des erreurs avec `@ExceptionHandler`
- Sécurité avec `@PreAuthorize`

### Package Security

#### Configuration JWT complète
- Génération et validation des tokens
- Filtre d'authentification
- Configuration des CORS et permissions

## Architecture Backend SGAOA

### 🏗️ **Structure en Couches**

1. **Controllers** : API REST (/api/*)
2. **Services** : Logique métier
3. **Repositories** : Accès données (JPA)
4. **Entities** : Modèle de données
5. **DTOs** : Transfert d'objets
6. **Security** : Authentification/Authorization

### 🔐 **Sécurité Intégrée**

- JWT stateless avec refresh tokens
- Rôles et permissions granulaires
- CORS configuré pour Angular/React
- Validation des inputs

### 📊 **Base de Données MySQL**

- Entités JPA avec relations complètes
- Indexation automatique des clés étrangères
- Auditing avec timestamps
- Enumérations pour l'intégrité référentielle

### 🚀 **Performance & Scalabilité**

- Pagination avec `Pageable`
- Cache secondaire configurable
- Connexion pool avec HikariCP
- Async processing possible

**Ce diagramme de classes backend est optimisé pour Spring Boot et prêt pour l'implémentation !**
