package com.sgaoa.repository;

import com.sgaoa.entity.Adoptant;
import com.sgaoa.enums.Role;
import com.sgaoa.enums.StatutCompte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdoptantRepository extends JpaRepository<Adoptant, Long> {

    Optional<Adoptant> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByTelephone(String telephone);

    List<Adoptant> findByRole(Role role);

    List<Adoptant> findByStatut(StatutCompte statut);

    List<Adoptant> findByProfession(String profession);

    @Query("SELECT a FROM Adoptant a WHERE a.situationFamiliale = :situation")
    List<Adoptant> findBySituationFamiliale(@Param("situation") String situation);

    @Query("SELECT COUNT(a) FROM Adoptant a WHERE a.statut = :statut")
    long countByStatut(@Param("statut") StatutCompte statut);

    @Query("SELECT a FROM Adoptant a WHERE a.dateCreation BETWEEN :debut AND :fin")
    List<Adoptant> findByDateCreationBetween(@Param("debut") java.time.LocalDateTime debut, 
                                              @Param("fin") java.time.LocalDateTime fin);

    @Query("SELECT a FROM Adoptant a WHERE a.nom LIKE %:nom% OR a.prenom LIKE %:prenom%")
    List<Adoptant> findByNomOrPrenomContaining(@Param("nom") String nom, @Param("prenom") String prenom);
}
