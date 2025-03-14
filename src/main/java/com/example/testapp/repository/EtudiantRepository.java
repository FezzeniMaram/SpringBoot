package com.example.testapp.repository;

import com.example.testapp.entities.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    Optional<Etudiant> findByEmailEtudiant(String emailEtudiant);
    boolean existsByEmailEtudiant(String emailEtudiant);


}
