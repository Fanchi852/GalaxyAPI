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
    
    private Integer logFleetId, totalLife, life, totalShield, shield, totalDamage, damage, totalSpeed, speed, coordinates, destination;
    private Float strategy, logistics, engineering;
    private String name, image, description;
    private Timestamp departureTime, last_update;
    private Imperium imperium;
    private Planet base;
    public enum FleetType {transport,exploration,military};
    private FleetType fType;
    private Resources resources;
    public enum Status {ready, docked, upgrading, repairing};
    private Status state;

    public LogFleet() {
    }

    public LogFleet(Integer totalLife, Integer life, Integer totalShield, Integer shield, Integer totalDamage, Integer damage, Integer totalSpeed, Integer speed, Integer coordinates, Integer destination, Float strategy, Float logistics, Float engineering, String name, String image, String description, Timestamp departureTime, Imperium imperium, Timestamp last_update, Planet base, FleetType fType, Resources resources, Status state) {
        this.totalLife = totalLife;
        this.life = life;
        this.totalShield = totalShield;
        this.shield = shield;
        this.totalDamage = totalDamage;
        this.damage = damage;
        this.totalSpeed = totalSpeed;
        this.speed = speed;
        this.coordinates = coordinates;
        this.destination = destination;
        this.strategy = strategy;
        this.logistics = logistics;
        this.engineering = engineering;
        this.name = name;
        this.image = image;
        this.description = description;
        this.departureTime = departureTime;
        this.imperium = imperium;
        this.last_update = last_update;
        this.base = base;
        this.fType = fType;
        this.resources = resources;
        this.state = state;
    }

    public Status getState() {
        return state;
    }

    public Boolean setState(String state) {
        Boolean res = false;
        switch(state){
            case "ready":
                this.state = Status.ready;
                res = true;
                break;
            case "docked":
                this.state = Status.docked;
                res = true;
                break;
            case "upgrading":
                this.state = Status.upgrading;
                res = true;
                break;
            case "repairing":
                this.state = Status.repairing;
                res = true;
                break;
        }
        return res;
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

    public Integer getTotalLife() {
        return totalLife;
    }

    public void setTotalLife(Integer totalLife) {
        this.totalLife = totalLife;
    }

    public Integer getLife() {
        return life;
    }

    public void setLife(Integer life) {
        this.life = life;
    }

    public Integer getTotalShield() {
        return totalShield;
    }

    public void setTotalShield(Integer totalShield) {
        this.totalShield = totalShield;
    }

    public Integer getTotalDamage() {
        return totalDamage;
    }

    public void setTotalDamage(Integer totalDamage) {
        this.totalDamage = totalDamage;
    }

    public Integer getDamage() {
        return damage;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }

    public Integer getTotalSpeed() {
        return totalSpeed;
    }

    public void setTotalSpeed(Integer totalSpeed) {
        this.totalSpeed = totalSpeed;
    }

    public Timestamp getLast_update() {
        return last_update;
    }

    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
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

    public Float getLogistics() {
        return logistics;
    }

    public void setLogistics(Float logistics) {
        this.logistics = logistics;
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

    @Override
    public String toString() {
        return "LogFleet{" + "logFleetId=" + logFleetId + ", totalLife=" + totalLife + ", life=" + life + ", totalShield=" + totalShield + ", shield=" + shield + ", totalDamage=" + totalDamage + ", damage=" + damage + ", totalSpeed=" + totalSpeed + ", speed=" + speed + ", coordinates=" + coordinates + ", destination=" + destination + ", strategy=" + strategy + ", logistics=" + logistics + ", engineering=" + engineering + ", name=" + name + ", image=" + image + ", description=" + description + ", departureTime=" + departureTime + ", imperium=" + imperium + ", last_update=" + last_update + ", base=" + base + ", fType=" + fType + ", resources=" + resources + ", state=" + state + '}';
    }
    
}
