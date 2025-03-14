package com.example.testapp.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
//@Getter
//@Setter
@Data
@Table(name = "Tuteur")
public class Tuteur {
    @Override
    public String toString() {
        return "Tuteur{" +
                "idTuteur=" + idTuteur +
                ", nomTuteur='" + nomTuteur + '\'' +
                ", emailTuteur='" + emailTuteur + '\'' +
                ", motPasseTuteur='" + motPasseTuteur + '\'' +
                ", coursPublies=" + coursPublies +
                '}';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTuteur;
    @Column(length = 10,nullable = true)
    private  String nomTuteur;
    private  String emailTuteur;
    private  String motPasseTuteur;

    public Long getIdTuteur() {
        return idTuteur;
    }

    public String getNomTuteur() {
        return nomTuteur;
    }

    public String getEmailTuteur() {
        return emailTuteur;
    }

    public String getMotPasseTuteur() {
        return motPasseTuteur;
    }

    public List<Cours> getCoursPublies() {
        return coursPublies;
    }

    public void setIdTuteur(Long idTuteur) {
        this.idTuteur = idTuteur;
    }

    public void setNomTuteur(String nomTuteur) {
        this.nomTuteur = nomTuteur;
    }

    public void setEmailTuteur(String emailTuteur) {
        this.emailTuteur = emailTuteur;
    }

    public void setMotPasseTuteur(String motPasseTuteur) {
        this.motPasseTuteur = motPasseTuteur;
    }

    public void setCoursPublies(List<Cours> coursPublies) {
        this.coursPublies = coursPublies;
    }

    @OneToMany(mappedBy = "tuteur", cascade = CascadeType.ALL)
    private List<Cours> coursPublies;
}
