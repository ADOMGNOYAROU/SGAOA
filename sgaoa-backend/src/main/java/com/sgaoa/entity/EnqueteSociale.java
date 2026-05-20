package com.sgaoa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "enquetes_sociales")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnqueteSociale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_visite", nullable = false)
    private LocalDate dateVisite;

    @Column(name = "lieu_visite", nullable = false)
    private String lieuVisite;

    @Column(name = "rapport_visite", columnDefinition = "TEXT")
    private String rapportVisite;

    @Column(nullable = false)
    private String conclusion;

    private String recommandation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_social_id", nullable = false)
    private Utilisateur agentSocial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demande_adoption_id", nullable = false)
    private DemandeAdoption demandeAdoption;

    @OneToMany(mappedBy = "enqueteSociale", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Document> photos;

    @OneToMany(mappedBy = "enqueteSociale", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Document> documents;

    @PrePersist
    protected void onCreate() {
        if (dateVisite == null) {
            dateVisite = LocalDate.now();
        }
    }

    public boolean realiserVisite() {
        return dateVisite != null && lieuVisite != null;
    }

    public boolean redigerRapport(String rapport) {
        if (rapport != null && !rapport.trim().isEmpty()) {
            this.rapportVisite = rapport;
            return true;
        }
        return false;
    }

    public boolean ajouterPhotos(List<Document> photosList) {
        if (photos != null) {
            this.photos.addAll(photosList);
            return true;
        }
        this.photos = photosList;
        return true;
    }

    public boolean validerEnquete() {
        return rapportVisite != null && conclusion != null && !conclusion.trim().isEmpty();
    }
}
