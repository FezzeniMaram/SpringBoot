package com.example.testapp.serviceimplement;

import com.example.testapp.entities.Chapitre;
import com.example.testapp.repository.ChapitreRepository;
import com.example.testapp.services.ChapitreIntreface;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ChapitreService implements ChapitreIntreface {
@Autowired
    ChapitreRepository chapitreRepository;
    @Override
    public Chapitre addChapitre(Chapitre chapitre) {
        return chapitreRepository.save(chapitre);
    }
    @Override
    public void deletChapitre(Long id) {
        chapitreRepository.deleteById(id);
    }
    @Override
    public List<Chapitre> getAllChapitre() {
        return chapitreRepository.findAll();
    }

    @Override
    public Chapitre getChapitreById(Long id) {
        return chapitreRepository.findById(id).orElse(null);
    }


    @Override
    public Chapitre updateChapitre(Long id, Chapitre chapitre) {
        Chapitre chapitre1 = chapitreRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Chapitre non trouver"));
        chapitre1.setTitreChapitre(chapitre.getTitreChapitre());
        chapitre1.setTypeChapitre(chapitre.getTypeChapitre());
        chapitre1.setContenuChapitre(chapitre.getContenuChapitre());
        return chapitreRepository.save(chapitre1);
    }
}
