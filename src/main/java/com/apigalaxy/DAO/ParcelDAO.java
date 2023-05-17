/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.DAO;

import com.apigalaxy.DAOFactory.MysqlDAOFactory;
import com.apigalaxy.POJOs.BuildType;
import com.apigalaxy.POJOs.Parcel;
import com.apigalaxy.routines.Routines;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.Duration;
import java.time.Instant;
import static java.time.Instant.now;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Francisco Jesus Moya Olivares
 */
public class ParcelDAO implements com.apigalaxy.interfaces.IDAO<Parcel, Map<String, String[]>>{
    
    //almacenamos los datos necesarios para crear los statment, de esta manera nos sera mas facil alterarlos si fuese necesario
    private final String DB_TABLE = "parcel";
    private final String ID_OBJECT = "parcel_id";
    //aqui almacenamos las 4 sentencias CRUD
    private final String ADD = "INSERT INTO " + DB_TABLE + " (building, to_building, construction_start_date, storage_capacity, basic_normal_cost, basic_rare_cost, basic_time_cost, location, ";
    private final String DELETE = "DELETE FROM " + DB_TABLE + " WHERE " + ID_OBJECT + " = ?";
    private final String FIND_BY = "SELECT * FROM " + DB_TABLE;
    private final String STARTUPDATE = "UPDATE " + DB_TABLE + " SET "
            + "building = ?,  "
            + "to_building = ?, "
            + "construction_start_date = ?, "
            + "storage_capacity = ?, "
            + "basic_normal_cost = ?, "
            + "basic_rare_cost = ?, "
            + "basic_time_cost = ?, "
            + "location = ? ";
    private final String ENDUPDATE = "WHERE " + ID_OBJECT + " = ?";
    //por ultimo la conexion
    private Connection connection;

    public ParcelDAO() {
        MysqlDAOFactory mysqlDAOFactory = new MysqlDAOFactory();
        this.connection = mysqlDAOFactory.connect();
    }

