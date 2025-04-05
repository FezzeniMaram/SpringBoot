package com.example.testapp.serviceimplement;

import com.example.testapp.entities.Cours;
import com.example.testapp.entities.Etudiant;
import com.example.testapp.repository.CoursRepository;
import com.example.testapp.repository.EtudiantRepository;
import com.example.testapp.services.EtudiantInterface;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtudiantService implements EtudiantInterface {

    @Autowired
    EtudiantRepository etudiantRepository;

    @Autowired
    CoursRepository coursRepository;

    @Override
    public String inscrireEtudiant(Etudiant etudiant) {
        if (etudiantRepository.existsByEmailEtudiant(etudiant.getEmailEtudiant())) {
            return "L'utilisateur avec cet email existe déjà.";
        } else {
            etudiantRepository.save(etudiant);
            return "L'étudiant a été inscrit avec succès.";
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
    public Etudiant updateEtudiant(Long id, Etudiant etudiant) {
        Etudiant etudiant1 = etudiantRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Etudiant avec l'ID " + id + " non trouvé"));
        etudiant1.setNomEtudiant(etudiant.getNomEtudiant());
        etudiant1.setEmailEtudiant(etudiant.getEmailEtudiant());

        return etudiantRepository.save(etudiant1);
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
}
