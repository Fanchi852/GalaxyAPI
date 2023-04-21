/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy;

import com.apigalaxy.DAO.UserSessionDAO;
import com.apigalaxy.POJOs.User;

/**
 *
 * @author Fanch
 */
public class Main {
     public static void main (String[] args) {
         UserSessionDAO usdao = new UserSessionDAO();
         User user = new User();
         user.setName("asd");
         user.setPassword("123123");
         usdao.login(user);
     }
}
