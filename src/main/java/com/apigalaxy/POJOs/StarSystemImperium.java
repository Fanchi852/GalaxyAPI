/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.POJOs;

/**
 *
 * @author Fanch
 */
public class StarSystemImperium {
    
    private Integer StarSystemImperiumId;
    private Imperium imperium;
    private StarSystem star;

    public StarSystemImperium() {
    }

    public StarSystemImperium(Imperium imperium, StarSystem star) {
        this.imperium = imperium;
        this.star = star;
    }

    public Integer getStarSystemImperiumId() {
        return StarSystemImperiumId;
    }

    public void setStarSystemImperiumId(Integer StarSystemImperiumId) {
        this.StarSystemImperiumId = StarSystemImperiumId;
    }

    public Imperium getImperium() {
        return imperium;
    }

    public void setImperium(Imperium imperium) {
        this.imperium = imperium;
    }

    public StarSystem getStar() {
        return star;
    }

    public void setStar(StarSystem star) {
        this.star = star;
    }

    @Override
    public String toString() {
        return "StarSystemImperium{" + "StarSystemImperiumId=" + StarSystemImperiumId + ", imperium=" + imperium + ", star=" + star + '}';
    }
    
}
