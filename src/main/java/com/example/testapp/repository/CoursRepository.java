package com.example.testapp.repository;

import com.example.testapp.entities.Cours;
import com.example.testapp.entities.Tuteur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoursRepository extends JpaRepository<Cours, Long> {
    List<Cours> findByTuteur(Tuteur tuteur);
}
