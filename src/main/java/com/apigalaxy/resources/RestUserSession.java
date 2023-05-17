/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.resources;

import com.apigalaxy.DAO.UserSessionDAO;
import com.apigalaxy.POJOs.UserSession;
import com.apigalaxy.routines.Routines;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author Francisco Jesus Moya Olivares
 */

@Path("usersession")
public class RestUserSession {
    
    @POST
    @Path("/checksession")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response checksession(UserSession usersession) {
        
        Response response;
        Routines routines = new Routines();
        UserSessionDAO usersessionDAO = new UserSessionDAO();
        List<UserSession> userSessionList = usersessionDAO.findBy(routines.constructMap("userSession_id", usersession.getUserSession_id().toString()));
        response = Response.ok(routines.checkSessionUp(userSessionList.get(0))).build();
        
        return response;
    }
    
}
