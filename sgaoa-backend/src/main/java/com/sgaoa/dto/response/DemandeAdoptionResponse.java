package com.sgaoa.dto.response;

import com.sgaoa.enums.StatutDemande;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemandeAdoptionResponse {
    private Long id;
    private String reference;
    private LocalDate dateDepot;
    private StatutDemande statut;
    private String motifRejet;
    private LocalDate dateDecision;
    private Long adoptantId;
    private Long orphelinId;
}
