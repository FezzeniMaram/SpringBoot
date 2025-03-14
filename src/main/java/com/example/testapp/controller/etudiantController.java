package com.example.testapp.controller;

import com.example.testapp.entities.Etudiant;
import com.example.testapp.services.EtudiantInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/etudiant")
public class etudiantController {
    @Autowired
    EtudiantInterface etudiantInterface;

    @PostMapping("/inscrire")
    public String inscrireEtudiant(@RequestBody Etudiant etudiant) {
        String message = etudiantInterface.inscrireEtudiant(etudiant);
        return message;
    }

    @DeleteMapping("/delete/{id}")
    public void  deleteEtudiant(@PathVariable Long id){
        etudiantInterface.deleteEtudiant(id);
    }
    @PatchMapping("/update/{ids}")
    public Etudiant UPDATEEtudiant(@PathVariable("ids")Long id, @RequestBody Etudiant etudiant){
        return etudiantInterface.updateEtudiant(id, etudiant);
    }

    @GetMapping("/getAll")
    public List<Etudiant> getAllEtudiants(){
        return etudiantInterface.getAllEtudiants();
    }

    @GetMapping("/getById/{id}")
    public Etudiant getEtudiantById(@PathVariable Long id){
        return etudiantInterface.getEtudiantById(id);
    }


    @PostMapping("/authentification")
    public ResponseEntity<String> login(@RequestBody Map<String, String> requestBody) {
        String emailEtudiant = requestBody.get("emailEtudiant");
        String motPasseEtudiant = requestBody.get("motPasseEtudiant");

        String response = etudiantInterface.Authen(emailEtudiant, motPasseEtudiant);
        return ResponseEntity.ok(response);
    }



}
