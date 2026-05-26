package com.sgaoa.service;

import com.sgaoa.dto.request.DemandeAdoptionRequest;
import com.sgaoa.dto.response.DemandeAdoptionResponse;
import com.sgaoa.enums.StatutDemande;

import java.util.List;
import java.util.Map;

public interface DemandeAdoptionService {
    List<DemandeAdoptionResponse> listerDemandes();
    DemandeAdoptionResponse obtenirDemandeParId(Long id);
    DemandeAdoptionResponse creerDemande(DemandeAdoptionRequest request);
    DemandeAdoptionResponse modifierDemande(Long id, DemandeAdoptionRequest request);
    void validerDemande(Long id);
    void rejeterDemande(Long id, String motif);
    List<DemandeAdoptionResponse> obtenirDemandesAdoptant(Long adoptantId);
    List<DemandeAdoptionResponse> obtenirDemandesParStatut(StatutDemande statut);
    Map<String, Object> getStatistiquesDemandes();
}
