package com.example.testapp.services;

import com.example.testapp.entities.Cours;
import com.example.testapp.entities.Etudiant;

import java.util.List;

public interface EtudiantInterface {
    public String  inscrireEtudiant(Etudiant etudiant);
    void deleteEtudiant (Long id);
    public List<Etudiant>getAllEtudiants();
    public Etudiant getEtudiantById(Long id);
    
    public  Etudiant updateEtudiant(Long id, Etudiant etudiant);
    public String authenEtudiant(String emailEtudiant, String motPasseEtudiant);
    public String inscrireEtudiantAuCours(Long etudiantId , Long coursId);
    List<Cours> getCoursByEtudiant(Long etudiantId);

}
