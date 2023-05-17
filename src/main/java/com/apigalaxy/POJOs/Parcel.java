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
 * este POJO corresponde a la tabla parcel en la base de datos.
 * 
 */
public class Parcel {
    
    private Integer parcelId, storageCapacity, basic_normal_cost, basic_rare_cost,location;
    public BuildType Building, to_building;
    public enum LocationType {planet, moon}
    private LocationType locationT;
    private Timestamp construction_start_date;
    private Long basic_time_cost;

    public Parcel() {
    }

    public Parcel(Integer parcelId, Integer storageCapacity, Integer basic_normal_cost, Integer basic_rare_cost, Integer location, BuildType Building, BuildType to_building, LocationType locationT, Timestamp construction_start_date, Long basic_time_cost) {
        this.parcelId = parcelId;
        this.storageCapacity = storageCapacity;
        this.basic_normal_cost = basic_normal_cost;
        this.basic_rare_cost = basic_rare_cost;
        this.location = location;
        this.Building = Building;
        this.to_building = to_building;
        this.locationT = locationT;
        this.construction_start_date = construction_start_date;
        this.basic_time_cost = basic_time_cost;
    }


    public Integer getBasic_normal_cost() {
        return basic_normal_cost;
    }

    public void setBasic_normal_cost(Integer basic_normal_cost) {
        this.basic_normal_cost = basic_normal_cost;
    }

    public Integer getBasic_rare_cost() {
        return basic_rare_cost;
    }

    public void setBasic_rare_cost(Integer basic_rare_cost) {
        this.basic_rare_cost = basic_rare_cost;
    }

    public Timestamp getConstruction_start_date() {
        return construction_start_date;
    }

    public void setConstruction_start_date(Timestamp construction_start_date) {
        this.construction_start_date = construction_start_date;
    }

    public Long getBasic_time_cost() {
        return basic_time_cost;
    }

    public void setBasic_time_cost(Long basic_time_cost) {
        this.basic_time_cost = basic_time_cost;
    }

    public BuildType getBuilding() {
        return Building;
    }

    public void setBuilding(BuildType Building) {
        this.Building = Building;
    }

    public BuildType getTo_building() {
        return to_building;
    }

    public void setTo_building(BuildType to_building) {
        this.to_building = to_building;
    }
    
    public Integer getParcelId() {
        return parcelId;
    }

    public void setParcelId(Integer parcelId) {
        this.parcelId = parcelId;
    }

    public Integer getStorageCapacity() {
        return storageCapacity;
    }

    public void setStorageCapacity(Integer storageCapacity) {
        this.storageCapacity = storageCapacity;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public LocationType getLocationT() {
        return locationT;
    }

    public Boolean setLocationT(String stringLocation) {
        Boolean res = false;
        switch(stringLocation){
            case "planet":
                this.locationT = LocationType.planet;
                res = true;
                break;
            case "moon":
                this.locationT = LocationType.moon;
                res = true;
                break;
        }
        return res;
    }

    @Override
    public String toString() {
        return "Parcel{" + "parcelId=" + parcelId + ", storageCapacity=" + storageCapacity + ", basic_normal_cost=" + basic_normal_cost + ", basic_rare_cost=" + basic_rare_cost + ", location=" + location + ", Building=" + Building + ", to_building=" + to_building + ", locationT=" + locationT + ", construction_start_date=" + construction_start_date + ", basic_time_cost=" + basic_time_cost + '}';
    }
    
}
