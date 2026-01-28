package com.jakartaeeudbl.jakartamission.beans;

import com.jakartaeeudbl.jakartamission.business.LieuEntrepriseBean;
import com.jakartaeeudbl.jakartamission.business.SessionManager;
import com.jakartaeeudbl.jakartamission.business.UtilisateurEntrepriseBean;
import com.jakartaeeudbl.jakartamission.business.VisiteEntrepriseBean;
import com.jakartaeeudbl.jakartamission.entities.Utilisateur;
import com.jakartaeeudbl.jakartamission.entities.Visite;
import jakarta.faces.view.ViewScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import java.io.Serializable;
import java.util.List;

@Named("visiteBean")
@ViewScoped
public class VisiteBean implements Serializable {

    @Inject
    private VisiteEntrepriseBean visiteEntrepriseBean;

    @Inject
    private LieuEntrepriseBean lieuEntrepriseBean;

    @Inject
    private SessionManager sessionManager;

    @Inject
    private UtilisateurEntrepriseBean utilisateurEntrepriseBean;

    private List<Visite> visitesUtilisateur;
    private int lieuSelectionneId;
    private String dateVisite;
    private String tempsPasse;
    private String observations;
    private String depenses;
    private String meteoTexte = "La météo sera chargée automatiquement pour ce lieu.";

    public void afficherFormulaireVisite(int lieuId) {
        this.lieuSelectionneId = lieuId;
        // Champ décoratif : pas de traitement côté serveur
        this.dateVisite = "";
    }

    // Appelé automatiquement par f:ajax quand on change de lieu
    public void chargerMeteoPourLieuAjax(AjaxBehaviorEvent event) {
        chargerMeteoPourLieu(this.lieuSelectionneId);
    }

    // Consommation de l'API météo JakartaWeather côté Java
    public void chargerMeteoPourLieu(int lieuId) {
        if (lieuId == 0) {
            meteoTexte = "Veuillez d'abord choisir un lieu.";
            return;
        }

        var lieu = lieuEntrepriseBean.trouverLieuParId(lieuId);
        if (lieu == null) {
            meteoTexte = "Lieu introuvable.";
            return;
        }

        double latitude = lieu.getLatitude();
        double longitude = lieu.getLongitude();

        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client
                    .target("http://localhost:8080/j-weather/webapi/JakartaWeather")
                    .queryParam("latitude", latitude)
                    .queryParam("longitude", longitude);

            String resultat = target.request().get(String.class);
            meteoTexte = resultat != null && !resultat.isBlank()
                    ? resultat
                    : "Aucune donnée météo reçue.";
        } catch (Exception e) {
            meteoTexte = "Impossible de charger la météo.";
        }
    }

    public void enregistrerVisite() {
        FacesContext context = FacesContext.getCurrentInstance();

        String email = sessionManager.getValueFromSession("user");
        if (email == null || email.isBlank()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Session expirée. Veuillez vous reconnecter.", null));
            return;
        }

        Utilisateur utilisateur = utilisateurEntrepriseBean.trouverUtilisateurParEmail(email);
        if (utilisateur == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Utilisateur introuvable.", null));
            return;
        }

        visiteEntrepriseBean.enregistrerVisite(
            utilisateur.getId(),
            lieuSelectionneId
        );

        // Forcer le rechargement de la liste depuis la base
        visitesUtilisateur = null;
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Lieu marqué comme visité.", null));
    }

    public List<Visite> getVisitesUtilisateur() {
        if (visitesUtilisateur == null) {
            String email = sessionManager.getValueFromSession("user");
            if (email != null && !email.isBlank()) {
                Utilisateur utilisateur = utilisateurEntrepriseBean.trouverUtilisateurParEmail(email);
                if (utilisateur != null) {
                    visitesUtilisateur = visiteEntrepriseBean.listerVisitesParUtilisateur(utilisateur.getId());
                }
            }
        }
        return visitesUtilisateur;
    }

    public int getLieuSelectionneId() {
        return lieuSelectionneId;
    }

    public void setLieuSelectionneId(int lieuSelectionneId) {
        this.lieuSelectionneId = lieuSelectionneId;
    }

    public String getDateVisite() {
        return dateVisite;
    }

    public void setDateVisite(String dateVisite) {
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

    public String getMeteoTexte() {
        return meteoTexte;
    }

    public void setMeteoTexte(String meteoTexte) {
        this.meteoTexte = meteoTexte;
    }
}
