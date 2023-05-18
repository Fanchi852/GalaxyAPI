/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.DAO;

import com.apigalaxy.DAOFactory.MysqlDAOFactory;
import com.apigalaxy.POJOs.Fleet;
import com.apigalaxy.POJOs.Resources;
import com.apigalaxy.POJOs.Ship;
import com.apigalaxy.POJOs.ShipClass;
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
public class ShipDAO implements com.apigalaxy.interfaces.IDAO<Ship, Map<String, String[]>>{
    
    //almacenamos los datos necesarios para crear los statment, de esta manera nos sera mas facil alterarlos si fuese necesario
    private final String DB_TABLE = "ship";
    private final String ID_OBJECT = "ship_id";
    //aqui almacenamos las 4 sentencias CRUD
    private final String ADD = "INSERT INTO " + DB_TABLE + " (name, class, storage_capacity, life, shield, damage, resources, fleet) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private final String DELETE = "DELETE FROM " + DB_TABLE + " WHERE " + ID_OBJECT + " = ?";
    private final String FIND_BY = "SELECT * FROM " + DB_TABLE;
    private final String UPDATE = "UPDATE " + DB_TABLE + " SET "
            + "name = ?,  "
            + "class = ?, "
            + "storage_capacity = ?, "
            + "life = ?, "
            + "shield = ?, "
            + "damage = ?, "
            + "resources = ?, "
            + "fleet = ? "
            + "WHERE " + ID_OBJECT + " = ?";
    //por ultimo la conexion
    private Connection connection;

    public ShipDAO() {
        MysqlDAOFactory mysqlDAOFactory = new MysqlDAOFactory();
        this.connection = mysqlDAOFactory.connect();
    }

    @Override
    public Integer add(Ship ship) {
        //preparamos una respuesta negativa para informar de un posible fallo
        Integer res = -1;
        
        try{
            // usamos la conexxion para preparar el statment 
            PreparedStatement statement = connection.prepareStatement(ADD, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, ship.getName());
            statement.setInt(2, ship.getShipClass().getShipClassId());
            statement.setInt(3, ship.getStorageCapacity());
            statement.setInt(4, ship.getLife());
            statement.setInt(5, ship.getShield());
            statement.setInt(6, ship.getDamage());
            statement.setInt(7, ship.getResources().getResourceId());
            statement.setInt(8, ship.getFleet().getFleetId());
            //ejecutamos el statment y almacenamos la respuesta
            Integer respons = statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                res = (int) generatedKeys.getLong(1);
            }
        }catch(SQLException ex) {
            Logger.getLogger(ShipDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    @Override
    public Boolean delete(Ship ship) {
        //preparamos la respuesta en false para informar en caso de fallo
        Boolean res = false;
        try {
            //preparamos la sentencia
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setInt(1, ship.getShipId());
            //ejecutamos la sentencia y almacenamos la respuesta que sear true en caso de no haber habido fallos
            res = statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ShipDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    @Override
    public List findBy(Map<String, String[]> filter) {
        //creamos una lista para devolver en la que introduciremos los usuarios encontrados
        List<Ship> ships = new ArrayList<>();
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
                Ship newShip = new Ship();
                
                newShip.setShipId(res.getInt("ship_id"));
                newShip.setName(res.getString("name"));
                ShipClass shipClass = new ShipClass();
                shipClass.setShipClassId(res.getInt("class"));
                newShip.setShipClass(shipClass);
                newShip.setStorageCapacity(res.getInt("storage_capacity"));
                newShip.setLife(res.getInt("life"));
                newShip.setShield(res.getInt("shield"));
                newShip.setDamage(res.getInt("damage"));
                Resources resources = new Resources();
                resources.setResourceId(res.getInt("resources"));
                newShip.setResources(resources);
                Fleet fleet = new Fleet();
                fleet.setFleetId(res.getInt("fleet"));
                newShip.setFleet(fleet);
                ships.add(newShip);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ShipDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ships;
    }

    @Override
    public Integer update(Ship ship) {
        //preparamos la respuesta en false para informar en caso de fallo
        Integer res = 0;
        try {
            // usamos la conexxion para preparar el statment 
            PreparedStatement statement = connection.prepareStatement(UPDATE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, ship.getName());
            statement.setInt(2, ship.getShipClass().getShipClassId());
            statement.setInt(3, ship.getStorageCapacity());
            statement.setInt(4, ship.getLife());
            statement.setInt(5, ship.getShield());
            statement.setInt(6, ship.getDamage());
            statement.setInt(7, ship.getResources().getResourceId());
            statement.setInt(8, ship.getFleet().getFleetId());
            statement.setInt(9, ship.getShipId());
            
            res = statement.executeUpdate();
        } catch (SQLException ex){
            Logger.getLogger(ShipDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
}
