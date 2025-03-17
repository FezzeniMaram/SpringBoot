package com.example.testapp.serviceimplement;

import com.example.testapp.entities.Cours;
import com.example.testapp.entities.Tuteur;
import com.example.testapp.repository.CoursRepository;
import com.example.testapp.repository.TuteurRepository;
import com.example.testapp.services.CoursInterface;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CoursService implements CoursInterface {
    @Autowired
    CoursRepository coursRepository;
    @Autowired
    TuteurRepository tuteurRepository;
    @Override
    public Cours addCours(Cours cours) {
        return coursRepository.save(cours);
    }

    @Override
    public void deleteCours(Long id) {
        coursRepository.deleteById(id);
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
    public Cours updateCours(Long id, Cours cours) {
        Cours cours1 = coursRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cours avec l'ID " + id + " non trouvé"));
        cours1.setTitreCours(cours.getTitreCours());
        cours1.setDescriptionCours(cours.getDescriptionCours());
        cours1.setMontantCours(cours.getMontantCours());
        return coursRepository.save(cours1);
    }

    @Override
    public List<Cours> getCoursByTuteur(Long tuteurId) {
        Tuteur tuteur = tuteurRepository.findById(tuteurId).orElseThrow(() -> new RuntimeException("Tuteur non trouvé avec l'ID : " + tuteurId));

        return coursRepository.findByTuteur(tuteur);
    }

    @Override
    public Cours getCoursById(Long coursId) {
        return coursRepository.findById(coursId).orElse(null);
    }
}
