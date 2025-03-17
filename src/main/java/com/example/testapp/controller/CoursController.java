package com.example.testapp.controller;

import com.example.testapp.entities.Chapitre;
import com.example.testapp.entities.Cours;
import com.example.testapp.entities.Etudiant;
import com.example.testapp.entities.Tuteur;
import com.example.testapp.services.CoursInterface;
import com.example.testapp.services.TuteurInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("cours")
public class CoursController {
    @Autowired
    CoursInterface coursInterface;
    @Autowired
    TuteurInterface tuteurInterface;


    @PostMapping("/add")
    public Cours addCours(@RequestBody Map<String, Object> requestData){

        try {
            Long userId = requestData.get("tuteur_id") != null? Long.valueOf(requestData.get("tuteur_id").toString()):null;


            Tuteur user = tuteurInterface.getTuteurById(userId);
            List< Chapitre > chapiters = new ArrayList<Chapitre>();
            List<Etudiant> etudiants = new ArrayList<Etudiant>();
            Cours cours;
            cours = new Cours(requestData.get("titreCours").toString(),
                    requestData.get("descriptionCours").toString(),
                    Float.parseFloat(requestData.get("montantCours").toString()),user,chapiters, etudiants);

            return coursInterface.addCours(cours);
        } catch (Exception e) {
            System.out.println(e);
            
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCours(@PathVariable Long id){
        coursInterface.deleteCours(id);
    }

    @GetMapping("/getAllCours")
    public List<Cours> getAllCours (){
        return coursInterface.getAllCours();
    }

    @GetMapping("/getById/{id}")
    public Cours getById(@PathVariable Long id){
        return coursInterface.getById(id);
    }

    @PatchMapping("/update/{id}")
    public Cours updateCours(@PathVariable Long id, @RequestBody Cours cours){
        return coursInterface.updateCours(id, cours);
    }
    @GetMapping("/tuteur/{tuteurId}")
    public List<Cours> getCoursByTuteur(@PathVariable Long tuteurId) {
        return coursInterface.getCoursByTuteur(tuteurId);
    }
}
