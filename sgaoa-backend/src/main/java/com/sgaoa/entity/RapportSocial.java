package com.sgaoa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "rapports_sociaux")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RapportSocial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Column(name = "contenu", columnDefinition = "TEXT", nullable = false)
    private String contenu;

    @Column(name = "date_rapport", nullable = false)
    private LocalDate dateRapport;

    @Column(name = "type_rapport")
    private String typeRapport;

    @Column(nullable = false)
    private String conclusion;

    @Column(name = "recommandations", columnDefinition = "TEXT")
    private String recommandations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_social_id", nullable = false)
    private Utilisateur agentSocial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orphelin_id", nullable = false)
    private Orphelin orphelin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enquete_sociale_id")
    private EnqueteSociale enqueteSociale;

    @PrePersist
    protected void onCreate() {
        if (dateRapport == null) {
            dateRapport = LocalDate.now();
        }
    }

    public boolean estFavorable() {
        return conclusion != null && conclusion.toUpperCase().contains("FAVORABLE");
    }

    public boolean estDefavorable() {
        return conclusion != null && conclusion.toUpperCase().contains("DÉFAVORABLE") 
            || conclusion.toUpperCase().contains("DEFAVORABLE");
    }

    public boolean necessiteSuivi() {
        return recommandations != null && !recommandations.trim().isEmpty();
    }
}
