package com.example.testapp.services;

import com.example.testapp.dto.AuthRequest;
import com.example.testapp.dto.AuthResponse;
import com.example.testapp.dto.RegisterRequest;
import com.example.testapp.dto.RegisterResponse;

public interface AuthInterface {

        // Login selon le type d'utilisateur
        AuthResponse loginEtudiant(AuthRequest request);

        AuthResponse loginTuteur(AuthRequest request);

        AuthResponse loginAdmin(AuthRequest request);

        // Enregistrement (register)
        RegisterResponse registerEtudiant(RegisterRequest request);

        RegisterResponse registerTuteur(RegisterRequest request);

        String registreAdmin(RegisterRequest request);
    }

