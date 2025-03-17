package com.example.testapp.controller;

import com.example.testapp.entities.Chapitre;
import com.example.testapp.entities.Cours;
import com.example.testapp.entities.Video;
import com.example.testapp.services.ChapitreIntreface;
import com.example.testapp.services.CoursInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chapitre")
public class ChapitreController {
    @Autowired
    ChapitreIntreface chapitreIntreface;
    @Autowired
    CoursInterface coursInterface;

    @PostMapping("/addChapitre")

    public Chapitre addChapitre(@RequestBody Map<String, Object> requestData) {
        try {
            Long coursId = requestData.get("id_cour") != null? Long.valueOf(requestData.get("id_cour").toString()):null;
            Cours cours = coursInterface.getCoursById(coursId);
            Chapitre chapitre;
            chapitre = new Chapitre(null,requestData.get("titreChapitre").toString(),
                    requestData.get("typeChapitre").toString(),
                    requestData.get("contenuChapitre").toString(),
                    cours,null);
            return chapitreIntreface.addChapitre(chapitre);
        }catch (Exception e) {
            System.out.println(e);

            throw new RuntimeException(e);}
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
        return  chapitreIntreface.getChapirteById(id);
    }
}
