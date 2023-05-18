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
    
    private Integer ShipId, life, shield, damage, storageCapacity;
    private String name;
    private ShipClass shipClass;
    private Fleet fleet;
    private Resources resources;

    public Ship() {
    }

    public Ship(Integer life, Integer shield, Integer damage, Resources resources, String name, ShipClass shipClass, Fleet fleet,Integer  storageCapacity) {
        this.life = life;
        this.shield = shield;
        this.damage = damage;
        this.resources = resources;
        this.name = name;
        this.shipClass = shipClass;
        this.fleet = fleet;
        this.storageCapacity = storageCapacity;
    }

    public Integer getStorageCapacity() {
        return storageCapacity;
    }

    public void setStorageCapacity(Integer storageCapacity) {
        this.storageCapacity = storageCapacity;
    }

    public Resources getResources() {
        return resources;
    }

    public void setResources(Resources resources) {
        this.resources = resources;
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
        return "Ship{" + "ShipId=" + ShipId + ", life=" + life + ", shield=" + shield + ", damage=" + damage + ", storageCapacity=" + storageCapacity + ", name=" + name + ", shipClass=" + shipClass + ", fleet=" + fleet + ", resources=" + resources + '}';
    }
    
}
