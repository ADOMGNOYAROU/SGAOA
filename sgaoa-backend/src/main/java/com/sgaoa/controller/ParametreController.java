package com.sgaoa.controller;

import com.sgaoa.service.ParametreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/parametres")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ParametreController {

    private final ParametreService parametreService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<Map<String, Object>> getParametres() {
        return ResponseEntity.ok(parametreService.getParametres());
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<Map<String, Object>> updateParametres(@RequestBody Map<String, Object> parametres) {
        return ResponseEntity.ok(parametreService.updateParametres(parametres));
    }

    @GetMapping("/systeme")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR', 'PRESIDENT_COMITE')")
    public ResponseEntity<Map<String, Object>> getParametresSysteme() {
        return ResponseEntity.ok(parametreService.getParametresSysteme());
    }

    @GetMapping("/notifications")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR', 'PRESIDENT_COMITE')")
    public ResponseEntity<Map<String, Object>> getParametresNotifications() {
        return ResponseEntity.ok(parametreService.getParametresNotifications());
    }
}
