/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jakartaeeudbl.jakartamission.beans;

import com.jakartaeeudbl.jakartamission.business.LieuEntrepriseBean;
import com.jakartaeeudbl.jakartamission.entities.Lieu;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lkanan
 */
@Named(value = "lieuBean")
@RequestScoped
public class LieuBean implements Serializable{
    private int id;
        private String nom;
    private String description;
    private double longitude;
    private double latitude;
    private List<Lieu> lieux = new ArrayList<>();

    @Inject
    private LieuEntrepriseBean lieuEntrepriseBean;

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public List<Lieu> getLieux() { return lieuEntrepriseBean.listerTousLesLieux(); }

    public void ajouterLieu() {
        if (nom != null && !nom.isEmpty() && description != null && !description.isEmpty()) {
            lieuEntrepriseBean.insererLieuEntreprise(nom, description, latitude, longitude);
        }
    }
    public void supprimerLieu(int LieuId){
        lieuEntrepriseBean.supprimerLieu(LieuId);
    }
    public void modifierLieu(int id, String nouveauNom, String nouvelleDescription, double nouvelleLatitude, double nouvelleLongitude) {
        lieuEntrepriseBean.modifierLieu(id, nouveauNom, nouvelleDescription, nouvelleLatitude, nouvelleLongitude);
    }
    public Lieu trouverLieuParId(int id){
        return lieuEntrepriseBean.trouverLieuParId(id);
    }
}
