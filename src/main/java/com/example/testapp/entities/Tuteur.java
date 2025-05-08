package com.example.testapp.entities;

import com.example.testapp.serializer.TuteurSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "Tuteur")
@JsonSerialize(using = TuteurSerializer.class)
public class Tuteur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTuteur;
    private  String nomTuteur;
    private  String emailTuteur;
    private  String motPasseTuteur;
    @Enumerated(EnumType.STRING)
    private Role role = Role.TUTEUR;
    @Column(nullable = false)
    private boolean active = false;
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;
    @Column(name = "date_naissance")
    private LocalDate dateNaissanceTuteur;


    public Tuteur() {}


    @Override
    public String toString() {
        return "Tuteur{" +
                "idTuteur=" + idTuteur +
                ", nomTuteur='" + nomTuteur + '\'' +
                ", emailTuteur='" + emailTuteur + '\'' +
                ", motPasseTuteur='" + motPasseTuteur + '\'' +
                ", role=" + role +
                ", active=" + active +
                ", gender=" + gender +
                ", dateNaissanceTuteur=" + dateNaissanceTuteur +
                ", coursPublies=" + coursPublies +
                '}';
    }

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender= gender;
    }

    public LocalDate getDateNaissanceTuteur() {
        return dateNaissanceTuteur;
    }

    public void setDateNaissanceTuteur(LocalDate dateNaissanceTuteur) {
        this.dateNaissanceTuteur = dateNaissanceTuteur;
    }

    @OneToMany(mappedBy = "tuteur", cascade = CascadeType.ALL)
    private List<Cours> coursPublies;

    public Tuteur(Long id) {
        this.idTuteur = id;
    }
}
