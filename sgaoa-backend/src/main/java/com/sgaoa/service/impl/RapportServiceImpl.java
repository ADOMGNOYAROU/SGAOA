package com.sgaoa.service.impl;

import com.sgaoa.repository.DemandeAdoptionRepository;
import com.sgaoa.repository.OrphelinRepository;
import com.sgaoa.repository.AdoptantRepository;
import com.sgaoa.repository.UtilisateurRepository;
import com.sgaoa.service.RapportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RapportServiceImpl implements RapportService {

    private final OrphelinRepository orphelinRepository;
    private final AdoptantRepository adoptantRepository;
    private final DemandeAdoptionRepository demandeAdoptionRepository;
    private final UtilisateurRepository utilisateurRepository;

    @Override
    public Map<String, Object> getStatistiquesGenerales() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("orphelins", orphelinRepository.count());
        stats.put("adoptants", adoptantRepository.count());
        stats.put("demandes", demandeAdoptionRepository.count());
        stats.put("utilisateurs", utilisateurRepository.count());
        return stats;
    }

    @Override
    public Map<String, Object> getStatistiquesOrphelins() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", orphelinRepository.count());
        stats.put("disponibles", orphelinRepository.countByStatut(com.sgaoa.enums.StatutOrphelin.DISPONIBLE));
        stats.put("en_processus", orphelinRepository.countByStatut(com.sgaoa.enums.StatutOrphelin.EN_PROCESSUS_ADOPTION));
        stats.put("adoptes", orphelinRepository.countByStatut(com.sgaoa.enums.StatutOrphelin.ADOPTÉ));
        return stats;
    }

    @Override
    public Map<String, Object> getStatistiquesAdoptants() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", adoptantRepository.count());
        return stats;
    }

    @Override
    public Map<String, Object> getStatistiquesDemandes() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", demandeAdoptionRepository.count());
        stats.put("en_attente", demandeAdoptionRepository.countByStatut(com.sgaoa.enums.StatutDemande.EN_ATTENTE));
        stats.put("en_instruction", demandeAdoptionRepository.countByStatut(com.sgaoa.enums.StatutDemande.EN_INSTRUCTION));
        stats.put("approuvees", demandeAdoptionRepository.countByStatut(com.sgaoa.enums.StatutDemande.APPROUVÉE));
        stats.put("rejetees", demandeAdoptionRepository.countByStatut(com.sgaoa.enums.StatutDemande.REJETÉE));
        return stats;
    }

    @Override
    public Map<String, Object> getStatistiquesAdoptions() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", demandeAdoptionRepository.countByStatut(com.sgaoa.enums.StatutDemande.COMPLÉTÉE));
        return stats;
    }
}
