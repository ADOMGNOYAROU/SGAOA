package com.sgaoa.controller;

import com.sgaoa.dto.request.DemandeAdoptionRequest;
import com.sgaoa.dto.response.DemandeAdoptionResponse;
import com.sgaoa.enums.StatutDemande;
import com.sgaoa.service.DemandeAdoptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/demandes-adoption")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class DemandeAdoptionController {

    private final DemandeAdoptionService demandeAdoptionService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR', 'PRESIDENT_COMITE', 'SECRETAIRE', 'AGENT_SOCIAL')")
    public ResponseEntity<List<DemandeAdoptionResponse>> listerDemandes() {
        List<DemandeAdoptionResponse> demandes = demandeAdoptionService.listerDemandes();
        return ResponseEntity.ok(demandes);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR', 'PRESIDENT_COMITE', 'SECRETAIRE', 'AGENT_SOCIAL', 'ADOPTANT')")
    public ResponseEntity<DemandeAdoptionResponse> obtenirDemande(@PathVariable Long id) {
        DemandeAdoptionResponse demande = demandeAdoptionService.obtenirDemandeParId(id);
        return ResponseEntity.ok(demande);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR', 'ADOPTANT')")
    public ResponseEntity<DemandeAdoptionResponse> creerDemande(@Valid @RequestBody DemandeAdoptionRequest request) {
        DemandeAdoptionResponse demande = demandeAdoptionService.creerDemande(request);
        return new ResponseEntity<>(demande, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR', 'PRESIDENT_COMITE')")
    public ResponseEntity<DemandeAdoptionResponse> modifierDemande(@PathVariable Long id,
                                                                     @Valid @RequestBody DemandeAdoptionRequest request) {
        DemandeAdoptionResponse demande = demandeAdoptionService.modifierDemande(id, request);
        return ResponseEntity.ok(demande);
    }

    @PatchMapping("/{id}/valider")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR', 'PRESIDENT_COMITE')")
    public ResponseEntity<Void> validerDemande(@PathVariable Long id) {
        demandeAdoptionService.validerDemande(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/rejeter")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR', 'PRESIDENT_COMITE')")
    public ResponseEntity<Void> rejeterDemande(@PathVariable Long id, @RequestBody String motif) {
        demandeAdoptionService.rejeterDemande(id, motif);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/adoptant/{adoptantId}")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR', 'PRESIDENT_COMITE', 'SECRETAIRE', 'ADOPTANT')")
    public ResponseEntity<List<DemandeAdoptionResponse>> obtenirDemandesAdoptant(@PathVariable Long adoptantId) {
        List<DemandeAdoptionResponse> demandes = demandeAdoptionService.obtenirDemandesAdoptant(adoptantId);
        return ResponseEntity.ok(demandes);
    }

    @GetMapping("/statut/{statut}")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR', 'PRESIDENT_COMITE', 'SECRETAIRE')")
    public ResponseEntity<List<DemandeAdoptionResponse>> obtenirDemandesParStatut(@PathVariable StatutDemande statut) {
        List<DemandeAdoptionResponse> demandes = demandeAdoptionService.obtenirDemandesParStatut(statut);
        return ResponseEntity.ok(demandes);
    }

    @GetMapping("/statistiques")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR', 'PRESIDENT_COMITE', 'SECRETAIRE')")
    public ResponseEntity<?> getStatistiquesDemandes() {
        return ResponseEntity.ok(demandeAdoptionService.getStatistiquesDemandes());
    }
}