    @Override
    public Integer add(Parcel parcel) {
        //preparamos una respuesta negativa para informar de un posible fallo
        Integer res = -1;
        
        try{
            String querry = ADD;
            //tenemos quemodificar la sentencia antes de terminar de crearla en funcion de el enum
            switch (parcel.getLocationT()){
                case planet:
                    querry = querry + "planet) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    break;
                case moon:
                    querry = querry + "moon) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    break;
            }
            // usamos la conexxion para preparar el statment 
            PreparedStatement statement = connection.prepareStatement(querry, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, parcel.getBuilding().getBuildType_id());
            statement.setInt(2, parcel.getTo_building().getBuildType_id());
            statement.setTimestamp(3, parcel.getConstruction_start_date());
            statement.setInt(4, parcel.getStorageCapacity());
            statement.setInt(5, parcel.getBasic_normal_cost());
            statement.setInt(6, parcel.getBasic_rare_cost());
            statement.setLong(7, parcel.getBasic_time_cost());
            statement.setString(8, parcel.getLocationT().name());
            statement.setInt(9, parcel.getLocation());
            //ejecutamos el statment y almacenamos la respuesta
            Integer respons = statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                res = (int) generatedKeys.getLong(1);
            }
                    
        }catch(SQLException ex) {
            Logger.getLogger(ParcelDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }

    @Override
    public Boolean delete(Parcel parcel) {
        //preparamos la respuesta en false para informar en caso de fallo
        Boolean res = false;
        try {
            //preparamos la sentencia
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setInt(1, parcel.getParcelId());
            //ejecutamos la sentencia y almacenamos la respuesta que sear true en caso de no haber habido fallos
            res = statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ParcelDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    @Override
    public List findBy(Map<String, String[]> filter) {
        //creamos una lista para devolver en la que introduciremos los usuarios encontrados
        List<Parcel> parcels = new ArrayList<>();
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
                Parcel newParcel = new Parcel();
                
                newParcel.setParcelId(res.getInt("parcel_id"));
                BuildType buildType = new BuildType();
                BuildType toBuildType = new BuildType();
                buildType.setBuildType_id(res.getInt("building"));
                newParcel.setBuilding(buildType);
                toBuildType.setBuildType_id(res.getInt("to_building"));
                newParcel.setTo_building(toBuildType);
                newParcel.setConstruction_start_date(res.getTimestamp("construction_start_date"));
                newParcel.setStorageCapacity(res.getInt("storage_capacity"));
                newParcel.setBasic_normal_cost(res.getInt("basic_normal_cost"));
                newParcel.setBasic_rare_cost(res.getInt("basic_rare_cost"));
                newParcel.setBasic_time_cost(res.getLong("basic_time_cost"));
                newParcel.setLocationT(res.getString("location"));
                Integer planet = null;
                Integer moon =null;
                try{
                    planet = res.getInt("planet");
                    moon = res.getInt("moon");
                }catch(SQLException ex){
                    Logger.getLogger(ParcelDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (planet == null){
                    newParcel.setLocation(moon);
                }else{
                    newParcel.setLocation(planet);
                }
                
                parcels.add(newParcel);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ParcelDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return parcels;
    }

    @Override
    public Integer update(Parcel parcel) {
        //preparamos la respuesta en false para informar en caso de fallo
        Integer res = 0;
        
        try {
            String querry = STARTUPDATE;
            //tenemos quemodificar la sentencia antes de terminar de crarla en funcion de el enum
            switch (parcel.getLocationT()){
                case planet:
                    querry = querry + "planet = ?, " + ENDUPDATE;
                    break;
                case moon:
                    querry = querry + "moon = ?, " + ENDUPDATE;
                    break;
            }
            // usamos la conexxion para preparar el statment 
            PreparedStatement statement = connection.prepareStatement(querry, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, parcel.getBuilding().getBuildType_id());
            statement.setInt(2, parcel.getTo_building().getBuildType_id());
            statement.setTimestamp(3, parcel.getConstruction_start_date());
            statement.setInt(4, parcel.getStorageCapacity());
            statement.setInt(5, parcel.getBasic_normal_cost());
            statement.setInt(6, parcel.getBasic_rare_cost());
            statement.setLong(7, parcel.getBasic_time_cost());
            statement.setString(8, parcel.getLocationT().name());
            statement.setInt(9, parcel.getLocation());
            statement.setInt(10, parcel.getParcelId());
            
            res = statement.executeUpdate();
        } catch (SQLException ex){
            Logger.getLogger(ParcelDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }
    
    public Parcel starConstruction(Parcel parcel){
        Parcel res = new Parcel();
        Routines routines = new Routines();
        String CALL = "call start_build_parcel("+parcel.getTo_building().getBuildType_id()+", "+parcel.getLocation()+", ?);";
        System.out.println("CALL de star construction: "+CALL);
        try{
            CallableStatement callableStatement = connection.prepareCall(CALL);
            // Registrar el argumento de salida como un entero
            
            callableStatement.registerOutParameter(1, Types.INTEGER);
            System.out.println("statement de star construction: "+callableStatement.toString());
            callableStatement.execute();
            
            Integer result = callableStatement.getInt(1);
            
            ParcelDAO parcelDAO = new ParcelDAO();
            List<Parcel> parcelList = parcelDAO.findBy(routines.constructMap("parcel_id", result.toString()));
            res = parcelList.get(0);
        }catch(SQLException ex){
            Logger.getLogger(ParcelDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }
    
    public Parcel endConstruction(Parcel parcel){
        Parcel res = new Parcel();
        Routines routines = new Routines();
        String CALL = "call terminate_build_parcel("+parcel.getParcelId()+");";
        System.out.println("CALL de end construction: "+CALL);
        try{
            ParcelDAO parcelDAO = new ParcelDAO();
            List<Parcel> parcelList = parcelDAO.findBy(routines.constructMap("parcel_id", parcel.getParcelId().toString()));
            Parcel parcelaux = parcelList.get(0);
            
            //Timestamp startDate = parcelaux.getConstruction_start_date();
            System.out.println("================================================");
            Calendar calStart = Calendar.getInstance();
            calStart.setTimeInMillis(parcelaux.getConstruction_start_date().getTime());
            calStart.add(Calendar.HOUR, -2);
            //parcelaux.setConstruction_start_date(new Timestamp(calStart.getTime().getTime()));
            //System.out.println("startDate: " + startDate);
            
            Calendar calEnd = Calendar.getInstance();
            
            //Instant date1 = Instant.ofEpochMilli(parcelaux.getConstruction_start_date().getTime());
            //Instant date2 = Instant.ofEpochMilli(System.currentTimeMillis());
            //System.out.println("parcelaux.getConstruction_start_date(): " + parcelaux.getConstruction_start_date());
            //System.out.println("date1: " + date1);
            //System.out.println("date2: " + date2);
            //System.out.println("en fechas date 1: "+parcelaux.getConstruction_start_date()+" date 2: "+System.currentTimeMillis());
            //System.out.println("en milisegundos date 1: "+parcelaux.getConstruction_start_date().getTime()+" date 2: "+System.currentTimeMillis());
            //System.out.println("date 1: "+date1.getEpochSecond()+" date 2: "+date2.getEpochSecond());
            //System.out.println("esta es la fecha actual"+now());
            System.out.println("================================================");
            Duration dif = Duration.between(calStart.toInstant(), calEnd.toInstant());
            System.out.println("dif: " + dif.getSeconds());
            System.out.println("parcelaux.getBasic_time_cost(): " + parcelaux.getBasic_time_cost());
            //System.out.println("la contruccion lleva " + dif.getSeconds() +" segundos de "+parcelaux.getBasic_time_cost()+" segundos que tiene que durar");
            if (dif.getSeconds()>parcelaux.getBasic_time_cost()){
                System.out.println("================================================");
                System.out.println("VOY A ACYUALIZAR");
                System.out.println("================================================");
                PreparedStatement statement = connection.prepareStatement(CALL);
                System.out.println("statement de end construction: "+statement.toString());
                statement.executeQuery();

                List<Parcel> parcelList1 = parcelDAO.findBy(routines.constructMap("parcel_id", parcel.getParcelId().toString()));
                res = parcelList1.get(0);
            }else{
                res = parcelaux;
            }
            
        }catch(SQLException ex){
            Logger.getLogger(ParcelDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }
    
}
