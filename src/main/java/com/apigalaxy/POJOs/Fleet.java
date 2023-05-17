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
 * este POJO corresponde a la tabla fleet en la base de datos.
 * 
 */
public class Fleet {
    
    private Integer fleetId, coordinates, destination;
    private FleetType fleetType;
    private Imperium imperium;
    private Planet base;
    private String name, description, image;
    private Timestamp departureTime;
    private Resources resources;

    public Fleet() {
    }

    public Fleet(Resources resources, Integer coordinates, Integer destination, FleetType fleetType, Imperium imperium, Planet base, String name, String description, String image, Timestamp departureTime) {
        this.coordinates = coordinates;
        this.destination = destination;
        this.fleetType = fleetType;
        this.imperium = imperium;
        this.base = base;
        this.name = name;
        this.description = description;
        this.image = image;
        this.departureTime = departureTime;
        this.resources = resources;
    }

    public Integer getFleetId() {
        return fleetId;
    }

    public Resources getResources() {
        return resources;
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }

    public void setFleetId(Integer fleetId) {
        this.fleetId = fleetId;
    }

    public Integer getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Integer coordinates) {
        this.coordinates = coordinates;
    }

    public Integer getDestination() {
        return destination;
    }

    public void setDestination(Integer destination) {
        this.destination = destination;
    }

    public FleetType getFleetType() {
        return fleetType;
    }

    public void setFleetType(FleetType fleetType) {
        this.fleetType = fleetType;
    }

    public Imperium getImperium() {
        return imperium;
    }

    public void setImperium(Imperium imperium) {
        this.imperium = imperium;
    }

    public Planet getBase() {
        return base;
    }

    public void setBase(Planet base) {
        this.base = base;
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

    public Timestamp getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Timestamp departureTime) {
        this.departureTime = departureTime;
    }

    @Override
    public String toString() {
        return "Fleet{" + "fleetId=" + fleetId + ", coordinates=" + coordinates + ", destination=" + destination + ", fleetType=" + fleetType + ", imperium=" + imperium + ", base=" + base + ", name=" + name + ", description=" + description + ", image=" + image + ", departureTime=" + departureTime + ", resources=" + resources + '}';
    }
    
    
    
}
