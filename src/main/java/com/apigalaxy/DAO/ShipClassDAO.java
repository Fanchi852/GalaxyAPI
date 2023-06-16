  /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.DAO;

import com.apigalaxy.DAOFactory.MysqlDAOFactory;
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
public class ShipClassDAO implements com.apigalaxy.interfaces.IDAO<ShipClass, Map<String, String[]>>{
    
    //almacenamos los datos necesarios para crear los statment, de esta manera nos sera mas facil alterarlos si fuese necesario
    private final String DB_TABLE = "shipClass";
    private final String ID_OBJECT = "shipClass_id";
    //aqui almacenamos las 4 sentencias CRUD
    private final String ADD = "INSERT INTO " + DB_TABLE + " (name, descripcion, basic_storage_capacity, basic_life, basic_shield, basic_damage, basic_speed, basic_normal_cost, basic_rare_cost, basic_people_cost, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String DELETE = "DELETE FROM " + DB_TABLE + " WHERE " + ID_OBJECT + " = ?";
    private final String FIND_BY = "SELECT * FROM " + DB_TABLE;
    private final String UPDATE = "UPDATE " + DB_TABLE + " SET "
            + "name = ?,  "
            + "descripcion = ?, "
            + "basic_storage_capacity = ?, "
            + "basic_life = ?, "
            + "basic_shield = ?, "
            + "basic_damage = ?, "
            + "basic_speed = ?, "
            + "basic_normal_cost = ?, "
            + "basic_rare_cost = ?, "
            + "basic_people_cost = ?, "
            + "image = ? "
            + "WHERE " + ID_OBJECT + " = ?";
    //por ultimo la conexion
    private Connection connection;

    public ShipClassDAO() {
        MysqlDAOFactory mysqlDAOFactory = new MysqlDAOFactory();
        this.connection = mysqlDAOFactory.connect();
    }

    @Override
    public Integer add(ShipClass shipClass) {
        //preparamos una respuesta negativa para informar de un posible fallo
        Integer res = -1;
        
        try{
            // usamos la conexxion para preparar el statment 
            PreparedStatement statement = connection.prepareStatement(ADD, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, shipClass.getName());
            statement.setString(2, shipClass.getDescription());
            statement.setInt(3, shipClass.getBasicStorage());
            statement.setInt(4, shipClass.getBasicLife());
            statement.setInt(5, shipClass.getBasicShield());
            statement.setInt(6, shipClass.getBasicDamage());
            statement.setInt(7, shipClass.getBasicSpeed());
            statement.setInt(8, shipClass.getBasic_normal_cost());
            statement.setInt(9, shipClass.getBasic_rare_cost());
            statement.setInt(10, shipClass.getBasic_people_cost());
            statement.setString(11, shipClass.getImage());
            //ejecutamos el statment y almacenamos la respuesta
            Integer respons = statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                res = (int) generatedKeys.getLong(1);
            }
                    
        }catch(SQLException ex) {
            Logger.getLogger(ShipClassDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }

    @Override
    public Boolean delete(ShipClass shipClass) {
        //preparamos la respuesta en false para informar en caso de fallo
        Boolean res = false;
        try {
            //preparamos la sentencia
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setInt(1, shipClass.getShipClassId());
            //ejecutamos la sentencia y almacenamos la respuesta que sear true en caso de no haber habido fallos
            res = statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ShipClassDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    @Override
    public List findBy(Map<String, String[]> filter) {
        //creamos una lista para devolver en la que introduciremos los usuarios encontrados
        List<ShipClass> shipClases = new ArrayList<>();
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
                ShipClass newShipClass = new ShipClass();
                
                newShipClass.setShipClassId(res.getInt("shipClass_id"));
                newShipClass.setName(res.getString("name"));
                newShipClass.setDescription(res.getString("description"));
                newShipClass.setBasicStorage(res.getInt("basic_storage_capacity"));
                newShipClass.setBasicLife(res.getInt("basic_life"));
                newShipClass.setBasicShield(res.getInt("basic_shield"));
                newShipClass.setBasicDamage(res.getInt("basic_damage"));
                newShipClass.setBasicSpeed(res.getInt("basic_speed"));
                newShipClass.setBasic_normal_cost(res.getInt("basic_normal_cost"));
                newShipClass.setBasic_rare_cost(res.getInt("basic_rare_cost"));
                newShipClass.setBasic_people_cost(res.getInt("basic_people_cost"));
                newShipClass.setImage(res.getString("image"));
                shipClases.add(newShipClass);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ShipClassDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return shipClases;
    }

    @Override
    public Integer update(ShipClass shipClass) {
        //preparamos la respuesta en false para informar en caso de fallo
        Integer res = 0;
        try {
            // usamos la conexxion para preparar el statment 
            PreparedStatement statement = connection.prepareStatement(UPDATE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, shipClass.getName());
            statement.setString(2, shipClass.getDescription());
            statement.setInt(3, shipClass.getBasicStorage());
            statement.setInt(4, shipClass.getBasicLife());
            statement.setInt(5, shipClass.getBasicShield());
            statement.setInt(6, shipClass.getBasicDamage());
            statement.setInt(7, shipClass.getBasicSpeed());
            statement.setInt(8, shipClass.getBasic_normal_cost());
            statement.setInt(9, shipClass.getBasic_rare_cost());
            statement.setInt(10, shipClass.getBasic_people_cost());
            statement.setString(11, shipClass.getImage());
            statement.setInt(12, shipClass.getShipClassId());
            
            res = statement.executeUpdate();
        } catch (SQLException ex){
            Logger.getLogger(ShipClassDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
}
