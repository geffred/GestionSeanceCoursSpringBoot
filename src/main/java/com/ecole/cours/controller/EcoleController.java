package com.ecole.cours.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EcoleController {
    
    @GetMapping("/")
    public String home(){

        return"index";
    }

}
