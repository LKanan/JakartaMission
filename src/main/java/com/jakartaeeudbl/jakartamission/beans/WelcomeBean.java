/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jakartaeeudbl.jakartamission.beans;

import jakarta.enterprise.context.RequestScoped;
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
}
