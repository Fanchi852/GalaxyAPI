/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.DAO;

import com.apigalaxy.POJOs.Resources;
import com.apigalaxy.DAOFactory.MysqlDAOFactory;
import com.apigalaxy.POJOs.Planet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author FRancisco Jesus Moya Olivares
 * 
 */
public class ResourcesDAO implements com.apigalaxy.interfaces.IDAO<Resources, Map<String, String[]>>{
    //almacenamos los datos necesarios para crear los statment, de esta manera nos sera mas facil alterarlos si fuese necesario
    private final String DB_TABLE = "resources";
    private final String ID_OBJECT = "resources_id";
    //aqui almacenamos las 4 sentencias CRUD
    private final String ADD = "INSERT INTO " + DB_TABLE + " (normal_quantity, rare_quantity, population_quantity, normal_capacity, rare_capacity, population_capacity) VALUES (?, ?, ?, ?, ?, ?)";
    private final String DELETE = "DELETE FROM " + DB_TABLE + " WHERE " + ID_OBJECT + " = ?";
    private final String FIND_BY = "SELECT * FROM " + DB_TABLE;
    private final String UPDATE = "UPDATE " + DB_TABLE + " SET "
            + "normal_quantity = ?,  "
            + "rare_quantity = ?, "
            + "population_quantity = ?, "
            + "normal_capacity = ?, "
            + "rare_capacity = ?, "
            + "population_capacity = ? "
            + "WHERE " + ID_OBJECT + " = ?";
    //por ultimo la conexion
    private Connection connection;

    public ResourcesDAO() {
        MysqlDAOFactory mysqlDAOFactory = new MysqlDAOFactory();
        this.connection = mysqlDAOFactory.connect();
    }

    @Override
    public Integer add(Resources resources) {
        //preparamos una respuesta negativa para informar de un posible fallo
        Integer res = -1;
        
        try{
            // usamos la conexxion para preparar el statment 
            PreparedStatement statement = connection.prepareStatement(ADD, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, resources.getNormal_quantity());
            statement.setInt(2, resources.getRare_quantity());
            statement.setInt(3, resources.getPopulation_quantity());
            statement.setInt(4, resources.getNormal_capacity());
            statement.setInt(5, resources.getRare_capacity());
            statement.setInt(6, resources.getPopulation_capacity());
            //ejecutamos el statment y almacenamos la respuesta
            Integer respons = statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                res = (int) generatedKeys.getLong(1);
            }
                    
        }catch(SQLException ex) {
            Logger.getLogger(ResourcesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }

    @Override
    public Boolean delete(Resources resources) {
        //preparamos la respuesta en false para informar en caso de fallo
        Boolean res = false;
        
        try {
            //preparamos la sentencia
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setInt(1, resources.getResourceId());
            //ejecutamos la sentencia y almacenamos la respuesta que sear true en caso de no haber habido fallos
            res = statement.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(ResourcesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }

    @Override
    public List findBy(Map<String, String[]> filter) {
        //creamos una lista para devolver en la que introduciremos los usuarios encontrados
        List<Resources> resourcesList = new ArrayList<>();
        //creamos unas bariables necesarias
        //usando hasAnd para el caso en que haya mas de una condicion para la busqueda        
        //y hasWhere para poder controllar cuendo añadir where
        String where = " WHERE ";
        Boolean hasAnd = false;
        Boolean haswhere = false;
        //recorreremos el mapa y por cada campo que tengamos que buscar iramos construllendo la sentencia añadiendo los where y and necesarios para crearla de manera correcta
        for (String key : filter.keySet()){
            if(!key.equals("ACTION")){
                haswhere = true;
                String[] values = filter.get(key);
            
                if(hasAnd){
                    where += " AND ";
                }
                hasAnd = true;

                where += " " + key + " IN (";
                
                Integer count = 0;
                
                for(String value: values){
                    where += count != 0 ? ", ": "";
                    where += "\"" + value + "\"";
                    count ++;
                }
                where += ")";
            }
        }
        //una vez creada la sentencia ahora tenemos que añadir los valores necesarios 
        System.out.println("SLQ SENCENCE: " + FIND_BY + where);
        try {
            //preparamos la sentencia en funcion de si hay que añadir buscadores o traer toda la tabla
            PreparedStatement statement;
            if(haswhere){
                statement = connection.prepareStatement(FIND_BY + where);
            }else{
                statement = connection.prepareStatement(FIND_BY);
            }
            //ejecutamos la sentencia
            ResultSet res = statement.executeQuery();
            // vamos almacenando las respuestas ya sea una sola o una lista y devolvemos las respuesta sen una lista
            while (res.next()){
                Resources newResources = new Resources();
                
                newResources.setResourceId(res.getInt("resources_id"));
                newResources.setNormal_quantity(res.getInt("normal_quantity"));
                newResources.setRare_quantity(res.getInt("rare_quantity"));
                newResources.setPopulation_quantity(res.getInt("population_quantity"));
                newResources.setNormal_capacity(res.getInt("normal_capacity"));
                newResources.setRare_capacity(res.getInt("rare_capacity"));
                newResources.setPopulation_capacity(res.getInt("population_capacity"));
                resourcesList.add(newResources);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ResourcesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return resourcesList;
    }

    @Override
    public Integer update(Resources resources) {
        //preparamos la respuesta en false para informar en caso de fallo
        Integer res = 0;
        
        try {
            // usamos la conexxion para preparar el statment 
            PreparedStatement statement = connection.prepareStatement(UPDATE, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, resources.getNormal_quantity());
            statement.setInt(2, resources.getRare_quantity());
            statement.setInt(3, resources.getPopulation_quantity());
            statement.setInt(4, resources.getNormal_capacity());
            statement.setInt(5, resources.getRare_capacity());
            statement.setInt(6, resources.getPopulation_capacity());
            statement.setInt(7, resources.getResourceId());
            
            res = statement.executeUpdate();
        } catch (SQLException ex){
            Logger.getLogger(ResourcesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }
    
    public void quantity_resources_update(Integer planetId){
        
        try {
            // usamos la conexxion para preparar el statment 
            PreparedStatement statement = connection.prepareStatement("call quantity_resources_update("+planetId+")", Statement.RETURN_GENERATED_KEYS);
            
            statement.executeQuery();
        } catch (SQLException ex){
            Logger.getLogger(ResourcesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
