package com.sgaoa.dto.response;

import com.sgaoa.enums.Role;
import com.sgaoa.enums.StatutCompte;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurResponse {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private Role role;
    private StatutCompte statut;
    private Boolean emailVerifie;
    private LocalDateTime dateCreation;
    private LocalDateTime dernierConnexion;
}