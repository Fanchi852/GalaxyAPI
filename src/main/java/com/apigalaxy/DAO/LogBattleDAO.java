/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.DAO;

import com.apigalaxy.DAOFactory.MysqlDAOFactory;
import com.apigalaxy.POJOs.LogBattle;
import com.apigalaxy.POJOs.LogFleet;
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
public class LogBattleDAO implements com.apigalaxy.interfaces.IDAO<LogBattle, Map<String, String[]>>{
    //almacenamos los datos necesarios para crear los statment, de esta manera nos sera mas facil alterarlos si fuese necesario
    private final String DB_TABLE = "logBattle";
    private final String ID_OBJECT = "logBattle_id";
    //aqui almacenamos las 4 sentencias CRUD
    private final String ADD = "INSERT INTO " + DB_TABLE + " (date, def_log_fleet, atk_log_fleet, victory, total_rare, total_normal) VALUES (?, ?, ?, ?, ?, ?)";
    private final String DELETE = "DELETE FROM " + DB_TABLE + " WHERE " + ID_OBJECT + " = ?";
    private final String FIND_BY = "SELECT * FROM " + DB_TABLE;
    private final String UPDATE = "UPDATE " + DB_TABLE + " SET "
            + "date = ?,  "
            + "def_log_fleet = ?, "
            + "atk_log_fleet = ?, "
            + "victory = ?, "
            + "total_rare = ?, "
            + "total_normal = ? "
            + "WHERE " + ID_OBJECT + " = ?";
    //por ultimo la conexion
    private Connection connection;

    public LogBattleDAO() {
        MysqlDAOFactory mysqlDAOFactory = new MysqlDAOFactory();
        this.connection = mysqlDAOFactory.connect();
    }

    @Override
    public Integer add(LogBattle logBattle) {
        //preparamos una respuesta negativa para informar de un posible fallo
        Integer res = -1;
        
        try{
            // usamos la conexxion para preparar el statment 
            PreparedStatement statement = connection.prepareStatement(ADD, Statement.RETURN_GENERATED_KEYS);
            statement.setTimestamp(1, logBattle.getDate());
            statement.setInt(2, logBattle.getDefFleet().getLogFleetId());
            statement.setInt(3, logBattle.getAtkFleet().getLogFleetId());
            statement.setString(4, logBattle.getVictory().name());
            statement.setFloat(5, logBattle.getTotalRare());
            statement.setFloat(6, logBattle.getTotalNormal());
            //ejecutamos el statment y almacenamos la respuesta
            Integer respons = statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                res = (int) generatedKeys.getLong(1);
            }
                    
        }catch(SQLException ex) {
            Logger.getLogger(LogBattleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }

    @Override
    public Boolean delete(LogBattle logBattle) {
        //preparamos la respuesta en false para informar en caso de fallo
        Boolean res = false;
        try {
            //preparamos la sentencia
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setInt(1, logBattle.getLogBattleId());
            //ejecutamos la sentencia y almacenamos la respuesta que sear true en caso de no haber habido fallos
            res = statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(LogBattleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    @Override
    public List findBy(Map<String, String[]> filter) {
        //creamos una lista para devolver en la que introduciremos los usuarios encontrados
        List<LogBattle> logBattles = new ArrayList<>();
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
                LogBattle newLogBattle = new LogBattle();
                newLogBattle.setLogBattleId(res.getInt("logBattle_id"));
                newLogBattle.setDate(res.getTimestamp("date"));
                LogFleet lf = new LogFleet();
                lf.setLogFleetId(res.getInt("def_log_fleet"));
                newLogBattle.setDefFleet(lf);
                lf.setLogFleetId(res.getInt("atk_log_fleet"));
                newLogBattle.setAtkFleet(lf);
                newLogBattle.setVictory(res.getString("victory"));
                newLogBattle.setTotalRare(res.getInt("total_rare"));
                newLogBattle.setTotalNormal(res.getInt("total_normal"));
                logBattles.add(newLogBattle);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LogBattleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return logBattles;
    }

    @Override
    public Integer update(LogBattle logBattle) {
        //preparamos la respuesta en false para informar en caso de fallo
        Integer res = 0;
        try {
            // usamos la conexxion para preparar el statment 
            PreparedStatement statement = connection.prepareStatement(UPDATE, Statement.RETURN_GENERATED_KEYS);
            statement.setTimestamp(1, logBattle.getDate());
            statement.setInt(2, logBattle.getDefFleet().getLogFleetId());
            statement.setInt(3, logBattle.getAtkFleet().getLogFleetId());
            statement.setString(4, logBattle.getVictory().name());
            statement.setFloat(5, logBattle.getTotalRare());
            statement.setFloat(6, logBattle.getTotalNormal());
            statement.setInt(7, logBattle.getLogBattleId());
            
            res = statement.executeUpdate();
        } catch (SQLException ex){
            Logger.getLogger(LogBattleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    
}
