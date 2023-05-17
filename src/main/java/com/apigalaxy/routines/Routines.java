/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.routines;

import com.apigalaxy.DAO.UserSessionDAO;
import com.apigalaxy.POJOs.UserSession;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Fanch
 */
public class Routines {
    
    public Boolean checkSessionUp (UserSession userSession){
        Boolean res = false;
        
        Instant date1 = Instant.ofEpochMilli(userSession.getLast_update().getTime());
        Instant date2 = Instant.ofEpochMilli(System.currentTimeMillis());
        Duration dif = Duration.between(date1, date2);
        
        if (dif.toMinutes() < 10){
            res = true;
            UserSessionDAO usDAO = new UserSessionDAO();
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            userSession.setLast_update(ts);
            usDAO.update(userSession);
        }
        
        return res;
    }
    
    public Map<String, String[]> constructMap(String p_key, String p_valor){
        
        Map<String, String[]> res = new HashMap<String, String[]>();
            String[] valor = new String[]{p_valor};
            res.put(p_key, valor);
            
        return res;
    }
    
}
