package com.sgaoa.service;

import com.sgaoa.dto.request.AdoptantRequest;
import com.sgaoa.dto.response.AdoptantResponse;

import java.util.List;
import java.util.Map;

public interface AdoptantService {
    List<AdoptantResponse> listerAdoptants();
    AdoptantResponse obtenirAdoptantParId(Long id);
    AdoptantResponse creerAdoptant(AdoptantRequest request);
    AdoptantResponse modifierAdoptant(Long id, AdoptantRequest request);
    void supprimerAdoptant(Long id);
    Map<String, Object> getStatistiquesAdoptants();
}
