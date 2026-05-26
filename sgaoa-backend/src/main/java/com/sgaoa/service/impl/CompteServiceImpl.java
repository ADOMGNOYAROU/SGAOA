package com.sgaoa.service.impl;

import com.sgaoa.dto.request.CreerCompteRequest;
import com.sgaoa.dto.request.InscriptionRequest;
import com.sgaoa.dto.request.LoginRequest;
import com.sgaoa.dto.response.AuthResponse;
import com.sgaoa.dto.response.UtilisateurResponse;
import com.sgaoa.entity.Utilisateur;
import com.sgaoa.exception.BusinessException;
import com.sgaoa.enums.Role;
import com.sgaoa.enums.StatutCompte;
import com.sgaoa.repository.UtilisateurRepository;
import com.sgaoa.security.JwtService;
import com.sgaoa.service.CompteService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("nullness")
public class CompteServiceImpl implements CompteService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    // UC1 - Inscription Adoptant
    @Override
    @SuppressWarnings("nullness")
    public UtilisateurResponse inscrireAdoptant(InscriptionRequest request) {
        if (utilisateurRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Un compte existe déjà avec cet email.");
        }
        if (request.getTelephone() != null &&
                utilisateurRepository.existsByTelephone(request.getTelephone())) {
            throw new BusinessException("Ce numéro de téléphone est déjà utilisé.");
        }

        Utilisateur utilisateur = Utilisateur.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .motDePasse(passwordEncoder.encode(request.getMotDePasse()))
                .telephone(request.getTelephone())
                .adresse(request.getAdresse())
                .role(Role.ADOPTANT)
                .statut(StatutCompte.ACTIF)
                .tokenVerificationEmail(UUID.randomUUID().toString())
                .emailVerifie(true)
                .build();

        if (utilisateur != null) {
            return toResponse(utilisateurRepository.save(utilisateur));
        }
        throw new BusinessException("Erreur lors de la création du compte.");
    }

    // Connexion
    @Override
    public AuthResponse connecter(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getMotDePasse()));

        Utilisateur utilisateur = utilisateurRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException("Compte introuvable."));

        utilisateur.setDernierConnexion(LocalDateTime.now());
        utilisateurRepository.save(utilisateur);

        String accessToken = jwtService.generateToken(utilisateur);
        String refreshToken = jwtService.generateRefreshToken(utilisateur);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .utilisateur(toResponse(utilisateur))
                .build();
    }

    // UC2 - Créer un compte
    @Override
    @SuppressWarnings("nullness")
    public UtilisateurResponse creerCompte(CreerCompteRequest request) {
        if (utilisateurRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Un compte existe déjà avec cet email.");
        }

        Utilisateur utilisateur = Utilisateur.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .motDePasse(passwordEncoder.encode(UUID.randomUUID().toString()))
                .telephone(request.getTelephone())
                .role(request.getRole())
                .statut(StatutCompte.ACTIF)
                .emailVerifie(true)
                .build();

        if (utilisateur != null) {
            return toResponse(utilisateurRepository.save(utilisateur));
        }
        throw new BusinessException("Erreur lors de la création du compte.");
    }

    // Modifier un compte
    @Override
    @SuppressWarnings("nullness")
    public UtilisateurResponse modifierCompte(@NonNull Long id, CreerCompteRequest request) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Compte introuvable."));

        utilisateur.setNom(request.getNom());
        utilisateur.setPrenom(request.getPrenom());
        utilisateur.setEmail(request.getEmail());
        utilisateur.setTelephone(request.getTelephone());
        utilisateur.setRole(request.getRole());

        return toResponse(utilisateurRepository.save(utilisateur));
    }

    // Changer le statut
    @Override
    @SuppressWarnings("nullness")
    public void changerStatutCompte(@NonNull Long id, StatutCompte statut) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Compte introuvable."));
        utilisateur.setStatut(statut);
        utilisateurRepository.save(utilisateur);
    }

    // Supprimer un compte
    @Override
    @SuppressWarnings("nullness")
    public void supprimerCompte(@NonNull Long id) {
        if (!utilisateurRepository.existsById(id)) {
            throw new BusinessException("Compte introuvable.");
        }
        utilisateurRepository.deleteById(id);
    }

    // Lister tous les comptes
    @Override
    @Transactional(readOnly = true)
    public List<UtilisateurResponse> listerTousLesComptes() {
        return utilisateurRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Obtenir un compte par ID
    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("nullness")
    public UtilisateurResponse obtenirCompteParId(@NonNull Long id) {
        return utilisateurRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new BusinessException("Compte introuvable."));
    }

    // Vérification email
    @Override
    public void verifierEmail(String token) {
        Utilisateur utilisateur = utilisateurRepository
                .findByTokenVerificationEmail(token)
                .orElseThrow(() -> new BusinessException("Token invalide."));

        utilisateur.setEmailVerifie(true);
        utilisateur.setTokenVerificationEmail(null);
        utilisateur.setStatut(StatutCompte.ACTIF);
        utilisateurRepository.save(utilisateur);
    }

    // Demander réinitialisation mot de passe
    @Override
    public void demanderReinitialisation(String email) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Compte introuvable."));

        utilisateur.setTokenReinitialisation(UUID.randomUUID().toString());
        utilisateur.setExpirationTokenReinitialisation(LocalDateTime.now().plusHours(1));
        utilisateurRepository.save(utilisateur);
    }

    // Réinitialiser mot de passe
    @Override
    public void reinitialiserMotDePasse(String token, String nouveauMotDePasse) {
        Utilisateur utilisateur = utilisateurRepository
                .findByTokenReinitialisation(token)
                .orElseThrow(() -> new BusinessException("Token invalide."));

        if (utilisateur.getExpirationTokenReinitialisation().isBefore(LocalDateTime.now())) {
            throw new BusinessException("Token expiré.");
        }

        utilisateur.setMotDePasse(passwordEncoder.encode(nouveauMotDePasse));
        utilisateur.setTokenReinitialisation(null);
        utilisateur.setExpirationTokenReinitialisation(null);
        utilisateurRepository.save(utilisateur);
    }

    // Convertir Entity → DTO Response
    private UtilisateurResponse toResponse(Utilisateur u) {
        return UtilisateurResponse.builder()
                .id(u.getId())
                .nom(u.getNom())
                .prenom(u.getPrenom())
                .email(u.getEmail())
                .telephone(u.getTelephone())
                .adresse(u.getAdresse())
                .role(u.getRole())
                .statut(u.getStatut())
                .emailVerifie(u.getEmailVerifie())
                .dateCreation(u.getDateCreation())
                .dernierConnexion(u.getDernierConnexion())
                .build();
    }
}