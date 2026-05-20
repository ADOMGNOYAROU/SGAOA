package com.sgaoa.dto.response;

import com.sgaoa.enums.StatutOrphelin;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrphelinResponse {
    private Long id;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String lieuNaissance;
    private String sexe;
    private String etatSante;
    private String historiqueMedical;
    private String situationFamiliale;
    private StatutOrphelin statut;
    private Integer age;
}
