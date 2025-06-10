package com.example.testapp.controller;

import com.example.testapp.dto.ApiResponse;
import com.example.testapp.entities.Chapitre;
import com.example.testapp.entities.Cours;
import com.example.testapp.services.ChapitreIntreface;
import com.example.testapp.services.CoursInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/chapitre")
public class ChapitreController {

    @Autowired
    private ChapitreIntreface chapitreIntreface;

    @Autowired
    private CoursInterface coursInterface;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('TUTEUR')")
    public ApiResponse<Chapitre> addChapitre(
            @RequestParam("titreChapitre") String titreChapitre,
            @RequestParam("contenuChapitre") String contenuChapitre,
            @RequestParam("id_cour") Long coursId,
            @RequestParam("video") MultipartFile videoFile
    ) {
        try {
            String videoPath = saveVideo(videoFile);
            Cours cours = coursInterface.getCoursById(coursId);

            Chapitre chapitre = new Chapitre(null, titreChapitre, contenuChapitre,  videoPath, cours);
            Chapitre saved = chapitreIntreface.addChapitre(chapitre);
            return new ApiResponse<>(true, "Chapitre ajouté avec succès", saved);

        } catch (IOException e) {
            return new ApiResponse<>(false, "Erreur upload vidéo : " + e.getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Erreur ajout chapitre : " + e.getMessage(), null);
        }
    }

    private String saveVideo(MultipartFile file) throws IOException {
        String uploadDir = "uploads/videos/";


        String uniqueId = IdGenerator.generateId();

        String extension = "";
        String originalName = file.getOriginalFilename();
        if (originalName != null && originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf('.'));
        }

        String fileName = uniqueId + extension;

        Path path = Paths.get(uploadDir, fileName);
        Files.createDirectories(path.getParent());
        file.transferTo(path);

        return fileName;
    }



    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('TUTEUR', 'ADMIN')")
    public ApiResponse<Void> deleteChapitre(@PathVariable Long id) {
        try {
            chapitreIntreface.deletChapitre(id);
            return new ApiResponse<>(true, "Chapitre supprimé avec succès", null);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Erreur suppression : " + e.getMessage(), null);
        }
    }

    @PatchMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('TUTEUR', 'ADMIN')")
    public ApiResponse<Chapitre> updateChapitre(@PathVariable Long id, @RequestBody Chapitre chapitre) {
        try {
            Chapitre updated = chapitreIntreface.updateChapitre(id, chapitre);
            return new ApiResponse<>(true, "Chapitre modifié", updated);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Erreur update : " + e.getMessage(), null);
        }
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('ETUDIANT', 'TUTEUR', 'ADMIN')")
    public ApiResponse<List<Chapitre>> getAllChapitre() {
        try {
            return new ApiResponse<>(true, "Liste chapitres", chapitreIntreface.getAllChapitre());
        } catch (Exception e) {
            return new ApiResponse<>(false, "Erreur get all : " + e.getMessage(), null);
        }
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasAnyAuthority('ETUDIANT', 'TUTEUR', 'ADMIN')")
    public ApiResponse<Chapitre> getById(@PathVariable Long id) {
        Chapitre chapitre = chapitreIntreface.getChapirteById(id);
        return (chapitre != null)
                ? new ApiResponse<>(true, "Chapitre trouvé", chapitre)
                : new ApiResponse<>(false, "Chapitre introuvable", null);
    }

    @GetMapping("/getChapitresByCours/{coursId}")
    @PreAuthorize("hasAnyAuthority('TUTEUR', 'ETUDIANT', 'ADMIN')")
    public ApiResponse<List<Chapitre>> getByCours(@PathVariable Long coursId) {
        try {
            List<Chapitre> list = chapitreIntreface.getChapitresByCoursId(coursId);
            return new ApiResponse<>(true, "Chapitres du cours", list);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Erreur get chapitres cours : " + e.getMessage(), null);
        }
    }
}