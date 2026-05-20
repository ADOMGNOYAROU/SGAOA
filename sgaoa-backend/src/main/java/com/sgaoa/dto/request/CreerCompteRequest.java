package com.sgaoa.dto.request;

import com.sgaoa.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreerCompteRequest {

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format email invalide")
    private String email;

    @NotNull(message = "Le rôle est obligatoire")
    private Role role;

    @Pattern(regexp = "^[+]?[0-9]{8,15}$", message = "Format téléphone invalide")
    private String telephone;
}