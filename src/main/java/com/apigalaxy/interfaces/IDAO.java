/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apigalaxy.interfaces;

import java.util.List;

/**
 *
 * @author Francisco Jesus Moya Olivares
 * @param <B> es un pojo
 * @param <M> es un mapa con los campos y valores para realizar una busqueda personalizada segun el caso
 */
public interface IDAO <B,M> {

    public Integer add (B bean);
    public Boolean delete (B bean);
    public List findBy (M filter);
    public Boolean update (B bean);
    
}
