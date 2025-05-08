package com.example.testapp.serviceimplement;

import com.example.testapp.entities.Cours;
import com.example.testapp.entities.Etudiant;
import com.example.testapp.repository.CoursRepository;
import com.example.testapp.repository.EtudiantRepository;
import com.example.testapp.services.EtudiantInterface;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EtudiantService implements EtudiantInterface {

    @Autowired
    EtudiantRepository etudiantRepository;

    @Autowired
    CoursRepository coursRepository;
    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public ResponseEntity<Map<String, String>> inscrireEtudiant(Etudiant etudiant) {
        if (etudiantRepository.existsByEmailEtudiant(etudiant.getEmailEtudiant())) {
            return ResponseEntity.badRequest().body(Map.of("message", "L'utilisateur avec cet email existe déjà."));
        } else {
            etudiantRepository.save(etudiant);
            return ResponseEntity.ok(Map.of("message", "L'étudiant a été inscrit avec succès."));
        }

    }

    @Override
    public void deleteEtudiant(Long id) {
        etudiantRepository.deleteById(id);
    }

    @Override
    public List<Etudiant> getAllEtudiants() {
        return etudiantRepository.findAll();
    }

    @Override
    public Etudiant getEtudiantById(Long id) {
        return etudiantRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Étudiant avec l'ID " + id + " non trouvé"));
    }

    @Override
    public Etudiant updateNom(Long id, String ancienMotDePasse, String nouveauNom) {
        Etudiant etudiant = etudiantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Étudiant avec ID " + id + " non trouvé."));

        if (ancienMotDePasse == null || ancienMotDePasse.isBlank() ||
                !passwordEncoder.matches(ancienMotDePasse, etudiant.getMotPasseEtudiant())) {
            throw new IllegalArgumentException("Ancien mot de passe incorrect ou manquant.");
        }

        if (nouveauNom == null || nouveauNom.isBlank()) {
            throw new IllegalArgumentException("Le nouveau nom ne peut pas être vide.");
        }

        etudiant.setNomEtudiant(nouveauNom);
        return etudiantRepository.save(etudiant);
    }
    @Override
    public Etudiant updateMotDePasse(Long id, String ancienMotDePasse, String nouveauMotDePasse) {
        Etudiant etudiant = etudiantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Étudiant avec ID " + id + " non trouvé."));

        if (ancienMotDePasse == null || ancienMotDePasse.isBlank() ||
                !passwordEncoder.matches(ancienMotDePasse, etudiant.getMotPasseEtudiant())) {
            throw new IllegalArgumentException("Ancien mot de passe incorrect ou manquant.");
        }

        if (nouveauMotDePasse == null || nouveauMotDePasse.isBlank()) {
            throw new IllegalArgumentException("Le nouveau mot de passe ne peut pas être vide.");
        }

        etudiant.setMotPasseEtudiant(passwordEncoder.encode(nouveauMotDePasse));
        return etudiantRepository.save(etudiant);
    }


    @Override
    public String authenEtudiant(String emailEtudiant, String motPasseEtudiant) {
        Optional<Etudiant> etudiantOptional = etudiantRepository.findByEmailEtudiant(emailEtudiant);
        if (etudiantOptional.isPresent()) {
            Etudiant etudiant = etudiantOptional.get();
            if (etudiant.getMotPasseEtudiant().equals(motPasseEtudiant)) {
                return "Utilisateur existe : " + etudiant.getNomEtudiant();
            } else {
                return "Mot de passe incorrect";
            }
        }
        return "Utilisateur n'existe pas";
    }

    @Override
    public String inscrireEtudiantAuCours(Long etudiantId, Long coursId) {
        Optional<Etudiant> etudiantOpt = etudiantRepository.findById(etudiantId);
        Optional<Cours> coursOpt = coursRepository.findById(coursId);

        if (etudiantOpt.isPresent() && coursOpt.isPresent()) {
            Etudiant etudiant = etudiantOpt.get();
            Cours cours = coursOpt.get();

            if (!etudiant.getCoursInscrits().contains(cours)) {
                etudiant.getCoursInscrits().add(cours);
                cours.getEtudiants().add(etudiant);

                etudiantRepository.save(etudiant);
                coursRepository.save(cours);

                return "L'étudiant a été inscrit au cours avec succès.";
            } else {
                return "L'étudiant est déjà inscrit à ce cours.";
            }
        } else {
            return "Étudiant ou cours introuvable.";
        }
    }

    @Override
    public List<Cours> getCoursByEtudiant(Long etudiantId) {
        Optional<Etudiant> etudiantOpt = etudiantRepository.findById(etudiantId);

        if (etudiantOpt.isPresent()) {
            return etudiantOpt.get().getCoursInscrits();
        } else {
            throw new EntityNotFoundException("Étudiant avec l'ID " + etudiantId + " non trouvé.");
        }
    }

    @Override
    public Etudiant getEtudiantByEmail(String email) {
        return etudiantRepository.findByEmailEtudiant(email).orElse(null);
    }

    @Override
    public String supprimerCoursEtudiant(Long etudiantId, Long coursId) {
        Optional<Etudiant> etudiantOpt = etudiantRepository.findById(etudiantId);
        Optional<Cours> coursOpt = coursRepository.findById(coursId);

        if (etudiantOpt.isPresent() && coursOpt.isPresent()) {
            Etudiant etudiant = etudiantOpt.get();
            Cours cours = coursOpt.get();

            if (etudiant.getCoursInscrits().contains(cours)) {
                etudiant.getCoursInscrits().remove(cours);
                etudiantRepository.save(etudiant);
                return "Cours supprimé avec succès de la liste de l'étudiant.";
            } else {
                return "L'étudiant n'est pas inscrit à ce cours.";
            }
        }
        return "Cours ou étudiant introuvable.";
    }
    }

