/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.POJOs;

/**
 *
 * @author Francisco Jesus Moya Olivares
 * 
 * este POJO corresponde a la tabla technology en la base de datos.
 * 
 */
public class Technology {
    
    private Integer technologyId,basicCost;
    private String name, description, image;
    private Float bono;
    public enum TechnologyType {military, industrial}
    TechnologyType techType;

    public Technology() {
    }

    public Technology(Integer basicCost, String name, String description, String image, Float bono, TechnologyType techtype) {
        this.basicCost = basicCost;
        this.name = name;
        this.description = description;
        this.image = image;
        this.bono = bono;
        this.techType = techtype;
    }

    public Float getBono() {
        return bono;
    }

    public void setBono(Float bono) {
        this.bono = bono;
    }

    public Integer getTechnologyId() {
        return technologyId;
    }

    public void setTechnologyId(Integer technologyId) {
        this.technologyId = technologyId;
    }

    public Integer getBasicCost() {
        return basicCost;
    }

    public void setBasicCost(Integer basicCost) {
        this.basicCost = basicCost;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public TechnologyType getTechType() {
        return techType;
    }

    public Boolean setTechType(String stringTechType) {
        Boolean res = false;
        switch(stringTechType){
            case "military":
                this.techType = TechnologyType.military;
                res = true;
                break;
            case "industrial":
                this.techType = TechnologyType.industrial;
                res = true;
                break;
        }
        return res;
    }

    @Override
    public String toString() {
        return "Technology{" + "technologyId=" + technologyId + ", basicCost=" + basicCost + ", name=" + name + ", description=" + description + ", image=" + image + ", bono=" + bono + ", techType=" + techType + '}';
    }

}
