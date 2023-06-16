/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.POJOs;

import java.sql.Timestamp;

/**
 *
 * @author Francisco Jesus Moya Olivares
 * 
 * este POJO corresponde a la tabla resources en la base de datos.
 * 
 */
public class Resources {
    
    private Integer resourceId, normal_quantity, rare_quantity, population_quantity, normal_capacity, rare_capacity, population_capacity;
    private Timestamp last_update;

    public Resources() {
    }

    public Resources(Integer normal_quantity, Integer rare_quantity, Integer population_quantity, Integer normal_capacity, Integer rare_capacity, Integer population_capacity) {
        this.normal_quantity = normal_quantity;
        this.rare_quantity = rare_quantity;
        this.population_quantity = population_quantity;
        this.normal_capacity = normal_capacity;
        this.rare_capacity = rare_capacity;
        this.population_capacity = population_capacity;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getNormal_quantity() {
        return normal_quantity;
    }

    public void setNormal_quantity(Integer normal_quantity) {
        this.normal_quantity = normal_quantity;
    }

    public Integer getRare_quantity() {
        return rare_quantity;
    }

    public void setRare_quantity(Integer rare_quantity) {
        this.rare_quantity = rare_quantity;
    }

    public Integer getPopulation_quantity() {
        return population_quantity;
    }

    public void setPopulation_quantity(Integer population_quantity) {
        this.population_quantity = population_quantity;
    }

    public Integer getNormal_capacity() {
        return normal_capacity;
    }

    public void setNormal_capacity(Integer normal_capacity) {
        this.normal_capacity = normal_capacity;
    }

    public Integer getRare_capacity() {
        return rare_capacity;
    }

    public void setRare_capacity(Integer rare_capacity) {
        this.rare_capacity = rare_capacity;
    }

    public Integer getPopulation_capacity() {
        return population_capacity;
    }

    public void setPopulation_capacity(Integer population_capacity) {
        this.population_capacity = population_capacity;
    }

    @Override
    public String toString() {
        return "Resources{" + "resourceId=" + resourceId + ", normal_quantity=" + normal_quantity + ", rare_quantity=" + rare_quantity + ", population_quantity=" + population_quantity + ", normal_capacity=" + normal_capacity + ", rare_capacity=" + rare_capacity + ", population_capacity=" + population_capacity + '}';
    }

    
}
