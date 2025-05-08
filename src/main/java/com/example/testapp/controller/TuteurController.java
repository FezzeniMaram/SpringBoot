package com.example.testapp.controller;

import com.example.testapp.dto.UpdateTuteurRequest;
import com.example.testapp.entities.Tuteur;
import com.example.testapp.services.TuteurInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tuteur")
@CrossOrigin(origins = "http://localhost:4200")
public class TuteurController {

    @Autowired
    TuteurInterface tuteurInterface;

    // ✅ Inscription : accessible à tous
    @PostMapping("/inscrire")
    public ResponseEntity<Map<String, Object>> inscrireTuteur(@RequestBody Tuteur tuteur) {
        String message = tuteurInterface.inscrireTuteur(tuteur);
        boolean success = !message.toLowerCase().contains("déjà");
        return ResponseEntity.ok(Map.of(
                "success", success,
                "message", message
        ));
    }

    // ❌ À sécuriser plus tard si nécessaire
    @PostMapping("/authentification")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> requestBody) {
        String emailTuteur = requestBody.get("emailTuteur");
        String motPasseTuteur = requestBody.get("motPasseTuteur");

        String response = tuteurInterface.authenTuteur(emailTuteur, motPasseTuteur);
        boolean success = !response.toLowerCase().contains("incorrect") && !response.toLowerCase().contains("introuvable");

        return ResponseEntity.ok(Map.of(
                "success", success,
                "message", response
        ));
    }

    // ✅ Suppression de tuteur : réservé à l'ADMIN
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteTuteur(@PathVariable Long id) {
        tuteurInterface.deleteTuteur(id);
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Tuteur supprimé avec succès."
        ));
    }

    // ✅ Mise à jour du tuteur : accessible uniquement au TUTEUR
    @PreAuthorize("hasAuthority('TUTEUR')")
    @PutMapping("/updateNom/{id}")
    public ResponseEntity<Map<String, Object>> updateNom(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {

        Map<String, Object> response = new HashMap<>();
        String ancienMotDePasse = request.get("ancienMotDePasse");
        String nouveauNom = request.get("nouveauNom");

        try {
            Tuteur updatedTuteur = tuteurInterface.updateNom(id, ancienMotDePasse, nouveauNom);

            response.put("success", true);
            response.put("message", "Nom du tuteur mis à jour avec succès.");
            response.put("data", updatedTuteur);

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Erreur lors de la mise à jour du nom du tuteur.");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    // ✅ Modifier seulement le MOT DE PASSE
    @PreAuthorize("hasAuthority('TUTEUR')")
    @PutMapping("/updateMotDePasse/{id}")
    public ResponseEntity<Map<String, Object>> updateMotDePasse(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {

        Map<String, Object> response = new HashMap<>();
        String ancienMotDePasse = request.get("ancienMotDePasse");
        String nouveauMotDePasse = request.get("nouveauMotDePasse");

        try {
            Tuteur updatedTuteur = tuteurInterface.updateMotDePasse(id, ancienMotDePasse, nouveauMotDePasse);

            response.put("success", true);
            response.put("message", "Mot de passe du tuteur mis à jour avec succès.");
            response.put("data", updatedTuteur);

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Erreur lors de la mise à jour du mot de passe du tuteur.");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    // ✅ Voir tous les tuteurs : réservé à l'ADMIN
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/findAll")
    public ResponseEntity<Map<String, Object>> getAllTuteurs() {
        List<Tuteur> tuteurs = tuteurInterface.getAllTuteurs();
        return ResponseEntity.ok(Map.of(
                "success", true,
                "data", tuteurs
        ));
    }

    // ✅ Récupérer un tuteur : TUTEUR ou ADMIN
    @PreAuthorize("hasAuthority('TUTEUR') or hasAuthority('ADMIN')")
    @GetMapping("/getById/{id}")
    public ResponseEntity<Map<String, Object>> getTuteurById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            Tuteur tuteur = tuteurInterface.getTuteurById(id);

            if (tuteur != null) {
                response.put("success", true);
                response.put("data", tuteur);
                response.put("message", "Tuteur trouvé avec succès.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Tuteur introuvable.");
                return ResponseEntity.status(404).body(response);
            }

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Erreur lors de la récupération du tuteur.");
            return ResponseEntity.internalServerError().body(response);
        }
    }


    // ✅ Voir les cours publiés d’un tuteur : TUTEUR ou ADMIN
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ETUDIANT', 'TUTEUR')")
    @GetMapping("/coursPublier/{idTuteur}")
    public ResponseEntity<Map<String, Object>> getCoursPublies(@PathVariable Long idTuteur) {
        Object response = tuteurInterface.getCoursPubliesByTuteur(idTuteur);
        boolean success = !(response instanceof String && ((String) response).toLowerCase().contains("aucun"));

        return ResponseEntity.ok(Map.of(
                "success", success,
                "data", response
        ));
    }

    // ✅ Test TUTEUR token
    @GetMapping("/nombreCours/{idTuteur}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ETUDIANT', 'TUTEUR')")
    public ResponseEntity<Integer> getNombreCours(@PathVariable Long idTuteur) {
        int nombre = tuteurInterface.getNombreCoursPublies(idTuteur);
        return ResponseEntity.ok(nombre);
    }
}
