package com.example.testapp.repository;

import com.example.testapp.entities.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {

}
