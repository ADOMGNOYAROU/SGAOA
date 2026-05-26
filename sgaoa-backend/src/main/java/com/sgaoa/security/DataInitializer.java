package com.sgaoa.security;

import com.sgaoa.entity.Utilisateur;
import com.sgaoa.enums.Role;
import com.sgaoa.enums.StatutCompte;
import com.sgaoa.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        creerAdminSiAbsent();
    }

    @SuppressWarnings("nullness")
    private void creerAdminSiAbsent() {
        String emailAdmin = "admin@sgaoa.local";

        if (utilisateurRepository.existsByEmail(emailAdmin)) {
            return;
        }

        Utilisateur admin = Utilisateur.builder()
                .nom("Admin")
                .prenom("SGAOA")
                .email(emailAdmin)
                .motDePasse(passwordEncoder.encode("Admin@1234"))
                .telephone(null)
                .role(Role.ADMINISTRATEUR)
                .statut(StatutCompte.ACTIF)
                .tokenVerificationEmail(UUID.randomUUID().toString())
                .emailVerifie(true)
                .build();

        if (admin != null) {
            utilisateurRepository.save(admin);
        }
    }
}
