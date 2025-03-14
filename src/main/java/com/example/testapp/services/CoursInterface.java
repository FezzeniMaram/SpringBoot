package com.example.testapp.services;

import com.example.testapp.entities.Cours;

import java.util.List;

public interface CoursInterface {
    public Cours addCours (Cours cours);
    public void deleteCours(Long id);
    public List<Cours> getAllCours();
    public Cours getById(Long id);
    public Cours updateCours(Long id, Cours cours);

}
