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
 * este POJO corresponde a la tabla logBattle en la base de datos.
 * 
 */
public class LogBattle {
    
    private Integer logBattleId, totalNormal, totalRare;
    private LogFleet atkFleet, defFleet;
    public enum Victory {atk, def};
    private Victory victory;
    private Timestamp date;

    public LogBattle() {
    }

    public LogBattle(Integer totalNormal, Integer totalRare, LogFleet atkFleet, LogFleet defFleet, Victory victory, Timestamp date) {
        this.totalNormal = totalNormal;
        this.totalRare = totalRare;
        this.atkFleet = atkFleet;
        this.defFleet = defFleet;
        this.victory = victory;
        this.date = date;
    }

    public Integer getLogBattleId() {
        return logBattleId;
    }

    public void setLogBattleId(Integer logBattleId) {
        this.logBattleId = logBattleId;
    }

    public Integer getTotalNormal() {
        return totalNormal;
    }

    public void setTotalNormal(Integer totalNormal) {
        this.totalNormal = totalNormal;
    }

    public Integer getTotalRare() {
        return totalRare;
    }

    public void setTotalRare(Integer totalRare) {
        this.totalRare = totalRare;
    }

    public LogFleet getAtkFleet() {
        return atkFleet;
    }

    public void setAtkFleet(LogFleet atkFleet) {
        this.atkFleet = atkFleet;
    }

    public LogFleet getDefFleet() {
        return defFleet;
    }

    public void setDefFleet(LogFleet defFleet) {
        this.defFleet = defFleet;
    }

    public Victory getVictory() {
        return victory;
    }

    public Boolean setVictory(String victory) {
        Boolean res = false;
        switch(victory){
            case "atk":
                this.victory = Victory.atk;
                res = true;
                break;
            case "def":
                this.victory = Victory.def;
                res = true;
                break;
        }
        return res;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
    
    
    
}
