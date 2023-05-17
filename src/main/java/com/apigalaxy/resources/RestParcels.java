/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.resources;

import com.apigalaxy.DAO.ParcelDAO;
import com.apigalaxy.DAO.PlanetDAO;
import com.apigalaxy.DAO.ResourcesDAO;
import com.apigalaxy.DAO.UserSessionDAO;
import com.apigalaxy.POJOs.Parcel;
import com.apigalaxy.POJOs.Planet;
import com.apigalaxy.POJOs.Resources;
import com.apigalaxy.POJOs.UserSession;
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

/**
 *
 * @author Francisco Jesus Moya Olivares
 */
@Path("parcels")
public class RestParcels {
    
    @POST
    @Path("/counttypes")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response countTypes(Parcel parcel) {
        
        ParcelDAO parceldao = new ParcelDAO();
        Response response;
        Map<String, String[]> map = new HashMap<String, String[]>();
        String[] valor1 = new String[]{parcel.getBuilding().getBuildType_id().toString()};
        map.put("building", valor1);
        String[] valor = new String[]{parcel.getLocation().toString()};
        if (parcel.getLocationT().name() == "planet"){
            map.put("planet", valor);
        }else{
            map.put("moon", valor);
        }
        
        List<Parcel> parcelList = parceldao.findBy(map);
        
        if(!parcelList.isEmpty()){
            response = Response.ok(parcelList.size()).build();
        }else{
            response = Response.status(Response.Status.NOT_FOUND).build();
        }
        
        return response;
    }
    
    @POST
    @Path("/getparcelsbytype")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getParcelsByType(Parcel parcel) {
        Routines routines = new Routines();
        ParcelDAO parceldao = new ParcelDAO();
        Response response;  
        Map<String, String[]> map = new HashMap<String, String[]>();
         Map<String, String[]> mapToBuilding = new HashMap<String, String[]>();
        String[] valor1 = new String[]{parcel.getBuilding().getBuildType_id().toString()};
        map.put("building", valor1);
        mapToBuilding.put("building", new String[]{"2"});
        mapToBuilding.put("to_building", valor1);
        String[] valor = new String[]{parcel.getLocation().toString()};
        if (parcel.getLocationT().name() == "planet"){
            map.put("planet", valor);
            mapToBuilding.put("planet", valor);
        }else{
            map.put("moon", valor);
            mapToBuilding.put("moon", valor);
        }
        
        List<Parcel> parcelList = parceldao.findBy(map);
        List<Parcel> parcelListToBuilding = parceldao.findBy(mapToBuilding);
        
        parcelList.addAll(parcelListToBuilding);
        
        response = Response.ok(parcelList).build();
        
        
        
        return response;
    }
    
    @POST
    @Path("/startconstruction")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response startConstruction(Parcel parcel) {
        Routines routines = new Routines();
        ParcelDAO parcelDAO = new ParcelDAO();
        ResourcesDAO resDAO = new ResourcesDAO();
        PlanetDAO planetDAO = new PlanetDAO();
        Response response;
        
        List<Planet> planetList = planetDAO.findBy(routines.constructMap("planet_id", parcel.getLocation().toString()));
        Resources resources = planetList.get(0).getResources();
        Map<String, String[]> map = new HashMap<String, String[]>();
        String[] valor = new String[]{parcel.getLocation().toString()};
        map.put("planet", valor);
        valor = new String[]{parcel.getTo_building().getBuildType_id().toString()};
        map.put("building", valor);
        List<Parcel> parcelList = parcelDAO.findBy(map);
        Integer normalCost = parcelList.get(0).getBasic_normal_cost();
        Integer rareCost = parcelList.get(0).getBasic_rare_cost();
        
        System.out.println("el coste de la construccion es: ");
        System.out.println("minerales normales: "+normalCost+" y tenemos para gastar: "+resources.getNormal_quantity());
        System.out.println("minerales raros: "+rareCost+" y tenemos para gastar: "+resources.getRare_quantity());
        
        if (resources.getNormal_quantity() > normalCost && resources.getRare_quantity() > rareCost){
            resources.setNormal_quantity(resources.getNormal_quantity()-normalCost);
            resources.setRare_quantity(resources.getRare_quantity()-rareCost);
            resDAO.update(resources);
            response = Response.ok(parcelDAO.starConstruction(parcel)).build();
        }else{
            response = Response.status(Response.Status.CONFLICT).build();
        }
        
        return response;
    }
    
    @POST
    @Path("/endconstruction")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response endConstruction(Parcel parcel) {
        
        ParcelDAO parceldao = new ParcelDAO();
        Response response;
        
        response = Response.ok(parceldao.endConstruction(parcel)).build();
        
        return response;
    }
    
}
