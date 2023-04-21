/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.resources;

import com.apigalaxy.DAO.UserDAO;
import com.apigalaxy.DAO.UserSessionDAO;
import com.apigalaxy.POJOs.User;
import com.apigalaxy.POJOs.UserSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Fanch
 */
@Path("user")
public class RestUser {
    
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(User user) {
        System.out.println("login user: "+user.toString());
        UserSessionDAO usdao = new UserSessionDAO();
        Response response;
        UserSession userSession = usdao.login(user);
        response = Response.ok(userSession).build();
        
        /*
        UserDAO  userDAO = new UserDAO();
        
        Map<String, String[]> map = new HashMap<String, String[]>();
        String[] email = new String[]{user.getEmail()};
        String[] password = new String[]{user.getPassword()};
        map.put("password", password);
        map.put("email", email);
        
        List<User> users = userDAO.findBy(map);
        
        if(users.get(0).getEmail().equals(user.getEmail())){
                        response = Response.ok(users.get(0)).build();
                    }else{
                        response = Response.notAcceptable(Collections.emptyList()).build();
                    }*/
        
        return response;
    }
    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(User user) {
        System.out.println("register user: "+user.toString());
        UserDAO usdao = new UserDAO();
        Response response;
        response = Response.ok(usdao.add(user)).build();
        
        /*
        UserDAO  userDAO = new UserDAO();
        
        Map<String, String[]> map = new HashMap<String, String[]>();
        String[] email = new String[]{user.getEmail()};
        String[] password = new String[]{user.getPassword()};
        map.put("password", password);
        map.put("email", email);
        
        List<User> users = userDAO.findBy(map);
        
        if(users.get(0).getEmail().equals(user.getEmail())){
                        response = Response.ok(users.get(0)).build();
                    }else{
                        response = Response.notAcceptable(Collections.emptyList()).build();
                    }*/
        
        return response;
    }
}
