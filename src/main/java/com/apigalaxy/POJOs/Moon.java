/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.POJOs;

/**
 *
 * @author Francisco Jesus Moya Olivares
 * 
 * este POJO corresponde a la tabla moon en la base de datos.
 * 
 */
public class Moon {
    
    private Integer moonId, parcels, coordinates, normalOreProduction, rareOreProduction, population_changes, cientific_data_changes;
    private String name;
    private PlanetType planetType;
    private Resources resources;
    private Planet planet;
    public enum StrongResource {normal, rare, population, cientific}
    private StrongResource strResource;

    public Moon() {
    }

    public Moon(String name, Integer parcels, Integer coordinates, Integer normalOreProduction, Integer rareOreProduction, Integer population_changes, Integer cientific_data_changes, PlanetType planetType, Planet planet, StrongResource strResource, Resources resources) {
        this.name = name;
        this.parcels = parcels;
        this.coordinates = coordinates;
        this.normalOreProduction = normalOreProduction;
        this.rareOreProduction = rareOreProduction;
        this.population_changes = population_changes;
        this.cientific_data_changes = cientific_data_changes;
        this.planetType = planetType;
        this.planet = planet;
        this.strResource = strResource;
        this.resources = resources;
    }

    public Resources getResources() {
        return resources;
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }
    
    public Integer getMoonId() {
        return moonId;
    }

    public void setMoonId(Integer moonId) {
        this.moonId = moonId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getParcels() {
        return parcels;
    }

    public void setParcels(Integer parcels) {
        this.parcels = parcels;
    }

    public Integer getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Integer coordinates) {
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

    public PlanetType getPlanetType() {
        return planetType;
    }

    public void setPlanetType(PlanetType planetType) {
        this.planetType = planetType;
    }

    public Planet getPlanet() {
        return planet;
    }

    public void setPlanet(Planet planet) {
        this.planet = planet;
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
    
    
    
}
