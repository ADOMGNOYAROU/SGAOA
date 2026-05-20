package com.sgaoa.repository;

import com.sgaoa.entity.Orphelin;
import com.sgaoa.enums.StatutOrphelin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrphelinRepository extends JpaRepository<Orphelin, Long> {

    List<Orphelin> findByStatut(StatutOrphelin statut);

    List<Orphelin> findByNomContaining(String nom);

    List<Orphelin> findByPrenomContaining(String prenom);

    @Query("SELECT o FROM Orphelin o WHERE o.statut = :statut ORDER BY o.dateNaissance DESC")
    List<Orphelin> findByStatutOrderByDateNaissanceDesc(@Param("statut") StatutOrphelin statut);

    @Query("SELECT o FROM Orphelin o WHERE o.sexe = :sexe AND o.statut = :statut")
    List<Orphelin> findBySexeAndStatut(@Param("sexe") String sexe, @Param("statut") StatutOrphelin statut);

    @Query("SELECT o FROM Orphelin o WHERE o.dateNaissance BETWEEN :debut AND :fin")
    List<Orphelin> findByDateNaissanceBetween(@Param("debut") LocalDate debut, @Param("fin") LocalDate fin);

    @Query("SELECT COUNT(o) FROM Orphelin o WHERE o.statut = :statut")
    long countByStatut(@Param("statut") StatutOrphelin statut);

    @Query("SELECT o FROM Orphelin o WHERE o.nom LIKE CONCAT('%', :recherche, '%') OR o.prenom LIKE CONCAT('%', :recherche, '%')")
    List<Orphelin> findByNomOrPrenomContaining(@Param("recherche") String recherche);

    @Query("SELECT o FROM Orphelin o WHERE o.etatSante LIKE CONCAT('%', :etat, '%') AND o.statut = :statut")
    List<Orphelin> findByEtatSanteContainingAndStatut(@Param("etat") String etat, @Param("statut") StatutOrphelin statut);

    @Query("SELECT o FROM Orphelin o WHERE o.statut = :statut ORDER BY o.nom ASC")
    List<Orphelin> findDisponibles(@Param("statut") StatutOrphelin statut);

    default List<Orphelin> findDisponibles() {
        return findDisponibles(StatutOrphelin.DISPONIBLE);
    }
}
