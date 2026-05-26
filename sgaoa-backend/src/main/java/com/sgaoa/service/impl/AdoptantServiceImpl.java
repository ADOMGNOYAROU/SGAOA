package com.sgaoa.service.impl;

import com.sgaoa.dto.request.AdoptantRequest;
import com.sgaoa.dto.response.AdoptantResponse;
import com.sgaoa.entity.Adoptant;
import com.sgaoa.exception.BusinessException;
import com.sgaoa.repository.AdoptantRepository;
import com.sgaoa.service.AdoptantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AdoptantServiceImpl implements AdoptantService {

    private final AdoptantRepository adoptantRepository;

    @Override
    public List<AdoptantResponse> listerAdoptants() {
        return adoptantRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AdoptantResponse obtenirAdoptantParId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null");
        }
        Adoptant adoptant = adoptantRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Adoptant introuvable"));
        return toResponse(adoptant);
    }

    @Override
    public AdoptantResponse creerAdoptant(AdoptantRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("La requête ne peut pas être null");
        }
        Adoptant adoptant = Adoptant.builder()
                .profession(request.getProfession())
                .adresse(request.getAdresse())
                .situationFamiliale(request.getSituationFamiliale())
                .nombreEnfants(request.getNombreEnfants())
                .revenuMensuel(request.getRevenuMensuel())
                .build();

        return toResponse(adoptantRepository.save(adoptant));
    }

    @Override
    public AdoptantResponse modifierAdoptant(Long id, AdoptantRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null");
        }
        Adoptant adoptant = adoptantRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Adoptant introuvable"));

        adoptant.setProfession(request.getProfession());
        adoptant.setAdresse(request.getAdresse());
        adoptant.setSituationFamiliale(request.getSituationFamiliale());
        adoptant.setNombreEnfants(request.getNombreEnfants());
        adoptant.setRevenuMensuel(request.getRevenuMensuel());

        return toResponse(adoptantRepository.save(adoptant));
    }

    @Override
    public void supprimerAdoptant(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null");
        }
        if (!adoptantRepository.existsById(id)) {
            throw new BusinessException("Adoptant introuvable");
        }
        adoptantRepository.deleteById(id);
    }

    @Override
    public Map<String, Object> getStatistiquesAdoptants() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", adoptantRepository.count());
        stats.put("actifs", adoptantRepository.count());
        return stats;
    }

    private AdoptantResponse toResponse(Adoptant adoptant) {
        return AdoptantResponse.builder()
                .id(adoptant.getId())
                .nom(adoptant.getNom())
                .prenom(adoptant.getPrenom())
                .email(adoptant.getEmail())
                .telephone(adoptant.getTelephone())
                .profession(adoptant.getProfession())
                .adresse(adoptant.getAdresse())
                .situationFamiliale(adoptant.getSituationFamiliale())
                .nombreEnfants(adoptant.getNombreEnfants())
                .revenuMensuel(adoptant.getRevenuMensuel())
                .build();
    }
}
