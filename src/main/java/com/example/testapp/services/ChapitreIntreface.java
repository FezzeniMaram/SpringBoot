package com.example.testapp.services;

import com.example.testapp.entities.Chapitre;
import java.util.List;

public interface ChapitreIntreface {
    public Chapitre addChapitre(Chapitre chapitre);
    public void deletChapitre(Long id);
    public List<Chapitre> getAllChapitre();

    public Chapitre updateChapitre(Long id, Chapitre chapitre);
    public Chapitre getChapirteById(Long id);

}
