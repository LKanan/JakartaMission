package com.jakartaeeudbl.jakartamission.beans;

import com.jakartaeeudbl.jakartamission.business.LieuEntrepriseBean;
import com.jakartaeeudbl.jakartamission.entities.Lieu;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;

@Named("meteoBean")
@RequestScoped
public class MeteoBean {

    @EJB
    private LieuEntrepriseBean lieuEntrepriseBean;

    @Inject
    private VisiteBean visiteBean;

    private String meteoTexte = "La météo sera chargée automatiquement pour ce lieu.";

    public void chargerMeteoPourLieuAjax(AjaxBehaviorEvent event) {
        int lieuId = visiteBean != null ? visiteBean.getLieuSelectionneId() : 0;
        chargerMeteoPourLieu(lieuId);
    }

    public void chargerMeteoPourLieu(int lieuId) {
        if (lieuId == 0) {
            meteoTexte = "Veuillez d'abord choisir un lieu.";
            return;
        }

        Lieu lieu = lieuEntrepriseBean.trouverLieuParId(lieuId);
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

    public String getMeteoTexte() {
        return meteoTexte;
    }

    public void setMeteoTexte(String meteoTexte) {
        this.meteoTexte = meteoTexte;
    }
}
