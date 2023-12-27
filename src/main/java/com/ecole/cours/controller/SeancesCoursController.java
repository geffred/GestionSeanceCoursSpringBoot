package com.ecole.cours.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
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
    
    private String msgError ="";
    @Autowired
    private SeancesCoursRepository seancesCoursRepository;
    @Autowired
    private ProfRepository profRepository;
    @Autowired
    private CoursRepository coursRepository;
    private final int HEUREMIN =540;
    private final int HEUREMAX=1080; 
    
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
        model.addAttribute("msgError", msgError);
        msgError ="";
        return "seancesCours/seancesCoursRegistration";
    }

    @PostMapping("/seancesCoursRegistrationForm")
    public String seancesCoursRegistrationForm(@Valid SeancesCours seancesCours, Errors errors){

        String nomProf = seancesCours.getProf().getNom();
      
        List<SeancesCours> seances = seancesCoursRepository.findByProfNom(nomProf);

        if (errors.hasErrors()) {

            msgError="Les heures de cours ne doivent pas etre vide";
            
        }
        else {

             int heureDeb = seancesCours.getHeureDeb().getHour()*60 + seancesCours.getHeureDeb().getMinute();
             int heureFin = seancesCours.getHeureFin().getHour()*60 + seancesCours.getHeureFin().getMinute();

             if (!seances.isEmpty()) {
           
                    for (SeancesCours s : seances) {
                        int hd = s.getHeureDeb().getHour()*60 +  s.getHeureDeb().getMinute() ;
                        int hf = s.getHeureFin().getHour()*60 +  s.getHeureFin().getMinute() ;
                        
                        if (((heureDeb>=hd && heureDeb <= hf)|| (heureFin>=hd && heureFin <= hf)) && (s.getJours() == seancesCours.getJours() ) ) {
                        
                            msgError="Ce professeur n'est pas disponible à cette horaire";
                            return"redirect:/seancesCoursRegistration";
                           
                        }
                        
                    }
                }

             if (heureDeb < HEUREMIN || heureFin > HEUREMAX) {
                
                msgError="Les de cours s'éffectue de 9h à 18h";
             }
             else if( heureDeb > heureFin){

                msgError="l'heure de début doit etre inférieur à l'heure de fin";
             }

             else{

                msgError="Enregistrement reussi";
                seancesCoursRepository.save(seancesCours);  

            }
             
        }
       
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
    public String seancesCoursRegistrationFormUpdate(Model model , SeancesCours seancesCoursUpdate ){

        List<Prof> profs = profRepository.findAll();
        List<Cours> courses = coursRepository.findAll();
        String nomProf = seancesCoursUpdate.getProf().getNom();
        List<SeancesCours> seances = seancesCoursRepository.findByProfNom(nomProf);

         model.addAttribute("profs", profs);
         model.addAttribute("courses", courses);
         
        

        int heureDeb = seancesCoursUpdate.getHeureDeb().getHour()*60 + seancesCoursUpdate.getHeureDeb().getMinute();
        int heureFin = seancesCoursUpdate.getHeureFin().getHour()*60 + seancesCoursUpdate.getHeureFin().getMinute();

        if (!seances.isEmpty()) {
           
                    for (SeancesCours s : seances) {
                        int hd = s.getHeureDeb().getHour()*60 +  s.getHeureDeb().getMinute() ;
                        int hf = s.getHeureFin().getHour()*60 +  s.getHeureFin().getMinute() ;

                        if (s.getId() == seancesCoursUpdate.getId() ) {
                            
                                continue;
                        }
                        
                        if (((heureDeb>=hd && heureDeb <= hf)|| (heureFin>=hd && heureFin <= hf)) && (s.getJours() == seancesCoursUpdate.getJours() ) ) {
                        
                            msgError="Ce professeur n'est pas disponible à cette horaire";
                            model.addAttribute("msgError", msgError);
                            msgError ="";
                            return "seancesCours/seancesCoursUpdate";
                           
                        }
                        
                    }
        }
                
        if((heureDeb < HEUREMIN || heureFin > HEUREMAX)){

            msgError="Les de cours s'éffectue de 9h à 18h ";
            msgError="Ce professeur n'est pas disponible à cette horaire";
            model.addAttribute("msgError", msgError);
            msgError ="";
        }
        else if(  heureDeb > heureFin ){

            msgError=" l'heure de début doit etre inférieur à l'heure de fin";
            model.addAttribute("msgError", msgError);
            msgError ="";
        }
       
        else{

             msgError="Enregistrement reussi";
             model.addAttribute("msgError", msgError);
             msgError ="";
             seancesCoursRepository.save(seancesCoursUpdate);   
        }

        

       

         return "seancesCours/seancesCoursUpdate";
    }

    @GetMapping("/seancesCoursDelete/{id}")
    public String seancesCoursDelete(@PathVariable Long id){

        seancesCoursRepository.deleteById(id);
        
        return "redirect:/seancesCours";
    }


}
