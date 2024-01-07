package com.ecole.cours.repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecole.cours.entity.JourSemaine;
//import com.ecole.cours.entity.JourSemaine;
import com.ecole.cours.entity.SeancesCours;


public interface SeancesCoursRepository extends JpaRepository<SeancesCours , Long> {

    Optional<SeancesCours> findById(Long id);

    @Query("SELECT s FROM SeancesCours s WHERE ( s.cours.nom LIKE %:chaine% AND s.jours = :jours )  ")
    List<SeancesCours> filterCoursAndJours(@Param("chaine") String nomCours ,
                            @Param("jours") JourSemaine jours
                               );
    
    List<SeancesCours> findByCoursNomContaining(String c);
    List<SeancesCours> findByProfId(Long id);
    List<SeancesCours> findByCoursId(Long id);
    //List<SeancesCours> findByProfNom(String nom);
    //List<SeancesCours> findByJoursContaining(JourSemaine c);
    
    

    
                                
}
