package com.sgaoa.entity;

import com.sgaoa.enums.StatutOrphelin;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orphelins")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orphelin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @Column(name = "lieu_naissance")
    private String lieuNaissance;

    @Column(nullable = false)
    private String sexe;

    @Column(name = "etat_sante")
    private String etatSante;

    @Column(name = "historique_medical", columnDefinition = "TEXT")
    private String historiqueMedical;

    @Column(name = "situation_familiale", columnDefinition = "TEXT")
    private String situationFamiliale;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private StatutOrphelin statut = StatutOrphelin.DISPONIBLE;

    @OneToMany(mappedBy = "orphelin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Document> documents;

    @OneToMany(mappedBy = "orphelin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RapportSocial> rapportsSociaux;

    @PrePersist
    protected void onCreate() {
        if (statut == null) {
            statut = StatutOrphelin.DISPONIBLE;
        }
    }

    public Integer getAge() {
        if (dateNaissance == null) {
            return null;
        }
        return java.time.Period.between(dateNaissance, LocalDate.now()).getYears();
    }

    public boolean estDisponible() {
        return statut == StatutOrphelin.DISPONIBLE;
    }
}
