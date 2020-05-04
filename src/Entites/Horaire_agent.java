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
public class Horaire_agent {
    private int id_horaire_agent;
    private Date date_de_service;
    private Time heure_tot;
    private Time heure_tard;
    private Agent agent = new Agent();
    

    public Horaire_agent() {
    }

    public Horaire_agent(int id_horaire_agent,Date date_de_service, Time heure_tot, Time heure_tard, Agent agent) {
        this.date_de_service = date_de_service;
        this.heure_tot = heure_tot;
        this.heure_tard = heure_tard;
        this.agent = agent;
        this.id_horaire_agent=id_horaire_agent;
    }

    public int getId_horaire_agent() {
        return id_horaire_agent;
    }

    public void setId_horaire_agent(int id_horaire_agent) {
        this.id_horaire_agent = id_horaire_agent;
    }

    public Date getDate_de_service() {
        return date_de_service;
    }

    public void setDate_de_service(Date date_de_service) {
        this.date_de_service = date_de_service;
    }

    public Time getHeure_tot() {
        return heure_tot;
    }

    public void setHeure_tot(Time heure_tot) {
        this.heure_tot = heure_tot;
    }

    public Time getHeure_tard() {
        return heure_tard;
    }

    public void setHeure_tard(Time heure_tard) {
        this.heure_tard = heure_tard;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }
}
