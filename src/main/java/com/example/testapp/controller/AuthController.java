package com.example.testapp.controller;


import com.example.testapp.config.JwtUtil;
import com.example.testapp.dto.AuthRequest;
import com.example.testapp.dto.AuthResponse;
import com.example.testapp.entities.Etudiant;
import com.example.testapp.entities.Tuteur;
import com.example.testapp.repository.EtudiantRepository;
import com.example.testapp.repository.TuteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private TuteurRepository tuteurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // AuthController - Méthode de login
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        try {
            // Authentifier l'utilisateur (email + mot de passe)
            System.out.println(request.getEmail());
            System.out.println(request.getPassword());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword() )
            );
            // Trouver l'utilisateur et déterminer son rôle
            Optional<Etudiant> etudiantOpt = etudiantRepository.findByEmailEtudiant(request.getEmail());
            System.out.println(etudiantOpt.toString());
            if (etudiantOpt.isPresent()) {
                String token = jwtUtil.generateToken(request.getEmail(), "ETUDIANT");
                System.out.println(token);
                return new AuthResponse(token.toString(), "ETUDIANT");
            }

            Optional<Tuteur> tuteurOpt = tuteurRepository.findByEmailTuteur(request.getEmail());
            if (tuteurOpt.isPresent()) {
                String token = jwtUtil.generateToken(request.getEmail(), "TUTEUR");
                return new AuthResponse(token, "TUTEUR");
            }

//             Si l'utilisateur n'est ni étudiant ni tuteur
            throw new RuntimeException("Utilisateur non trouvé !");


        } catch (Exception e) {
            System.out.println(e.toString());
            return new AuthResponse(null, "TUTEUR");
            //throw new RuntimeException("Email ou mot de passe incorrect !");

        }
    }



    // ✅ Pour créer un compte étudiant (exemple)
    @PostMapping("/register/etudiant")
    public String registerEtudiant(@RequestBody Etudiant etudiant) {
        etudiant.setMotPasseEtudiant(passwordEncoder.encode(etudiant.getMotPasseEtudiant()));
        etudiantRepository.save(etudiant);
        return "Etudiant enregistré avec succès !";
    }

    // ✅ Pour créer un compte tuteur (exemple)
    @PostMapping("/register/tuteur")
    public String registerTuteur(@RequestBody Tuteur tuteur) {
        tuteur.setMotPasseTuteur(passwordEncoder.encode(tuteur.getMotPasseTuteur()));
        tuteurRepository.save(tuteur);
        return "Tuteur enregistré avec succès !";
    }
}

