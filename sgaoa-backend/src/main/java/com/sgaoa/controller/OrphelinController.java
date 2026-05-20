package com.sgaoa.controller;

import com.sgaoa.dto.response.OrphelinResponse;
import com.sgaoa.enums.StatutOrphelin;
import com.sgaoa.service.OrphelinService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orphelins")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class OrphelinController {

    private final OrphelinService orphelinService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR', 'PRESIDENT_COMITE', 'AGENT_SOCIAL', 'SECRETAIRE')")
    public ResponseEntity<List<OrphelinResponse>> listerOrphelins() {
        List<OrphelinResponse> orphelins = orphelinService.listerOrphelins();
        return ResponseEntity.ok(orphelins);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR', 'PRESIDENT_COMITE', 'AGENT_SOCIAL', 'SECRETAIRE')")
    public ResponseEntity<OrphelinResponse> obtenirOrphelinParId(@PathVariable Long id) {
        OrphelinResponse orphelin = orphelinService.obtenirOrphelinParId(id);
        return ResponseEntity.ok(orphelin);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR', 'PRESIDENT_COMITE', 'AGENT_SOCIAL')")
    public ResponseEntity<OrphelinResponse> creerOrphelin(@Valid @RequestBody OrphelinService.CreateOrphelinRequest request) {
        OrphelinResponse orphelin = orphelinService.creerOrphelin(request);
        return new ResponseEntity<>(orphelin, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR', 'PRESIDENT_COMITE', 'AGENT_SOCIAL')")
    public ResponseEntity<OrphelinResponse> modifierOrphelin(@PathVariable Long id, 
                                                             @Valid @RequestBody OrphelinService.UpdateOrphelinRequest request) {
        OrphelinResponse orphelin = orphelinService.modifierOrphelin(id, request);
        return ResponseEntity.ok(orphelin);
    }

    @GetMapping("/disponibles")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR', 'PRESIDENT_COMITE', 'AGENT_SOCIAL', 'SECRETAIRE', 'ADOPTANT')")
    public ResponseEntity<List<OrphelinResponse>> listerOrphelinsDisponibles() {
        List<OrphelinResponse> orphelins = orphelinService.listerOrphelinsDisponibles();
        return ResponseEntity.ok(orphelins);
    }

    @PatchMapping("/{id}/statut")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR', 'PRESIDENT_COMITE', 'AGENT_SOCIAL')")
    public ResponseEntity<Void> changerStatutOrphelin(@PathVariable Long id, 
                                                       @RequestBody StatutRequest request) {
        orphelinService.changerStatutOrphelin(id, request.getStatut());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<Void> supprimerOrphelin(@PathVariable Long id) {
        orphelinService.supprimerOrphelin(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR', 'PRESIDENT_COMITE', 'AGENT_SOCIAL', 'SECRETAIRE')")
    public ResponseEntity<List<OrphelinResponse>> rechercherOrphelins(@RequestParam String q) {
        List<OrphelinResponse> orphelins = orphelinService.rechercherOrphelins(q);
        return ResponseEntity.ok(orphelins);
    }

    @GetMapping("/statut/{statut}")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR', 'PRESIDENT_COMITE', 'AGENT_SOCIAL', 'SECRETAIRE')")
    public ResponseEntity<List<OrphelinResponse>> getOrphelinsByStatut(@PathVariable StatutOrphelin statut) {
        List<OrphelinResponse> orphelins = orphelinService.getOrphelinsByStatut(statut);
        return ResponseEntity.ok(orphelins);
    }

    @GetMapping("/sexe/{sexe}")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR', 'PRESIDENT_COMITE', 'AGENT_SOCIAL', 'SECRETAIRE')")
    public ResponseEntity<List<OrphelinResponse>> getOrphelinsBySexe(@PathVariable String sexe) {
        List<OrphelinResponse> orphelins = orphelinService.getOrphelinsBySexe(sexe);
        return ResponseEntity.ok(orphelins);
    }

    @GetMapping("/statistiques")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR', 'PRESIDENT_COMITE', 'SECRETAIRE')")
    public ResponseEntity<?> getStatistiquesOrphelins() {
        return ResponseEntity.ok(orphelinService.getStatistiquesOrphelins());
    }

    // DTO pour le changement de statut
    public static class StatutRequest {
        private StatutOrphelin statut;

        public StatutOrphelin getStatut() {
            return statut;
        }

        public void setStatut(StatutOrphelin statut) {
            this.statut = statut;
        }
    }
}
