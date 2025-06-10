package com.example.testapp.controller;

import com.example.testapp.dto.UpdateEtudiantRequest;
import com.example.testapp.entities.Cours;
import com.example.testapp.entities.Etudiant;
import com.example.testapp.services.EtudiantInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/etudiant")
@CrossOrigin(origins = "http://localhost:4200")
public class EtudiantController {

    @Autowired
    EtudiantInterface etudiantInterface;

    @PostMapping("/inscrire")
    public ResponseEntity<Map<String, String>> inscrireEtudiant(@RequestBody Etudiant etudiant) {
        return etudiantInterface.inscrireEtudiant(etudiant);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public void deleteEtudiant(@PathVariable Long id) {
        etudiantInterface.deleteEtudiant(id);
    }

    @PreAuthorize("hasAuthority('ETUDIANT')")
    @PutMapping("/updateNom/{id}")
    public ResponseEntity<Map<String, Object>> updateNom(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {

        Map<String, Object> response = new HashMap<>();
        String ancienMotDePasse = request.get("ancienMotDePasse");
        String nouveauNom = request.get("nouveauNom");

        try {
            Etudiant updatedEtudiant = etudiantInterface.updateNom(id, ancienMotDePasse, nouveauNom);

            response.put("success", true);
            response.put("message", "Nom mis à jour avec succès.");
            response.put("data", updatedEtudiant);

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Erreur lors de la mise à jour du nom.");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PreAuthorize("hasAuthority('ETUDIANT')")
    @PutMapping("/updateMotDePasse/{id}")
    public ResponseEntity<Map<String, Object>> updateMotDePasse(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {

        Map<String, Object> response = new HashMap<>();
        String ancienMotDePasse = request.get("ancienMotDePasse");
        String nouveauMotDePasse = request.get("nouveauMotDePasse");

        try {
            Etudiant updatedEtudiant = etudiantInterface.updateMotDePasse(id, ancienMotDePasse, nouveauMotDePasse);

            response.put("success", true);
            response.put("message", "Mot de passe mis à jour avec succès.");
            response.put("data", updatedEtudiant);

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Erreur lors de la mise à jour du mot de passe.");
            return ResponseEntity.internalServerError().body(response);
        }
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAll")
    public List<Etudiant> getAllEtudiants() {
        return etudiantInterface.getAllEtudiants();
    }

    @PreAuthorize("hasAuthority('ETUDIANT') or hasAuthority('ADMIN')")
    @GetMapping("/getById/{id}")
    public ResponseEntity<Map<String, Object>> getEtudiantById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            Etudiant etudiant = etudiantInterface.getEtudiantById(id);

            if (etudiant != null) {
                response.put("success", true);
                response.put("message", "Étudiant trouvé avec succès.");
                response.put("data", etudiant);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Étudiant introuvable.");
                return ResponseEntity.status(404).body(response);
            }

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Erreur lors de la récupération de l'étudiant.");
            return ResponseEntity.internalServerError().body(response);
        }
    }


    @PostMapping("/authentification")
    public ResponseEntity<String> login(@RequestBody Map<String, String> requestBody) {
        String emailEtudiant = requestBody.get("emailEtudiant");
        String motPasseEtudiant = requestBody.get("motPasseEtudiant");

        String response = etudiantInterface.authenEtudiant(emailEtudiant, motPasseEtudiant);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('ETUDIANT')")
    @PostMapping("/{etudiantId}/inscrire/{coursId}")
    public ResponseEntity<Map<String, Object>> inscrireEtudiantAuCours(@PathVariable Long etudiantId, @PathVariable Long coursId) {
        String message = etudiantInterface.inscrireEtudiantAuCours(etudiantId, coursId);

        boolean success = !message.toLowerCase().contains("déjà inscrit");

        return ResponseEntity.ok(Map.of(
                "success", success,
                "message", message
        ));
    }


    @PreAuthorize("hasAuthority('ETUDIANT')")
    @GetMapping("/getcours/{etudiantId}")
    public ResponseEntity<List<Cours>> getCoursByEtudiant(@PathVariable Long etudiantId) {
        List<Cours> coursList = etudiantInterface.getCoursByEtudiant(etudiantId);
        return ResponseEntity.ok(coursList);
    }

    @PreAuthorize("hasAuthority('ETUDIANT')")
    @GetMapping("/test")
    public String testToken() {
        return "Accès autorisé pour l'étudiant !";
    }


    @PreAuthorize("hasAuthority('ETUDIANT')")
    @DeleteMapping("/{etudiantId}/supprimerCours/{coursId}")
    public ResponseEntity<Map<String, Object>> supprimerCoursEtudiant(
            @PathVariable Long etudiantId,
            @PathVariable Long coursId) {

        String message = etudiantInterface.supprimerCoursEtudiant(etudiantId, coursId);

        boolean success = message.toLowerCase().contains("succès");

        return ResponseEntity.ok(Map.of(
                "success", success,
                "message", message
        ));
    }
}
