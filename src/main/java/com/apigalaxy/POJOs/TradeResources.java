/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.POJOs;

/**
 *
 * @author Fanch
 */
public class TradeResources {
    
    private Resources transaction, origin, receiver;

    public TradeResources() {
    }

    public Resources getTransaction() {
        return transaction;
    }

    public void setTransaction(Resources transaction) {
        this.transaction = transaction;
    }

    public Resources getOrigin() {
        return origin;
    }

    public void setOrigin(Resources origin) {
        this.origin = origin;
    }

    public Resources getReceiver() {
        return receiver;
    }

    public void setReceiver(Resources receiver) {
        this.receiver = receiver;
    }

    @Override
    public String toString() {
        return "TradeResources{" + "transaction=" + transaction.toString() + ", origin=" + origin.toString() + ", receptor=" + receiver.toString() + '}';
    }
    
}
