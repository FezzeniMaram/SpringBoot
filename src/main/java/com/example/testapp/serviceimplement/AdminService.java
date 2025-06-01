package com.example.testapp.serviceimplement;

import com.example.testapp.repository.*;
import com.example.testapp.services.AdminInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminService implements AdminInterface {
    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private TuteurRepository tuteurRepository;

    @Autowired
    private CoursRepository coursRepository;

    @Autowired
    private ChapitreRepository chapitreRepository;

    @Autowired
    private AvisRepository avisRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public Map<String, Long> getDashboardStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("nbEtudiants", etudiantRepository.count());
        stats.put("nbTuteurs", tuteurRepository.count());
        stats.put("nbCours", coursRepository.count());
        stats.put("nbChapitres", chapitreRepository.count());
        stats.put("nbAvis", avisRepository.count());
        stats.put("nbMessages", chatRepository.count());
        Long nbInscriptions = etudiantRepository.countTotalInscriptions();
        stats.put("nbInscriptions", nbInscriptions);

        return stats;

    }
}