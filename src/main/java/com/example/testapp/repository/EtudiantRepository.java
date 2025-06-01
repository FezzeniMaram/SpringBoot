package com.example.testapp.repository;

import com.example.testapp.entities.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    Optional<Etudiant> findByEmailEtudiant(String emailEtudiant);
    boolean existsByEmailEtudiant(String emailEtudiant);

    List<Etudiant> findByActiveFalse();

    @Query("SELECT COUNT(c) FROM Etudiant e JOIN e.coursInscrits c")
    Long countTotalInscriptions();

}
