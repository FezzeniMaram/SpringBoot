package com.example.testapp.serviceimplement;

import com.example.testapp.entities.Cours;
import com.example.testapp.entities.Tuteur;
import com.example.testapp.repository.CoursRepository;
import com.example.testapp.repository.TuteurRepository;
import com.example.testapp.services.CoursInterface;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoursService implements CoursInterface {

    @Autowired
    private CoursRepository coursRepository;

    @Autowired
    private TuteurRepository tuteurRepository;

    @Override
    public Cours addCours(Cours cours) {
        String email = getCurrentEmail();
        Tuteur tuteur = tuteurRepository.findByEmailTuteur(email)
                .orElseThrow(() -> new RuntimeException("Tuteur non trouvé"));
        cours.setTuteur(tuteur);
        return coursRepository.save(cours);
    }

    @Override
    public void deleteCours(Long id) {
        Cours cours = coursRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cours non trouvé"));

        if (isTuteur()) {
            Tuteur tuteur = tuteurRepository.findByEmailTuteur(getCurrentEmail())
                    .orElseThrow(() -> new RuntimeException("Tuteur non trouvé"));
            if (!cours.getTuteur().getIdTuteur().equals(tuteur.getIdTuteur())) {
                throw new RuntimeException("Non autorisé à supprimer ce cours.");
            }
        }

        coursRepository.deleteById(id);
    }

    @Override
    public Cours updateCours(Long id, Cours updated) {
        Cours cours = coursRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cours non trouvé"));

        if (isTuteur()) {
            Tuteur tuteur = tuteurRepository.findByEmailTuteur(getCurrentEmail())
                    .orElseThrow(() -> new RuntimeException("Tuteur non trouvé"));
            if (!cours.getTuteur().getIdTuteur().equals(tuteur.getIdTuteur())) {
                throw new RuntimeException("Non autorisé à modifier ce cours.");
            }
        }

        cours.setTitreCours(updated.getTitreCours());
        cours.setDescriptionCours(updated.getDescriptionCours());

        return coursRepository.save(cours);
    }

    @Override
    public List<Cours> getAllCours() {
        return coursRepository.findAll();
    }

    @Override
    public Cours getById(Long id) {
        return coursRepository.findById(id).orElse(null);
    }

    @Override
    public List<Cours> getCoursByTuteur(Long tuteurId) {
        Tuteur tuteur = tuteurRepository.findById(tuteurId)
                .orElseThrow(() -> new RuntimeException("Tuteur non trouvé"));
        return coursRepository.findByTuteur(tuteur);
    }

    @Override
    public Cours getCoursById(Long coursId) {
        return coursRepository.findById(coursId).orElse(null);
    }



    private String getCurrentEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));
    }

    private boolean isTuteur() {
        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("TUTEUR"));
    }
}
