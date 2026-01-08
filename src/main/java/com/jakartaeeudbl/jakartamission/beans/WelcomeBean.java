/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jakartaeeudbl.jakartamission.beans;

import com.jakartaeeudbl.jakartamission.business.SessionManager;
import com.jakartaeeudbl.jakartamission.business.UtilisateurEntrepriseBean;
import com.jakartaeeudbl.jakartamission.entities.Utilisateur;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author lkanan
 */
@RequestScoped
@Named
public class WelcomeBean {
    private String nom;
    private String message;
    private double dollar;
    private double indonesien;
    private double taux = 16677.45;
    
    private String email;
    private String password;

    @Inject
    private SessionManager sessionManager;

    @Inject
    private UtilisateurEntrepriseBean utilisateurEntrepriseBean;
    
    public String getNom(){
        return nom;
    }
    public void setNom(String nom){
        this.nom = nom;
    }
    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public double getDollar(){
        return dollar;
    }
    public void setDollar(double dollar){
        this.dollar = dollar;
    }
    public double getIndonesien(){
        return indonesien;
    }
    public void setIndonesien(double indonesien){
        this.indonesien = indonesien;
    }
    public void afficher(){
        this.message = "Welcome to Indonesia " + this.nom;
    }
    public void convertirDollarsIndonesien(){
        this.indonesien = this.dollar * this.taux;
    }
    public void convertirIndonesienDollars(){
        this.dollar = this.indonesien / this.taux;
    }

    public String sauthentifier() {
        if (email == null || password == null || email.isBlank() || password.isBlank()) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email et mot de passe requis", null));
            return null;
        }
        Utilisateur utilisateur = utilisateurEntrepriseBean.trouverUtilisateurParEmail(email);
        if (utilisateur != null && utilisateurEntrepriseBean.verifierMotDePasse(password, utilisateur.getPassword())) {
            sessionManager.createSession("user", utilisateur.getEmail());
            return "/pages/home?faces-redirect=true";
        }
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email ou mot de passe invalide", null));
        return null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
