/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.DAO;

import com.apigalaxy.DAOFactory.MysqlDAOFactory;
import com.apigalaxy.POJOs.Fleet;
import com.apigalaxy.POJOs.FleetType;
import com.apigalaxy.POJOs.Imperium;
import com.apigalaxy.POJOs.Planet;
import com.apigalaxy.POJOs.Resources;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Francisco Jesus Moya Olivares
 */
public class FleetDAO implements com.apigalaxy.interfaces.IDAO<Fleet, Map<String, String[]>>{
    //almacenamos los datos necesarios para crear los statment, de esta manera nos sera mas facil alterarlos si fuese necesario
    private final String DB_TABLE = "fleet";
    private final String ID_OBJECT = "fleet_id";
    //aqui almacenamos las 4 sentencias CRUD
    private final String ADD = "INSERT INTO " + DB_TABLE + " (name, descripcion, fleettype, coordinates, destination, departure_time, base, resources, image, owner) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String DELETE = "DELETE FROM " + DB_TABLE + " WHERE " + ID_OBJECT + " = ?";
    private final String FIND_BY = "SELECT * FROM " + DB_TABLE;
    private final String UPDATE = "UPDATE " + DB_TABLE + " SET "
            + "name = ?,  "
            + "descripcion = ?, "
            + "fleettype = ?, "
            + "coordinates = ?, "
            + "destination = ?, "
            + "departure_time = ?, "
            + "base = ?, "
            + "resources = ?, "
            + "image = ?, "
            + "owner = ? "
            + "WHERE " + ID_OBJECT + " = ?";
    //por ultimo la conexion
    private Connection connection;

    public FleetDAO() {
        MysqlDAOFactory mysqlDAOFactory = new MysqlDAOFactory();
        this.connection = mysqlDAOFactory.connect();
    }

    @Override
    public Integer add(Fleet fleet) {
        //preparamos una respuesta negativa para informar de un posible fallo
        Integer res = -1;
        
        try{
            // usamos la conexxion para preparar el statment 
            PreparedStatement statement = connection.prepareStatement(ADD, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, fleet.getName());
            statement.setString(2, fleet.getDescription());
            statement.setInt(3, fleet.getFleetType().getFleetTypeId());
            statement.setInt(4, fleet.getCoordinates());
            statement.setInt(5, fleet.getDestination());
            statement.setTimestamp(6, fleet.getDepartureTime());
            statement.setInt(7, fleet.getBase().getPlanetId());
            statement.setInt(8, fleet.getResources().getResourceId());
            statement.setString(9, fleet.getImage());
            statement.setInt(10, fleet.getImperium().getImperiumId());
            //ejecutamos el statment y almacenamos la respuesta
            Integer respons = statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                res = (int) generatedKeys.getLong(1);
            }
                    
        }catch(SQLException ex) {
            Logger.getLogger(FleetDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }

    @Override
    public Boolean delete(Fleet fleet) {
        //preparamos la respuesta en false para informar en caso de fallo
        Boolean res = false;
        try {
            //preparamos la sentencia
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setInt(1, fleet.getFleetId());
            //ejecutamos la sentencia y almacenamos la respuesta que sear true en caso de no haber habido fallos
            res = statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(FleetDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    @Override
    public List findBy(Map<String, String[]> filter) {
        //creamos una lista para devolver en la que introduciremos los usuarios encontrados
        List<Fleet> fleets = new ArrayList<>();
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
                Fleet newFleet = new Fleet();
                newFleet.setFleetId(res.getInt("fleet_id"));
                newFleet.setName(res.getString("name"));
                newFleet.setDescription(res.getString("descripcion"));
                FleetType ft = new FleetType();
                ft.setFleetTypeId(res.getInt("fleettype"));
                newFleet.setFleetType(ft);
                newFleet.setCoordinates(res.getInt("coordinates"));
                newFleet.setDestination(res.getInt("destination"));
                newFleet.setDepartureTime(res.getTimestamp("departure_time"));
                Planet planet = new Planet();
                planet.setPlanetId(res.getInt("base"));
                newFleet.setBase(planet);
                Resources resources = new Resources();
                resources.setResourceId(res.getInt("resources"));
                newFleet.setResources(resources);
                newFleet.setImage(res.getString("image"));
                Imperium imperium = new Imperium();
                imperium.setImperiumId(res.getInt("owner"));
                newFleet.setImperium(imperium);
                fleets.add(newFleet);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FleetDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fleets;
    }

    @Override
    public Integer update(Fleet fleet) {
        //preparamos la respuesta en false para informar en caso de fallo
        Integer res = 0;
        try {
            // usamos la conexxion para preparar el statment 
            PreparedStatement statement = connection.prepareStatement(UPDATE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, fleet.getName());
            statement.setString(2, fleet.getDescription());
            statement.setInt(3, fleet.getFleetType().getFleetTypeId());
            statement.setInt(4, fleet.getCoordinates());
            statement.setInt(5, fleet.getDestination());
            statement.setTimestamp(6, fleet.getDepartureTime());
            statement.setInt(7, fleet.getBase().getPlanetId());
            statement.setInt(8, fleet.getResources().getResourceId());
            statement.setString(9, fleet.getImage());
            statement.setInt(10, fleet.getImperium().getImperiumId());
            statement.setInt(11, fleet.getFleetId());
            
            res = statement.executeUpdate();
        } catch (SQLException ex){
            Logger.getLogger(FleetDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
}
