package com.ecole.cours.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecole.cours.entity.Gender;
import com.ecole.cours.entity.Prof;

import jakarta.transaction.Transactional;

import java.util.Optional;

public interface ProfRepository extends JpaRepository<Prof , Long> {
    
    @Modifying
    @Transactional
    @Query("UPDATE Prof p SET p.nom =:nom , p.prenom = :prenom , p.sexe = :sexe , p.dateNaiss = :dateNaiss WHERE p.id = :id")
    public void profUpdate(@Param("id") Long id ,
                           @Param("nom") String nom ,
                           @Param("prenom") String prenom ,
                           @Param("sexe") Gender sexe ,
                           @Param("dateNaiss") LocalDate dateNaiss);

    Optional<Prof> findById(Long id);
}
