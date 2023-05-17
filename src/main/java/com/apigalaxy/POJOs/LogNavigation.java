/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.POJOs;

/**
 *
 * @author Francisco Jesus Moya Olivares
 * 
 * este POJO corresponde a la tabla logNavigation en la base de datos.
 * 
 */
public class LogNavigation {
    
    private Integer logNavigationId, origen, speed;
    private LogFleet fleet;

    public LogNavigation() {
    }

    public LogNavigation(Integer origen, Integer detino, Integer speed, LogFleet fleet) {
        this.origen = origen;
        this.speed = speed;
        this.fleet = fleet;
    }

    public Integer getLogNavigationId() {
        return logNavigationId;
    }

    public void setLogNavigationId(Integer logNavigationId) {
        this.logNavigationId = logNavigationId;
    }

    public Integer getOrigen() {
        return origen;
    }

    public void setOrigen(Integer origen) {
        this.origen = origen;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public LogFleet getFleet() {
        return fleet;
    }

    public void setFleet(LogFleet fleet) {
        this.fleet = fleet;
    }

    @Override
    public String toString() {
        return "LogNavigation{" + "logNavigationId=" + logNavigationId + ", origen=" + origen + ", speed=" + speed + ", fleet=" + fleet + '}';
    }
    
    
    
}
