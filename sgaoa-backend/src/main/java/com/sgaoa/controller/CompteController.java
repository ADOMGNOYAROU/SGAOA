package com.sgaoa.controller;

import com.sgaoa.dto.request.CreerCompteRequest;
import com.sgaoa.dto.response.UtilisateurResponse;
import com.sgaoa.enums.StatutCompte;
import com.sgaoa.service.CompteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comptes")
@RequiredArgsConstructor
@SuppressWarnings("nullness")
public class CompteController {

    private final CompteService compteService;

    // GET /api/comptes
    // Lister tous les comptes
    @GetMapping
    public ResponseEntity<List<UtilisateurResponse>> listerComptes() {
        return ResponseEntity.ok(compteService.listerTousLesComptes());
    }

    // GET /api/comptes/{id}
    // Obtenir un compte par ID
    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurResponse> obtenirCompte(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(compteService.obtenirCompteParId(id));
    }

    // POST /api/comptes
    // Créer un compte (Admin, Président, Secrétaire)
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR', 'PRESIDENT_COMITE', 'SECRETAIRE')")
    public ResponseEntity<UtilisateurResponse> creerCompte(
            @Valid @RequestBody CreerCompteRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(compteService.creerCompte(request));
    }

    // PUT /api/comptes/{id}
    // Modifier un compte
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR', 'PRESIDENT_COMITE', 'SECRETAIRE')")
    public ResponseEntity<UtilisateurResponse> modifierCompte(
            @PathVariable @NonNull Long id,
            @Valid @RequestBody CreerCompteRequest request) {
        return ResponseEntity.ok(compteService.modifierCompte(id, request));
    }

    // PATCH /api/comptes/{id}/statut
    // Changer le statut d'un compte (activer, suspendre...)
    @PatchMapping("/{id}/statut")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR', 'PRESIDENT_COMITE')")
    public ResponseEntity<String> changerStatut(
            @PathVariable @NonNull Long id,
            @RequestParam StatutCompte statut) {
        compteService.changerStatutCompte(id, statut);
        return ResponseEntity.ok("Statut mis à jour avec succès !");
    }

    // DELETE /api/comptes/{id}
    // Supprimer un compte
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<String> supprimerCompte(@PathVariable @NonNull Long id) {
        compteService.supprimerCompte(id);
        return ResponseEntity.ok("Compte supprimé avec succès !");
    }
}