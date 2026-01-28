package com.jakartaeeudbl.jakartamission.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDate;
import java.io.Serializable;

@Entity
@Table(name = "visite")
public class Visite implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    @ManyToOne(optional = false)
    @JoinColumn(name = "lieu_id", nullable = false)
    private Lieu lieu;

    // Champs utilisés uniquement dans l'interface (non stockés en base)
    @Transient
    private LocalDate dateVisite;

    @Transient
    private String tempsPasse;

    @Transient
    private String observations;

    @Transient
    private String depenses;

    public Visite() {
    }

    public Visite(Utilisateur utilisateur, Lieu lieu) {
        this.utilisateur = utilisateur;
        this.lieu = lieu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Lieu getLieu() {
        return lieu;
    }

    public void setLieu(Lieu lieu) {
        this.lieu = lieu;
    }

    public LocalDate getDateVisite() {
        return dateVisite;
    }

    public void setDateVisite(LocalDate dateVisite) {
        this.dateVisite = dateVisite;
    }

    public String getTempsPasse() {
        return tempsPasse;
    }

    public void setTempsPasse(String tempsPasse) {
        this.tempsPasse = tempsPasse;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getDepenses() {
        return depenses;
    }

    public void setDepenses(String depenses) {
        this.depenses = depenses;
    }
}
