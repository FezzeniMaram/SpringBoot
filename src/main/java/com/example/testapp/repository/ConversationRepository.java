package com.example.testapp.repository;

import com.example.testapp.entities.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    Optional<Conversation> findByEtudiant_IdEtudiantAndTuteur_IdTuteur(Long etudiantId, Long tuteurId);

    List<Conversation> findByEtudiant_IdEtudiant(Long etudiantId);

    List<Conversation> findByTuteur_IdTuteur(Long tuteurId);

    List<Conversation> findByEtudiant_IdEtudiantAndEtudiantBloqueTuteurTrue(Long idEtudiant);
    List<Conversation> findByTuteur_IdTuteurAndTuteurBloqueEtudiantTrue(Long idTuteur);

}


