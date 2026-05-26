package com.sgaoa.controller;

import com.sgaoa.service.RapportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/rapports")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class RapportController {

    private final RapportService rapportService;

    @GetMapping("/statistiques-generales")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR', 'PRESIDENT_COMITE', 'SECRETAIRE')")
    public ResponseEntity<Map<String, Object>> getStatistiquesGenerales() {
        return ResponseEntity.ok(rapportService.getStatistiquesGenerales());
    }

    @GetMapping("/statistiques-orphelins")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR', 'PRESIDENT_COMITE', 'SECRETAIRE')")
    public ResponseEntity<Map<String, Object>> getStatistiquesOrphelins() {
        return ResponseEntity.ok(rapportService.getStatistiquesOrphelins());
    }

    @GetMapping("/statistiques-adoptants")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR', 'PRESIDENT_COMITE', 'SECRETAIRE')")
    public ResponseEntity<Map<String, Object>> getStatistiquesAdoptants() {
        return ResponseEntity.ok(rapportService.getStatistiquesAdoptants());
    }

    @GetMapping("/statistiques-demandes")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR', 'PRESIDENT_COMITE', 'SECRETAIRE')")
    public ResponseEntity<Map<String, Object>> getStatistiquesDemandes() {
        return ResponseEntity.ok(rapportService.getStatistiquesDemandes());
    }

    @GetMapping("/statistiques-adoptions")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR', 'PRESIDENT_COMITE', 'SECRETAIRE')")
    public ResponseEntity<Map<String, Object>> getStatistiquesAdoptions() {
        return ResponseEntity.ok(rapportService.getStatistiquesAdoptions());
    }
}
