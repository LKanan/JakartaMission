/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jakartaeeudbl.jakartamission.business;

/**
 *
 * @author lkanan
 */
import com.jakartaeeudbl.jakartamission.entities.Utilisateur;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

@Stateless
public class UtilisateurEntrepriseBean {

    @PersistenceContext
    private EntityManager em;
    
    
    public boolean verifierMotDePasse(String password, String hashedPassword) { 
        return BCrypt.checkpw(password, hashedPassword); 
    } 

    @Transactional
    public void ajouterUtilisateurEntreprise(String username, String email, String password, String description) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        Utilisateur utilisateur = new Utilisateur(username, email, hashedPassword, description);
        em.persist(utilisateur);
    }

    public List<Utilisateur> listerTousLesUtilisateurs() {
        return em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class).getResultList();
    }

    @Transactional
    public void supprimerUtilisateur(Long id) {
        Utilisateur utilisateur = em.find(Utilisateur.class, id);
        if (utilisateur != null) {
            em.remove(utilisateur);
        }
    }

    public Utilisateur trouverUtilisateurParId(Long id) {
        return em.find(Utilisateur.class, id);
    }

    public Utilisateur trouverUtilisateurParEmail(String email) {
        try {
            return em.createQuery("SELECT u FROM Utilisateur u WHERE u.email = :email", Utilisateur.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public void mettreAJourUtilisateur(Long id, String nouveauNom, String nouvelEmail, String nouveauMotDePasse, String nouvelleDescription) {
        if (id == null) {
            return;
        }

        Utilisateur utilisateurAModifier = em.find(Utilisateur.class, id);
        if (utilisateurAModifier == null) {
            return;
        }

        utilisateurAModifier.setUsername(nouveauNom);
        utilisateurAModifier.setEmail(nouvelEmail);
        utilisateurAModifier.setDescription(nouvelleDescription);

        if (nouveauMotDePasse != null && !nouveauMotDePasse.isBlank()) {
            // Si déjà au format bcrypt, on le conserve tel quel ; sinon on hash
            boolean estHashBcrypt = nouveauMotDePasse.startsWith("$2a$")
                    || nouveauMotDePasse.startsWith("$2b$")
                    || nouveauMotDePasse.startsWith("$2y$");
            if (estHashBcrypt) {
                utilisateurAModifier.setPassword(nouveauMotDePasse);
            } else {
                utilisateurAModifier.setPassword(BCrypt.hashpw(nouveauMotDePasse, BCrypt.gensalt()));
            }
        }

        // L'entité 'utilisateurAModifier' est managée, elle sera synchronisée automatiquement
    }
}