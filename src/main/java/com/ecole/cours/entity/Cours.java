package com.ecole.cours.entity;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "cours")
public class Cours {
    
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^([ ]*[a-zA-Z][ ]*)+$", message = "Seules les lettres sont acceptées")
    @Column(unique = true)
    @NotEmpty(message = "Le champs Nom ne doit pas être vide")
    private String nom;

    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JoinColumn(name = "cours_id", updatable = false)
    private List<SeancesCours> SeancesCours = new ArrayList<>();

    public Cours(){ }

    public Cours (String nom){
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public List<SeancesCours> getSeancesCours() {
        return SeancesCours;
    }
    public void setSeancesCours(List<SeancesCours> seancesCours) {
        SeancesCours = seancesCours;
    }
    
}
