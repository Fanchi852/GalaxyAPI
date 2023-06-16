/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.DAO;

import com.apigalaxy.DAOFactory.MysqlDAOFactory;
import com.apigalaxy.POJOs.Imperium;
import com.apigalaxy.POJOs.Planet;
import com.apigalaxy.POJOs.PlanetType;
import com.apigalaxy.POJOs.Resources;
import com.apigalaxy.POJOs.StarSystem;
import com.apigalaxy.POJOs.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fanch
 */
public class CoreDAO {
    
    
    private Connection connection;

    public CoreDAO() {
        MysqlDAOFactory mysqlDAOFactory = new MysqlDAOFactory();
        this.connection = mysqlDAOFactory.connect();
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
        res.setCoordinates(resultset.getString("coordinates"));
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
        statement.close();
        connection.close();
    }catch(SQLException ex){
        Logger.getLogger(CoreDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return res;
    }
    
}
