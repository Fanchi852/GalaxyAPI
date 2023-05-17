/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.POJOs;

/**
 *
 * @author Francisco Jesus Moya Olivares
 * 
 * este POJO corresponde a la tabla shipClass en la base de datos.
 * 
 */
public class ShipClass {
    
    private Integer shipClassId, basicLife, basicShield, basicDamage, basic_normal_cost, basic_rare_cost;
    private String name, description;

    public ShipClass() {
    }

    public ShipClass(Integer basicLife, Integer basicShield, Integer basicDamage, Integer basic_normal_cost, Integer basic_rare_cost, String name, String description) {
        this.basicLife = basicLife;
        this.basicShield = basicShield;
        this.basicDamage = basicDamage;
        this.basic_normal_cost = basic_normal_cost;
        this.basic_rare_cost = basic_rare_cost;
        this.name = name;
        this.description = description;
    }

    public Integer getBasic_normal_cost() {
        return basic_normal_cost;
    }

    public void setBasic_normal_cost(Integer basic_normal_cost) {
        this.basic_normal_cost = basic_normal_cost;
    }

    public Integer getBasic_rare_cost() {
        return basic_rare_cost;
    }

    public void setBasic_rare_cost(Integer basic_rare_cost) {
        this.basic_rare_cost = basic_rare_cost;
    }
    
    public Integer getShipClassId() {
        return shipClassId;
    }

    public void setShipClassId(Integer shipClassId) {
        this.shipClassId = shipClassId;
    }

    public Integer getBasicLife() {
        return basicLife;
    }

    public void setBasicLife(Integer basicLife) {
        this.basicLife = basicLife;
    }

    public Integer getBasicShield() {
        return basicShield;
    }

    public void setBasicShield(Integer basicShield) {
        this.basicShield = basicShield;
    }

    public Integer getBasicDamage() {
        return basicDamage;
    }

    public void setBasicDamage(Integer basicDamage) {
        this.basicDamage = basicDamage;
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

    @Override
    public String toString() {
        return "ShipClass{" + "shipClassId=" + shipClassId + ", basicLife=" + basicLife + ", basicShield=" + basicShield + ", basicDamage=" + basicDamage + ", basic_normal_cost=" + basic_normal_cost + ", basic_rare_cost=" + basic_rare_cost + ", name=" + name + ", description=" + description + '}';
    }
    
}
