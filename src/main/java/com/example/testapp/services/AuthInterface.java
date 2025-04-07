package com.example.testapp.services;

import com.example.testapp.dto.AuthRequest;
import com.example.testapp.dto.AuthResponse;
import com.example.testapp.dto.RegisterRequest;

public interface AuthInterface {
    AuthResponse login(AuthRequest request);
    String registerEtudiant(RegisterRequest request);
    String registerTuteur(RegisterRequest request);
}