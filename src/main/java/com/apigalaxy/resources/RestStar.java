/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.resources;


import com.apigalaxy.DAO.StarSystemDAO;
import com.apigalaxy.DAO.StarSystemImperiumDAO;
import com.apigalaxy.POJOs.Imperium;
import com.apigalaxy.POJOs.StarSystem;
import com.apigalaxy.POJOs.StarSystemImperium;
import com.apigalaxy.routines.Routines;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Jesus Moya Olivares
 * 
 */
@Path("star")
public class RestStar {
    
    @GET
    @Path("/liststar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response listFleet() {
        StarSystemDAO stardao = new StarSystemDAO();
        Response response;
        Map<String, String[]> map = new HashMap<String, String[]>();
        List<StarSystem> starList = stardao.findBy(map);
        
        response = Response.ok(starList).build();
        
        return response;
    }
    
    @POST
    @Path("/liststarknown")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response listFleetKnow(Imperium imperium) {
        System.out.println("imperio por el que buscamos estrellas: "+imperium.toString());
        StarSystemImperiumDAO knowStardao = new StarSystemImperiumDAO();
        StarSystemDAO starSysDAO = new StarSystemDAO();
        List<StarSystem> res = new ArrayList();
        Routines routines = new Routines();
        Response response;
        List<StarSystemImperium> starImperiumList = knowStardao.findBy(routines.constructMap("imperium", imperium.getImperiumId().toString()));
        
        for (StarSystemImperium starSystemImperium : starImperiumList){
            
            List<StarSystem> starList = starSysDAO.findBy(routines.constructMap("star_id", starSystemImperium.getStar().getStarId().toString()));
            
            res.add(starList.get(0));
        }
        
        response = Response.ok(res).build();
        
        return response;
    }
    
}
