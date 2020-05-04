/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import java.sql.Connection;

/**
 *
 * @author Gauss
 */
public abstract class Dao<T> {
    public Connection connect = Connexion.Connexion.getInstance();
    
    public abstract T find(long id);
    public abstract T create(T obj);
    public abstract T update(T obj);
    public abstract void delete(T obj);
}
