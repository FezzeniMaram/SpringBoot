package com.example.testapp.controller;

import com.example.testapp.dto.ApiResponse;
import com.example.testapp.entities.Avis;
import com.example.testapp.entities.Cours;
import com.example.testapp.entities.Etudiant;
import com.example.testapp.entities.Tuteur;
import com.example.testapp.repository.TuteurRepository;
import com.example.testapp.services.AvisInterface;
import com.example.testapp.services.CoursInterface;
import com.example.testapp.services.EtudiantInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/avis")
public class AvisController {

    @Autowired
    private AvisInterface avisInterface;

    @Autowired
    private EtudiantInterface etudiantInterface;

    @Autowired
    private CoursInterface coursInterface;

    @Autowired
    private TuteurRepository tuteurRepository;

    // ✅ Ajouter un avis (étudiant ou tuteur)
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('ETUDIANT', 'TUTEUR')")
    public ApiResponse<Avis> addAvis(@RequestBody Map<String, Object> requestData) {
        try {
            String email = getCurrentEmail();
            Etudiant etudiant = etudiantInterface.getEtudiantByEmail(email);
            Optional<Tuteur> optionalTuteur  = tuteurRepository.findByEmailTuteur(email);
            Long coursId = Long.valueOf(requestData.get("cours_id").toString());
            Cours cours = coursInterface.getCoursById(coursId);

            Avis avis = new Avis();
            avis.setCommentaireAvis(requestData.get("commentaire_avis").toString());
            avis.setCours(cours);

            if (etudiant != null) {
                avis.setEtudiant(etudiant);
            } else if (optionalTuteur  != null) {
                avis.setTuteur(optionalTuteur.get());
            }

            Avis saved = avisInterface.addAvis(avis);
            return new ApiResponse<>(true, "Avis ajouté avec succès", saved);

        } catch (Exception e) {
            return new ApiResponse<>(false, "Erreur : " + e.getMessage(), null);
        }
    }

    // ✅ Supprimer un avis
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ETUDIANT', 'TUTEUR', 'ADMIN')")
    public ApiResponse<Void> deleteAvis(@PathVariable Long id) {
        try {
            Avis avis = avisInterface.getAvisById(id);
            if (avis == null) {
                return new ApiResponse<>(false, "Avis introuvable", null);
            }

            String email = getCurrentEmail();
            boolean isAdmin = hasRole("ADMIN");

            // Auteur étudiant
            if (avis.getEtudiant() != null && avis.getEtudiant().getEmailEtudiant().equals(email)) {
                avisInterface.deleteAvis(id);
                return new ApiResponse<>(true, "Avis supprimé (étudiant)", null);
            }

            // Auteur tuteur
            if (avis.getTuteur() != null && avis.getTuteur().getEmailTuteur().equals(email)) {
                avisInterface.deleteAvis(id);
                return new ApiResponse<>(true, "Avis supprimé (tuteur auteur)", null);
            }

            // Tuteur du cours
            Cours cours = avis.getCours();
            Tuteur tuteur = cours.getTuteur();
            if (tuteur != null && tuteur.getEmailTuteur().equals(email)) {
                avisInterface.deleteAvis(id);
                return new ApiResponse<>(true, "Avis supprimé (tuteur du cours)", null);
            }

            // Admin
            if (isAdmin) {
                avisInterface.deleteAvis(id);
                return new ApiResponse<>(true, "Avis supprimé (admin)", null);
            }

            return new ApiResponse<>(false, "Non autorisé à supprimer cet avis", null);

        } catch (Exception e) {
            return new ApiResponse<>(false, "Erreur : " + e.getMessage(), null);
        }
    }

    // ✅ Modifier un avis
    @PatchMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ETUDIANT', 'TUTEUR')")
    public ApiResponse<Avis> updateAvis(@PathVariable Long id, @RequestBody Avis avisModif) {
        try {
            Avis avis = avisInterface.getAvisById(id);
            if (avis == null) {
                return new ApiResponse<>(false, "Avis introuvable", null);
            }

            String email = getCurrentEmail();

            // Étudiant auteur
            if (avis.getEtudiant() != null && avis.getEtudiant().getEmailEtudiant().equals(email)) {
                avis.setCommentaireAvis(avisModif.getCommentaireAvis());
                return new ApiResponse<>(true, "Avis modifié", avisInterface.updateAvis(id, avis));
            }

            // Tuteur auteur
            if (avis.getTuteur() != null && avis.getTuteur().getEmailTuteur().equals(email)) {
                avis.setCommentaireAvis(avisModif.getCommentaireAvis());
                return new ApiResponse<>(true, "Avis modifié", avisInterface.updateAvis(id, avis));
            }

            return new ApiResponse<>(false, "Vous n'avez pas le droit de modifier cet avis", null);

        } catch (Exception e) {
            return new ApiResponse<>(false, "Erreur : " + e.getMessage(), null);
        }
    }

    // ✅ Obtenir tous les avis
    @GetMapping("/getAllAvis")
    @PreAuthorize("hasAnyAuthority('ETUDIANT', 'TUTEUR', 'ADMIN')")
    public ApiResponse<List<Avis>> getAllAvis() {
        return new ApiResponse<>(true, "Tous les avis récupérés", avisInterface.getAllAvis());
    }

    // ✅ Obtenir un avis par ID
    @GetMapping("/getAvisById/{id}")
    @PreAuthorize("hasAnyAuthority('ETUDIANT', 'TUTEUR', 'ADMIN')")
    public ApiResponse<Avis> getAvisById(@PathVariable Long id) {
        Avis avis = avisInterface.getAvisById(id);
        return (avis != null)
                ? new ApiResponse<>(true, "Avis trouvé", avis)
                : new ApiResponse<>(false, "Avis introuvable", null);
    }

    // ✅ Obtenir les avis d’un cours
    @GetMapping("/getAvisByCours/{coursId}")
    @PreAuthorize("hasAnyAuthority('ETUDIANT', 'TUTEUR', 'ADMIN')")
    public ApiResponse<List<Avis>> getAvisByCours(@PathVariable Long coursId) {
        return new ApiResponse<>(true, "Avis du cours récupérés", avisInterface.getAvisBycours(coursId));
    }

    // ======================
    // 🔐 Méthodes de sécurité
    // ======================
    private String getCurrentEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private boolean hasRole(String roleName) {
        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals(roleName));
    }
}
