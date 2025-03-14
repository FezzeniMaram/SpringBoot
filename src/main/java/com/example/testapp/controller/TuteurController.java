package com.example.testapp.controller;

import com.example.testapp.entities.Tuteur;
import com.example.testapp.services.TuteurInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tuteur")
public class TuteurController {
    @Autowired
    TuteurInterface tuteurInterface;

    @GetMapping("/add")
    public Tuteur inscrireTuteur(@RequestBody Tuteur tuteur){
        return tuteurInterface.inscrireTuteur(tuteur);
    }

    @DeleteMapping("/delete/{id}")
    public void  deleteTutuer( @PathVariable  Long id){
         tuteurInterface.deleteTuteur(id);
    }

    @PatchMapping("/update/{id}")
        public Tuteur updateTuteur(@PathVariable Long id, @RequestBody Tuteur tuteur ){
            return tuteurInterface.updateTuteur(id, tuteur);
        }
    @GetMapping("findAll")
    public List <Tuteur> getAllTuteurs (){
        return tuteurInterface.getAllTuteurs();
    }
    @GetMapping("finById/{id}")
    public Tuteur getTuteurById(@PathVariable  Long id){
        return tuteurInterface.getTuteurById(id);
    }
}
