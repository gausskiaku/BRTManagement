/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entites;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

/**
 *
 * @author Gauss
 */
public class Presence {
    private int id_presence;
    private String date_presence;
    private String jour;
    private String heure_arrive;
    private String heure_depart;
    private String disponibilite;
    private Agent agent;
    
    public Presence() {
    }

    public Presence(int id_presence, String date_presence, String jour, String heure_arrive, String heure_depart, String disponibilite,Agent agent) {
        this.id_presence = id_presence;
        this.date_presence = date_presence;
        this.jour = jour;
        this.heure_arrive = heure_arrive;
        this.heure_depart = heure_depart;
        this.disponibilite = disponibilite;
        this.agent=agent;
    }

    public String getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(String disponibilite) {
        this.disponibilite = disponibilite;
    }

    public int getId_presence() {
        return id_presence;
    }

    public void setId_presence(int id_presence) {
        this.id_presence = id_presence;
    }

    public String getDate_presence() {
        return date_presence;
    }

    public void setDate_presence(String date_presence) {
        this.date_presence = date_presence;
    }

    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

    public String getHeure_arrive() {
        return heure_arrive;
    }

    public void setHeure_arrive(String heure_arrive) {
        this.heure_arrive = heure_arrive;
    }

    public String getHeure_depart() {
        return heure_depart;
    }

    public void setHeure_depart(String heure_depart) {
        this.heure_depart = heure_depart;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }
}
