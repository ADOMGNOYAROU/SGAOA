package com.sgaoa.entity;

import com.sgaoa.enums.StatutPaiement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "paiements")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String reference;

    @Column(nullable = false)
    private BigDecimal montant;

    @Column(name = "date_paiement")
    private LocalDate datePaiement;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private StatutPaiement statut = StatutPaiement.EN_ATTENTE;

    @Column(name = "mode_paiement")
    private String modePaiement;

    @Column(name = "transaction_id")
    private String transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adoptant_id", nullable = false)
    private Adoptant adoptant;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demande_adoption_id")
    private DemandeAdoption demandeAdoption;

    @PrePersist
    protected void onCreate() {
        if (reference == null) {
            reference = genererReference();
        }
    }

    private String genererReference() {
        return "PAY-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 1000);
    }

    public boolean effectuerPaiement(BigDecimal montant, String mode) {
        if (montant != null && montant.compareTo(BigDecimal.ZERO) > 0 && mode != null) {
            this.montant = montant;
            this.modePaiement = mode;
            this.statut = StatutPaiement.EFFECTUÉ;
            this.datePaiement = LocalDate.now();
            return true;
        }
        return false;
    }

    public boolean confirmerPaiement(String transactionId) {
        if (transactionId != null && !transactionId.trim().isEmpty()) {
            this.transactionId = transactionId;
            this.statut = StatutPaiement.EFFECTUÉ;
            this.datePaiement = LocalDate.now();
            return true;
        }
        return false;
    }

    public String genererRecu() {
        if (statut == StatutPaiement.EFFECTUÉ) {
            return "RECU-" + reference + "-" + datePaiement;
        }
        return null;
    }

    public boolean estRegle() {
        return statut == StatutPaiement.EFFECTUÉ;
    }

    public boolean echouer() {
        if (statut != StatutPaiement.REMBOURSÉ) {
            statut = StatutPaiement.ECHOUÉ;
            return true;
        }
        return false;
    }

    public boolean rembourser() {
        if (statut == StatutPaiement.EFFECTUÉ) {
            statut = StatutPaiement.REMBOURSÉ;
            return true;
        }
        return false;
    }
}
