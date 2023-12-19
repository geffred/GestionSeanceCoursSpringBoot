package com.ecole.cours.controller;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecole.cours.entity.Cours;
import com.ecole.cours.entity.JourSemaine;
import com.ecole.cours.entity.Prof;
import com.ecole.cours.entity.SeancesCours;
import com.ecole.cours.repository.CoursRepository;
import com.ecole.cours.repository.ProfRepository;
import com.ecole.cours.repository.SeancesCoursRepository;

import jakarta.validation.Valid;



@Controller
public class SeancesCoursController {
    
    @Autowired
    private SeancesCoursRepository seancesCoursRepository;
    @Autowired
    private ProfRepository profRepository;
    @Autowired
    private CoursRepository coursRepository;
    
    @GetMapping("/seancesCours")
    public String seancesCours(Model model , @RequestParam(name = "nomCours", defaultValue = "") String nomCours , @RequestParam( name = "jours" , defaultValue = "") JourSemaine jours){

        List<SeancesCours> seancesCours = new ArrayList<>();

        seancesCours = seancesCoursRepository.findAll();
    
        if (nomCours != "" && jours != null) {
            
            seancesCours = seancesCoursRepository.filterCoursAndJours(nomCours, jours);
        }
        else if (nomCours != "" && jours == null) {
                
                 seancesCours = seancesCoursRepository.findByCoursNomContaining(nomCours);
            }
       
        model.addAttribute("nomSearch",nomCours);
        model.addAttribute("joursSearch", jours);
        model.addAttribute("seancesCours", seancesCours);
        model.addAttribute("seancesFilter",new SeancesCours());
        
        return "seancesCours/seancesCours";

    }

    @GetMapping("/seancesCoursRegistration")
    public String seanceCours(Model model){

        List<Prof> profs = profRepository.findAll();
        List<Cours> courses = coursRepository.findAll();
       
        model.addAttribute("seancesCours", new SeancesCours());
        model.addAttribute("profs", profs);
        model.addAttribute("courses", courses);
        return "seancesCours/seancesCoursRegistration";
    }

    @PostMapping("/seancesCoursRegistrationForm")
    public String seancesCoursRegistrationForm(@Valid SeancesCours seancesCours){
        
       LocalTime heureDeb = seancesCours.getHeureDeb();

        System.out.println(heureDeb);
        seancesCoursRepository.save(seancesCours);        
        return"redirect:/seancesCoursRegistration";
    }
    
    @GetMapping("/seancesCoursUpdate/{id}")
    public String seancesCoursUpdate(@PathVariable Long id , Model model){

        Optional<SeancesCours> optSeancesCours = seancesCoursRepository.findById(id);

        if (optSeancesCours.isPresent()) {
            
            model.addAttribute("seancesCours", optSeancesCours.get());
        }

        List<Prof> profs = profRepository.findAll();
        List<Cours> courses = coursRepository.findAll();
        model.addAttribute("profs", profs);
        model.addAttribute("courses", courses);

        return "seancesCours/seancesCoursUpdate";
    }


    @PostMapping("/seancesCoursRegistrationFormUpdate")
    public String seancesCoursRegistrationFormUpdate(SeancesCours seancesCoursUpdate){

        seancesCoursRepository.save(seancesCoursUpdate);

        return "redirect:/seancesCours";
    }

    @GetMapping("/seancesCoursDelete/{id}")
    public String seancesCoursDelete(@PathVariable Long id){

        seancesCoursRepository.deleteById(id);
        
        return "redirect:/seancesCours";
    }

    

}
