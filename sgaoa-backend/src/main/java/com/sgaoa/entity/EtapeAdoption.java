package com.sgaoa.entity;

import com.sgaoa.enums.StatutEtape;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "etapes_adoption")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EtapeAdoption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private StatutEtape statut = StatutEtape.NON_DÉMARÉE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsable_id")
    private Utilisateur responsable;

    @Column(columnDefinition = "TEXT")
    private String commentaires;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demande_adoption_id", nullable = false)
    private DemandeAdoption demandeAdoption;

    @PrePersist
    protected void onCreate() {
        if (statut == null) {
            statut = StatutEtape.NON_DÉMARÉE;
        }
    }

    public boolean demarrer() {
        if (statut == StatutEtape.NON_DÉMARÉE) {
            statut = StatutEtape.EN_COURS;
            dateDebut = LocalDate.now();
            return true;
        }
        return false;
    }

    public boolean finaliser() {
        if (statut == StatutEtape.EN_COURS) {
            statut = StatutEtape.TERMINEE;
            dateFin = LocalDate.now();
            return true;
        }
        return false;
    }

    public boolean estEnRetard() {
        if (statut == StatutEtape.EN_COURS && dateDebut != null) {
            // Considère 30 jours comme délai standard
            return dateDebut.plusDays(30).isBefore(LocalDate.now());
        }
        return false;
    }

    public boolean estTerminee() {
        return statut == StatutEtape.TERMINEE && dateFin != null;
    }
}
