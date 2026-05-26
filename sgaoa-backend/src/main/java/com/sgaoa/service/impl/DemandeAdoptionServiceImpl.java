package com.sgaoa.service.impl;

import com.sgaoa.dto.request.DemandeAdoptionRequest;
import com.sgaoa.dto.response.DemandeAdoptionResponse;
import com.sgaoa.entity.Adoptant;
import com.sgaoa.entity.DemandeAdoption;
import com.sgaoa.entity.Orphelin;
import com.sgaoa.enums.StatutDemande;
import com.sgaoa.exception.BusinessException;
import com.sgaoa.repository.AdoptantRepository;
import com.sgaoa.repository.DemandeAdoptionRepository;
import com.sgaoa.repository.OrphelinRepository;
import com.sgaoa.service.DemandeAdoptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DemandeAdoptionServiceImpl implements DemandeAdoptionService {

    private final DemandeAdoptionRepository demandeAdoptionRepository;
    private final AdoptantRepository adoptantRepository;
    private final OrphelinRepository orphelinRepository;

    @Override
    public List<DemandeAdoptionResponse> listerDemandes() {
        return demandeAdoptionRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DemandeAdoptionResponse obtenirDemandeParId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null");
        }
        DemandeAdoption demande = demandeAdoptionRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Demande introuvable"));
        return toResponse(demande);
    }

    @Override
    @SuppressWarnings("nullness")
    public DemandeAdoptionResponse creerDemande(DemandeAdoptionRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("La requête ne peut pas être null");
        }

        Adoptant adoptant = adoptantRepository.findById(request.getAdoptantId())
                .orElseThrow(() -> new BusinessException("Adoptant introuvable"));

        Orphelin orphelin = orphelinRepository.findById(request.getOrphelinId())
                .orElseThrow(() -> new BusinessException("Orphelin introuvable"));

        DemandeAdoption demande = DemandeAdoption.builder()
                .reference(genererReference())
                .dateDepot(LocalDate.now())
                .statut(StatutDemande.EN_ATTENTE)
                .adoptant(adoptant)
                .orphelin(orphelin)
                .build();

        return toResponse(demandeAdoptionRepository.save(demande));
    }

    @Override
    @SuppressWarnings("nullness")
    public DemandeAdoptionResponse modifierDemande(Long id, DemandeAdoptionRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null");
        }
        DemandeAdoption demande = demandeAdoptionRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Demande introuvable"));

        // Les relations adoptant et orphelin seront gérées séparément
        // Pour l'instant, on ne modifie que les champs de base

        return toResponse(demandeAdoptionRepository.save(demande));
    }

    @Override
    @SuppressWarnings("nullness")
    public void validerDemande(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null");
        }
        DemandeAdoption demande = demandeAdoptionRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Demande introuvable"));
        demande.setStatut(StatutDemande.APPROUVÉE);
        demande.setDateDecision(LocalDate.now());
        demandeAdoptionRepository.save(demande);
    }

    @Override
    public void rejeterDemande(Long id, String motif) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null");
        }
        DemandeAdoption demande = demandeAdoptionRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Demande introuvable"));
        demande.setStatut(StatutDemande.REJETÉE);
        demande.setMotifRejet(motif);
        demande.setDateDecision(LocalDate.now());
        demandeAdoptionRepository.save(demande);
    }

    @Override
    public List<DemandeAdoptionResponse> obtenirDemandesAdoptant(Long adoptantId) {
        return demandeAdoptionRepository.findByAdoptantId(adoptantId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<DemandeAdoptionResponse> obtenirDemandesParStatut(StatutDemande statut) {
        return demandeAdoptionRepository.findByStatut(statut)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> getStatistiquesDemandes() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", demandeAdoptionRepository.count());
        stats.put("en_attente", demandeAdoptionRepository.countByStatut(StatutDemande.EN_ATTENTE));
        stats.put("en_instruction", demandeAdoptionRepository.countByStatut(StatutDemande.EN_INSTRUCTION));
        stats.put("approuvees", demandeAdoptionRepository.countByStatut(StatutDemande.APPROUVÉE));
        stats.put("rejetees", demandeAdoptionRepository.countByStatut(StatutDemande.REJETÉE));
        stats.put("completees", demandeAdoptionRepository.countByStatut(StatutDemande.COMPLÉTÉE));
        return stats;
    }

    private String genererReference() {
        return "DEM-" + System.currentTimeMillis();
    }

    private DemandeAdoptionResponse toResponse(DemandeAdoption demande) {
        return DemandeAdoptionResponse.builder()
                .id(demande.getId())
                .reference(demande.getReference())
                .dateDepot(demande.getDateDepot())
                .statut(demande.getStatut())
                .motifRejet(demande.getMotifRejet())
                .dateDecision(demande.getDateDecision())
                .adoptantId(demande.getAdoptant() != null ? demande.getAdoptant().getId() : null)
                .orphelinId(demande.getOrphelin() != null ? demande.getOrphelin().getId() : null)
                .build();
    }
}
