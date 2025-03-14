package com.example.testapp.serviceimplement;

import com.example.testapp.entities.Tuteur;
import com.example.testapp.repository.TuteurRepository;
import com.example.testapp.services.TuteurInterface;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TuteurService implements TuteurInterface {
    @Autowired
    TuteurRepository tuteurRepository;
    @Override
    public Tuteur inscrireTuteur(Tuteur tuteur) {
        return tuteurRepository.save(tuteur);
    }

    @Override
    public void deleteTuteur(Long id) {
    tuteurRepository.deleteById(id);
    }

    @Override
    public List<Tuteur> getAllTuteurs() {
        return tuteurRepository.findAll();
    }

    @Override
    public Tuteur getTuteurById(Long id) {
        return tuteurRepository.findById(id).orElse(null);
    }

    @Override
    public Tuteur updateTuteur(Long id, Tuteur tuteur) {
        Tuteur tuteur1 = tuteurRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Etudiant avec l'ID " + id + " non trouvé"));
        tuteur1.setNomTuteur(tuteur.getNomTuteur());
        tuteur1.setEmailTuteur(tuteur.getNomTuteur());
        return tuteurRepository.save(tuteur1);
    }
}
