package com.example.testapp.controller;

import com.example.testapp.entities.Etudiant;
import com.example.testapp.entities.Tuteur;
import com.example.testapp.repository.EtudiantRepository;
import com.example.testapp.repository.TuteurRepository;
import com.example.testapp.serviceimplement.AdminService;
import com.example.testapp.services.AdminInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("admin")

public class adminController {
    @Autowired
    EtudiantRepository etudiantRepository;
    @Autowired
    TuteurRepository tuteurRepository;
    @Autowired
    private AdminInterface adminService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/activer-etudiant/{id}")
    public ResponseEntity<?> activerEtudiant(@PathVariable Long id) {
        Optional<Etudiant> optionalEtudiant = etudiantRepository.findById(id);
        if (optionalEtudiant.isEmpty()) {
            return ResponseEntity.status(404).body(Collections.singletonMap("message", "Étudiant introuvable."));
        }

        Etudiant etu = optionalEtudiant.get();
        etu.setActive(true);
        etudiantRepository.save(etu);

        return ResponseEntity.ok(Collections.singletonMap("message", "Compte étudiant activé avec succès."));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/activer-tuteur/{id}")
    public ResponseEntity<?> activerTuteur(@PathVariable Long id) {
        Optional<Tuteur> optionalTuteur = tuteurRepository.findById(id);
        if (optionalTuteur.isEmpty()) {
            return ResponseEntity.status(404).body(Collections.singletonMap("message", "Tuteur introuvable."));
        }

        Tuteur tut = optionalTuteur.get();
        tut.setActive(true);
        tuteurRepository.save(tut);

        return ResponseEntity.ok(Collections.singletonMap("message", "Compte tuteur activé avec succès."));
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/etudiants-inactifs")
    public ResponseEntity<?> getEtudiantsInactifs() {
        List<Etudiant> liste = etudiantRepository.findByActiveFalse();
        if (liste.isEmpty()) {
            return ResponseEntity
                    .ok(Collections.singletonMap("message", "Aucun étudiant inactif."));
        }
        // Sinon, on renvoie la liste des étudiants
        return ResponseEntity.ok(liste);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/tuteurs-inactifs")
    public ResponseEntity<?> getTuteursInactifs() {
        List<Tuteur> liste = tuteurRepository.findByActiveFalse();
        if (liste.isEmpty()) {
            // Aucun tuteur inactif → on renvoie un message
            return ResponseEntity
                    .ok(Collections.singletonMap("message", "Aucun tuteur inactif."));
        }
        // Sinon, on renvoie la liste des tuteurs
        return ResponseEntity.ok(liste);
    }
    @PreAuthorize("hasAuthority('ADMIN')")

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getDashboardStats() {
        Map<String, Long> stats = adminService.getDashboardStats();
        return ResponseEntity.ok(stats);
    }
}
