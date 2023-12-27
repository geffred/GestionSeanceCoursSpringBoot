package com.ecole.cours.controller;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
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
    private String msgError ="";
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
        model.addAttribute("msgError",msgError);
        return"cours/coursRegistration";
    }


    @PostMapping("/coursRegistrationForm")
    public String coursRegistrationForm(Model model, @Valid Cours cours , Errors errors){

        String nomCours = cours.getNom();
        cours.setNom(nomCours.trim().toLowerCase());
        List<Cours> courses = coursRepository.findByNom(cours.getNom());

       
        if (errors.hasErrors()){ 
            
            msgError = " " ;
        }
        
        else if (!courses.isEmpty()) {
            
             msgError = "Ce nom de cours existe déjà" ;
        }
        else {

             coursRepository.save(cours);
             msgError ="enregistrement reussi";
        } 

        model.addAttribute("msgError", msgError);
        msgError ="";

        return"cours/coursRegistration"; 
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
    public String coursRegistrationFormUpdate(Model model, @Valid Cours coursUpdate , Errors errors ){

        
        String nomCours = coursUpdate.getNom();
        coursUpdate.setNom(nomCours.trim().toLowerCase());
        List<Cours> courses = coursRepository.findByNom(coursUpdate.getNom());

       
        if (errors.hasErrors()){ 
            
            msgError = " " ;
        }
        
        else if (!courses.isEmpty()) {
            
             msgError = "Ce nom de cours existe déjà" ;
        }
        else {

            coursRepository.save(coursUpdate);
             msgError ="Enregistrement reussi";
        } 

        model.addAttribute("msgError", msgError);

        return "cours/coursUpdate";

    }

    @GetMapping("/coursDelete/{id}")
    public String coursDelete(@PathVariable Long id){

       List<SeancesCours> optSeance = seancesCoursRepository.findByCoursId(id);

        if (optSeance.isEmpty()) {

             coursRepository.deleteById(id);
        }
        else{

            deleteMessage = " Le cours "+coursRepository.findById(id).get().getNom() + " existe déjà dans une seance de cours.";
        }

        return"redirect:/cours";

    }


}
