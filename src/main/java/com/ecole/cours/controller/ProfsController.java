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
    private String updateMessage = " ";
    private String errorMessageText = "";
    @Autowired
    private ProfRepository profRepository;
    @Autowired
    private SeancesCoursRepository seancesCoursRepository;

    @GetMapping("/profs")
    public String profs(Model model){
        List<Prof> profs = profRepository.findAll();
        model.addAttribute("profs", profs);
        model.addAttribute("errorMessageText", errorMessageText);
        errorMessageText ="";
        return "profs/profs";
    }
    
    @GetMapping("/profsRegistration")
    public String profRegistration(Model model){

        model.addAttribute("prof",new Prof());
        model.addAttribute("errorMessageText", errorMessageText);
        errorMessageText = "";
        return "profs/profsRegistration";
    }

    @PostMapping("/profsRegistrationForm")
    public String profsRegistrationForm(@Valid Prof prof , Errors errors){

        String nomProf = prof.getNom().trim().toLowerCase();
        prof.setNom(nomProf);
        String prenomProf = prof.getPrenom().trim().toLowerCase();
        prof.setPrenom(prenomProf);

        if (errors.hasErrors()) {

            return"profs/profsRegistration";  
        }
        errorMessageText = "Enregistrement Reussi";
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
    public String profsRegistrationFormUpdate(Model model , @Valid Prof updateProf, Errors errors){

            String nomProf = updateProf.getNom().trim().toLowerCase();
            updateProf.setNom(nomProf);
            String prenomProf = updateProf.getPrenom().trim().toLowerCase();
            updateProf.setPrenom(prenomProf);

            if (errors.hasErrors()) {

                updateMessage ="Une erreur est survenue";
        
            }
            else{

                profRepository.save(updateProf);
                updateMessage ="Mise à jour réussi";
                
            }
            model.addAttribute("updateMessage", updateMessage);
            updateMessage =" ";
            return"profs/profsUpdate";
    }


    @GetMapping("/profsDelete/{id}")
    public String profsDelete(@PathVariable Long id){

       List<SeancesCours> seances = seancesCoursRepository.findByProfId(id);

        if (!seances.isEmpty()) {
            
            errorMessageText =" Le prof "
                                         +profRepository.findById(id).get().getNom()+ 
                                         " " + profRepository.findById(id).get().getPrenom()+
                                "effectue déjà des seances de cours ";
       }
       else{

             errorMessageText =" suppression du prof "+profRepository.findById(id).get().getNom() +"reussi";
             profRepository.deleteById(id);    
       }
       
       

        return"redirect:/profs"; 
    }


}
