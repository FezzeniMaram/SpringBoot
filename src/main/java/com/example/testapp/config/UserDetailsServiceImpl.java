package com.example.testapp.config;

import com.example.testapp.entities.Admin;
import com.example.testapp.entities.Etudiant;
import com.example.testapp.entities.Tuteur;
import com.example.testapp.repository.AdminRepository;
import com.example.testapp.repository.EtudiantRepository;
import com.example.testapp.repository.TuteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private TuteurRepository tuteurRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Recherche de l'utilisateur : " + email);

        Etudiant etu = etudiantRepository.findByEmailEtudiant(email).orElse(null);
        if (etu != null) {
            System.out.println("✅ Étudiant trouvé !");
            return new User(
                    etu.getEmailEtudiant(),
                    etu.getMotPasseEtudiant(),
                    Collections.singletonList(new SimpleGrantedAuthority("ETUDIANT"))
            );
        }

        Tuteur tut = tuteurRepository.findByEmailTuteur(email).orElse(null);
        if (tut != null) {
            System.out.println("✅ Tuteur trouvé !");
            return new User(
                    tut.getEmailTuteur(),
                    tut.getMotPasseTuteur(),
                    Collections.singletonList(new SimpleGrantedAuthority("TUTEUR"))
            );
        }

        Admin admin = adminRepository.findByEmail(email).orElse(null);
        if (admin != null) {
            System.out.println("✅ Admin trouvé !");
            return new User(
                    admin.getEmail(),
                    admin.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ADMIN"))
            );
        }

        throw new UsernameNotFoundException("Utilisateur non trouvé !");
    }
}
