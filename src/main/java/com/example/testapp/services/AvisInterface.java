package com.example.testapp.services;

import com.example.testapp.entities.Avis;

import java.util.List;

public interface AvisInterface {
    public Avis addAvis(Avis avis);
    public void deleteAvis(Long id);
    public List<Avis> getAllAvis();
    public Avis getAvisById(Long id);
    public Avis updateAvis(Long id , Avis avis);

}
