package com.jakartaeeudbl.jakartamission.business;

import com.jakartaeeudbl.jakartamission.entities.Lieu;
import com.jakartaeeudbl.jakartamission.entities.Utilisateur;
import com.jakartaeeudbl.jakartamission.entities.Visite;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@Stateless
@LocalBean
public class VisiteEntrepriseBean {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void enregistrerVisite(Long utilisateurId, int lieuId) {
        if (utilisateurId == null) {
            return;
        }

        Utilisateur utilisateur = em.find(Utilisateur.class, utilisateurId);
        Lieu lieu = em.find(Lieu.class, lieuId);

        if (utilisateur == null || lieu == null) {
            return;
        }

        // Vérifier si la visite existe déjà pour éviter les doublons (par utilisateur/lieu)
        List<Visite> existantes = em.createQuery(
                "SELECT v FROM Visite v WHERE v.utilisateur = :u AND v.lieu = :l",
                        Visite.class)
                .setParameter("u", utilisateur)
            .setParameter("l", lieu)
                .getResultList();

        if (!existantes.isEmpty()) {
            return;
        }

        Visite visite = new Visite(utilisateur, lieu);
        // Les autres champs (dateVisite, tempsPasse, observations, depenses)
        // sont transients et uniquement utilisés côté interface
        em.persist(visite);
    }

    public List<Visite> listerVisitesParUtilisateur(Long utilisateurId) {
        return em.createQuery(
                        "SELECT v FROM Visite v WHERE v.utilisateur.id = :id",
                        Visite.class)
                .setParameter("id", utilisateurId)
                .getResultList();
    }
}
