/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.DAO;

import com.apigalaxy.DAOFactory.MysqlDAOFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import com.apigalaxy.POJOs.User;
import java.sql.PreparedStatement;
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
public class UserDAO implements com.apigalaxy.interfaces.IDAO<User, Map<String, String[]>> {
    //almacenamos los datos necesarios para crear los statment, de esta manera nos sera mas facil alterarlos si fuese necesario
    private final String DB_TABLE = "user";
    private final String ID_OBJECT = "user_id";
    //aqui almacenamos las 4 sentencias CRUD
    private final String ADD = "INSERT INTO " + DB_TABLE + " (name, password, email) VALUES (?, ?, ?)";
    private final String DELETE = "DELETE FROM " + DB_TABLE + " WHERE " + ID_OBJECT + " = ?";
    private final String FIND_BY = "SELECT * FROM " + DB_TABLE;
    private final String UPDATE = "UPDATE " + DB_TABLE + " SET name = ?,  password = ?, email = ? WHERE " + ID_OBJECT + " = ?";
    //por ultimo la conexion
    private Connection connection;

    public UserDAO() {
        //creamos la conexion con la clase
        MysqlDAOFactory mysqlDAOFactory = new MysqlDAOFactory();
        this.connection = mysqlDAOFactory.connect();
    }

    @Override
    public Integer add(User user) {
        //preparamos una respuesta negativa para informar de un posible fallo
        Integer res = -1;
        
        try{
            // usamos la conexxion para preparar el statment 
            PreparedStatement statement = connection.prepareStatement(ADD, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
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
    public Boolean delete(User user) {
        //preparamos la respuesta en false para informar en caso de fallo
        Boolean res = false;
        
        try {
            //preparamos la sentencia
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setInt(1, user.getUserId());
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
        List<User> users = new ArrayList<>();
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
                User newuser = new User();
                
                newuser.setUserId(res.getInt("user_id"));
                newuser.setName(res.getString("name"));
                newuser.setPassword(res.getString("password"));
                newuser.setEmail(res.getString("email"));
                
                users.add(newuser);
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }

    @Override
    public Boolean update(User user) {
        //preparamos la respuesta en false para informar en caso de fallo
        Boolean res = false;
        
        try {
            //preparamos y ejecutamos la sentencia almacenando y devolviendo la respuesta
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setInt(4, user.getUserId());
            
            res = statement.execute();
        } catch (SQLException ex){
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
}