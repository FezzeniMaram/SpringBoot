package com.example.testapp.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "Cours")
public class Cours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCour;
    private String titreCours;

    public Cours(String titreCours, String descriptionCours, float montantCours, Tuteur tuteur, List<Chapitre> chapitres, List<Etudiant> etudiants) {
        this.titreCours = titreCours;
        this.descriptionCours = descriptionCours;
        this.montantCours = montantCours;
        this.tuteur = tuteur;
        this.chapitres = chapitres;
        this.etudiants = etudiants;
    }

    public Cours() {

    }


    @Override
    public String toString() {
        return "Cours{" +
                "idCour=" + idCour +
                ", titreCours='" + titreCours + '\'' +
                ", descriptionCours='" + descriptionCours + '\'' +
                ", montantCours=" + montantCours +
                ", tuteur=" + tuteur +
                ", chapitres=" + chapitres +
                ", etudiants=" + etudiants +
                '}';
    }

    private String descriptionCours;
    private float montantCours;

    public Long getIdCour() {
        return idCour;
    }

    public String getTitreCours() {
        return titreCours;
    }

    public String getDescriptionCours() {
        return descriptionCours;
    }

    public float getMontantCours() {
        return montantCours;
    }

    public Tuteur getTuteur() {
        return tuteur;
    }

    public List<Chapitre> getChapitres() {
        return chapitres;
    }

    public List<Etudiant> getEtudiants() {
        return etudiants;
    }

    public void setIdCour(Long idCour) {
        this.idCour = idCour;
    }

    public void setTitreCours(String titreCours) {
        this.titreCours = titreCours;
    }

    public void setDescriptionCours(String descriptionCours) {
        this.descriptionCours = descriptionCours;
    }

    public void setMontantCours(float montantCours) {
        this.montantCours = montantCours;
    }

    public void setTuteur(Tuteur tuteur) {
        this.tuteur = tuteur;
    }

    public void setChapitres(List<Chapitre> chapitres) {
        this.chapitres = chapitres;
    }

    public void setEtudiants(List<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

    @ManyToOne
    @JoinColumn(name = "tuteur_id")
    private Tuteur tuteur;

    @OneToMany(mappedBy = "cours", cascade = CascadeType.ALL)
    private List<Chapitre> chapitres;

    @ManyToMany(mappedBy = "coursInscrits")
    private List<Etudiant> etudiants;

}
