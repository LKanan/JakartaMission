/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jakartaeeudbl.jakartamission.beans;

import com.jakartaeeudbl.jakartamission.business.SessionManager;
import com.jakartaeeudbl.jakartamission.business.UtilisateurEntrepriseBean;
import com.jakartaeeudbl.jakartamission.entities.Utilisateur;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 *
 * @author lkanan
 */
@Named("utilisateurBean")
@RequestScoped
public class UtilisateurBean {
    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    @Size(min = 3, max = 50, message = "Le nom d'utilisateur doit avoir entre 3 et"
            + " 50 caractères")
    private String username;
    
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    private String email;
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$",
        message = "Le mot de passe doit contenir au moins une majuscule, un chiffre et un caractère spécial"
    )
    private String password;
     @NotBlank(message = "Veuillez confirmer votre mot de passe")
    private String confirmPassword;
    private String description;
    
    @Inject
    private UtilisateurEntrepriseBean utilisateurEntrepriseBean;
    
    @Inject
    private SessionManager sessionManager;

    private Utilisateur utilisateurConnecte;
     
    public String trouverUtilisateurParEmail(String email) {
        if(utilisateurEntrepriseBean.trouverUtilisateurParEmail(email) == null){
            return "null";
        }else{
            return utilisateurEntrepriseBean.trouverUtilisateurParEmail(email).getEmail();
        }
    }
    
    public void ajouterUtilisateur() {
        FacesContext context = FacesContext.getCurrentInstance();
        // Vérifier si les mots de passe correspondent
        if (!password.equals(confirmPassword)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Les mots de passe ne correspondent pas", null));
            return;
        }
   
        
        if (this.trouverUtilisateurParEmail(email).equals(email)){
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Un utilisateur avec cet email existe déjà", null));
        }
        else{
//             Ajouter l'utilisateur avec un mot de passe haché
            utilisateurEntrepriseBean.ajouterUtilisateurEntreprise(username, email, password, description);
        // Ajout du message de succès si le mot de passe respecte les critères
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Utilisateur ajouté avec succès", null));
        System.out.println("Utilisateur ajouté : " + username + " - " + email);
        }
        

       
        // Réinitialisation des champs
        username = "";
        email = "";
        password = "";
        description = "";
    }
    
    public void chargerUtilisateurConnecte() {
        String email = sessionManager.getValueFromSession("user");
        if (email != null) {
            utilisateurConnecte = utilisateurEntrepriseBean.trouverUtilisateurParEmail(email);
            if (utilisateurConnecte != null) {
                this.username = utilisateurConnecte.getUsername();
                this.email = utilisateurConnecte.getEmail();
                this.password = utilisateurConnecte.getPassword();
                this.description = utilisateurConnecte.getDescription();
            }
        }
    }

    public String mettreAJourUtilisateur() {
        if (utilisateurConnecte != null) {
            utilisateurConnecte.setUsername(this.username);
            utilisateurConnecte.setEmail(this.email);
            utilisateurConnecte.setPassword(this.password);
            utilisateurConnecte.setDescription(this.description);
//            utilisateurEntrepriseBean.mettreAJourUtilisateur(utilisateurConnecte);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Profil mis à jour avec succès", null));
            return null;
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de la mise à jour", null));
        return null;
    }
    
    @PostConstruct
    public void init() {
        chargerUtilisateurConnecte();
    }

//    getters et setters
    public String getDescription() {
        return description;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
