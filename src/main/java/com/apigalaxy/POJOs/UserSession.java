/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.POJOs;

import java.sql.Timestamp;

/**
 *
 * @author Francisco Jesus Moya Olivares
 */
public class UserSession {
    
    private Integer userSession_id;
    private User user;
    private Timestamp initial_date, last_update;

    public UserSession() {
    }

    public UserSession(User user, Timestamp initial_date, Timestamp last_update) {
        this.user = user;
        this.initial_date = initial_date;
        this.last_update = last_update;
    }

    public Integer getUserSession_id() {
        return userSession_id;
    }

    public void setUserSession_id(Integer userSession_id) {
        this.userSession_id = userSession_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getInitial_date() {
        return initial_date;
    }

    public void setInitial_date(Timestamp initial_date) {
        this.initial_date = initial_date;
    }

    public Timestamp getLast_update() {
        return last_update;
    }

    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }

    @Override
    public String toString() {
        return "UserSession{" + "userSession_id=" + userSession_id + ", user=" + user + ", initial_date=" + initial_date + ", last_update=" + last_update + '}';
    }
    
    
}
