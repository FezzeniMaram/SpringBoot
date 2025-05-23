package com.example.testapp.repository;

import com.example.testapp.entities.Chapitre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChapitreRepository extends JpaRepository<Chapitre, Long> {
    List<Chapitre> findByCours_IdCour(Long coursId);
}
