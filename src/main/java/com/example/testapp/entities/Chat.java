package com.example.testapp.entities;

import com.example.testapp.serializer.ChatSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@JsonSerialize(using = ChatSerializer.class)
@Entity
@Data
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idChat;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String messageChat;

    private LocalDateTime dateChat;

    @Column(nullable = false)
    private String expediteurRole; // "ETUDIANT" ou "TUTEUR"

    @Column(nullable = false)
    private boolean visibleParEtudiant = true;

    @Column(nullable = false)
    private boolean visibleParTuteur = true;


    public Long getIdChat() {
        return idChat;
    }

    public void setIdChat(Long idChat) {
        this.idChat = idChat;
    }

    public String getMessageChat() {
        return messageChat;
    }

    public void setMessageChat(String messageChat) {
        this.messageChat = messageChat;
    }

    public LocalDateTime getDateChat() {
        return dateChat;
    }

    public void setDateChat(LocalDateTime dateChat) {
        this.dateChat = dateChat;
    }

    public String getExpediteurRole() {
        return expediteurRole;
    }

    public void setExpediteurRole(String expediteurRole) {
        this.expediteurRole = expediteurRole;
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

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
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

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "tuteur_id")
    private Tuteur tuteur;

    @PrePersist
    public void setDate() {
        this.dateChat = LocalDateTime.now();
    }


}
