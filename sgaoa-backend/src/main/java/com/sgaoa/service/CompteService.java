package com.sgaoa.service;

import com.sgaoa.dto.request.CreerCompteRequest;
import com.sgaoa.dto.request.InscriptionRequest;
import com.sgaoa.dto.request.LoginRequest;
import com.sgaoa.dto.response.AuthResponse;
import com.sgaoa.dto.response.UtilisateurResponse;
import com.sgaoa.enums.StatutCompte;
import org.springframework.lang.NonNull;

import java.util.List;

public interface CompteService {

    // UC1 - Inscription adoptant
    UtilisateurResponse inscrireAdoptant(InscriptionRequest request);

    // Connexion
    AuthResponse connecter(LoginRequest request);

    // UC2 - Gestion comptes
    UtilisateurResponse creerCompte(CreerCompteRequest request);

    UtilisateurResponse modifierCompte(@NonNull Long id, CreerCompteRequest request);

    void changerStatutCompte(@NonNull Long id, StatutCompte statut);

    void supprimerCompte(@NonNull Long id);

    // Consultation
    List<UtilisateurResponse> listerTousLesComptes();

    UtilisateurResponse obtenirCompteParId(@NonNull Long id);

    // Vérification email
    void verifierEmail(String token);

    // Réinitialisation mot de passe
    void demanderReinitialisation(String email);

    void reinitialiserMotDePasse(String token, String nouveauMotDePasse);
}