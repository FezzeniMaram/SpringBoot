package com.example.testapp.services;

import com.example.testapp.entities.Tuteur;

import java.util.List;

public interface TuteurInterface {
    public Tuteur inscrireTuteur(Tuteur tuteur);
    void deleteTuteur (Long id);
    public List<Tuteur> getAllTuteurs();
    public Tuteur getTuteurById(Long id);
    public  Tuteur updateTuteur(Long id, Tuteur tuteur);
}
