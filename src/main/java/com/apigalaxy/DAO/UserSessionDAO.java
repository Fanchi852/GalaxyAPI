/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.DAO;

import com.apigalaxy.DAOFactory.MysqlDAOFactory;
import com.apigalaxy.POJOs.User;
import com.apigalaxy.POJOs.UserSession;
import java.sql.CallableStatement;
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
public class UserSessionDAO implements com.apigalaxy.interfaces.IDAO<UserSession, Map<String, String[]>>{
    
    //almacenamos los datos necesarios para crear los statment, de esta manera nos sera mas facil alterarlos si fuese necesario
    private final String DB_TABLE = "userSession";
    private final String ID_OBJECT = "userSession_id";
    //aqui almacenamos las 4 sentencias CRUD
    private final String ADD = "INSERT INTO " + DB_TABLE + " (user, initial_date, last_update) VALUES (?, ?, ?)";
    private final String DELETE = "DELETE FROM " + DB_TABLE + " WHERE " + ID_OBJECT + " = ?";
    private final String FIND_BY = "SELECT * FROM " + DB_TABLE;
    private final String UPDATE = "UPDATE " + DB_TABLE + " SET user = ?,  initial_date = ?, last_update = ? WHERE " + ID_OBJECT + " = ?";
    //aqui otras sentencias que llaman a procedimiento o funciones concretas en la BD
    private final String LOGIN = "call login (?, ?);";
    //por ultimo la conexion
    private Connection connection;

    public UserSessionDAO() {
        //creamos la conexion con la clase
        MysqlDAOFactory mysqlDAOFactory = new MysqlDAOFactory();
        this.connection = mysqlDAOFactory.connect();
    }

    @Override
    public Integer add(UserSession userSession) {
        //preparamos una respuesta negativa para informar de un posible fallo
        Integer res = -1;
        
        try{
            // usamos la conexxion para preparar el statment 
            PreparedStatement statement = connection.prepareStatement(ADD, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, userSession.getUser().getUserId());
            statement.setTimestamp(2, userSession.getInitial_date());
            statement.setTimestamp(3, userSession.getLast_update());
            //ejecutamos el statment y almacenamos la respuesta
            Integer respons = statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                res = (int) generatedKeys.getLong(1);
            }
                    
        }catch(SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    @Override
    public Boolean delete(UserSession userSession) {
    //preparamos la respuesta en false para informar en caso de fallo
        Boolean res = false;
        
        try {
            //preparamos la sentencia
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setInt(1, userSession.getUserSession_id());
            //ejecutamos la sentencia y almacenamos la respuesta que sear true en caso de no haber habido fallos
            res = statement.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    @Override
    public List findBy(Map<String, String[]> filter) {
    //creamos una lista para devolver en la que introduciremos los usuarios encontrados
        List<UserSession> users = new ArrayList<>();
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
                UserSession newUserSession = new UserSession();
                
                newUserSession.setUserSession_id(res.getInt("userSession_id"));
                User user = new User();
                user.setUserId(res.getInt("user"));
                newUserSession.setUser(user);
                newUserSession.setInitial_date(res.getTimestamp("initial_date"));
                newUserSession.setLast_update(res.getTimestamp("last_update"));
                
                users.add(newUserSession);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }

    @Override
    public Boolean update(UserSession userSession) {
        //preparamos la respuesta en false para informar en caso de fallo
        Boolean res = false;
        
        try {
            //preparamos y ejecutamos la sentencia almacenando y devolviendo la respuesta
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setInt(1, userSession.getUser().getUserId());
            statement.setTimestamp(2, userSession.getInitial_date());
            statement.setTimestamp(3, userSession.getLast_update());
            statement.setInt(4, userSession.getUserSession_id());
            
            res = statement.execute();
        } catch (SQLException ex){
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    public UserSession login(User user){
        //preparamos la respuesta en false para informar en caso de fallo
        UserSession userSession = new UserSession();
        try{
            //preparamos y ejecutamos la sentencia almacenando y devolviendo la respuesta
            CallableStatement statement = connection.prepareCall(LOGIN);
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            System.out.println("este es el statement: "+statement);
            ResultSet res = statement.executeQuery();
            while (res.next()){
            //construimos la devuelta por el servidor
            userSession.setUserSession_id(res.getInt("userSession_id"));
            user.setUserId(res.getInt("user"));
            userSession.setUser(user);
            userSession.setInitial_date(res.getTimestamp("initial_date"));
            userSession.setLast_update(res.getTimestamp("last_update"));
            }
        } catch (SQLException ex){
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(userSession.toString());
        return userSession;
    }
}
