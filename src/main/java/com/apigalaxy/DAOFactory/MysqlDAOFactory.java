/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apigalaxy.DAOFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

/**
 *
 * @author Francisco Jesus Moya Olivares
 * 
 * en esta clase preparamos la conexxion con la base de datos,
 * 
 */
public class MysqlDAOFactory {
    
    private Connection conn;
    //almacensmos todas las variables de manera estatica para una mejor alteracion en caso de ser necesario
    private static final String USER="root";
    private static final String PASS="root";
    private static final String DRIVER ="com.mysql.cj.jdbc.Driver";
    private static final String URL
            = "jdbc:mysql://localhost:3306/galaxia?"
            + "useUnicode=true&"
            + "useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&"
            + "serverTimezone=UTC&"
            + "useSSL=false"
            ;

    public MysqlDAOFactory() {
    }
    
    public Connection connect() {
        try {
            
            //indicamos donde esta el controllador de la base de datos que usamos
            Class.forName(DRIVER);
            // y cual sera la contraseña y usuario de la misma ademas de la direccion
            conn = DriverManager.getConnection(URL, USER, PASS);
            
            // Con el objeto connection creare un statement
            System.out.println("conectado");
        }catch (ClassNotFoundException | SQLException ex) {
            System.out.println(Arrays.toString(ex.getStackTrace()));
            System.out.println(ex.toString());
            System.out.println(ex.getMessage());
        }
        return conn;
    }
    
}
