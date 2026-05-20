package com.sgaoa.controller;

import com.sgaoa.dto.request.InscriptionRequest;
import com.sgaoa.dto.request.LoginRequest;
import com.sgaoa.dto.response.AuthResponse;
import com.sgaoa.dto.response.UtilisateurResponse;
import com.sgaoa.service.CompteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final CompteService compteService;

    // POST /api/auth/inscription
    // UC1 - Inscription d'un adoptant
    @PostMapping("/inscription")
    public ResponseEntity<UtilisateurResponse> inscrire(
            @Valid @RequestBody InscriptionRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(compteService.inscrireAdoptant(request));
    }

    // POST /api/auth/connexion
    // Connexion et réception du token JWT
    @PostMapping("/connexion")
    public ResponseEntity<AuthResponse> connecter(
            @Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(compteService.connecter(request));
    }

    // GET /api/auth/verifier-email/{token}
    // Vérification de l'email après inscription
    @GetMapping("/verifier-email/{token}")
    public ResponseEntity<String> verifierEmail(@PathVariable String token) {
        compteService.verifierEmail(token);
        return ResponseEntity.ok("Email vérifié avec succès !");
    }

    // POST /api/auth/reinitialiser-mot-de-passe
    // Demande de réinitialisation du mot de passe
    @PostMapping("/reinitialiser-mot-de-passe")
    public ResponseEntity<String> demanderReinitialisation(
            @RequestParam String email) {
        compteService.demanderReinitialisation(email);
        return ResponseEntity.ok("Email de réinitialisation envoyé !");
    }

    // POST /api/auth/nouveau-mot-de-passe
    // Définir le nouveau mot de passe
    @PostMapping("/nouveau-mot-de-passe")
    public ResponseEntity<String> reinitialiserMotDePasse(
            @RequestParam String token,
            @RequestParam String nouveauMotDePasse) {
        compteService.reinitialiserMotDePasse(token, nouveauMotDePasse);
        return ResponseEntity.ok("Mot de passe réinitialisé avec succès !");
    }
}