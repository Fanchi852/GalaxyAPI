/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.POJOs;

/**
 *
 * @author Francisco Jesus Moya Olivares
 * 
 * este POJO corresponde a la tabla planetType en la base de datos.
 * 
 */
public class PlanetType {
    
    private Integer planetTypeId;
    private String name,description, image;
    private Float pLive, pBuilding, pNormalMineral, pRareMineral,pCientific;

    public PlanetType() {
    }

    public PlanetType(String name, String description, String image, Float pLive, Float pBuilding, Float pNormalMineral, Float pRareMineral, Float pCientific) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.pLive = pLive;
        this.pBuilding = pBuilding;
        this.pNormalMineral = pNormalMineral;
        this.pRareMineral = pRareMineral;
        this.pCientific = pCientific;
    }

    public Integer getPlanetTypeId() {
        return planetTypeId;
    }

    public void setPlanetTypeId(Integer planetTypeId) {
        this.planetTypeId = planetTypeId;
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

    public Float getpLive() {
        return pLive;
    }

    public void setpLive(Float pLive) {
        this.pLive = pLive;
    }

    public Float getpBuilding() {
        return pBuilding;
    }

    public void setpBuilding(Float pBuilding) {
        this.pBuilding = pBuilding;
    }

    public Float getpNormalMineral() {
        return pNormalMineral;
    }

    public void setpNormalMineral(Float pNormalMineral) {
        this.pNormalMineral = pNormalMineral;
    }

    public Float getpRareMineral() {
        return pRareMineral;
    }

    public void setpRareMineral(Float pRareMineral) {
        this.pRareMineral = pRareMineral;
    }

    public Float getpCientific() {
        return pCientific;
    }

    public void setpCientific(Float pCientific) {
        this.pCientific = pCientific;
    }

    
    
}
