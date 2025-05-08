package com.example.testapp.entities;

import com.example.testapp.serializer.ChapitreSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="Chapitre")
@JsonSerialize(using = ChapitreSerializer.class)
public class Chapitre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long idChapitre;
    private String titreChapitre;
    private String contenuChapitre;
    private String videoPath;


    public Chapitre(Long idChapitre, String titreChapitre, String contenuChapitre, String videoPath, Cours cours) {
        this.idChapitre = idChapitre;
        this.titreChapitre = titreChapitre;
        this.contenuChapitre = contenuChapitre;
        this.videoPath = videoPath;
        this.cours = cours;

    }

    public Chapitre(){

    }

    @Override
    public String toString() {
        return "Chapitre{" +
                "idChapitre=" + idChapitre +
                ", titreChapitre='" + titreChapitre + '\'' +
                ", contenuChapitre='" + contenuChapitre + '\'' +
                ", videoPath='" + videoPath + '\'' +
                ", cours=" + cours +
                '}';
    }

    public Long getIdChapitre() {
        return idChapitre;
    }

    public void setIdChapitre(Long idChapitre) {
        this.idChapitre = idChapitre;
    }

    public String getTitreChapitre() {
        return titreChapitre;
    }

    public void setTitreChapitre(String titreChapitre) {
        this.titreChapitre = titreChapitre;
    }

    public String getContenuChapitre() {
        return contenuChapitre;
    }

    public void setContenuChapitre(String contenuChapitre) {
        this.contenuChapitre = contenuChapitre;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public Cours getCours() {
        return cours;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }


    @ManyToOne
    @JoinColumn(name = "cours_id")
    private Cours cours;

}
