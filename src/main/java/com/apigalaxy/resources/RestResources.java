/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.resources;

import com.apigalaxy.DAO.ResourcesDAO;
import com.apigalaxy.POJOs.Resources;
import com.apigalaxy.POJOs.TradeResources;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author Francisco Jesus Moya Olivares
 *
 */
@Path("resources")
public class RestResources {
  
    @POST
    @Path("/traderesources")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findplanets(TradeResources tradeResources) {
        System.out.println("transaccion entgrante: "+tradeResources.toString());
        ResourcesDAO resDAO = new ResourcesDAO();
        Response response;
        if(tradeResources.getOrigin().getNormal_quantity()>=tradeResources.getTransaction().getNormal_quantity() && tradeResources.getOrigin().getRare_quantity()>=tradeResources.getTransaction().getRare_quantity() && tradeResources.getOrigin().getPopulation_quantity()>=tradeResources.getTransaction().getPopulation_quantity()){
            if((tradeResources.getReceiver().getNormal_quantity()+tradeResources.getTransaction().getNormal_quantity())<=tradeResources.getReceiver().getNormal_capacity() && (tradeResources.getReceiver().getRare_quantity()+tradeResources.getTransaction().getRare_quantity())<=tradeResources.getReceiver().getRare_capacity() && (tradeResources.getReceiver().getPopulation_quantity()+tradeResources.getTransaction().getPopulation_quantity())<=tradeResources.getReceiver().getPopulation_capacity()){
                Resources receptor = new Resources();
                receptor.setNormal_quantity(tradeResources.getReceiver().getNormal_quantity()+tradeResources.getTransaction().getNormal_quantity());
                receptor.setRare_quantity(tradeResources.getReceiver().getRare_quantity()+tradeResources.getTransaction().getRare_quantity());
                receptor.setPopulation_quantity(tradeResources.getReceiver().getPopulation_quantity()+tradeResources.getTransaction().getPopulation_quantity());
                receptor.setNormal_capacity(tradeResources.getReceiver().getNormal_capacity());
                receptor.setRare_capacity(tradeResources.getReceiver().getRare_capacity());
                receptor.setPopulation_capacity(tradeResources.getReceiver().getPopulation_capacity());
                receptor.setResourceId(tradeResources.getReceiver().getResourceId());
                System.out.println("este es el receptor:  "+receptor.toString());
                resDAO.update(receptor);
                Resources origin = new Resources();
                origin.setNormal_quantity(tradeResources.getOrigin().getNormal_quantity()-tradeResources.getTransaction().getNormal_quantity());
                origin.setRare_quantity(tradeResources.getOrigin().getRare_quantity()-tradeResources.getTransaction().getRare_quantity());
                origin.setPopulation_quantity(tradeResources.getOrigin().getPopulation_quantity()-tradeResources.getTransaction().getPopulation_quantity());
                origin.setResourceId(tradeResources.getOrigin().getResourceId());
                origin.setNormal_capacity(tradeResources.getOrigin().getNormal_capacity());
                origin.setPopulation_capacity(tradeResources.getOrigin().getPopulation_capacity());
                origin.setRare_capacity(tradeResources.getOrigin().getRare_capacity());
                System.out.println("este es el origen:  "+receptor.toString());
                resDAO.update(origin);
                response = Response.ok("transaccion realizada").build();
            }else{
                response = Response.notModified().build();
            }
        }else{
            response = Response.notModified().build();
        }
        return response;
    }
    
}
