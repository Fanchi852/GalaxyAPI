/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.resources;


import com.apigalaxy.DAO.FleetDAO;
import com.apigalaxy.DAO.PlanetDAO;
import com.apigalaxy.DAO.ResourcesDAO;
import com.apigalaxy.DAO.ShipClassDAO;
import com.apigalaxy.DAO.ShipDAO;
import com.apigalaxy.DAO.StarSystemDAO;
import com.apigalaxy.DAO.StarSystemImperiumDAO;
import com.apigalaxy.POJOs.Fleet;
import com.apigalaxy.POJOs.Imperium;
import com.apigalaxy.POJOs.Planet;
import com.apigalaxy.POJOs.Resources;
import com.apigalaxy.POJOs.Ship;
import com.apigalaxy.POJOs.ShipClass;
import com.apigalaxy.POJOs.StarSystem;
import com.apigalaxy.POJOs.StarSystemImperium;
import com.apigalaxy.routines.Routines;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Francisco Jesus Moya Olivares
 */
@Path("fleet")
public class RestFleet {
    
    @POST
    @Path("/listfleet")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response listFleet(Planet planet) {
        System.out.println("planeta para listar flotas: "+planet.toString());
        FleetDAO fleetdao = new FleetDAO();
        Routines routines = new Routines();
        Response response;
        List<Fleet> FleetList = fleetdao.findBy(routines.constructMap("destination", planet.getPlanetId().toString()));
        List<Fleet> FleetList2 = fleetdao.findBy(routines.constructMap("coordinates", planet.getCoordinates()));
        FleetList.addAll(FleetList2);
        response = Response.ok(FleetList).build();
        
        return response;
    }
    
    @POST
    @Path("/listfleetinplanet")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response listFleetInPlanet(Planet planet) {
        System.out.println("planeta para listar flotas: "+planet.toString());
        FleetDAO fleetdao = new FleetDAO();
        Routines routines = new Routines();
        Response response;
        List<Fleet> FleetList = fleetdao.findBy(routines.constructMap("coordinates", planet.getCoordinates()));
        response = Response.ok(FleetList).build();
        
        return response;
    }
    
    //crea una flota nueva 
    @POST
    @Path("/createfleet")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createFleet(Fleet fleet) {
        System.out.println("flota a crear: "+fleet.toString());
        FleetDAO fleetdao = new FleetDAO();
        Routines routines = new Routines();
        Response response;
        fleetdao.createFleet(fleet);
        List<Fleet> fleetList = fleetdao.findBy(routines.constructMap("fleet_id", fleetdao.createFleet(fleet).getFleetId().toString()));
        
        response = Response.ok(fleetList.get(0)).build();
        return response;
    }
    
    //crea una flota nueva 
    @POST
    @Path("/addshiptofleet")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addShipToFleet(Ship ship) {
        System.out.println("nave a añadir: "+ship.toString());
        ShipDAO shipdao = new ShipDAO();
        Routines routines = new Routines();
        ResourcesDAO resDAO = new ResourcesDAO();
        FleetDAO fleetDAO = new FleetDAO();
        PlanetDAO planetDAO = new PlanetDAO();
        ShipClassDAO shipClassDAO = new ShipClassDAO();
        Response response;
        
        List<Fleet> fleetList = fleetDAO.findBy(routines.constructMap("fleet_id", ship.getFleet().getFleetId().toString()));
        List<Planet> planetList = planetDAO.findBy(routines.constructMap("planet_id", fleetList.get(0).getBase().getPlanetId().toString()));
        List<Resources> resourcesList = resDAO.findBy(routines.constructMap("resources_id", planetList.get(0).getResources().getResourceId().toString()));
        Resources resources = resourcesList.get(0);
        List<ShipClass> shipList = shipClassDAO.findBy(routines.constructMap("shipClass_id", ship.getShipClass().getShipClassId().toString()));
        ShipClass shipClass = shipList.get(0);
        if (resources.getNormal_quantity()>shipClass.getBasic_normal_cost() && resources.getRare_quantity()>shipClass.getBasic_rare_cost() && resources.getPopulation_quantity()>shipClass.getBasic_people_cost()){
            resources.setNormal_quantity(resources.getNormal_quantity()-shipClass.getBasic_normal_cost());
            resources.setRare_quantity(resources.getRare_quantity()-shipClass.getBasic_rare_cost());
            resources.setPopulation_quantity(resources.getPopulation_quantity()-shipClass.getBasic_people_cost());
            
            shipdao.createShip(ship);
            
            response = Response.ok("ok").build();
        }else{
            response = Response.notModified("recursos insuficientes").build();
        }
        
        response = Response.ok(fleetList.get(0)).build();
        return response;
    }
    
    //crea una flota nueva 
    @POST
    @Path("/explore")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response explore(Fleet fleet) {
        Routines routines = new Routines();
        Response response;
        StarSystemImperiumDAO knowStardao = new StarSystemImperiumDAO();
        StarSystemDAO starSysDAO = new StarSystemDAO();
        StarSystemImperium newStarSystemImperium = new StarSystemImperium();
        FleetDAO fleetDAO = new FleetDAO();
        Random rand = new Random();
        
        Map<String, String[]> map = new HashMap<String, String[]>();
        List<StarSystem> starList = starSysDAO.findBy(map);
        
        List<StarSystemImperium> starImperiumList = knowStardao.findBy(routines.constructMap("imperium", fleet.getImperium().getImperiumId().toString()));
        
        for (StarSystemImperium starImperium : starImperiumList){
            starList.remove(starImperium.getStar());
        }
        
        int randomIndex = rand.nextInt(starList.size());
        
        newStarSystemImperium.setImperium(fleet.getImperium());
        newStarSystemImperium.setStar(starList.get(randomIndex));
        knowStardao.add(newStarSystemImperium);
        
        fleetDAO.delete(fleet);
        
        response = Response.ok(newStarSystemImperium).build();
        return response;
    }
    
}
