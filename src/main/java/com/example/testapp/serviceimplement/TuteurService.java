package com.example.testapp.serviceimplement;

import com.example.testapp.entities.Cours;
import com.example.testapp.entities.Tuteur;
import com.example.testapp.repository.CoursRepository;
import com.example.testapp.repository.TuteurRepository;
import com.example.testapp.services.TuteurInterface;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TuteurService implements TuteurInterface {
    @Autowired
    TuteurRepository tuteurRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    CoursRepository coursRepository;

    @Override
    public String inscrireTuteur(Tuteur tuteur) {
        if (tuteurRepository.existsByEmailTuteur(tuteur.getEmailTuteur())) {
            return "L'utilisateur avec cet email existe déjà.";
        } else {
            tuteurRepository.save(tuteur);
            return "L'étudiant a été inscrit avec succès.";
        }
    }

    @Override
    public void deleteTuteur(Long id) {
    tuteurRepository.deleteById(id);
    }

    @Override
    public List<Tuteur> getAllTuteurs()  {
        return tuteurRepository.findAll();
    }

    @Override
    public Tuteur getTuteurById(Long id) {
        return tuteurRepository.findById(id).orElse(null);
    }

    public Tuteur updateNom(Long id, String ancienMotDePasse, String nouveauNom) {
        Tuteur tuteur = tuteurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tuteur avec ID " + id + " non trouvé."));

        if (ancienMotDePasse == null || ancienMotDePasse.isBlank() ||
                !passwordEncoder.matches(ancienMotDePasse, tuteur.getMotPasseTuteur())) {
            throw new IllegalArgumentException("Ancien mot de passe incorrect ou manquant.");
        }

        if (nouveauNom == null || nouveauNom.isBlank()) {
            throw new IllegalArgumentException("Le nouveau nom ne peut pas être vide.");
        }

        tuteur.setNomTuteur(nouveauNom);
        return tuteurRepository.save(tuteur);
    }

    @Override
    public Tuteur updateMotDePasse(Long id, String ancienMotDePasse, String nouveauMotDePasse) {
        Tuteur tuteur = tuteurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tuteur avec ID " + id + " non trouvé."));

        if (ancienMotDePasse == null || ancienMotDePasse.isBlank() ||
                !passwordEncoder.matches(ancienMotDePasse, tuteur.getMotPasseTuteur())) {
            throw new IllegalArgumentException("Ancien mot de passe incorrect ou manquant.");
        }

        if (nouveauMotDePasse == null || nouveauMotDePasse.isBlank()) {
            throw new IllegalArgumentException("Le nouveau mot de passe ne peut pas être vide.");
        }

        tuteur.setMotPasseTuteur(passwordEncoder.encode(nouveauMotDePasse));
        return tuteurRepository.save(tuteur);
    }

    @Override
    public String authenTuteur(String emailTuteur, String motPasseTuteur) {
        Optional<Tuteur> tuteurOptional = tuteurRepository.findByEmailTuteur(emailTuteur);
        if (tuteurOptional.isPresent()){
            Tuteur tuteur = tuteurOptional.get();
            if(tuteur.getMotPasseTuteur().equals(motPasseTuteur)){
                return "Utilisateur existe : " + tuteur.getNomTuteur();
            }else{
                return "Mot de passe incorrect";
            }
        }
        return "Utilisateur n'existe pas";

    }

    @Override
    public Object getCoursPubliesByTuteur(Long tuteurId) {
        Tuteur tuteur = tuteurRepository.findById(tuteurId).orElse(null);
        if (tuteur != null) {
            List<Cours> coursPublies = tuteur.getCoursPublies();
            if (coursPublies.isEmpty()) {
                return "Tu n'as aucun cours publié.";
            }
            return coursPublies;
        }
        return "Tuteur non trouvé";
    }

    @Override
    public int getNombreCoursPublies(Long idTuteur) {
        return coursRepository.countByTuteurIdTuteur(idTuteur);
    }

}
