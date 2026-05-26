package com.sgaoa.service;

import com.sgaoa.dto.request.CreateUtilisateurRequest;
import com.sgaoa.dto.response.UtilisateurResponse;
import com.sgaoa.entity.Utilisateur;
import com.sgaoa.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UtilisateurResponse> getAllUtilisateurs() {
        return utilisateurRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public UtilisateurResponse getUtilisateurById(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID: " + id));
        return mapToResponse(utilisateur);
    }

    public UtilisateurResponse createUtilisateur(CreateUtilisateurRequest request) {
        // Vérifier si l'email existe déjà
        if (utilisateurRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Un utilisateur avec cet email existe déjà");
        }

        // Vérifier si le téléphone existe déjà (seulement si non vide)
        if (request.getTelephone() != null && !request.getTelephone().isEmpty()
                && utilisateurRepository.existsByTelephone(request.getTelephone())) {
            throw new RuntimeException("Un utilisateur avec ce téléphone existe déjà");
        }

        // Créer l'utilisateur
        Utilisateur utilisateur = Utilisateur.builder()
                .prenom(request.getPrenom())
                .nom(request.getNom())
                .email(request.getEmail())
                .motDePasse(passwordEncoder.encode(request.getMotDePasse()))
                .telephone(request.getTelephone())
                .adresse(request.getAdresse())
                .role(request.getRole())
                .statut(request.getStatut())
                .emailVerifie(false)
                .build();

        Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);
        return mapToResponse(savedUtilisateur);
    }

    public UtilisateurResponse updateUtilisateur(Long id, CreateUtilisateurRequest request) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID: " + id));

        // Vérifier si l'email existe déjà (pour un autre utilisateur)
        if (!utilisateur.getEmail().equals(request.getEmail()) &&
                utilisateurRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Un utilisateur avec cet email existe déjà");
        }

        // Vérifier si le téléphone existe déjà (pour un autre utilisateur)
        if (!utilisateur.getTelephone().equals(request.getTelephone()) &&
                utilisateurRepository.existsByTelephone(request.getTelephone())) {
            throw new RuntimeException("Un utilisateur avec ce téléphone existe déjà");
        }

        utilisateur.setPrenom(request.getPrenom());
        utilisateur.setNom(request.getNom());
        utilisateur.setEmail(request.getEmail());
        if (request.getMotDePasse() != null && !request.getMotDePasse().isEmpty()) {
            utilisateur.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));
        }
        utilisateur.setTelephone(request.getTelephone());
        utilisateur.setAdresse(request.getAdresse());
        utilisateur.setRole(request.getRole());
        utilisateur.setStatut(request.getStatut());

        Utilisateur updatedUtilisateur = utilisateurRepository.save(utilisateur);
        return mapToResponse(updatedUtilisateur);
    }

    public void deleteUtilisateur(Long id) {
        if (!utilisateurRepository.existsById(id)) {
            throw new RuntimeException("Utilisateur non trouvé avec l'ID: " + id);
        }
        utilisateurRepository.deleteById(id);
    }

    public void changeStatut(Long id, String statut) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID: " + id));
        utilisateur.setStatut(com.sgaoa.enums.StatutCompte.valueOf(statut));
        utilisateurRepository.save(utilisateur);
    }

    public List<UtilisateurResponse> searchUtilisateurs(String query) {
        return utilisateurRepository.findAll().stream()
                .filter(u -> u.getNom().toLowerCase().contains(query.toLowerCase()) ||
                        u.getPrenom().toLowerCase().contains(query.toLowerCase()) ||
                        u.getEmail().toLowerCase().contains(query.toLowerCase()))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private UtilisateurResponse mapToResponse(Utilisateur utilisateur) {
        return UtilisateurResponse.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .email(utilisateur.getEmail())
                .telephone(utilisateur.getTelephone())
                .adresse(utilisateur.getAdresse())
                .role(utilisateur.getRole())
                .statut(utilisateur.getStatut())
                .emailVerifie(utilisateur.getEmailVerifie())
                .dateCreation(utilisateur.getDateCreation())
                .dernierConnexion(utilisateur.getDernierConnexion())
                .build();
    }
}
