/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.resources;


import com.apigalaxy.DAO.ImperiumDAO;
import com.apigalaxy.DAO.TechnologyDAO;
import com.apigalaxy.DAO.TechnologyImperiumDAO;
import com.apigalaxy.POJOs.Imperium;
import com.apigalaxy.POJOs.Technology;
import com.apigalaxy.POJOs.TechnologyImperium;
import com.apigalaxy.POJOs.TechnologyView;
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
@Path("technology")
public class RestTechnology {
    
    @POST
    @Path("/techlist")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response techList(Imperium imperium) {
        System.out.println("recibimos de la app: "+imperium.toString());
        TechnologyImperiumDAO TechnologyImperiumDAO = new TechnologyImperiumDAO();
        Response response;
        List<TechnologyView> technologyViewList = TechnologyImperiumDAO.technologyList(imperium);
        response = Response.ok(technologyViewList).build();
        
        return response;
    }
    
    @POST
    @Path("/techupdate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response techupdate(TechnologyImperium technologyImperium) {
        System.out.println("recibimos de la app: "+technologyImperium.toString());
        //Prueba
        TechnologyImperiumDAO technologyImperiumDAO = new TechnologyImperiumDAO();
        ImperiumDAO impDAO = new ImperiumDAO();
        TechnologyDAO technologyDAO = new TechnologyDAO();
        Routines routines = new Routines();
        Response response;
        
        List<TechnologyImperium> technologyImperiumList = technologyImperiumDAO.findBy(routines.constructMap("technology_imperium_id", technologyImperium.getTechnologyImperiumId().toString()));
        List<Imperium> imperiumList = impDAO.findBy(routines.constructMap("imperium_id", technologyImperiumList.get(0).getImperium().getImperiumId().toString()));
        List<Technology> technologyList = technologyDAO.findBy(routines.constructMap("technology_id", technologyImperiumList.get(0).getTechnology().getTechnologyId().toString()));
        System.out.println("technologyImperiumList: "+technologyImperiumList.get(0).toString());
        System.out.println("imperiumList: "+imperiumList.get(0).toString());
        System.out.println("technologyList: "+technologyList.get(0).toString());
        System.out.println("consters de la ciencia: " + imperiumList.get(0).getCientificData().toString() + " + " + technologyList.get(0).getBasicCost().toString());
        if (imperiumList.get(0).getCientificData()>technologyList.get(0).getBasicCost()){
            technologyImperium.setImperium(technologyImperiumList.get(0).getImperium());
            technologyImperium.setTechnology(technologyImperiumList.get(0).getTechnology());
            Integer res = technologyImperiumDAO.update(technologyImperium);
            System.out.println(res);
            if (res == 1){
                Imperium imp = technologyImperiumList.get(0).getImperium();
                imp.setCientificData(imperiumList.get(0).getCientificData()-technologyList.get(0).getBasicCost());
                impDAO.update(imp);
                response = Response.ok(res).build();
            }else{
                response = Response.notModified("fallo en la actualizacion").build();
            }
        }else{
            response = Response.notModified("Careces de los datos necesarios").build();
        }
        return response;
    } 
}
