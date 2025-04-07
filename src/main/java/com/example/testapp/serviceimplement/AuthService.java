package com.example.testapp.serviceimplement;


import com.example.testapp.config.JwtUtil;
import com.example.testapp.dto.AuthRequest;
import com.example.testapp.dto.AuthResponse;
import com.example.testapp.dto.RegisterRequest;
import com.example.testapp.entities.Etudiant;
import com.example.testapp.entities.Role;
import com.example.testapp.entities.Tuteur;
import com.example.testapp.repository.EtudiantRepository;
import com.example.testapp.repository.TuteurRepository;
import com.example.testapp.services.AuthInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements AuthInterface {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private TuteurRepository tuteurRepository;

    private final String ADMIN_EMAIL = "admin@skillhub.com";
    private final String ADMIN_PASSWORD = "admin123"; // à encoder pour plus de sécurité

    @Override
    public AuthResponse login(AuthRequest request) {
        // Vérification ADMIN
        if (request.getEmail().equals(ADMIN_EMAIL) && request.getPassword().equals(ADMIN_PASSWORD)) {
            String token = jwtUtil.generateToken(ADMIN_EMAIL, "ADMIN");
            return new AuthResponse(token, "ADMIN");
        }

        // Authentification standard
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        Optional<Etudiant> etudiant = etudiantRepository.findByEmailEtudiant(request.getEmail());
        if (etudiant.isPresent()) {
            return new AuthResponse(jwtUtil.generateToken(request.getEmail(), "ETUDIANT"), "ETUDIANT");
        }

        Optional<Tuteur> tuteur = tuteurRepository.findByEmailTuteur(request.getEmail());
        if (tuteur.isPresent()) {
            return new AuthResponse(jwtUtil.generateToken(request.getEmail(), "TUTEUR"), "TUTEUR");
        }

        throw new RuntimeException("Email ou mot de passe incorrect !");
    }


    @Override
    public String registerEtudiant(RegisterRequest request) {
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe ne peut pas être vide.");
        }

        Etudiant etudiant = new Etudiant();
        etudiant.setNomEtudiant(request.getNom());
        etudiant.setEmailEtudiant(request.getEmail());
        etudiant.setMotPasseEtudiant(passwordEncoder.encode(request.getPassword()));
        etudiant.setRole(Role.ETUDIANT);
        etudiantRepository.save(etudiant);
        return "Étudiant enregistré avec succès.";
    }

    @Override
    public String registerTuteur(RegisterRequest request) {
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe ne peut pas être vide.");
        }

        Tuteur tuteur = new Tuteur();
        tuteur.setNomTuteur(request.getNom());
        tuteur.setEmailTuteur(request.getEmail());
        tuteur.setMotPasseTuteur(passwordEncoder.encode(request.getPassword()));
        tuteur.setRole(Role.TUTEUR);
        tuteurRepository.save(tuteur);
        return "Tuteur enregistré avec succès.";
    }

}

