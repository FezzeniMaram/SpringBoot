package com.example.testapp.controller;

import com.example.testapp.entities.Etudiant;
import com.example.testapp.entities.Tuteur;
import com.example.testapp.services.TuteurInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tuteur")
@CrossOrigin(origins = "http://localhost:4200")
public class TuteurController {
    @Autowired
    TuteurInterface tuteurInterface;

    @PostMapping("/inscrire")
    public String inscrireTuteur(@RequestBody Tuteur tuteur) {
        String message = tuteurInterface.inscrireTuteur(tuteur);
        return message;
    }

    @PostMapping("/authentification")
    public ResponseEntity<String> login(@RequestBody Map<String, String> requestBody) {
        String emailTuteur = requestBody.get("emailTuteur");
        String motPasseTuteur = requestBody.get("motPasseTuteur");

        String response = tuteurInterface.authenTuteur(emailTuteur, motPasseTuteur);
        return ResponseEntity.ok(response);
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

    @GetMapping("/coursPublier/{idTuteur}")
    public ResponseEntity<?> getCoursPublies(@PathVariable Long idTuteur) {
        Object response = tuteurInterface.getCoursPubliesByTuteur(idTuteur);

        if (response instanceof String) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.ok(response);
        }
    }
}
