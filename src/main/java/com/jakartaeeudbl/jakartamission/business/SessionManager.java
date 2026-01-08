package com.jakartaeeudbl.jakartamission.business;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;

@Named
@RequestScoped
public class SessionManager {

    public void createSession(String key, String value) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSession(true);
        session.setAttribute(key, value);
    }

    public String getValueFromSession(String key) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSession(false);
        if (session != null) {
            return (String) session.getAttribute(key);
        }
        return null;
    }

    public void invalidateSession() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
