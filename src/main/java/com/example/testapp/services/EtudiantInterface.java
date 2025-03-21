package com.example.testapp.services;

import com.example.testapp.entities.Etudiant;

import java.util.List;

public interface EtudiantInterface {
    public String  inscrireEtudiant(Etudiant etudiant);
    void deleteEtudiant (Long id);
    public List<Etudiant>getAllEtudiants();
    public Etudiant getEtudiantById(Long id);
    public  Etudiant updateEtudiant(Long id, Etudiant etudiant);
    public String authenEtudiant(String emailEtudiant, String motPasseEtudiant);

}
