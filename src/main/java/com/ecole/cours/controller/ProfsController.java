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

import com.ecole.cours.entity.Prof;
import com.ecole.cours.entity.SeancesCours;
import com.ecole.cours.repository.ProfRepository;
import com.ecole.cours.repository.SeancesCoursRepository;

import jakarta.validation.Valid;

@Controller
public class ProfsController {

    private String deleteMessage = " ";
    @Autowired
    private ProfRepository profRepository;
    @Autowired
    private SeancesCoursRepository seancesCoursRepository;

    @GetMapping("/profs")
    public String profs(Model model){
        List<Prof> profs = profRepository.findAll();
        model.addAttribute("profs", profs);
        model.addAttribute("deleteMessage", deleteMessage);
        deleteMessage = " ";
        return "profs/profs";
    }
    
    @GetMapping("/profsRegistration")
    public String profRegistration(Model model){

        model.addAttribute("prof",new Prof());
        return "profs/profsRegistration";
    }

    @PostMapping("/profsRegistrationForm")
    public String profsRegistrationForm(@Valid Prof prof , Errors errors){

        String nomProf = prof.getNom().trim().toLowerCase();
        prof.setNom(nomProf);
        String prenomProf = prof.getPrenom().trim().toLowerCase();
        prof.setPrenom(prenomProf);

        if (errors.hasErrors()) {

            return"redirect:/profsRegistration";
            
        }
        profRepository.save(prof);
        return"redirect:/profsRegistration";
    }

    @GetMapping("/profsUpdate/{id}")
    public String profsUpdate(@PathVariable Long id , Model model){

        Optional<Prof> optionalProf = profRepository.findById(id);

      if (optionalProf.isPresent()) {

            model.addAttribute("prof", optionalProf.get());
      }

        return "profs/profsUpdate";
    }

    @PostMapping("/profsRegistrationFormUpdate")
    public String profsRegistrationFormUpdate(Prof updateProf ){

        profRepository.save(updateProf);
     
        return"redirect:/profs";
    }


    @GetMapping("/profsDelete/{id}")
    public String profsDelete(@PathVariable Long id){

        Optional<SeancesCours> seances = seancesCoursRepository.findByProfId(id);

        if (seances.isEmpty()) {
            
            profRepository.deleteById(id);

       }else{

           deleteMessage =" Le prof " + seances.get().getProf().getNom() + "  effectue déjà des seances de cours ";
       }

        return"redirect:/profs"; 
    }


}
