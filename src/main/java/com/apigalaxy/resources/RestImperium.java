/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.resources;

import com.apigalaxy.DAO.ImperiumDAO;
import com.apigalaxy.DAO.UserSessionDAO;
import com.apigalaxy.POJOs.Imperium;
import com.apigalaxy.POJOs.Planet;
import com.apigalaxy.POJOs.User;
import com.apigalaxy.POJOs.UserSession;
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
@Path("imperium")
public class RestImperium {
    
    //crea el imperio 
    @POST
    @Path("/createImperium")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    //en este mapa a de entrar una clave con la sesion y otra con el nombre del imperio
    public Response createImperium(Map<String, String> map) {
        UserSession userSession = new UserSession();
        userSession.setUserSession_id(Integer.parseInt(map.get("session_id")));
        Imperium imperium = new Imperium();
        imperium.setName(map.get("name"));
        //creamos variables y objetos que vamos a necesitar
        System.out.println("Imperio a crear: "+imperium.toString()+ " con la sesion: "+userSession.toString());
        UserSessionDAO usdao = new UserSessionDAO();
        Response response;
        // creamos un mapa para poder buscar una sesion de usuario
        Map<String, String[]> auxmap = new HashMap<String, String[]>();
        String[] userSession_id = new String[]{userSession.getUserSession_id().toString()};
        auxmap.put("userSession_id", userSession_id);
        //almacenamos la lista de sesiones
        List<UserSession> sessions = usdao.findBy(auxmap);
        //obtenemos la sesion que queremos y calculamos si la diferencia de timepo entre la ultima actualizacion de sesion y la sesion actual
        UserSession Session = sessions.get(0);
        Routines routines = new Routines();
        if (routines.checkSessionUp(Session)){
            ImperiumDAO impDAO = new ImperiumDAO();
            Imperium planet = impDAO.createImpetium(Session.getUser(), imperium);
            System.out.println("esta dentro de la parte donde crea el imperio, se ha enviado user: "+Session.getUser() + " mas imperium: "+ imperium.toString());
            response = Response.ok(planet).build();
            System.out.println("este es el response: "+response+ " y este es el planeta que tendria que tener dentro: "+planet);
        }
        else{
            System.out.println("esta en la parte de la sesion expirada");
            response = Response.status(101, "sesion expirada").build();
            
        }
        
        return response;
    }
    
    //devuelve el imperio asociado a una sesion
    @POST
    @Path("/loadimperium")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loadImperium(UserSession userSession) {
        //creamos variables y objetos que vamos a necesitar
        System.out.println("sesion en la que buscamos: "+userSession.toString());
        UserSessionDAO usdao = new UserSessionDAO();
        Response response;
        // creamos un mapa para poder buscar una sesion de usuario
        Map<String, String[]> map = new HashMap<String, String[]>();
        String[] userSession_id = new String[]{userSession.getUserSession_id().toString()};
        map.put("userSession_id", userSession_id);
        //almacenamos la lista de sesiones
        List<UserSession> sessions = usdao.findBy(map);
        //obtenemos la sesion que queremos y calculamos si la diferencia de timepo entre la ultima actualizacion de sesion y la sesion actual
        UserSession Session = sessions.get(0);
        Routines routines = new Routines();
        if (routines.checkSessionUp(Session)){
            ImperiumDAO impDAO = new ImperiumDAO();
            
            Map<String, String[]> mapimperium = new HashMap<String, String[]>();
            String[] user = new String[]{Session.getUser().getUserId().toString()};
            mapimperium.put("user", user);
            //almacenamos la lista de sesiones
            List<Imperium> Imperiums = impDAO.findBy(mapimperium);
            response = Response.ok(Imperiums).build();
            System.out.println("este es el response de cuando se carga la lista: "+response);
        }
        else{
            response = Response.status(101, "sesion expirada").build();
        }
        
        return response;
    }

    //cambia el propietario de un imperio de un jugador a gaia
    @POST
    @Path("/deleteimperium")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteImperium(Map<String, String> map) {
        
        UserSession userSession = new UserSession();
        userSession.setUserSession_id(Integer.parseInt(map.get("session_id")));
        Imperium imperium = new Imperium();
        imperium.setName((map.get("imperium_name")));
        UserSessionDAO usdao = new UserSessionDAO();
        Response response;
        Routines routines = new Routines();
        List<UserSession> sessions = usdao.findBy(routines.constructMap("userSession_id", userSession.getUserSession_id().toString()));
        //obtenemos la sesion que queremos y calculamos si la diferencia de timepo entre la ultima actualizacion de sesion y la sesion actual
        UserSession Session = sessions.get(0);        
        if (routines.checkSessionUp(Session)){
            ImperiumDAO impDAO = new ImperiumDAO();
            User user = new User();
            user.setUserId(1);
            List<Imperium> impList = impDAO.findBy(routines.constructMap("name", imperium.getName()));
            imperium = impList.get(0);
            imperium.setUser(user);
            imperium.setCientificData(0);
            if (impDAO.update(imperium) > 0){
                System.out.println("eliminacion correcta del imperio");
                response = Response.ok(Json.createObjectBuilder().add("success", true).build()).build();
            }else{
                System.out.println("eliminacion incorrecta del imperio");
                response = Response.status(103, "fallo al actualizar el imperio").build();
            }
        }
        else{
            response = Response.status(101, "sesion expirada").build();
        }
        
        return response;
    }
    
    //busca un imperio
    @POST
    @Path("/findimperium")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findImperium(Map<String, String> map) {
        
        UserSession userSession = new UserSession();
        userSession.setUserSession_id(Integer.parseInt(map.get("session_id")));
        Imperium imperium = new Imperium();
        imperium.setName((map.get("imperium_name")));
        UserSessionDAO usdao = new UserSessionDAO();
        Response response;
        Routines routines = new Routines();
        List<UserSession> sessions = usdao.findBy(routines.constructMap("userSession_id", userSession.getUserSession_id().toString()));
        //obtenemos la sesion que queremos y calculamos si la diferencia de timepo entre la ultima actualizacion de sesion y la sesion actual
        UserSession Session = sessions.get(0);   
        //comprobamos si la sesion esta activa o no
        if (routines.checkSessionUp(Session)){
            ImperiumDAO impDAO = new ImperiumDAO();
            User user = new User();
            user.setUserId(1);
            List<Imperium> impList = impDAO.findBy(routines.constructMap("name", imperium.getName()));
            imperium = impList.get(0);
            response = Response.ok(imperium).build();
            
        }
        else{
            response = Response.status(101, "sesion expirada").build();
        }
        
        return response;
    }
}
