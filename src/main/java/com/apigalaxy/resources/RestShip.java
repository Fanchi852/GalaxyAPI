/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.resources;


import com.apigalaxy.DAO.ShipClassDAO;
import com.apigalaxy.POJOs.ShipClass;
import com.apigalaxy.routines.Routines;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
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
 * 
 */
@Path("ship")
public class RestShip {
    
    @GET
    @Path("/lishipclass")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response listShipClass() {
        ShipClassDAO stardao = new ShipClassDAO();
        Response response;
        Map<String, String[]> map = new HashMap<String, String[]>();
        List<ShipClass> shipClassList = stardao.findBy(map);
        
        response = Response.ok(shipClassList).build();
        
        return response;
    }
    
}
