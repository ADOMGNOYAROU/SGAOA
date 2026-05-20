package com.sgaoa.service;

import com.sgaoa.dto.response.OrphelinResponse;
import com.sgaoa.enums.StatutOrphelin;

import java.util.List;
import java.util.Map;

public interface OrphelinService {

    OrphelinResponse creerOrphelin(CreateOrphelinRequest request);
    OrphelinResponse modifierOrphelin(Long id, UpdateOrphelinRequest request);
    OrphelinResponse obtenirOrphelinParId(Long id);
    List<OrphelinResponse> listerOrphelins();
    List<OrphelinResponse> listerOrphelinsDisponibles();
    void changerStatutOrphelin(Long id, StatutOrphelin statut);
    void supprimerOrphelin(Long id);
    List<OrphelinResponse> rechercherOrphelins(String query);
    List<OrphelinResponse> getOrphelinsByStatut(StatutOrphelin statut);
    List<OrphelinResponse> getOrphelinsBySexe(String sexe);
    Map<String, Object> getStatistiquesOrphelins();

    // DTOs pour les requêtes
    record CreateOrphelinRequest(
        String nom,
        String prenom,
        String dateNaissance,
        String lieuNaissance,
        String sexe,
        String etatSante,
        String historiqueMedical,
        String situationFamiliale,
        StatutOrphelin statut
    ) {}

    record UpdateOrphelinRequest(
        String nom,
        String prenom,
        String dateNaissance,
        String lieuNaissance,
        String sexe,
        String etatSante,
        String historiqueMedical,
        String situationFamiliale,
        StatutOrphelin statut
    ) {}
}
