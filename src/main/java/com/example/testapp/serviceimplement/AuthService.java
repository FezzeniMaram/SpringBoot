package com.example.testapp.serviceimplement;

import com.example.testapp.config.JwtUtil;
import com.example.testapp.dto.AuthRequest;
import com.example.testapp.dto.AuthResponse;
import com.example.testapp.dto.RegisterRequest;

import com.example.testapp.dto.RegisterResponse;
import com.example.testapp.entities.Admin;
import com.example.testapp.entities.Etudiant;
import com.example.testapp.entities.Role;
import com.example.testapp.entities.Tuteur;
import com.example.testapp.repository.AdminRepository;
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

    @Autowired
    private AdminRepository adminRepository;

    public AuthResponse loginEtudiant(AuthRequest request) {
        if (isInvalid(request)) {
            return new AuthResponse(false, "Email ou mot de passe manquant.", null, null, null, null,null,null,null);
        }

        Optional<Etudiant> etu = etudiantRepository.findByEmailEtudiant(request.getEmail());
        if (etu.isEmpty()) {
            return new AuthResponse(false, "Aucun étudiant trouvé avec cet email.", null, null, null, null,null,null,null);
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            Etudiant etudiant = etu.get();
            String token = jwtUtil.generateToken(request.getEmail(), "ETUDIANT");
            return new AuthResponse(true, "Connexion réussie", token, "ETUDIANT", etudiant.getNomEtudiant(), etudiant.getIdEtudiant(),
                    etudiant.getGender().toString() , etudiant.getDateNaissanceEtudiant()  ,etudiant.getEmailEtudiant());

        } catch (BadCredentialsException e) {
            return new AuthResponse(false, "Mot de passe incorrect.", null, null, null, null,null,null,null);
        }
    }

    public AuthResponse loginTuteur(AuthRequest request) {
        if (isInvalid(request)) {
            return new AuthResponse(false, "Email ou mot de passe manquant.", null, null, null, null,null,null,null);
        }

        Optional<Tuteur> tut = tuteurRepository.findByEmailTuteur(request.getEmail());
        if (tut.isEmpty()) {
            return new AuthResponse(false, "Aucun tuteur trouvé avec cet email.", null, null, null, null,null,null,null);
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            Tuteur tuteur = tut.get();
            String token = jwtUtil.generateToken(request.getEmail(), "TUTEUR");
            return new AuthResponse(true, "Connexion réussie", token, "TUTEUR", tuteur.getNomTuteur(), tuteur.getIdTuteur(),
                    tuteur.getGender().toString(), tuteur.getDateNaissanceTuteur(), tuteur.getEmailTuteur());

        } catch (BadCredentialsException e) {
            return new AuthResponse(false, "Mot de passe incorrect.", null, null, null, null,null,null,null);
        }
    }

    public AuthResponse loginAdmin(AuthRequest request) {
        if (isInvalid(request)) {
            return new AuthResponse(false, "Email ou mot de passe manquant.", null, null, null, null,null,null,null);
        }

        Optional<Admin> admin = adminRepository.findByEmail(request.getEmail());
        if (admin.isEmpty()) {
            return new AuthResponse(false, "Aucun admin trouvé avec cet email.", null, null, null, null,null,null,null);
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            Admin a = admin.get();
            String token = jwtUtil.generateToken(request.getEmail(), "ADMIN");
            return new AuthResponse(true, "Connexion réussie", token, "ADMIN", null, a.getId(),null,null,null);

        } catch (BadCredentialsException e) {
            return new AuthResponse(false, "Mot de passe incorrect.", null, null, null, null,null,null,null);
        }
    }

    @Override
    public RegisterResponse registerEtudiant(RegisterRequest request) {
        if (isInvalid(request)) {
            return new RegisterResponse(false, "Email ou mot de passe manquant.");
        }

        if (etudiantRepository.findByEmailEtudiant(request.getEmail()).isPresent()) {
            return new RegisterResponse(false, "Email déjà utilisé.");
        }

        Etudiant etu = new Etudiant();
        etu.setNomEtudiant(request.getNom());
        etu.setEmailEtudiant(request.getEmail());
        etu.setMotPasseEtudiant(passwordEncoder.encode(request.getPassword()));
        etu.setRole(Role.ETUDIANT);
        etudiantRepository.save(etu);
        return new RegisterResponse(true, "Étudiant enregistré avec succès.");
    }

    @Override
    public RegisterResponse registerTuteur(RegisterRequest request) {
        if (isInvalid(request)) {
            return new RegisterResponse(false, "Email ou mot de passe manquant.");
        }

        if (tuteurRepository.findByEmailTuteur(request.getEmail()).isPresent()) {
            return new RegisterResponse(false, "Email déjà utilisé.");
        }

        Tuteur tut = new Tuteur();
        tut.setNomTuteur(request.getNom());
        tut.setEmailTuteur(request.getEmail());
        tut.setMotPasseTuteur(passwordEncoder.encode(request.getPassword()));
        tut.setRole(Role.TUTEUR);
        tuteurRepository.save(tut);
        return new RegisterResponse(true, "Tuteur enregistré avec succès.");
    }

    @Override
    public String registreAdmin(RegisterRequest request) {
        if (isInvalid(request)) {
            return "Email ou mot de passe manquant.";
        }

        if (adminRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email déjà utilisé.";
        }

        Admin admin = new Admin();
        admin.setEmail(request.getEmail());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setRole(Role.ADMIN);
        adminRepository.save(admin);
        return "Admin enregistré avec succès.";
    }

    private boolean isInvalid(AuthRequest request) {
        return request.getEmail() == null || request.getEmail().isEmpty() ||
                request.getPassword() == null || request.getPassword().isEmpty();
    }

    private boolean isInvalid(RegisterRequest request) {
        return request.getEmail() == null || request.getEmail().isEmpty() ||
                request.getPassword() == null || request.getPassword().isEmpty();
    }
}



