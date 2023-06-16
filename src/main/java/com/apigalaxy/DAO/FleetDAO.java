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
import com.apigalaxy.routines.Routines;
import java.sql.CallableStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
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
    private final String ADD = "INSERT INTO " + DB_TABLE + " (name, descripcion, fleettype, coordinates, destination, departure_time, total_life, life, total_shield, shield, total_damage, damage, total_speed, speed, base, resources, owner) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String DELETE = "DELETE FROM " + DB_TABLE + " WHERE " + ID_OBJECT + " = ?";
    private final String FIND_BY = "SELECT * FROM " + DB_TABLE;
    private final String UPDATE = "UPDATE " + DB_TABLE + " SET "
            + "name = ?,  "
            + "descripcion = ?, "
            + "fleettype = ?, "
            + "coordinates = ?, "
            + "destination = ?, "
            + "departure_time = ?, "
            + "total_life = ?, "
            + "life = ?, "
            + "total_shield = ?, "
            + "shield = ?, "
            + "total_damage = ?, "
            + "damage = ?, "
            + "total_speed = ?, "
            + "speed = ?, "
            + "base = ?, "
            + "resources = ?, "
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
            statement.setString(4, fleet.getCoordinates());
            statement.setInt(5, fleet.getDestination());
            statement.setTimestamp(6, fleet.getDepartureTime());
            statement.setInt(7, fleet.getTotalLife());
            statement.setInt(8, fleet.getLife());
            statement.setInt(9, fleet.getTotalShield());
            statement.setInt(10, fleet.getShield());
            statement.setInt(11, fleet.getTotalDamage());
            statement.setInt(12, fleet.getDamage());
            statement.setInt(13, fleet.getTotalSpeed());
            statement.setInt(14, fleet.getSpeed());
            statement.setInt(15, fleet.getBase().getPlanetId());
            statement.setInt(16, fleet.getResources().getResourceId());
            //statement.setString(9, fleet.getImage());
            statement.setInt(17, fleet.getImperium().getImperiumId());
            //ejecutamos el statment y almacenamos la respuesta
            Integer respons = statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                res = (int) generatedKeys.getLong(1);
            }
            statement.close();
            connection.close();
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
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(FleetDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    @Override
    public List findBy(Map<String, String[]> filter) {
        //intentaremos traer los datos actualizados de los recursos si es que tenemos el id
        ResourcesDAO resourcesDAO = new ResourcesDAO();
        FleetTypeDAO ftDAO = new FleetTypeDAO();
        Routines routines = new Routines();
        try{
            fleetUpdate(Integer.parseInt(filter.get("fleet_id")[0]));
        }catch(Exception error){
            //Logger.getLogger(PlanetDAO.class.getName()).log(Level.SEVERE, "error controlado y esperable por NullPointerException: ", error);
        }
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
                List<FleetType> fleetTypeList = ftDAO.findBy(routines.constructMap("fleetType_id", res.getString("fleettype")));
                FleetType ft = fleetTypeList.get(0);
                newFleet.setFleetType(ft);
                newFleet.setCoordinates(res.getString("coordinates"));
                newFleet.setDestination(res.getInt("destination"));
                newFleet.setDepartureTime(res.getTimestamp("departure_time"));
                newFleet.setTotalLife(res.getInt("total_life"));
                newFleet.setLife(res.getInt("life"));
                newFleet.setTotalShield(res.getInt("total_shield"));
                newFleet.setShield(res.getInt("shield"));
                newFleet.setTotalDamage(res.getInt("total_damage"));
                newFleet.setDamage(res.getInt("damage"));
                newFleet.setTotalSpeed(res.getInt("total_speed"));
                newFleet.setSpeed(res.getInt("speed"));
                newFleet.setState(res.getString("state"));
                Planet planet = new Planet();
                planet.setPlanetId(res.getInt("base"));
                newFleet.setBase(planet);
                newFleet.setLast_update(res.getTimestamp("last_update"));
                List<Resources> resourcesList = resourcesDAO.findBy(routines.constructMap("resources_id", res.getString("resources")));
                Resources resources = resourcesList.get(0);
                resources.setResourceId(res.getInt("resources"));
                newFleet.setResources(resources);
                newFleet.setImage(res.getString("image"));
                Imperium imperium = new Imperium();
                imperium.setImperiumId(res.getInt("owner"));
                newFleet.setImperium(imperium);
                try{
                    fleetUpdate(newFleet.getFleetId());
                }catch(Exception error){
                    //Logger.getLogger(PlanetDAO.class.getName()).log(Level.SEVERE, "error controlado y esperable por NullPointerException: ", error);
                }
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
            statement.setString(4, fleet.getCoordinates());
            statement.setInt(5, fleet.getDestination());
            statement.setTimestamp(6, fleet.getDepartureTime());
            statement.setInt(7, fleet.getTotalLife());
            statement.setInt(8, fleet.getLife());
            statement.setInt(9, fleet.getTotalShield());
            statement.setInt(10, fleet.getShield());
            statement.setInt(11, fleet.getTotalDamage());
            statement.setInt(12, fleet.getDamage());
            statement.setInt(13, fleet.getTotalSpeed());
            statement.setInt(14, fleet.getSpeed());
            statement.setInt(15, fleet.getBase().getPlanetId());
            statement.setInt(16, fleet.getResources().getResourceId());
            //statement.setString(9, fleet.getImage());
            statement.setInt(17, fleet.getImperium().getImperiumId());
            statement.setInt(18, fleet.getFleetId());
            
            res = statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException ex){
            Logger.getLogger(FleetDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    public void fleetUpdate(Integer fleetId){
        
        try {
            // usamos la conexxion para preparar el statment 
            PreparedStatement statement = connection.prepareStatement("call update_fleet("+fleetId+")", Statement.RETURN_GENERATED_KEYS);
            
            statement.executeQuery();
        } catch (SQLException ex){
            Logger.getLogger(ResourcesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Fleet createFleet(Fleet fleet){
        Fleet res = new Fleet();
        try {
            // usamos la conexxion para preparar el statment 
            CallableStatement statement = connection.prepareCall("call create_Fleet(?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, fleet.getName());
            statement.setString(2, fleet.getDescription());
            statement.setInt(3, fleet.getFleetType().getFleetTypeId());
            statement.setString(4, fleet.getCoordinates());            
            statement.setString(5, fleet.getState().name());
            statement.setInt(6, fleet.getBase().getPlanetId());
            statement.setInt(7, fleet.getImperium().getImperiumId());
            statement.registerOutParameter(8, Types.INTEGER);
            statement.execute();
            Integer outputValue = statement.getInt(8);
            res.setFleetId(outputValue);
            return res;
        } catch (SQLException ex){
            Logger.getLogger(ResourcesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    public void combatFleet(Fleet fleet1,Fleet fleet2){
        try {
            // usamos la conexxion para preparar el statment 
            PreparedStatement statement = connection.prepareStatement("call combat_fleet("+fleet1.getFleetId()+", "+fleet1.getFleetId()+")", Statement.RETURN_GENERATED_KEYS);
            
            statement.executeQuery();
        } catch (SQLException ex){
            Logger.getLogger(ResourcesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
