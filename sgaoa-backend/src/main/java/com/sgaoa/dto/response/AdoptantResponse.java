package com.sgaoa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdoptantResponse {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String profession;
    private String adresse;
    private String situationFamiliale;
    private Integer nombreEnfants;
    private BigDecimal revenuMensuel;
}
