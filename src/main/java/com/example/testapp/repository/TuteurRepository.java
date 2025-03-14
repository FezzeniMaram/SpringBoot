package com.example.testapp.repository;

import com.example.testapp.entities.Tuteur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TuteurRepository extends JpaRepository<Tuteur, Long> {
}
