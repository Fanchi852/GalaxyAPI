/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.POJOs;

/**
 *
 * @author Francisco Jesus Moya Olivares
 * 
 * este POJO corresponde a la tabla planet en la base de datos.
 * 
 */
public class Planet {
    
    private Integer planetId, parcels, normalOreProduction, rareOreProduction, population_changes, cientific_data_changes;
    private String name, coordinates;
    public enum StrongResource {normal, rare, population, cientific}
    private StrongResource strResource;
    private Imperium imperium;
    private PlanetType planetType;
    private StarSystem Star;
    private Resources resources;

    public Planet() {
    }

    public Planet(Integer parcels, String coordinates, Integer normalOreProduction, Integer rareOreProduction, Integer population_changes, Integer cientific_data_changes, String name, StrongResource strResource, Imperium imperium, PlanetType planetType, StarSystem Star, Resources resources) {
        this.parcels = parcels;
        this.coordinates = coordinates;
        this.normalOreProduction = normalOreProduction;
        this.rareOreProduction = rareOreProduction;
        this.population_changes = population_changes;
        this.cientific_data_changes = cientific_data_changes;
        this.name = name;
        this.strResource = strResource;
        this.imperium = imperium;
        this.planetType = planetType;
        this.Star = Star;
        this.resources = resources;
    }

    public Resources getResources() {
        return resources;
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }
    
    public StarSystem getStar() {
        return Star;
    }

    public void setStar(StarSystem Star) {
        this.Star = Star;
    }

    public PlanetType getPlanetType() {
        return planetType;
    }

    public void setPlanetType(PlanetType planetType) {
        this.planetType = planetType;
    }

    public Imperium getImperium() {
        return imperium;
    }

    public void setImperium(Imperium imperium) {
        this.imperium = imperium;
    }
    
    public Integer getPlanetId() {
        return planetId;
    }

    public void setPlanetId(Integer planetId) {
        this.planetId = planetId;
    }

    public Integer getParcels() {
        return parcels;
    }

    public void setParcels(Integer parcels) {
        this.parcels = parcels;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public Integer getNormalOreProduction() {
        return normalOreProduction;
    }

    public void setNormalOreProduction(Integer normalOreProduction) {
        this.normalOreProduction = normalOreProduction;
    }

    public Integer getRareOreProduction() {
        return rareOreProduction;
    }

    public void setRareOreProduction(Integer rareOreProduction) {
        this.rareOreProduction = rareOreProduction;
    }

    public Integer getPopulation_changes() {
        return population_changes;
    }

    public void setPopulation_changes(Integer population_changes) {
        this.population_changes = population_changes;
    }

    public Integer getCientific_data_changes() {
        return cientific_data_changes;
    }

    public void setCientific_data_changes(Integer cientific_data_changes) {
        this.cientific_data_changes = cientific_data_changes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StrongResource getStrResource() {
        return strResource;
    }

    public Boolean setStrResource(String strResource) {
        Boolean res = false;
        switch(strResource){
            case "normal":
                this.strResource = StrongResource.normal;
                res = true;
                break;
            case "rare":
                this.strResource = StrongResource.rare;
                res = true;
                break;
            case "population":
                this.strResource = StrongResource.population;
                res = true;
                break;
            case "cientific":
                this.strResource = StrongResource.cientific;
                res = true;
                break;
        }
        return res;
    }

    @Override
    public String toString() {
        return "Planet{" + "planetId=" + planetId + ", parcels=" + parcels + ", coordinates=" + coordinates + ", normalOreProduction=" + normalOreProduction + ", rareOreProduction=" + rareOreProduction + ", population_changes=" + population_changes + ", cientific_data_changes=" + cientific_data_changes + ", name=" + name + ", strResource=" + strResource + ", imperium=" + imperium + ", planetType=" + planetType + ", Star=" + Star + ", resources=" + resources + '}';
    }
    
            
    
}
