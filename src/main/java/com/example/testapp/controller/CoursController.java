package com.example.testapp.controller;

import com.example.testapp.dto.ApiResponse;
import com.example.testapp.entities.Cours;
import com.example.testapp.entities.Tuteur;
import com.example.testapp.services.CoursInterface;
import com.example.testapp.services.TuteurInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@RestController
@RequestMapping("/cours")
public class CoursController {

    private static final String UPLOAD_DIR = "uploads/images";

    @Autowired
    private CoursInterface coursInterface;

    @Autowired
    private TuteurInterface tuteurInterface;

    // ✅ Ajouter un cours → TUTEUR
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('TUTEUR')") 
    public ApiResponse<Cours> addCours(
            @RequestParam("titreCours") String titreCours,
            @RequestParam("descriptionCours") String descriptionCours,
            @RequestParam("tuteur_id") Long tuteurId,
            @RequestParam("image") MultipartFile imageFile
    ) {
        try {
            String imagePath = saveImage(imageFile);
            Tuteur tuteur = tuteurInterface.getTuteurById(tuteurId);
            Cours cours = new Cours(
                    titreCours,
                    descriptionCours,
                    imagePath,
                    tuteur,
                    new ArrayList<>(),
                    new ArrayList<>()
            );

            Cours savedCours = coursInterface.addCours(cours);
            return new ApiResponse<>(true, "Cours ajouté avec succès", savedCours);

        } catch (IOException e) {
            return new ApiResponse<>(false, "Erreur lors de l'upload de l'image : " + e.getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Erreur lors de l'ajout du cours : " + e.getMessage(), null);
        }
    }

    @PatchMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('TUTEUR', 'ADMIN')")
    public ApiResponse<Cours> updateCours(
            @PathVariable Long id,
            @RequestParam("titreCours") String titreCours,
            @RequestParam("descriptionCours") String descriptionCours,
            @RequestParam(value = "image", required = false) MultipartFile imageFile
    ) {
        try {
            Cours existingCours = coursInterface.getById(id);
            if (existingCours == null) {
                return new ApiResponse<>(false, "Cours introuvable", null);
            }

            existingCours.setTitreCours(titreCours);
            existingCours.setDescriptionCours(descriptionCours);

            if (imageFile != null && !imageFile.isEmpty()) {
                String oldImagePath = existingCours.getImagePath();
                if (oldImagePath != null) {
                    Path oldPath = Paths.get(UPLOAD_DIR).resolve(oldImagePath);
                    if (Files.exists(oldPath)) {
                        Files.delete(oldPath);
                    }
                }

                String newImagePath = saveImage(imageFile);
                existingCours.setImagePath(newImagePath);
            }

            Cours updatedCours = coursInterface.updateCours(id, existingCours);
            return new ApiResponse<>(true, "Cours mis à jour avec succès", updatedCours);

        } catch (IOException e) {
            return new ApiResponse<>(false, "Erreur image : " + e.getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Erreur : " + e.getMessage(), null);
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('TUTEUR', 'ADMIN')")
    public ApiResponse<Void> deleteCours(@PathVariable Long id) {
        try {
            Cours cours = coursInterface.getById(id);
            if (cours == null) {
                return new ApiResponse<>(false, "Cours introuvable", null);
            }

            String imagePath = cours.getImagePath(); // ex: images/xxx.jpg
            if (imagePath != null && !imagePath.isEmpty()) {
                Path imageFile = Paths.get(System.getProperty("user.dir"))
                        .resolve("uploads")
                        .resolve(imagePath);

                if (Files.exists(imageFile)) {
                    Files.delete(imageFile);
                }
            }

            coursInterface.deleteCours(id);

            return new ApiResponse<>(true, "Cours supprimé avec succès", null);

        } catch (IOException e) {
            return new ApiResponse<>(false, "Erreur lors de la suppression de l'image : " + e.getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Erreur lors de la suppression du cours : " + e.getMessage(), null);
        }
    }


    @GetMapping("/getAllCours")
    public ApiResponse<List<Cours>> getAllCours() {
        try {
            return new ApiResponse<>(true, "Liste des cours récupérée", coursInterface.getAllCours());
        } catch (Exception e) {
            return new ApiResponse<>(false, "Erreur récupération des cours : " + e.getMessage(), null);
        }
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasAnyAuthority('ETUDIANT', 'TUTEUR', 'ADMIN')")
    public ApiResponse<Cours> getById(@PathVariable Long id) {
        Cours cours = coursInterface.getById(id);
        return (cours != null)
                ? new ApiResponse<>(true, "Cours trouvé", cours)
                : new ApiResponse<>(false, "Cours introuvable", null);
    }

    @GetMapping("/tuteur/{tuteurId}")
    @PreAuthorize("hasAnyAuthority('TUTEUR', 'ADMIN')")
    public ApiResponse<List<Cours>> getCoursByTuteur(@PathVariable Long tuteurId) {
        try {
            List<Cours> coursList = coursInterface.getCoursByTuteur(tuteurId);
            return new ApiResponse<>(true, "Cours du tuteur récupérés", coursList);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Erreur récupération des cours du tuteur", null);
        }
    }

    private String saveImage(MultipartFile file) throws IOException {
        String baseDir = System.getProperty("user.dir");
        String uploadDir = "uploads/images";
        Path imageFolder = Paths.get(baseDir, uploadDir);

        if (!Files.exists(imageFolder)) {
            Files.createDirectories(imageFolder);
        }


        String uniqueId = IdGenerator.generateId();

        String extension = "";
        String originalName = file.getOriginalFilename();
        if (originalName != null && originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf('.'));
        }

        String fileName = uniqueId + extension;

        Path imagePath = imageFolder.resolve(fileName);
        file.transferTo(imagePath.toFile());
        return "images/" + fileName;
    }



}
