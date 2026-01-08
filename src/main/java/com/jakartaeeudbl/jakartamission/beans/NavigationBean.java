/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jakartaeeudbl.jakartamission.beans;

import com.jakartaeeudbl.jakartamission.business.SessionManager;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.IOException;

/**
 *
 * @author lkanan
 */
@Named(value="navigationController")
@RequestScoped
public class NavigationBean {
    @Inject
    private SessionManager sessionManager;
    public void voirApropos(){
        try{
            FacesContext.getCurrentInstance().getExternalContext().redirect("a_propos.xhtml");
        }catch(IOException e){
           e.printStackTrace();
        }
    }
    public void voirLieux(){
        try{
            FacesContext.getCurrentInstance().getExternalContext().redirect("lieux.xhtml");
        }catch(IOException e){
           e.printStackTrace();
        }
    }
    public void voirProfil(){
        try{
            FacesContext.getCurrentInstance().getExternalContext().redirect("profil.xhtml");
        }catch(IOException e){
           e.printStackTrace();
        }
    }
    public void seDeconnecter(){
        try{
            sessionManager.invalidateSession();
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        }catch(IOException e){
           e.printStackTrace();
        }
    }
}
