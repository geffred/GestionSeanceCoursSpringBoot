package com.ecole.cours.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ecole.cours.entity.Cours;
import com.ecole.cours.entity.SeancesCours;
import com.ecole.cours.repository.CoursRepository;
import com.ecole.cours.repository.SeancesCoursRepository;

import jakarta.validation.Valid;

@Controller
public class CoursController {
    
    List<String> erreurs = new ArrayList<>();
    @Autowired
    CoursRepository coursRepository;
    @Autowired
    SeancesCoursRepository seancesCoursRepository;
    private String deleteMessage = "";

    @GetMapping("/cours")
    public String cours(Model model){
        List< Cours> cours = coursRepository.findAll();
        model.addAttribute("cours",cours);
        model.addAttribute("deleteMessage", deleteMessage);
        deleteMessage ="";
        return"cours/cours";
    }

    @GetMapping("/coursRegistration")
    public String coursRegistration(Model model ){
        
        model.addAttribute("cours", new Cours());
        model.addAttribute("errors",erreurs);
        erreurs = new ArrayList<>();
        return"cours/coursRegistration";
    }


    @PostMapping("/coursRegistrationForm")
    public String coursRegistrationForm( @Valid Cours cours , BindingResult res , Model model  ){

        String nomCours = cours.getNom();
        cours.setNom(nomCours.trim().toLowerCase());

        if (res.hasErrors()){ 
            
            for (int i = 0 ; i<res.getAllErrors().size() ; i++) {
                
                erreurs.add(res.getAllErrors().get(i).getDefaultMessage());
            }
        }
        else if (!coursRepository.findByNom(nomCours).isEmpty()) {

             erreurs.add("Ce cours existe déjà!");
            
        }  
        else{

            erreurs.add("Cours enregistrer avec success ");
            coursRepository.save(cours);
           
        } 

        return"redirect:/coursRegistration"; 
    }

    @GetMapping("/coursUpdate/{id}")
    public String coursUpdate(@PathVariable Long id , Model model){

        Optional<Cours> optCours = coursRepository.findById(id);

        if (optCours.isPresent()) {

            model.addAttribute("cours", optCours.get());
        }

        return "cours/coursUpdate";
    }

    @PostMapping("/coursRegistrationFormUpdate")
    public String coursRegistrationFormUpdate( Cours coursUpdate){

        coursRepository.save(coursUpdate);

        return "redirect:/cours";

    }

    @GetMapping("/coursDelete/{id}")
    public String coursDelete(@PathVariable Long id){

        Optional<SeancesCours> optSeance = seancesCoursRepository.findByCoursId(id);

        if (!optSeance.isPresent()) {

             coursRepository.deleteById(id);
        }
        else{

            deleteMessage = "le cours "+seancesCoursRepository.findById(id).get().getCours().getNom() + " existe déjà dans une seance de cours";
        }

        return"redirect:/cours";

    }


}
