/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.resources;


import com.apigalaxy.DAO.PlanetDAO;
import com.apigalaxy.POJOs.Planet;
import com.apigalaxy.routines.Routines;
import jakarta.json.Json;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Jesus Moya Olivares
 */
@Path("planet")
public class RestPlanet {
    
    @POST
    @Path("/findplanets")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findplanets(Planet planet) {
        System.out.println("RestRlanet findplanets este es el planeta a buscar: "+planet.toString());
        Response response;
        PlanetDAO planetDAO = new PlanetDAO();
        String[] valor;
        Map<String, String[]> filter = new HashMap<String, String[]>();
        if(planet.getPlanetId() != null){
            valor = new String[]{planet.getPlanetId().toString()};
            filter.put("planet_id", valor);
        }
        if(planet.getName() != null){
            valor = new String[]{planet.getName()};
            filter.put("name", valor);
        }
        if(planet.getPlanetType() != null){
            valor = new String[]{planet.getPlanetType().getPlanetTypeId().toString()};
            filter.put("type", valor);
        }
        if(planet.getImperium() != null){
            valor = new String[]{planet.getImperium().getImperiumId().toString()};
            filter.put("owner", valor);
        }
        if(planet.getCoordinates() != null){
            valor = new String[]{planet.getCoordinates()};
            filter.put("coordinates", valor);
        }
        if(planet.getStar() != null){
            valor = new String[]{planet.getStar().getStarId().toString()};
            filter.put("star", valor);
        }
        System.out.println("este es el filtro: "+filter.toString());
        List<Planet> planetList = planetDAO.findBy(filter);
        response = Response.ok(planetList).build();
        return response;
    }
}
