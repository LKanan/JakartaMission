/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package com.jakartaeeudbl.jakartamission.business;

import com.jakartaeeudbl.jakartamission.entities.Lieu;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

/**
 *
 * @author lkanan
 */
@Stateless
@LocalBean
public class LieuEntrepriseBean {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insererLieuEntreprise(String nom, String description, double latitude, double longitude) {
        Lieu lieu = new Lieu(nom, description, latitude, longitude);
        em.persist(lieu);
    }

    public List<Lieu> listerTousLesLieux() {
        return em.createQuery("SELECT L FROM Lieu L", Lieu.class).getResultList();
    }

    @Transactional
    public void supprimerLieu(int id) {
        Lieu lieu = em.find(Lieu.class, id);
        if (lieu != null) {
            em.remove(lieu);
        }
    }
    
    public Lieu trouverLieuParId(int id) {
        return em.find(Lieu.class, id);
    }
    @Transactional
    public void modifierLieu(int id, String nouveauNom, String nouvelleDescription, double nouvelleLatitude, double nouvelleLongitude) {
        // 1. Récupérer l'entité existante par son ID
        Lieu lieuAModifier = this.trouverLieuParId(id);

        // Vérifier si l'entité existe
        if (lieuAModifier != null) {
            // 2. Modifier les attributs de l'entité gérée
            lieuAModifier.setNom(nouveauNom);
            lieuAModifier.setDescription(nouvelleDescription);
            lieuAModifier.setLatitude(nouvelleLatitude);
            lieuAModifier.setLongitude(nouvelleLongitude);
            em.merge(lieuAModifier);
        
    }


            // 3. L'entité est désormais mise à jour dans le contexte de persistance.
            // Puisque la méthode est @Transactional, l'entité sera persistée 
            // en base de données à la fin de la méthode (ou la transaction).
            // Le em.merge() est implicite ou souvent non nécessaire pour une entité déjà trouvée.
//            return lieuAModifier;
        
    }
}
