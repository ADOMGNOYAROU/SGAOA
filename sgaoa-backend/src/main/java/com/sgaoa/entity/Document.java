package com.sgaoa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "documents")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String type;

    @Column(name = "chemin_fichier", nullable = false)
    private String cheminFichier;

    private Long taille;

    @Column(name = "date_depot", nullable = false)
    private LocalDate dateDepot;

    @Column(nullable = false)
    @Builder.Default
    private String statut = "EN_ATTENTE";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur deposePar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demande_adoption_id")
    private DemandeAdoption demandeAdoption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orphelin_id")
    private Orphelin orphelin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enquete_sociale_id")
    private EnqueteSociale enqueteSociale;

    @PrePersist
    protected void onCreate() {
        dateDepot = LocalDate.now();
    }

    public boolean uploader(String fichier) {
        if (fichier != null && !fichier.trim().isEmpty()) {
            this.cheminFichier = fichier;
            this.statut = "UPLOADÉ";
            return true;
        }
        return false;
    }

    public boolean valider() {
        if ("UPLOADÉ".equals(statut)) {
            this.statut = "VALIDÉ";
            return true;
        }
        return false;
    }

    public boolean supprimer() {
        this.statut = "SUPPRIMÉ";
        return true;
    }

    public boolean estValide() {
        return "VALIDÉ".equals(statut);
    }
}
