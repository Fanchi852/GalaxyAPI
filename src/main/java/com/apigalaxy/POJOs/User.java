/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.POJOs;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Francisco Jesus Moya Olivares
 * 
 * este POJO corresponde a la tabla user en la base de datos.
 * 
 */
public class User {
    
    private Integer userId;
    private String name, password, email;

    public User() {
    }

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", name=" + name + ", password=" + password + ", email=" + email + '}';
    }
    /*
    public Map<String, String[]> toMap(){
        
        
        Map<String, String[]> map = new HashMap<String, String[]>();
        String[] userId = new String[]{!=this.userId.toString()};
        String[] name = new String[]{this.name};
        String[] email = new String[]{this.email};
        String[] password = new String[]{this.password};
        map.put("userId", userId);
        map.put("name", name);
        map.put("password", password);
        map.put("email", email);
        return map;
    }
    */
}
