package com.sgaoa.controller;

import com.sgaoa.dto.request.AdoptantRequest;
import com.sgaoa.dto.response.AdoptantResponse;
import com.sgaoa.service.AdoptantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adoptants")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AdoptantController {

    private final AdoptantService adoptantService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR', 'PRESIDENT_COMITE', 'SECRETAIRE', 'AGENT_SOCIAL')")
    public ResponseEntity<List<AdoptantResponse>> listerAdoptants() {
        List<AdoptantResponse> adoptants = adoptantService.listerAdoptants();
        return ResponseEntity.ok(adoptants);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR', 'PRESIDENT_COMITE', 'SECRETAIRE', 'AGENT_SOCIAL', 'ADOPTANT')")
    public ResponseEntity<AdoptantResponse> obtenirAdoptant(@PathVariable Long id) {
        AdoptantResponse adoptant = adoptantService.obtenirAdoptantParId(id);
        return ResponseEntity.ok(adoptant);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR', 'PRESIDENT_COMITE')")
    public ResponseEntity<AdoptantResponse> creerAdoptant(@Valid @RequestBody AdoptantRequest request) {
        AdoptantResponse adoptant = adoptantService.creerAdoptant(request);
        return new ResponseEntity<>(adoptant, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR', 'PRESIDENT_COMITE', 'ADOPTANT')")
    public ResponseEntity<AdoptantResponse> modifierAdoptant(@PathVariable Long id,
                                                              @Valid @RequestBody AdoptantRequest request) {
        AdoptantResponse adoptant = adoptantService.modifierAdoptant(id, request);
        return ResponseEntity.ok(adoptant);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<Void> supprimerAdoptant(@PathVariable Long id) {
        adoptantService.supprimerAdoptant(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/statistiques")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR', 'PRESIDENT_COMITE', 'SECRETAIRE')")
    public ResponseEntity<?> getStatistiquesAdoptants() {
        return ResponseEntity.ok(adoptantService.getStatistiquesAdoptants());
    }
}
