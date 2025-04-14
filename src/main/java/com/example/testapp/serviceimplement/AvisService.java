package com.example.testapp.serviceimplement;

import com.example.testapp.entities.Avis;
import com.example.testapp.repository.AvisRepository;
import com.example.testapp.services.AvisInterface;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvisService implements AvisInterface {

    @Autowired
    private AvisRepository avisRepository;

    @Override
    public Avis addAvis(Avis avis) {
        return avisRepository.save(avis);
    }


    @Override
    public void deleteAvis(Long id) {
        if (!avisRepository.existsById(id)) {
            throw new EntityNotFoundException("Avis non trouvé avec l'ID : " + id);
        }
        avisRepository.deleteById(id);
    }

    @Override
    public List<Avis> getAllAvis() {
        return avisRepository.findAll();
    }

    @Override
    public Avis getAvisById(Long id) {
        return avisRepository.findById(id).orElse(null);
    }

    @Override
    public Avis updateAvis(Long id, Avis avis) {
        Avis avisExistant = avisRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Avis non trouvé avec l'ID : " + id));

        avisExistant.setCommentaireAvis(avis.getCommentaireAvis());
        return avisRepository.save(avisExistant);
    }

    @Override
    public List<Avis> getAvisBycours(Long coursId) {
        return avisRepository.findByCours_IdCour(coursId);
    }
}
