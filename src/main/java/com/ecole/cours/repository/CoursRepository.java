package com.ecole.cours.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ecole.cours.entity.Cours;
import java.util.List;




public interface CoursRepository extends JpaRepository<Cours,Long>{

    Optional<Cours> findById(Long id);
    List<Cours> findByNom(String nom);
   
} 