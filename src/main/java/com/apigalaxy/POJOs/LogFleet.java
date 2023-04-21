package com.apigalaxy.POJOs;

import java.sql.Timestamp;

/**
 *
 * @author Francisco Jesus Moya Olivares
 * 
 * este POJO corresponde a la tabla logFleet en la base de datos.
 * 
 */
public class LogFleet {
    
    private Integer logFleetId,speed, live, shield, coordinates, destination;
    private Float strategy, repairs, engineering;
    private String name, image, description;
    private Timestamp departureTime;
    private Imperium imperium;
    private Planet base;
    public enum FleetType {transport,exploration,military};
    private FleetType fType;
    private Resources resources;

    public LogFleet() {
    }

    public LogFleet(Resources resources, Integer speed, Integer live, Integer shield, Integer coordinates, Integer destination, Float strategy, Float repairs, Float engineering, String name, String image, String description, Timestamp date, Imperium imperium, Planet base) {
        this.speed = speed;
        this.live = live;
        this.shield = shield;
        this.coordinates = coordinates;
        this.destination = destination;
        this.strategy = strategy;
        this.repairs = repairs;
        this.engineering = engineering;
        this.name = name;
        this.image = image;
        this.description = description;
        this.departureTime = date;
        this.imperium = imperium;
        this.base = base;
        this.resources = resources;
    }

    public Resources getResources() {
        return resources;
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }

    public FleetType getfType() {
        return fType;
    }

    public Boolean setfType(String fType) {
        Boolean res = false;
        switch(fType){
            case "transport":
                this.fType = FleetType.transport;
                res = true;
                break;
            case "exploration":
                this.fType = FleetType.exploration;
                res = true;
                break;
            case "military":
                this.fType = FleetType.military;
                res = true;
                break;
        }
        return res;
    }

    public Integer getLogFleetId() {
        return logFleetId;
    }

    public void setLogFleetId(Integer logFleetId) {
        this.logFleetId = logFleetId;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getLive() {
        return live;
    }

    public void setLive(Integer live) {
        this.live = live;
    }

    public Integer getShield() {
        return shield;
    }

    public void setShield(Integer shield) {
        this.shield = shield;
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

    public Float getStrategy() {
        return strategy;
    }

    public void setStrategy(Float strategy) {
        this.strategy = strategy;
    }

    public Float getRepairs() {
        return repairs;
    }

    public void setRepairs(Float repairs) {
        this.repairs = repairs;
    }

    public Float getEngineering() {
        return engineering;
    }

    public void setEngineering(Float engineering) {
        this.engineering = engineering;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Timestamp departureTime) {
        this.departureTime = departureTime;
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
    
    
    
}
