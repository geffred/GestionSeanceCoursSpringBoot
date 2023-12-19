package com.ecole.cours.entity;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "professeurs")
public class Prof {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id ;

    @Column
    @Pattern(regexp = "^([ ]*[a-zA-Z][ ]*)+$", message = "Seules les lettres sont acceptées")
    @NotEmpty(message = "le champs nom ne doit pas être vide")
    private String nom ;

    @Column
    @Pattern(regexp = "^([ ]*[a-zA-Z][ ]*)+$", message = "Seules les lettres sont acceptées")
    @NotEmpty(message = "le champs prenom ne doit pas être vide")
    private String prenom ;

    @NotNull(message = "la date de naissance ne doit pas être null")
    @Column(name = "Date_naissance")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateNaiss;

    @Column
    @NotNull(message = "le champs sexe ne doit pas être null")
    @Enumerated(EnumType.STRING)
    @NotNull(message = " le champs sexe ne doit pas être vide ")
    private Gender sexe;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "prof_id", updatable = false)
    private List<SeancesCours> SeancesCours = new ArrayList<>();

    public Prof( ){}

    public Prof(String nom , String prenom ,Gender sexe , LocalDate dateNaiss){

        this.nom = nom ;
        this.prenom = prenom ;
        this.sexe = sexe ;
        this.dateNaiss = dateNaiss ;
    }


    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
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

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Gender getSexe() {
        return sexe;
    }

    public void setSexe(Gender sexe) {
        this.sexe = sexe;
    }

    public LocalDate getDateNaiss() {
        return dateNaiss;
    }

    public void setDateNaiss(LocalDate dateNaiss) {
        this.dateNaiss = dateNaiss;
    } 
    public List<SeancesCours> getSeancesCours() {
        return SeancesCours;
    }
    public void setSeancesCours(List<SeancesCours> seancesCours) {
        SeancesCours = seancesCours;
    }
    
    
}
