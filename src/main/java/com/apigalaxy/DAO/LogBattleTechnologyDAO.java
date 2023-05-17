/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.DAO;

import com.apigalaxy.DAOFactory.MysqlDAOFactory;
import com.apigalaxy.POJOs.Imperium;
import com.apigalaxy.POJOs.LogBattle;
import com.apigalaxy.POJOs.LogBattleTechnology;
import com.apigalaxy.POJOs.Technology;
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
public class LogBattleTechnologyDAO implements com.apigalaxy.interfaces.IDAO<LogBattleTechnology, Map<String, String[]>>{
    //almacenamos los datos necesarios para crear los statment, de esta manera nos sera mas facil alterarlos si fuese necesario
    private final String DB_TABLE = "logBattle_technology";
    private final String ID_OBJECT = "logBattle_technology_id";
    //aqui almacenamos las 4 sentencias CRUD
    private final String ADD = "INSERT INTO " + DB_TABLE + " (level, imperium, technology, battle) VALUES (?, ?, ?, ?)";
    private final String DELETE = "DELETE FROM " + DB_TABLE + " WHERE " + ID_OBJECT + " = ?";
    private final String FIND_BY = "SELECT * FROM " + DB_TABLE;
    private final String UPDATE = "UPDATE " + DB_TABLE + " SET "
            + "level = ?,  "
            + "imperium = ?, "
            + "technology = ?, "
            + "battle = ? "
            + "WHERE " + ID_OBJECT + " = ?";
    //por ultimo la conexion
    private Connection connection;

    public LogBattleTechnologyDAO() {
        MysqlDAOFactory mysqlDAOFactory = new MysqlDAOFactory();
        this.connection = mysqlDAOFactory.connect();
    }

    @Override
    public Integer add(LogBattleTechnology logBattleTechnology) {
        //preparamos una respuesta negativa para informar de un posible fallo
        Integer res = -1;
        
        try{
            // usamos la conexxion para preparar el statment 
            PreparedStatement statement = connection.prepareStatement(ADD, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, logBattleTechnology.getLevel());
            statement.setInt(2, logBattleTechnology.getImperium().getImperiumId());
            statement.setInt(3, logBattleTechnology.getTechnology().getTechnologyId());
            statement.setInt(4, logBattleTechnology.getLogBattle().getLogBattleId());
            //ejecutamos el statment y almacenamos la respuesta
            Integer respons = statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                res = (int) generatedKeys.getLong(1);
            }     
        }catch(SQLException ex) {
            Logger.getLogger(LogBattleTechnologyDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    @Override
    public Boolean delete(LogBattleTechnology logBattleTechnology) {
        //preparamos la respuesta en false para informar en caso de fallo
        Boolean res = false;
        try {
            //preparamos la sentencia
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setInt(1, logBattleTechnology.getLogBattleTechnologyId());
            //ejecutamos la sentencia y almacenamos la respuesta que sear true en caso de no haber habido fallos
            res = statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(LogBattleTechnologyDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    @Override
    public List findBy(Map<String, String[]> filter) {
        //creamos una lista para devolver en la que introduciremos los usuarios encontrados
        List<LogBattleTechnology> LogBattleTechnologies = new ArrayList<>();
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
                LogBattleTechnology newLogBattleTechnology = new LogBattleTechnology();
                newLogBattleTechnology.setLogBattleTechnologyId(res.getInt("logBattle_technology_id"));
                newLogBattleTechnology.setLevel(res.getInt("level"));
                Imperium imperium = new Imperium();
                imperium.setImperiumId(res.getInt("imperium"));
                newLogBattleTechnology.setImperium(imperium);
                Technology technology = new Technology();
                technology.setTechnologyId(res.getInt("technology"));
                newLogBattleTechnology.setTechnology(technology);
                LogBattle logBattle = new LogBattle();
                logBattle.setLogBattleId(res.getInt("battle"));
                newLogBattleTechnology.setLogBattle(logBattle);
                LogBattleTechnologies.add(newLogBattleTechnology);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LogBattleTechnologyDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return LogBattleTechnologies;
    }

    @Override
    public Integer update(LogBattleTechnology logBattleTechnology) {
        //preparamos la respuesta en false para informar en caso de fallo
        Integer res = 0;
        try {
            // usamos la conexxion para preparar el statment 
            PreparedStatement statement = connection.prepareStatement(UPDATE, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, logBattleTechnology.getLevel());
            statement.setInt(2, logBattleTechnology.getImperium().getImperiumId());
            statement.setInt(3, logBattleTechnology.getTechnology().getTechnologyId());
            statement.setInt(4, logBattleTechnology.getLogBattle().getLogBattleId());
            statement.setInt(5, logBattleTechnology.getLogBattleTechnologyId());
            
            res = statement.executeUpdate();
        } catch (SQLException ex){
            Logger.getLogger(LogBattleTechnologyDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
}
