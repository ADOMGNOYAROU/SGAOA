package com.sgaoa.controller;

import com.sgaoa.dto.request.CreateUtilisateurRequest;
import com.sgaoa.dto.response.UtilisateurResponse;
import com.sgaoa.service.UtilisateurService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/utilisateurs")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    @GetMapping
    public ResponseEntity<List<UtilisateurResponse>> getAllUtilisateurs() {
        List<UtilisateurResponse> utilisateurs = utilisateurService.getAllUtilisateurs();
        return ResponseEntity.ok(utilisateurs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurResponse> getUtilisateurById(@PathVariable Long id) {
        UtilisateurResponse utilisateur = utilisateurService.getUtilisateurById(id);
        return ResponseEntity.ok(utilisateur);
    }

    @PostMapping
    public ResponseEntity<UtilisateurResponse> createUtilisateur(
            @Valid @RequestBody CreateUtilisateurRequest request) {
        UtilisateurResponse newUtilisateur = utilisateurService.createUtilisateur(request);
        return new ResponseEntity<>(newUtilisateur, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UtilisateurResponse> updateUtilisateur(
            @PathVariable Long id,
            @Valid @RequestBody CreateUtilisateurRequest request) {
        UtilisateurResponse updatedUtilisateur = utilisateurService.updateUtilisateur(id, request);
        return ResponseEntity.ok(updatedUtilisateur);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
        utilisateurService.deleteUtilisateur(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/statut")
    public ResponseEntity<Void> changeStatut(
            @PathVariable Long id,
            @RequestBody Map<String, String> statutRequest) {
        utilisateurService.changeStatut(id, statutRequest.get("statut"));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<UtilisateurResponse>> searchUtilisateurs(@RequestParam String q) {
        List<UtilisateurResponse> utilisateurs = utilisateurService.searchUtilisateurs(q);
        return ResponseEntity.ok(utilisateurs);
    }
}
