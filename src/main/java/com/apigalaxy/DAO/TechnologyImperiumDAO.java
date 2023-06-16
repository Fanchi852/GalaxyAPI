/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.DAO;

import com.apigalaxy.DAOFactory.MysqlDAOFactory;
import com.apigalaxy.POJOs.Imperium;
import com.apigalaxy.POJOs.Planet;
import com.apigalaxy.POJOs.Technology;
import com.apigalaxy.POJOs.TechnologyImperium;
import com.apigalaxy.POJOs.TechnologyView;
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
 * 
 */
public class TechnologyImperiumDAO implements com.apigalaxy.interfaces.IDAO<TechnologyImperium, Map<String, String[]>>{
    
    //almacenamos los datos necesarios para crear los statment, de esta manera nos sera mas facil alterarlos si fuese necesario
    private final String DB_TABLE = "technology_imperium";
    private final String ID_OBJECT = "technology_imperium_id";
    //aqui almacenamos las 4 sentencias CRUD
    private final String ADD = "INSERT INTO " + DB_TABLE + " (level, imperium, technology) VALUES (?, ?, ?)";
    private final String DELETE = "DELETE FROM " + DB_TABLE + " WHERE " + ID_OBJECT + " = ?";
    private final String FIND_BY = "SELECT * FROM " + DB_TABLE;
    private final String UPDATE = "UPDATE " + DB_TABLE + " SET "
            + "level = ?,  "
            + "imperium = ?, "
            + "technology = ? "
            + "WHERE " + ID_OBJECT + " = ?";
    //por ultimo la conexion
    private Connection connection;
    
    public TechnologyImperiumDAO(){
    MysqlDAOFactory mysqlDAOFactory = new MysqlDAOFactory();
        this.connection = mysqlDAOFactory.connect();
    }

    @Override
    public Integer add(TechnologyImperium technologyImperium) {
        //preparamos una respuesta negativa para informar de un posible fallo
        Integer res = -1;
        
        try{
            // usamos la conexxion para preparar el statment 
            PreparedStatement statement = connection.prepareStatement(ADD, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, technologyImperium.getLevel());
            statement.setInt(2, technologyImperium.getImperium().getImperiumId());
            statement.setInt(3, technologyImperium.getTechnology().getTechnologyId());
            //ejecutamos el statment y almacenamos la respuesta
            Integer respons = statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                res = (int) generatedKeys.getLong(1);
            }
                    
        }catch(SQLException ex) {
            Logger.getLogger(TechnologyImperiumDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }

    @Override
    public Boolean delete(TechnologyImperium technologyImperium) {
        //preparamos la respuesta en false para informar en caso de fallo
        Boolean res = false;
        try {
            //preparamos la sentencia
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setInt(1, technologyImperium.getTechnologyImperiumId());
            //ejecutamos la sentencia y almacenamos la respuesta que sear true en caso de no haber habido fallos
            res = statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(TechnologyImperiumDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    @Override
    public List findBy(Map<String, String[]> filter) {
        //creamos una lista para devolver en la que introduciremos los usuarios encontrados
        List<TechnologyImperium> technologyImperiums = new ArrayList<>();
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
                TechnologyImperium newTechnologyImperium = new TechnologyImperium();
                Imperium imperium = new Imperium();
                Technology technology = new Technology();
                //Establecemos los valores de las propiedades usando los datos de la fila del ResultSet
                newTechnologyImperium.setTechnologyImperiumId(res.getInt("technology_imperium_id"));
                newTechnologyImperium.setLevel(res.getInt("level"));
                imperium.setImperiumId(res.getInt("imperium"));
                newTechnologyImperium.setImperium(imperium);
                technology.setTechnologyId(res.getInt("technology"));
                newTechnologyImperium.setTechnology(technology);
                //Añadir el objeto TechnologyImperium a la lista technologyImperiums
                technologyImperiums.add(newTechnologyImperium);
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TechnologyImperiumDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return technologyImperiums; 
   }

    @Override
    public Integer update(TechnologyImperium technologyImperium) {
        //preparamos la respuesta en false para informar en caso de fallo
        Integer res = 0;
        
        try {
            //preparamos y ejecutamos la sentencia almacenando y devolviendo la respuesta
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setInt(1, technologyImperium.getLevel());
            statement.setInt(2, technologyImperium.getImperium().getImperiumId());
            statement.setInt(3, technologyImperium.getTechnology().getTechnologyId());
            statement.setInt(4, technologyImperium.getTechnologyImperiumId());
            System.out.println("SLQ SENCENCE: " + statement);
            res = statement.executeUpdate();
        } catch (SQLException ex){
            Logger.getLogger(TechnologyImperiumDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }
    
    public List technologyList(Imperium imperium){
        //creamos una lista para devolver en la que introduciremos los usuarios encontrados
        List<TechnologyView> technologyViews = new ArrayList<>();
        String CALL = "call imperium_technologies("+imperium.getImperiumId()+");";
        System.out.println("llamando a imperium_technologies: "+CALL);
        
        try {
            //preparamos la sentenci
            PreparedStatement statement;
            statement = connection.prepareStatement(CALL);
            
            //Ejecutar la consulta y almacenar los resultados en un objeto ResultSet
            ResultSet res = statement.executeQuery();
            // vamos almacenando las respuestas ya sea una sola o una lista y devolvemos las respuestas en una lista.
            //Iteramos sobre el ResultSet para procesar cada fila
            while (res.next()){
                //Creamos nuevos objetos.
                TechnologyView techView = new TechnologyView();
                //Establecemos los valores de las propiedades usando los datos de la fila del ResultSet
                techView.setTechnology_imperium_id(res.getInt("technology_imperium_id"));
                techView.setLevel(res.getInt("level"));
                techView.setName(res.getString("name"));
                techView.setDescripcion(res.getString("descripcion"));
                techView.setType(res.getString("type"));
                techView.setBono(res.getFloat("bono"));
                techView.setBasic_cost(res.getInt("basic_cost"));
                techView.setImage(res.getString("image"));
                //Añadir el objeto TechnologyImperium a la lista technologyImperiums
                technologyViews.add(techView);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TechnologyImperiumDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return technologyViews; 
    }
    
}
