/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jakartaeeudbl.jakartamission.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.IOException;

/**
 *
 * @author lkanan
 */
@Named(value="navigationController")
@RequestScoped
public class NavigationBean {
    public void voirApropos(){
        try{
            FacesContext.getCurrentInstance().getExternalContext().redirect("pages/a_propos.xhtml");
        }catch(IOException e){
           e.printStackTrace();
        }
    }
    public void voirLieux(){
        try{
            FacesContext.getCurrentInstance().getExternalContext().redirect("pages/lieux.xhtml");
        }catch(IOException e){
           e.printStackTrace();
        }
    }
}
