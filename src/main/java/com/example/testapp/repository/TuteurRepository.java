package com.example.testapp.repository;

import com.example.testapp.entities.Tuteur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TuteurRepository extends JpaRepository<Tuteur, Long> {
    Optional<Tuteur> findByEmailTuteur(String emailTuteur);
    boolean existsByEmailTuteur(String emailTuteur);
}
