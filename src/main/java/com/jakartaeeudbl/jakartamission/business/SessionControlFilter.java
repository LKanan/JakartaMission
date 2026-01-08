package com.jakartaeeudbl.jakartamission.business;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/pages/*")
public class SessionControlFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialisation du filtre
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Vérifier si l'utilisateur est connecté en vérifiant la présence de l'attribut de session "user"
        String email = (String) httpRequest.getSession().getAttribute("user");
       
        if (email == null) {
            // L'utilisateur n'est pas connecté, rediriger vers la page d'accueil/connexion
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/index.xhtml");
        } else {
            // L'utilisateur est connecté, laisser la requête continuer
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // Nettoyage du filtre
    }
}
