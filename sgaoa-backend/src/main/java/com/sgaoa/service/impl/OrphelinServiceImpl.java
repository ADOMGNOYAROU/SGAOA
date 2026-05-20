package com.sgaoa.service.impl;

import com.sgaoa.dto.request.CreateOrphelinRequest;
import com.sgaoa.dto.request.UpdateOrphelinRequest;
import com.sgaoa.dto.response.OrphelinResponse;
import com.sgaoa.entity.Orphelin;
import com.sgaoa.enums.StatutOrphelin;
import com.sgaoa.repository.OrphelinRepository;
import com.sgaoa.service.OrphelinService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("nullness")
public class OrphelinServiceImpl implements OrphelinService {

    private final OrphelinRepository orphelinRepository;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public OrphelinResponse creerOrphelin(CreateOrphelinRequest request) {
        Orphelin orphelin = Orphelin.builder()
                .nom(request.nom())
                .prenom(request.prenom())
                .dateNaissance(LocalDate.parse(request.dateNaissance(), dateFormatter))
                .lieuNaissance(request.lieuNaissance())
                .sexe(request.sexe())
                .etatSante(request.etatSante())
                .historiqueMedical(request.historiqueMedical())
                .situationFamiliale(request.situationFamiliale())
                .statut(request.statut() != null ? request.statut() : StatutOrphelin.DISPONIBLE)
                .build();

        Orphelin savedOrphelin = orphelinRepository.save(orphelin);
        assert savedOrphelin != null;
        return mapToResponse(savedOrphelin);
    }

    @Override
    public OrphelinResponse modifierOrphelin(Long id, UpdateOrphelinRequest request) {
        Orphelin orphelin = orphelinRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orphelin non trouvé avec l'ID: " + id));
        assert orphelin != null;

        if (request.nom() != null)
            orphelin.setNom(request.nom());
        if (request.prenom() != null)
            orphelin.setPrenom(request.prenom());
        if (request.dateNaissance() != null)
            orphelin.setDateNaissance(LocalDate.parse(request.dateNaissance(), dateFormatter));
        if (request.lieuNaissance() != null)
            orphelin.setLieuNaissance(request.lieuNaissance());
        if (request.sexe() != null)
            orphelin.setSexe(request.sexe());
        if (request.etatSante() != null)
            orphelin.setEtatSante(request.etatSante());
        if (request.historiqueMedical() != null)
            orphelin.setHistoriqueMedical(request.historiqueMedical());
        if (request.situationFamiliale() != null)
            orphelin.setSituationFamiliale(request.situationFamiliale());
        if (request.statut() != null)
            orphelin.setStatut(request.statut());

        Orphelin savedOrphelin = orphelinRepository.save(orphelin);
        assert savedOrphelin != null;
        return mapToResponse(savedOrphelin);
    }

    @Override
    @Transactional(readOnly = true)
    public OrphelinResponse obtenirOrphelinParId(Long id) {
        Orphelin orphelin = orphelinRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orphelin non trouvé avec l'ID: " + id));
        assert orphelin != null;
        return mapToResponse(orphelin);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrphelinResponse> listerOrphelins() {
        return orphelinRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrphelinResponse> listerOrphelinsDisponibles() {
        return orphelinRepository.findDisponibles().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void changerStatutOrphelin(Long id, StatutOrphelin statut) {
        Orphelin orphelin = orphelinRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orphelin non trouvé avec l'ID: " + id));
        assert orphelin != null;
        orphelin.setStatut(statut);
        orphelinRepository.save(orphelin);
    }

    @Override
    public void supprimerOrphelin(Long id) {
        assert id != null;
        if (!orphelinRepository.existsById(id)) {
            throw new RuntimeException("Orphelin non trouvé avec l'ID: " + id);
        }
        orphelinRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrphelinResponse> rechercherOrphelins(String query) {
        return orphelinRepository.findByNomOrPrenomContaining(query).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrphelinResponse> getOrphelinsByStatut(StatutOrphelin statut) {
        return orphelinRepository.findByStatut(statut).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrphelinResponse> getOrphelinsBySexe(String sexe) {
        return orphelinRepository.findBySexeAndStatut(sexe, StatutOrphelin.DISPONIBLE).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getStatistiquesOrphelins() {
        Map<String, Object> stats = new HashMap<>();

        stats.put("total", orphelinRepository.count());
        stats.put("disponibles", orphelinRepository.countByStatut(StatutOrphelin.DISPONIBLE));
        stats.put("enProcessus", orphelinRepository.countByStatut(StatutOrphelin.EN_PROCESSUS_ADOPTION));
        stats.put("adoptes", orphelinRepository.countByStatut(StatutOrphelin.ADOPTÉ));
        stats.put("indisponibles", orphelinRepository.countByStatut(StatutOrphelin.INDISPONIBLE));

        // Répartition par sexe
        Map<String, Long> repartitionSexe = new HashMap<>();
        repartitionSexe.put("masculin",
                orphelinRepository.findBySexeAndStatut("M", StatutOrphelin.DISPONIBLE).stream().count());
        repartitionSexe.put("feminin",
                orphelinRepository.findBySexeAndStatut("F", StatutOrphelin.DISPONIBLE).stream().count());
        stats.put("repartitionSexe", repartitionSexe);

        return stats;
    }

    private OrphelinResponse mapToResponse(Orphelin orphelin) {
        return OrphelinResponse.builder()
                .id(orphelin.getId())
                .nom(orphelin.getNom())
                .prenom(orphelin.getPrenom())
                .dateNaissance(orphelin.getDateNaissance().format(dateFormatter))
                .lieuNaissance(orphelin.getLieuNaissance())
                .sexe(orphelin.getSexe())
                .etatSante(orphelin.getEtatSante())
                .historiqueMedical(orphelin.getHistoriqueMedical())
                .situationFamiliale(orphelin.getSituationFamiliale())
                .statut(orphelin.getStatut())
                .age(orphelin.getAge())
                .build();
    }
}
