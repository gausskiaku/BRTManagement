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
public class Animateur {
    private Agent agent = new Agent();
    private Programme programme = new Programme();
    

    public Animateur() {
    }

    public Animateur(Agent agent, Programme programme) {
        this.agent = agent;
        this.programme=programme;
        
                
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Programme getProgramme() {
        return programme;
    }

    public void setProgramme(Programme programme) {
        this.programme = programme;
    }

   }
