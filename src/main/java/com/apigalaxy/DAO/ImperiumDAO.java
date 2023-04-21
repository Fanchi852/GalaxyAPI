/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.DAO;


import com.apigalaxy.POJOs.Imperium;
import com.apigalaxy.POJOs.User;
import com.apigalaxy.DAOFactory.MysqlDAOFactory;
import com.apigalaxy.POJOs.Planet;
import com.apigalaxy.POJOs.PlanetType;
import com.apigalaxy.POJOs.Resources;
import com.apigalaxy.POJOs.StarSystem;
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
public class ImperiumDAO implements com.apigalaxy.interfaces.IDAO<Imperium, Map<String, String[]>> {
    //almacenamos los datos necesarios para crear los statment, de esta manera nos sera mas facil alterarlos si fuese necesario
    private final String DB_TABLE = "imperium";
    private final String ID_OBJECT = "imperium_id";
    //aqui almacenamos las 4 sentencias CRUD
    private final String ADD = "INSERT INTO " + DB_TABLE + " (name, user, cientific_data) VALUES (?, ?, ?)";
    private final String DELETE = "DELETE FROM " + DB_TABLE + " WHERE " + ID_OBJECT + " = ?";
    private final String FIND_BY = "SELECT * FROM " + DB_TABLE;
    private final String UPDATE = "UPDATE " + DB_TABLE + " SET name = ?, SET user = ?,  cientific_data = ? WHERE " + ID_OBJECT + " = ?";
    //por ultimo la conexion
    private Connection connection;
    
    public ImperiumDAO(){
    MysqlDAOFactory mysqlDAOFactory = new MysqlDAOFactory();
        this.connection = mysqlDAOFactory.connect();
    }

    @Override
    public Integer add(Imperium imperium) {
        //preparamos una respuesta negativa para informar de un posible fallo
        Integer res = -1;
        
        try{
            // usamos la conexxion para preparar el statment 
            PreparedStatement statement = connection.prepareStatement(ADD, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, imperium.getName());
            statement.setInt(2, imperium.getUser().getUserId());
            statement.setInt(3, imperium.getCientificData());
            //ejecutamos el statment y almacenamos la respuesta
            Integer respons = statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                res = (int) generatedKeys.getLong(1);
            }
                    
        }catch(SQLException ex) {
            Logger.getLogger(ImperiumDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }

    @Override
    public Boolean delete(Imperium imperium) {
        //preparamos la respuesta en false para informar en caso de fallo
        Boolean res = false;
        
        try {
            //preparamos la sentencia
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setInt(1, imperium.getImperiumId());
            //ejecutamos la sentencia y almacenamos la respuesta que sear true en caso de no haber habido fallos
            res = statement.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(ImperiumDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }

    @Override
    public List findBy(Map<String, String[]> filter) {
        //creamos una lista para devolver en la que introduciremos los usuarios encontrados
        List<Imperium> imperiums = new ArrayList<>();
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
                Imperium newImperium = new Imperium();
                
                newImperium.setImperiumId(res.getInt("imperium_id"));
                newImperium.setName(res.getString("name"));
                
                User auxUser = new User();
                auxUser.setUserId(res.getInt("user"));
                newImperium.setUser(auxUser);
                
                imperiums.add(newImperium);
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ImperiumDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return imperiums;
    }

    @Override
    public Boolean update(Imperium imperium) {
        //preparamos la respuesta en false para informar en caso de fallo
        Boolean res = false;
        
        try {
            //preparamos y ejecutamos la sentencia almacenando y devolviendo la respuesta
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, imperium.getName());
            statement.setInt(2, imperium.getUser().getUserId());
            statement.setInt(3, imperium.getCientificData());
            statement.setInt(4, imperium.getImperiumId());
            
            res = statement.execute();
        } catch (SQLException ex){
            Logger.getLogger(ImperiumDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }
    public Planet createImpetium(User user, Imperium imperium){
        String CREATE_IMPERIUM = "call create_imperium(" + user.getName() +", "+ imperium.getName() + ");";
        Planet res = new Planet();
        try{
            PreparedStatement statement = connection.prepareStatement(CREATE_IMPERIUM);
            ResultSet resultset = statement.executeQuery();

            //Establecemos los valores de las propiedades usando los datos de la fila del ResultSet
            res.setPlanetId(resultset.getInt("planet_id"));
            res.setName(resultset.getString("name"));
            PlanetType planetType = new PlanetType();
            planetType.setPlanetTypeId(resultset.getInt("type"));
            res.setPlanetType(planetType);
            res.setCoordinates(resultset.getInt("coordinates"));
            Imperium imperiumAUX = new Imperium();
            imperiumAUX.setImperiumId(resultset.getInt("owner"));
            res.setImperium(imperiumAUX);
            res.setStrResource(resultset.getString("strong_resource"));
            res.setNormalOreProduction(resultset.getInt("normal_ore_production"));
            res.setRareOreProduction(resultset.getInt("rare_ore_production"));
            res.setPopulation_changes(resultset.getInt("population_changes"));
            res.setCientific_data_changes(resultset.getInt("cientific_data_changes"));
            Resources resources = new Resources();
            resources.setResourceId(resultset.getInt("resources"));
            res.setResources(resources);
            StarSystem starsystem = new StarSystem();
            starsystem.setStarId(resultset.getInt("star"));
            res.setStar(starsystem);

        }catch(SQLException ex){
            Logger.getLogger(CoreDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return res;
    }
}