/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.POJOs;

/**
 *
 * @author Francisco Jesus Moya Olivares
 * 
 * este POJO corresponde a la tabla technology_imperium en la base de datos.
 * 
 */
public class TechnologyImperium {
    
    private Integer technologyImperiumId, level;
    private Imperium imperium;
    private Technology technology;

    public TechnologyImperium() {
    }

    public TechnologyImperium(Integer level, Imperium imperium, Technology technology) {
        this.level = level;
        this.imperium = imperium;
        this.technology = technology;
    }

    public Integer getTechnologyImperiumId() {
        return technologyImperiumId;
    }

    public void setTechnologyImperiumId(Integer technologyImperiumId) {
        this.technologyImperiumId = technologyImperiumId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Imperium getImperium() {
        return imperium;
    }

    public void setImperium(Imperium imperium) {
        this.imperium = imperium;
    }

    public Technology getTechnology() {
        return technology;
    }

    public void setTechnology(Technology technology) {
        this.technology = technology;
    }
    
}
