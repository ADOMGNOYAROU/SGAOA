package com.sgaoa.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AdoptantRequest {
    @NotBlank(message = "La profession est obligatoire")
    private String profession;

    @NotBlank(message = "L'adresse est obligatoire")
    private String adresse;

    @NotBlank(message = "La situation familiale est obligatoire")
    private String situationFamiliale;

    @NotNull(message = "Le nombre d'enfants est obligatoire")
    private Integer nombreEnfants;

    private BigDecimal revenuMensuel;
}
