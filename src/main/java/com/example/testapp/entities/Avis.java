package com.example.testapp.entities;

import com.example.testapp.serializer.AvisSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Avis")
@JsonSerialize(using = AvisSerializer.class)
public class Avis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAvis;
    private String commentaireAvis;

    public Avis(Long idAvis, String commentaireAvis, Etudiant etudiant, Cours cours, Tuteur tuteur)  {
        this.idAvis = idAvis;
        this.commentaireAvis = commentaireAvis;
        this.etudiant = etudiant;
        this.cours = cours;
        this.tuteur=tuteur;
    }
    public Avis(){

    }

    @Override
    public String toString() {
        return "Avis{" +
                "idAvis=" + idAvis +
                ", commentaireAvis='" + commentaireAvis + '\'' +
                ", etudiant=" + etudiant +
                ", cours=" + cours +
                ", tuteur=" + tuteur +
                '}';
    }

    public Tuteur getTuteur() {
        return tuteur;
    }

    public void setTuteur(Tuteur tuteur) {
        this.tuteur = tuteur;
    }

    public Long getIdAvis() {
        return idAvis;
    }

    public void setIdAvis(Long idAvis) {
        this.idAvis = idAvis;
    }

    public String getCommentaireAvis() {
        return commentaireAvis;
    }

    public void setCommentaireAvis(String commentaireAvis) {
        this.commentaireAvis = commentaireAvis;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Cours getCours() {
        return cours;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }


    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "cours_id")
    private Cours cours;

    @ManyToOne
    @JoinColumn(name = "tuteur_id")
    private Tuteur tuteur;


}
