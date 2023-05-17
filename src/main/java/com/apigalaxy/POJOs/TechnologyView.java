/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.POJOs;

/**
 *
 * @author Francisco Jesus Moya Olivares
 */
public class TechnologyView {
    
    private Integer technology_imperium_id, level, basic_cost;
    private Float bono;
    private String name, descripcion, type, image;

    public TechnologyView() {
    }

    public TechnologyView(Integer level, Integer basic_cost, Float bono, String name, String descripcion, String type, String image) {
        this.level = level;
        this.basic_cost = basic_cost;
        this.bono = bono;
        this.name = name;
        this.descripcion = descripcion;
        this.type = type;
        this.image = image;
    }

    public Integer getTechnology_imperium_id() {
        return technology_imperium_id;
    }

    public void setTechnology_imperium_id(Integer technology_imperium_id) {
        this.technology_imperium_id = technology_imperium_id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getBasic_cost() {
        return basic_cost;
    }

    public void setBasic_cost(Integer basic_cost) {
        this.basic_cost = basic_cost;
    }

    public Float getBono() {
        return bono;
    }

    public void setBono(Float bono) {
        this.bono = bono;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "TechnologyView{" + "technology_imperium_id=" + technology_imperium_id + ", level=" + level + ", basic_cost=" + basic_cost + ", bono=" + bono + ", name=" + name + ", descripcion=" + descripcion + ", type=" + type + ", image=" + image + '}';
    }

}
