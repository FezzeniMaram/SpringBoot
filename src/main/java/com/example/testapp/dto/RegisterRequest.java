package com.example.testapp.dto;


public class RegisterRequest {
    private String nom;
    private String email;
    private String password;
    // Tu peux aussi ajouter des champs spécifiques comme role, téléphone, etc.


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
