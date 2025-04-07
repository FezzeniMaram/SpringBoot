package com.example.testapp.config;


import com.example.testapp.entities.Etudiant;
import com.example.testapp.entities.Tuteur;
import com.example.testapp.repository.EtudiantRepository;
import com.example.testapp.repository.TuteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
        private EtudiantRepository etudiantRepository;

    @Autowired
    private TuteurRepository tuteurRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Recherche de l'utilisateur : " + email);

        Etudiant etu = etudiantRepository.findByEmailEtudiant(email).orElse(null);
        if (etu != null) {
            System.out.println("✅ Étudiant trouvé !");
            return new User(etu.getEmailEtudiant(), etu.getMotPasseEtudiant(),
                    List.of(new SimpleGrantedAuthority("ETUDIANT")));
        }

        Tuteur tut = tuteurRepository.findByEmailTuteur(email).orElse(null);
        if (tut != null) {
            return new User(tut.getEmailTuteur(), tut.getMotPasseTuteur(),
                    java.util.Collections.singletonList(new SimpleGrantedAuthority("TUTEUR")));
        }

        throw new UsernameNotFoundException("Utilisateur non trouvé");
    }
}
