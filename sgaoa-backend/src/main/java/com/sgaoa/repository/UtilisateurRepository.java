package com.sgaoa.repository;

import com.sgaoa.entity.Utilisateur;
import com.sgaoa.enums.Role;
import com.sgaoa.enums.StatutCompte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    // Trouver par email (pour la connexion)
    Optional<Utilisateur> findByEmail(String email);

    // Vérifier si email existe déjà (inscription)
    boolean existsByEmail(String email);

    // Vérifier si téléphone existe déjà
    boolean existsByTelephone(String telephone);

    // Trouver par token de réinitialisation mot de passe
    Optional<Utilisateur> findByTokenReinitialisation(String token);

    // Trouver par token de vérification email
    Optional<Utilisateur> findByTokenVerificationEmail(String token);

    // Lister par rôle
    List<Utilisateur> findByRole(Role role);

    // Lister par statut
    List<Utilisateur> findByStatut(StatutCompte statut);

    // Lister par rôle ET statut
    List<Utilisateur> findByRoleAndStatut(Role role, StatutCompte statut);
}