/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entites;

/**
 *
 * @author Gauss
 */
public class User {
    private int id_user;
    private String nom_user;
    private String passwd_user;
    private Agent agent;
    

    public User() {
    }

    public User(int id_user, String nom_user, String passwd_user, Agent agent) {
        this.id_user = id_user;
        this.nom_user = nom_user;
        this.passwd_user = passwd_user;
        this.agent = agent;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNom_user() {
        return nom_user;
    }

    public void setNom_user(String nom_user) {
        this.nom_user = nom_user;
    }

    public String getPasswd_user() {
        return passwd_user;
    }

    public void setPasswd_user(String passwd_user) {
        this.passwd_user = passwd_user;
    }

    
}
