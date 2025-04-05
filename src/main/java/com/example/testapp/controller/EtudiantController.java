package com.example.testapp.controller;

import com.example.testapp.entities.Cours;
import com.example.testapp.entities.Etudiant;
import com.example.testapp.services.EtudiantInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/etudiant")
public class EtudiantController {

    @Autowired
    EtudiantInterface etudiantInterface;

    @PostMapping("/inscrire")
    public String inscrireEtudiant(@RequestBody Etudiant etudiant) {
        // Inscription de l'étudiant
        String message = etudiantInterface.inscrireEtudiant(etudiant);
        return message;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEtudiant(@PathVariable Long id) {
        // Suppression de l'étudiant
        etudiantInterface.deleteEtudiant(id);
    }

    @PatchMapping("/update/{id}")
    public Etudiant updateEtudiant(@PathVariable("id") Long id, @RequestBody Etudiant etudiant) {
        // Mise à jour des informations de l'étudiant
        return etudiantInterface.updateEtudiant(id, etudiant);
    }

    @GetMapping("/getAll")
    public List<Etudiant> getAllEtudiants() {
        // Récupérer tous les étudiants
        return etudiantInterface.getAllEtudiants();
    }

    @GetMapping("/getById/{id}")
    public Etudiant getEtudiantById(@PathVariable Long id) {
        // Récupérer un étudiant par son ID
        return etudiantInterface.getEtudiantById(id);
    }

    @PostMapping("/authentification")
    public ResponseEntity<String> login(@RequestBody Map<String, String> requestBody) {
        // Authentification d'un étudiant
        String emailEtudiant = requestBody.get("emailEtudiant");
        String motPasseEtudiant = requestBody.get("motPasseEtudiant");

        String response = etudiantInterface.authenEtudiant(emailEtudiant, motPasseEtudiant);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{etudiantId}/inscrire/{coursId}")
    public ResponseEntity<String> inscrireEtudiantAuCours(@PathVariable Long etudiantId, @PathVariable Long coursId) {
        // Inscrire un étudiant à un cours
        String message = etudiantInterface.inscrireEtudiantAuCours(etudiantId, coursId);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{etudiantId}/cours")
    public ResponseEntity<List<Cours>> getCoursByEtudiant(@PathVariable Long etudiantId) {
        // Récupérer la liste des cours auxquels un étudiant est inscrit
        List<Cours> coursList = etudiantInterface.getCoursByEtudiant(etudiantId);
        return ResponseEntity.ok(coursList);
    }

}
