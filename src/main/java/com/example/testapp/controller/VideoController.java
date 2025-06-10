package com.example.testapp.controller;

import com.example.testapp.dto.ApiResponse;
import com.example.testapp.entities.Chapitre;
import com.example.testapp.entities.Video;
import com.example.testapp.services.ChapitreIntreface;
import com.example.testapp.services.VideoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoInterface videoInterface;

    @Autowired
    private ChapitreIntreface chapitreIntreface;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('TUTEUR')")
    public ApiResponse<Video> addVideo(
            @RequestParam("titre") String titre,
            @RequestParam("chapitre_id") Long chapitreId,
            @RequestParam("videoPath") MultipartFile videoFile) {
        try {
            Chapitre chapitre = chapitreIntreface.getChapirteById(chapitreId);

            String videoPath = saveVideo(videoFile);

            Video video = new Video(null, titre, chapitre, videoPath);

            Video saved = videoInterface.addVideo(video);

            return new ApiResponse<>(true, "Vidéo ajoutée avec succès", saved);
        } catch (IOException e) {
            return new ApiResponse<>(false, "Erreur lors de l'upload de la vidéo : " + e.getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Erreur lors de l'ajout de la vidéo : " + e.getMessage(), null);
        }
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('TUTEUR', 'ADMIN')")
    public ApiResponse<Void> deleteVideo(@PathVariable Long id) {
        try {
            videoInterface.deleteVideo(id);
            return new ApiResponse<>(true, "Vidéo supprimée avec succès", null);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Erreur lors de la suppression : " + e.getMessage(), null);
        }
    }

    @PatchMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('TUTEUR', 'ADMIN')")
    public ApiResponse<Video> updateVideo(@PathVariable Long id, @RequestBody Video video) {
        try {
            Video updated = videoInterface.updateVideo(id, video);
            return new ApiResponse<>(true, "Vidéo mise à jour", updated);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Erreur lors de la mise à jour", null);
        }
    }

    @GetMapping("/getAllVideo")

    @PreAuthorize("hasAnyAuthority('ETUDIANT', 'TUTEUR', 'ADMIN')")
    public ApiResponse<List<Video>> getAllVideo() {
        try {
            List<Video> list = videoInterface.getAllVideo();
            return new ApiResponse<>(true, "Liste des vidéos récupérée", list);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Erreur lors de la récupération", null);
        }
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasAnyAuthority('ETUDIANT', 'TUTEUR', 'ADMIN')")
    public ApiResponse<Video> getById(@PathVariable Long id) {
        Video video = videoInterface.getVideoById(id);
        if (video != null) {
            return new ApiResponse<>(true, "Vidéo trouvée", video);
        } else {
            return new ApiResponse<>(false, "Vidéo introuvable", null);
        }
    }

    private String saveVideo(MultipartFile file) throws IOException {
        String uploadDir = "uploads/videos/";
        Path path = Paths.get(uploadDir + file.getOriginalFilename());

        Files.createDirectories(path.getParent());

        file.transferTo(path);

        return path.toString();
    }
}

