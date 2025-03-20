package com.example.testapp.controller;

import com.example.testapp.entities.Avis;
import com.example.testapp.entities.Cours;
import com.example.testapp.entities.Etudiant;
import com.example.testapp.services.AvisInterface;
import com.example.testapp.services.CoursInterface;
import com.example.testapp.services.EtudiantInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/avis")

public class AvisController {
    @Autowired
    AvisInterface avisInterface;
    @Autowired
    EtudiantInterface etudiantInterface;
    @Autowired
    CoursInterface coursInterface;

    @PostMapping("/add")
    public Avis addAvis(@RequestBody Map<String, Object> requestData){
        try{
            Long etudiant_id = requestData.get("etudiant_id") != null? Long.valueOf(requestData.get("etudiant_id").toString()):null;
            Etudiant etudiant = etudiantInterface.getEtudiantById(etudiant_id);

           Long cours_id = requestData.get("cours_id") != null? Long.valueOf(requestData.get("cours_id").toString()):null;
            Cours cours = coursInterface.getCoursById(cours_id);
            Avis avis;
            avis = new Avis(null,
                    requestData.get("commentaire_avis").toString(), etudiant, cours);

            return avisInterface.addAvis(avis);
        }catch(Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);}
    }
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        avisInterface.deleteAvis(id);
    }
    @PatchMapping("/update/{id}")
    public Avis udpateAvis(@PathVariable Long id, @RequestBody Avis avis){
        return avisInterface.updateAvis(id, avis);
    }
    @GetMapping("/getAllAvis")
    public List<Avis> getAllAvis(){
        return avisInterface.getAllAvis();
    }
    @GetMapping("/getAvisById/{id}")
    public Avis getAvisById(@PathVariable Long id){
        return avisInterface.getAvisById(id);
    }

    @GetMapping("/getAvisByCours/{coursId}")
    public ResponseEntity<List<Avis>> getAvisByCours(@PathVariable Long coursId) {
        List<Avis> avisList = avisInterface.getAvisBycours(coursId);
        return ResponseEntity.ok(avisList);
    }
}
