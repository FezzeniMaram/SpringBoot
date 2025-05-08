package com.example.testapp.entities;

import com.example.testapp.serializer.ConversationSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
@JsonSerialize(using = ConversationSerializer.class)
@Entity
@Data
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Transient
    private String lastMessage;

    private boolean visibleParEtudiant = true;
    private boolean visibleParTuteur = true;

    private boolean etudiantBloqueTuteur = false;
    private boolean tuteurBloqueEtudiant = false;


    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chat> messages = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "tuteur_id")
    private Tuteur tuteur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public boolean isVisibleParEtudiant() {
        return visibleParEtudiant;
    }

    public void setVisibleParEtudiant(boolean visibleParEtudiant) {
        this.visibleParEtudiant = visibleParEtudiant;
    }

    public boolean isVisibleParTuteur() {
        return visibleParTuteur;
    }

    public void setVisibleParTuteur(boolean visibleParTuteur) {
        this.visibleParTuteur = visibleParTuteur;
    }

    public boolean isEtudiantBloqueTuteur() {
        return etudiantBloqueTuteur;
    }

    public void setEtudiantBloqueTuteur(boolean etudiantBloqueTuteur) {
        this.etudiantBloqueTuteur = etudiantBloqueTuteur;
    }

    public boolean isTuteurBloqueEtudiant() {
        return tuteurBloqueEtudiant;
    }

    public void setTuteurBloqueEtudiant(boolean tuteurBloqueEtudiant) {
        this.tuteurBloqueEtudiant = tuteurBloqueEtudiant;
    }

    public List<Chat> getMessages() {
        return messages;
    }

    public void setMessages(List<Chat> messages) {
        this.messages = messages;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Tuteur getTuteur() {
        return tuteur;
    }

    public void setTuteur(Tuteur tuteur) {
        this.tuteur = tuteur;
    }

}

