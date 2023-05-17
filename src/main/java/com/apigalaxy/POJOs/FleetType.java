/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.POJOs;

/**
 *
 * @author Francisco Jesus Moya Olivares
 * 
 * este POJO corresponde a la tabla fleetType en la base de datos.
 * 
 */
public class FleetType {
    
    private Integer fleetTypeId;
    private Float strategy, repairs, engineering;
    public enum FType {transport, exploration, military}
    private FType ftype;

    public FleetType() {
    }

    public FleetType(Float strategy, Float repairs, Float engineering, FType ftype) {
        this.strategy = strategy;
        this.repairs = repairs;
        this.engineering = engineering;
        this.ftype = ftype;
    }

    public Integer getFleetTypeId() {
        return fleetTypeId;
    }

    public void setFleetTypeId(Integer fleetTypeId) {
        this.fleetTypeId = fleetTypeId;
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

    public FType getFtype() {
        return ftype;
    }

    public Boolean setFtype(String ftype) {
        Boolean res = false;
        switch(ftype){
            case "transport":
                this.ftype = FType.transport;
                res = true;
                break;
            case "exploration":
                this.ftype = FType.exploration;
                res = true;
                break;
            case "military":
                this.ftype = FType.military;
                res = true;
                break;
        }
        return res;
    }

    @Override
    public String toString() {
        return "FleetType{" + "fleetTypeId=" + fleetTypeId + ", strategy=" + strategy + ", repairs=" + repairs + ", engineering=" + engineering + ", ftype=" + ftype + '}';
    }
    
    
    
}
