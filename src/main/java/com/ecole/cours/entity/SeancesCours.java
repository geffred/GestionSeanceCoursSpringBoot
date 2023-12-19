package com.ecole.cours.entity;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "seances")
public class SeancesCours {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    
    @Column
    @Enumerated(EnumType.STRING)
    private JourSemaine jours;
    
    @Column
    private LocalTime heureDeb;

    @Column
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime heureFin;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Prof prof;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cours cours;

    public SeancesCours(){}

    public SeancesCours(JourSemaine jours , LocalTime heureDeb , LocalTime heureFin){

        this.jours = jours ;
        this.heureDeb = heureDeb ;
        this.heureFin = heureFin ;
    }

    public JourSemaine getJours() {
        return jours;
    }

    public LocalTime getHeureDeb() {
        return heureDeb;
    }

    public LocalTime getHeureFin() {
        return heureFin;
    }
    public void setHeureDeb(LocalTime heureDeb) {
        this.heureDeb = heureDeb;
    }

    public void setHeureFin(LocalTime heureFin) {
        this.heureFin = heureFin;
    }
    public void setJours(JourSemaine jours) {
        this.jours = jours;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Cours getCours() {
        return cours;
    }
    public void setCours(Cours cours) {
        this.cours = cours;
    }
    public Prof getProf() {
        return prof;
    }
    public void setProf(Prof prof) {
        this.prof = prof;
    }
    
}
