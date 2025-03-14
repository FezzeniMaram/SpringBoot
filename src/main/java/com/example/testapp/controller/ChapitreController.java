package com.example.testapp.controller;

import com.example.testapp.entities.Chapitre;
import com.example.testapp.services.ChapitreIntreface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chapitre")
public class ChapitreController {
    @Autowired
    ChapitreIntreface chapitreIntreface;

    @GetMapping("/add")
    public Chapitre addChapitre(@RequestBody Chapitre chapitre){
        return chapitreIntreface.addChapitre(chapitre);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteChapitre(@PathVariable Long id){
        chapitreIntreface.deletChapitre(id);
    }
    @PatchMapping("/update/{id}")
    public Chapitre updateChappitre(@PathVariable Long id, @RequestBody Chapitre chapitre){
        return chapitreIntreface.updateChapitre(id, chapitre);
    }
    @GetMapping("/getAllChapitre")
    public List<Chapitre> getAllChapitre (){
        return chapitreIntreface.getAllChapitre();
    }
    @GetMapping("/getById/{id}")
    public Chapitre getChapitreById(@PathVariable  Long id){
        return  chapitreIntreface.getChapitreById(id);
    }
}
