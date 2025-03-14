package com.example.testapp.repository;

import com.example.testapp.entities.Chapitre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapitreRepository extends JpaRepository<Chapitre, Long> {
}
