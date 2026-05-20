package com.sgaoa.entity;

import com.sgaoa.enums.StatutDemande;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "demandes_adoption")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemandeAdoption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String reference;

    @Column(name = "date_depot", nullable = false)
    private LocalDate dateDepot;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private StatutDemande statut = StatutDemande.EN_ATTENTE;

    @Column(name = "motif_rejet", columnDefinition = "TEXT")
    private String motifRejet;

    @Column(name = "date_decision")
    private LocalDate dateDecision;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adoptant_id", nullable = false)
    private Adoptant adoptant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orphelin_id", nullable = false)
    private Orphelin orphelin;

    @OneToMany(mappedBy = "demandeAdoption", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EtapeAdoption> etapes;

    @OneToMany(mappedBy = "demandeAdoption", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Document> documents;

    @OneToMany(mappedBy = "demandeAdoption", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EnqueteSociale> enquetesSociales;

    @OneToOne(mappedBy = "demandeAdoption", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Paiement paiement;

    @PrePersist
    protected void onCreate() {
        dateDepot = LocalDate.now();
        if (reference == null) {
            reference = genererReference();
        }
    }

    private String genererReference() {
        return "ADO-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 1000);
    }

    public boolean valider() {
        if (statut == StatutDemande.COMITE_EXAMEN) {
            statut = StatutDemande.APPROUVÉE;
            dateDecision = LocalDate.now();
            return true;
        }
        return false;
    }

    public boolean rejeter(String motif) {
        if (statut != StatutDemande.COMPLÉTÉE && statut != StatutDemande.REJETÉE) {
            statut = StatutDemande.REJETÉE;
            motifRejet = motif;
            dateDecision = LocalDate.now();
            return true;
        }
        return false;
    }

    public boolean estComplete() {
        return statut == StatutDemande.COMPLÉTÉE;
    }

    public List<EtapeAdoption> suivreProgression() {
        return etapes != null ? etapes : List.of();
    }
}
