package com.sgaoa.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DemandeAdoptionRequest {
    @NotNull(message = "L'identifiant de l'adoptant est obligatoire")
    private Long adoptantId;

    @NotNull(message = "L'identifiant de l'orphelin est obligatoire")
    private Long orphelinId;
}
