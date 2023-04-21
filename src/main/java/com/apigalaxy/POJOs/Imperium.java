/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.POJOs;

/**
 *
 * @author Francsico Jesus Moya Olivares
 * 
 * este POJO corresponde a la tabla imperium en la base de datos.
 * 
 */
public class Imperium {
    
    private Integer imperiumId, cientificData;
    private String name;
    private User user;

    public Imperium() {
    }

    public Imperium(String name, User user,Integer cientificData) {
        this.name = name;
        this.user = user;
        this.cientificData = cientificData;
    }

    public Integer getCientificData() {
        return cientificData;
    }

    public void setCientificData(Integer cientificData) {
        this.cientificData = cientificData;
    }

    public Integer getImperiumId() {
        return imperiumId;
    }

    public void setImperiumId(Integer imperiumId) {
        this.imperiumId = imperiumId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
    
}
