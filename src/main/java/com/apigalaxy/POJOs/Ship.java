/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.POJOs;

/**
 *
 * @author Francisco Jesus Moya Olivares
 * 
 * este POJO corresponde a la tabla ship en la base de datos.
 * 
 */
public class Ship {
    
    private Integer ShipId, totalLife, life, totalShield, shield, totalDamage, damage, storageCapacity, totalSpeed, speed;
    private String name;
    private ShipClass shipClass;
    private Fleet fleet;

    public Ship() {
    }

    public Ship(Integer totalLife, Integer life, Integer totalShield, Integer shield, Integer totalDamage, Integer damage, Integer storageCapacity, Integer totalSpeed, Integer speed, String name, ShipClass shipClass, Fleet fleet) {
        this.totalLife = totalLife;
        this.life = life;
        this.totalShield = totalShield;
        this.shield = shield;
        this.totalDamage = totalDamage;
        this.damage = damage;
        this.storageCapacity = storageCapacity;
        this.totalSpeed = totalSpeed;
        this.speed = speed;
        this.name = name;
        this.shipClass = shipClass;
        this.fleet = fleet;
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
    
    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }
    
    public Integer getStorageCapacity() {
        return storageCapacity;
    }

    public void setStorageCapacity(Integer storageCapacity) {
        this.storageCapacity = storageCapacity;
    }

    public Integer getShipId() {
        return ShipId;
    }

    public void setShipId(Integer ShipId) {
        this.ShipId = ShipId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ShipClass getShipClass() {
        return shipClass;
    }

    public void setShipClass(ShipClass shipClass) {
        this.shipClass = shipClass;
    }

    public Fleet getFleet() {
        return fleet;
    }

    public void setFleet(Fleet fleet) {
        this.fleet = fleet;
    }

    @Override
    public String toString() {
        return "Ship{" + "ShipId=" + ShipId + ", totalLife=" + totalLife + ", life=" + life + ", totalShield=" + totalShield + ", shield=" + shield + ", totalDamage=" + totalDamage + ", damage=" + damage + ", storageCapacity=" + storageCapacity + ", totalSpeed=" + totalSpeed + ", speed=" + speed + ", name=" + name + ", shipClass=" + shipClass + ", fleet=" + fleet + '}';
    }

}
