/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package com.indonesia.j.weather;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.util.Random;

/**
 * REST Web Service
 *
 * @author lkanan
 */
@Path("JakartaWeather")
@RequestScoped
public class JakartaWeatherResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of JarkartaWeatherResource
     */
    public JakartaWeatherResource()  {
    }
   
     @GET
    @Produces(jakarta.ws.rs.core.MediaType.TEXT_PLAIN)
    public String getText(@QueryParam("lat") double latitude, @QueryParam("long") double longitude) {
        // Simuler une température aléatoire
        double temperature = generateRandomTemperature();
        
        // Générer un message en fonction de la température
        String message;
        if (temperature >= 30) {
            message = "Il fait chaud! N'oubliez pas de vous hydrater.";
        } else if (temperature <= 10) {
            message = "Il fait froid! Assurez-vous de vous couvrir bien.";
        } else {
            message = "La température est agréable. Profitez de votre journée!";
        }
        
        return message;
    }
    
    private double generateRandomTemperature() {
        // Générer une température aléatoire entre -10 et 40 degrés Celsius
        Random random = new Random();
        return random.nextDouble() * 50 - 10;
    }

    @PUT
    @Consumes(jakarta.ws.rs.core.MediaType.TEXT_PLAIN)
    public void putText(String content) {
    }
}
