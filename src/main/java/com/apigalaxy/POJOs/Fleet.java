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
    
    private Integer fleetId, destination, totalLife, life, totalShield, shield, totalDamage, damage, totalSpeed, speed;
    public enum Status {ready, docked, upgrading, repairing};
    private Status state;
    private FleetType fleetType;
    private Imperium imperium;
    private Planet base;
    private String name, description, image, coordinates;
    private Timestamp departureTime, last_update;
    private Resources resources;

    public Fleet() {
    }

    public Fleet(Integer destination, Integer totalLife, Integer life, Integer totalShield, Integer shield, Integer totalDamage, Integer damage, Integer totalSpeed, Integer speed, Status state, FleetType fleetType, Imperium imperium, Planet base, String name, String description, String image, String coordinates, Timestamp departureTime, Timestamp last_update, Resources resources) {
        this.destination = destination;
        this.totalLife = totalLife;
        this.life = life;
        this.totalShield = totalShield;
        this.shield = shield;
        this.totalDamage = totalDamage;
        this.damage = damage;
        this.totalSpeed = totalSpeed;
        this.speed = speed;
        this.state = state;
        this.fleetType = fleetType;
        this.imperium = imperium;
        this.base = base;
        this.name = name;
        this.description = description;
        this.image = image;
        this.coordinates = coordinates;
        this.departureTime = departureTime;
        this.last_update = last_update;
        this.resources = resources;
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

    public Integer getTotalLife() {
        return totalLife;
    }

    public void setTotalLife(Integer totalLife) {
        this.totalLife = totalLife;
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

    public Integer getLife() {
        return life;
    }

    public void setLife(Integer life) {
        this.life = life;
    }

    public Integer getShield() {
        return shield;
    }

    public void setShield(Integer shield) {
        this.shield = shield;
    }

    public Integer getDamage() {
        return damage;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Status getState() {
        return state;
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

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
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
        return "Fleet{" + "fleetId=" + fleetId + ", destination=" + destination + ", totalLife=" + totalLife + ", life=" + life + ", totalShield=" + totalShield + ", shield=" + shield + ", totalDamage=" + totalDamage + ", damage=" + damage + ", totalSpeed=" + totalSpeed + ", speed=" + speed + ", state=" + state + ", fleetType=" + fleetType + ", imperium=" + imperium + ", base=" + base + ", name=" + name + ", description=" + description + ", image=" + image + ", coordinates=" + coordinates + ", departureTime=" + departureTime + ", last_update=" + last_update + ", resources=" + resources + '}';
    }
    
}
