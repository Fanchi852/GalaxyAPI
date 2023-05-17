/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.POJOs;

/**
 *
 * @author Francisco Jesus Moya Olivares
 * 
 * este POJO corresponde a la tabla logBattle_technology en la base de datos.
 * 
 */
public class LogBattleTechnology {
    
    private Integer LogBattleTechnologyId, level;
    private Imperium imperium;
    private Technology technology;
    private LogBattle logBattle;

    public LogBattleTechnology() {
    }

    public LogBattleTechnology(Integer level, Imperium imperium, Technology technology, LogBattle logBattle) {
        this.level = level;
        this.imperium = imperium;
        this.technology = technology;
        this.logBattle = logBattle;
    }

    public Integer getLogBattleTechnologyId() {
        return LogBattleTechnologyId;
    }

    public void setLogBattleTechnologyId(Integer LogBattleTechnologyId) {
        this.LogBattleTechnologyId = LogBattleTechnologyId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Imperium getImperium() {
        return imperium;
    }

    public void setImperium(Imperium imperium) {
        this.imperium = imperium;
    }

    public Technology getTechnology() {
        return technology;
    }

    public void setTechnology(Technology technology) {
        this.technology = technology;
    }

    public LogBattle getLogBattle() {
        return logBattle;
    }

    public void setLogBattle(LogBattle logBattle) {
        this.logBattle = logBattle;
    }

    @Override
    public String toString() {
        return "LogBattleTechnology{" + "LogBattleTechnologyId=" + LogBattleTechnologyId + ", level=" + level + ", imperium=" + imperium + ", technology=" + technology + ", logBattle=" + logBattle + '}';
    }
    
    
    
}
