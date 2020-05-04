/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entites;

import java.sql.Date;

/**
 *
 * @author Gauss
 */
public class Assumer {
    
    private Fonction fonction = new Fonction();
    private Agent agent = new Agent();
    private String date_debut;

    public Assumer() {
    }

    public Assumer(Agent agent,Fonction fonction,String date_debut) {
        this.date_debut = date_debut;
        this.agent = agent;
        this.fonction= fonction;
    }

    public Fonction getFonction() {
        return fonction;
    }

    public void setFonction(Fonction fonction) {
        this.fonction = fonction;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }
}
