/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.DAO;

import com.apigalaxy.DAOFactory.MysqlDAOFactory;
import com.apigalaxy.POJOs.Moon;
import com.apigalaxy.POJOs.Planet;
import com.apigalaxy.POJOs.PlanetType;
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
public class MoomDAO implements com.apigalaxy.interfaces.IDAO<Moon, Map<String, String[]>>{
    
    //almacenamos los datos necesarios para crear los statment, de esta manera nos sera mas facil alterarlos si fuese necesario
    private final String DB_TABLE = "moon";
    private final String ID_OBJECT = "moon_id";
    //aqui almacenamos las 4 sentencias CRUD
    private final String ADD = "INSERT INTO " + DB_TABLE + " (name, parcels, type, coordinates, strong_resource, normal_ore_production, rare_ore_production, population_changes, cientific_data_changes, resources, planet) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String DELETE = "DELETE FROM " + DB_TABLE + " WHERE " + ID_OBJECT + " = ?";
    private final String FIND_BY = "SELECT * FROM " + DB_TABLE;
    private final String UPDATE = "UPDATE " + DB_TABLE + " SET "
            + "name = ?,  "
            + "parcels = ?, "
            + "type = ?, "
            + "coordinates = ?, "
            + "strong_resource = ?, "
            + "normal_ore_production = ?, "
            + "rare_ore_production = ?, "
            + "population_changes = ?, "
            + "cientific_data_changes = ?, "
            + "resources = ?, "
            + "planet = ? "
            + "WHERE " + ID_OBJECT + " = ?";
    //por ultimo la conexion
    private Connection connection;

    public MoomDAO() {
        MysqlDAOFactory mysqlDAOFactory = new MysqlDAOFactory();
        this.connection = mysqlDAOFactory.connect();
    }

    @Override
    public Integer add(Moon moon) {
        //preparamos una respuesta negativa para informar de un posible fallo
        Integer res = -1;
        
        try{
            // usamos la conexxion para preparar el statment 
            PreparedStatement statement = connection.prepareStatement(ADD, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, moon.getName());
            statement.setInt(2, moon.getParcels());
            statement.setInt(3, moon.getPlanetType().getPlanetTypeId());
            statement.setInt(4, moon.getCoordinates());
            statement.setString(5, moon.getStrResource().name());
            statement.setInt(6, moon.getNormalOreProduction());
            statement.setInt(7, moon.getRareOreProduction());
            statement.setInt(8, moon.getPopulation_changes());
            statement.setInt(9, moon.getCientific_data_changes());
            statement.setInt(10, moon.getResources().getResourceId());
            statement.setInt(11, moon.getPlanet().getPlanetId());
            //ejecutamos el statment y almacenamos la respuesta
            Integer respons = statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                res = (int) generatedKeys.getLong(1);
            }
                    
        }catch(SQLException ex) {
            Logger.getLogger(MoomDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }

    @Override
    public Boolean delete(Moon moon) {
        //preparamos la respuesta en false para informar en caso de fallo
        Boolean res = false;
        try {
            //preparamos la sentencia
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setInt(1, moon.getMoonId());
            //ejecutamos la sentencia y almacenamos la respuesta que sear true en caso de no haber habido fallos
            res = statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(MoomDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    @Override
    public List findBy(Map<String, String[]> filter) {
        //creamos una lista para devolver en la que introduciremos los usuarios encontrados
        List<Moon> moons = new ArrayList<>();
        //Inicializar las variables de control para construir la cláusula WHERE
        //usando hasAnd para el caso en que haya mas de una condicion para la busqueda        
        //y hasWhere para poder controllar cuendo añadir where
        String where = " WHERE ";
        Boolean hasAnd = false;
        Boolean haswhere = false;
        //recorreremos el mapa y por cada campo que tengamos iremos construllendo la sentencia añadiendo los where y and necesarios para crearla de manera correcta
        for (String key : filter.keySet()){
            if(!key.equals("ACTION")){
                haswhere = true;
                String[] values = filter.get(key);
                //Añadir " AND " a la cláusula WHERE si ya hay un filtro
                if(hasAnd){
                    where += " AND ";
                }
                hasAnd = true;
                //Añadir el nombre de la columna y la cláusula " IN ("
                where += " " + key + " IN (";
                //Construir la lista de valores para la cláusula IN
                Integer count = 0;
                for(String value: values){
                    where += count != 0 ? ", ": "";
                    where += "\"" + value + "\"";
                    count ++;
                }
                //terminamos cerrando la sentencia
                where += ")";
            }
        }
        //una vez creada la sentencia ahora tenemos que añadir los valores necesarios 
        //Imprimir la consulta SQL completa para depuración
        System.out.println("SLQ SENCENCE: " + FIND_BY + where);
        try {
            //preparamos la sentencia en funcion de si hay que añadir buscadores o traer toda la tabla
            PreparedStatement statement;
            if(haswhere){
                statement = connection.prepareStatement(FIND_BY + where);
            }else{
                statement = connection.prepareStatement(FIND_BY);
            }
            //Ejecutar la consulta y almacenar los resultados en un objeto ResultSet
            ResultSet res = statement.executeQuery();
            // vamos almacenando las respuestas ya sea una sola o una lista y devolvemos las respuestas en una lista.
            //Iteramos sobre el ResultSet para procesar cada fila
            while (res.next()){
                //Creamos nuevos objetos TechnologyImperium, Imperium y Technology. 
                Moon newMoon = new Moon();
                //Establecemos los valores de las propiedades usando los datos de la fila del ResultSet
                newMoon.setMoonId(res.getInt("moon_id"));
                newMoon.setName(res.getString("name"));
                PlanetType planetType = new PlanetType();
                planetType.setPlanetTypeId(res.getInt("type"));
                newMoon.setPlanetType(planetType);
                newMoon.setCoordinates(res.getInt("coordinates"));
                newMoon.setStrResource(res.getString("strong_resource"));
                newMoon.setNormalOreProduction(res.getInt("normal_ore_production"));
                newMoon.setRareOreProduction(res.getInt("rare_ore_production"));
                newMoon.setPopulation_changes(res.getInt("population_changes"));
                newMoon.setCientific_data_changes(res.getInt("cientific_data_changes"));
                Resources resources = new Resources();
                resources.setResourceId(res.getInt("resources"));
                newMoon.setResources(resources);
                Planet planet = new Planet();
                planet.setPlanetId(res.getInt("planet"));
                newMoon.setPlanet(planet);
                //Añadir el objeto TechnologyImperium a la lista technologyImperiums
                moons.add(newMoon);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MoomDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return moons; 
    }

    @Override
    public Boolean update(Moon moon) {
        //preparamos la respuesta en false para informar en caso de fallo
        Boolean res = false;
        
        try {
            //preparamos y ejecutamos la sentencia almacenando y devolviendo la respuesta
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, moon.getName());
            statement.setInt(2, moon.getParcels());
            statement.setInt(3, moon.getPlanetType().getPlanetTypeId());
            statement.setInt(4, moon.getCoordinates());
            statement.setString(5, moon.getStrResource().name());
            statement.setInt(6, moon.getNormalOreProduction());
            statement.setInt(7, moon.getRareOreProduction());
            statement.setInt(8, moon.getPopulation_changes());
            statement.setInt(9, moon.getCientific_data_changes());
            statement.setInt(10, moon.getResources().getResourceId());
            statement.setInt(11, moon.getPlanet().getPlanetId());
            statement.setInt(12, moon.getMoonId());
            
            res = statement.execute();
        } catch (SQLException ex){
            Logger.getLogger(MoomDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    
    
}
