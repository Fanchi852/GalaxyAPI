/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.POJOs;

/**
 *
 * @author Francisco Jesus Moya Olivares
 * 
 * este POJO corresponde a la tabla starSystem en la base de datos.
 * 
 */
public class StarSystem {
    
    private Integer starId;
    private String name, description;
    private Float habitability;

    public StarSystem() {
    }

    public StarSystem(Integer coordinates, String name, String description, Float habitability) {
        this.name = name;
        this.description = description;
        this.habitability = habitability;
    }

    public Integer getStarId() {
        return starId;
    }

    public void setStarId(Integer starId) {
        this.starId = starId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getHabitability() {
        return habitability;
    }

    public void setHabitability(Float habitability) {
        this.habitability = habitability;
    }
    
}
