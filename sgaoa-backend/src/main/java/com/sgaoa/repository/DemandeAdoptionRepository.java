package com.sgaoa.repository;

import com.sgaoa.entity.DemandeAdoption;
import com.sgaoa.entity.Adoptant;
import com.sgaoa.entity.Orphelin;
import com.sgaoa.enums.StatutDemande;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DemandeAdoptionRepository extends JpaRepository<DemandeAdoption, Long> {

    List<DemandeAdoption> findByAdoptant(Adoptant adoptant);

    List<DemandeAdoption> findByAdoptantId(Long adoptantId);

    List<DemandeAdoption> findByOrphelin(Orphelin orphelin);

    List<DemandeAdoption> findByOrphelinId(Long orphelinId);

    List<DemandeAdoption> findByStatut(StatutDemande statut);

    Optional<DemandeAdoption> findByReference(String reference);

    @Query("SELECT d FROM DemandeAdoption d WHERE d.statut = :statut ORDER BY d.dateDepot DESC")
    List<DemandeAdoption> findByStatutOrderByDateDepotDesc(@Param("statut") StatutDemande statut);

    @Query("SELECT d FROM DemandeAdoption d WHERE d.dateDepot BETWEEN :debut AND :fin")
    List<DemandeAdoption> findByDateDepotBetween(@Param("debut") LocalDate debut, @Param("fin") LocalDate fin);

    @Query("SELECT COUNT(d) FROM DemandeAdoption d WHERE d.statut = :statut")
    long countByStatut(@Param("statut") StatutDemande statut);

    @Query("SELECT d FROM DemandeAdoption d WHERE d.reference LIKE %:reference%")
    List<DemandeAdoption> findByReferenceContaining(@Param("reference") String reference);

    @Query("SELECT d FROM DemandeAdoption d WHERE d.adoptant.nom LIKE %:nom% OR d.adoptant.prenom LIKE %:prenom%")
    List<DemandeAdoption> findByAdoptantNomOrPrenomContaining(@Param("nom") String nom, @Param("prenom") String prenom);

    @Query("SELECT d FROM DemandeAdoption d WHERE d.orphelin.nom LIKE %:nom% OR d.orphelin.prenom LIKE %:prenom%")
    List<DemandeAdoption> findByOrphelinNomOrPrenomContaining(@Param("nom") String nom, @Param("prenom") String prenom);

    @Query("SELECT d FROM DemandeAdoption d WHERE d.statut IN :statuts ORDER BY d.dateDepot DESC")
    List<DemandeAdoption> findByStatutInOrderByDateDepotDesc(@Param("statuts") List<StatutDemande> statuts);

    @Query("SELECT d FROM DemandeAdoption d WHERE d.dateDecision BETWEEN :debut AND :fin")
    List<DemandeAdoption> findByDateDecisionBetween(@Param("debut") LocalDate debut, @Param("fin") LocalDate fin);

    @Query("SELECT d FROM DemandeAdoption d WHERE d.statut = :statut AND d.dateDepot >= :date")
    List<DemandeAdoption> findByStatutAndDateDepotAfter(@Param("statut") StatutDemande statut, @Param("date") LocalDate date);

    Page<DemandeAdoption> findByStatut(StatutDemande statut, Pageable pageable);

    @Query("SELECT d FROM DemandeAdoption d WHERE d.adoptant.id = :adoptantId AND d.statut IN :statuts")
    List<DemandeAdoption> findByAdoptantIdAndStatutIn(@Param("adoptantId") Long adoptantId, @Param("statuts") List<StatutDemande> statuts);

    @Query("SELECT d FROM DemandeAdoption d WHERE d.orphelin.id = :orphelinId AND d.statut != :statutExclu")
    List<DemandeAdoption> findByOrphelinIdAndStatutNot(@Param("orphelinId") Long orphelinId, @Param("statutExclu") StatutDemande statutExclu);
}
