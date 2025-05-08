package com.example.testapp.services;

import com.example.testapp.entities.Cours;
import com.example.testapp.entities.Tuteur;

import java.util.List;

public interface TuteurInterface {
    public String inscrireTuteur(Tuteur tuteur);
    void deleteTuteur (Long id);
    public List<Tuteur> getAllTuteurs();
    public Tuteur getTuteurById(Long id);
    Tuteur updateNom(Long id, String ancienMotDePasse, String nouveauNom);
    Tuteur updateMotDePasse(Long id, String ancienMotDePasse, String nouveauMotDePasse);
    public String authenTuteur(String emailTuteur, String motPasseTuteur);
    public Object getCoursPubliesByTuteur(Long tuteurId);
    int getNombreCoursPublies(Long idTuteur);
}
