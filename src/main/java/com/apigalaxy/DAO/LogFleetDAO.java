/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.DAO;

import com.apigalaxy.DAOFactory.MysqlDAOFactory;
import com.apigalaxy.POJOs.Imperium;
import com.apigalaxy.POJOs.LogFleet;
import com.apigalaxy.POJOs.Planet;
import com.apigalaxy.POJOs.Resources;
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
 * @author Francisco Jesus Moya Olivares
 */
public class LogFleetDAO implements com.apigalaxy.interfaces.IDAO<LogFleet, Map<String, String[]>>{
    
    //almacenamos los datos necesarios para crear los statment, de esta manera nos sera mas facil alterarlos si fuese necesario
    private final String DB_TABLE = "logFleet";
    private final String ID_OBJECT = "logFleet_id";
    //aqui almacenamos las 4 sentencias CRUD
    private final String ADD = "INSERT INTO " + DB_TABLE + " (name, descripcion, type, strategy, repairs, engineering, speed, live, shield, coordinates, destination, departure_time, resources, base, image, owner) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String DELETE = "DELETE FROM " + DB_TABLE + " WHERE " + ID_OBJECT + " = ?";
    private final String FIND_BY = "SELECT * FROM " + DB_TABLE;
    private final String UPDATE = "UPDATE " + DB_TABLE + " SET "
            + "name = ?,  "
            + "descripcion = ?, "
            + "fleettype = ?, "
            + "strategy = ?, "
            + "repairs = ?, "
            + "engineering = ?, "
            + "speed = ?, "
            + "live = ?, "
            + "shield = ?, "
            + "coordinates = ?, "
            + "destination = ?, "
            + "departure_time = ?, "
            + "resources = ?, "
            + "base = ?, "
            + "image = ?, "
            + "owner = ? "
            + "WHERE " + ID_OBJECT + " = ?";
    //por ultimo la conexion
    private Connection connection;

    public LogFleetDAO() {
        MysqlDAOFactory mysqlDAOFactory = new MysqlDAOFactory();
        this.connection = mysqlDAOFactory.connect();
    }

    @Override
    public Integer add(LogFleet logFleet) {
        //preparamos una respuesta negativa para informar de un posible fallo
        Integer res = -1;
        
        try{
            // usamos la conexxion para preparar el statment 
            PreparedStatement statement = connection.prepareStatement(ADD, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, logFleet.getName());
            statement.setString(2, logFleet.getDescription());
            statement.setString(3, logFleet.getfType().name());
            statement.setFloat(4, logFleet.getStrategy());
            statement.setFloat(5, logFleet.getRepairs());
            statement.setFloat(6, logFleet.getShield());
            statement.setInt(7, logFleet.getSpeed());
            statement.setInt(8, logFleet.getLive());
            statement.setInt(9, logFleet.getShield());
            statement.setInt(10, logFleet.getCoordinates());
            statement.setInt(11, logFleet.getDestination());
            statement.setTimestamp(12, logFleet.getDepartureTime());
            statement.setInt(13, logFleet.getResources().getResourceId());
            statement.setInt(14, logFleet.getBase().getPlanetId());
            statement.setString(15, logFleet.getImage());
            statement.setInt(16, logFleet.getImperium().getImperiumId());
            //ejecutamos el statment y almacenamos la respuesta
            Integer respons = statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                res = (int) generatedKeys.getLong(1);
            }
                    
        }catch(SQLException ex) {
            Logger.getLogger(LogFleetDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }

    @Override
    public Boolean delete(LogFleet logFleet) {
        //preparamos la respuesta en false para informar en caso de fallo
        Boolean res = false;
        try {
            //preparamos la sentencia
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setInt(1, logFleet.getLogFleetId());
            //ejecutamos la sentencia y almacenamos la respuesta que sear true en caso de no haber habido fallos
            res = statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(LogFleetDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    @Override
    public List findBy(Map<String, String[]> filter) {
        //creamos una lista para devolver en la que introduciremos los usuarios encontrados
        List<LogFleet> logFleets = new ArrayList<>();
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
                LogFleet newLogFleet = new LogFleet();
                newLogFleet.setLogFleetId(res.getInt("logFleet_id"));
                newLogFleet.setName(res.getString("name"));
                newLogFleet.setDescription(res.getString("descripcion"));
                newLogFleet.setfType(res.getString("type"));
                newLogFleet.setStrategy(res.getFloat("strategy"));
                newLogFleet.setRepairs(res.getFloat("repairs"));
                newLogFleet.setEngineering(res.getFloat("engineering"));
                newLogFleet.setSpeed(res.getInt("speed"));
                newLogFleet.setLive(res.getInt("live"));
                newLogFleet.setShield(res.getInt("shield"));
                newLogFleet.setCoordinates(res.getInt("coordinates"));
                newLogFleet.setDestination(res.getInt("destination"));
                newLogFleet.setDepartureTime(res.getTimestamp("departure_time"));
                Resources resources = new Resources();
                resources.setResourceId(res.getInt("resources"));
                newLogFleet.setResources(resources);
                Planet planet = new Planet();
                planet.setPlanetId(res.getInt("base"));
                newLogFleet.setBase(planet);
                newLogFleet.setImage(res.getString("image"));
                Imperium imperium = new Imperium();
                imperium.setImperiumId(res.getInt("owner"));
                newLogFleet.setImperium(imperium);
                logFleets.add(newLogFleet);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LogFleetDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return logFleets;
    }

    @Override
    public Boolean update(LogFleet logFleet) {
        //preparamos la respuesta en false para informar en caso de fallo
        Boolean res = false;
        try {
            // usamos la conexxion para preparar el statment 
            PreparedStatement statement = connection.prepareStatement(UPDATE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, logFleet.getName());
            statement.setString(2, logFleet.getDescription());
            statement.setString(3, logFleet.getfType().name());
            statement.setFloat(4, logFleet.getStrategy());
            statement.setFloat(5, logFleet.getRepairs());
            statement.setFloat(6, logFleet.getShield());
            statement.setInt(7, logFleet.getSpeed());
            statement.setInt(8, logFleet.getLive());
            statement.setInt(9, logFleet.getShield());
            statement.setInt(10, logFleet.getCoordinates());
            statement.setInt(11, logFleet.getDestination());
            statement.setTimestamp(12, logFleet.getDepartureTime());
            statement.setInt(13, logFleet.getResources().getResourceId());
            statement.setInt(14, logFleet.getBase().getPlanetId());
            statement.setString(15, logFleet.getImage());
            statement.setInt(16, logFleet.getImperium().getImperiumId());
            statement.setInt(17, logFleet.getLogFleetId());
            
            res = statement.execute();
        } catch (SQLException ex){
            Logger.getLogger(LogFleetDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
}
