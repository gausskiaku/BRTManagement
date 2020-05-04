/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entites;

import java.util.ArrayList;

/**
 *
 * @author Gauss
 */
public class Enfant {
    private int id_enfant;
    private String nom_enfant;
    private String post_nom_enfant;
    private String prenom;
    private Agent agent = new Agent();

    public Enfant() {
    }

    public Enfant(int id_enfant, String nom_enfant, String post_nom_enfant, String prenom, Agent agent) {
        this.id_enfant = id_enfant;
        this.nom_enfant = nom_enfant;
        this.post_nom_enfant = post_nom_enfant;
        this.prenom = prenom;
        this.agent = agent;
    }

    public int getId_enfant() {
        return id_enfant;
    }

    public void setId_enfant(int id_enfant) {
        this.id_enfant = id_enfant;
    }

    public String getNom_enfant() {
        return nom_enfant;
    }

    public void setNom_enfant(String nom_enfant) {
        this.nom_enfant = nom_enfant;
    }

    public String getPost_nom_enfant() {
        return post_nom_enfant;
    }

    public void setPost_nom_enfant(String post_nom_enfant) {
        this.post_nom_enfant = post_nom_enfant;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }
}