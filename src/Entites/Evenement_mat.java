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
public class Evenement_mat {
    private int id_ev;
    private String type_ev;
    private String jour;
    private String date_ev;
    private String heure;
    private String etat_mat;
    private int quantite;
    private Materiel materiel = new Materiel();
    private Agent agent = new Agent();

    public Evenement_mat() {
    }

    public Evenement_mat(int id_ev,String type_ev, String jour, String date_ev, String heure, String etat_mat, int quantite,Materiel materiel, Agent agent) {
        this.type_ev = type_ev;
        this.jour = jour;
        this.date_ev = date_ev;
        this.heure = heure;
        this.etat_mat = etat_mat;
        this.quantite = quantite;
        this.agent = agent;
        this.id_ev = id_ev;
        this.materiel = materiel;
        this.agent = agent;
    }

    public int getId_ev() {
        return id_ev;
    }

    public void setId_ev(int id_ev) {
        this.id_ev = id_ev;
    }

    public String getType_ev() {
        return type_ev;
    }

    public void setType_ev(String type_ev) {
        this.type_ev = type_ev;
    }

    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

    public String getDate_ev() {
        return date_ev;
    }

    public void setDate_ev(String date_ev) {
        this.date_ev = date_ev;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getEtat_mat() {
        return etat_mat;
    }

    public void setEtat_mat(String etat_mat) {
        this.etat_mat = etat_mat;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Materiel getMateriel() {
        return materiel;
    }

    public void setMateriel(Materiel materiel) {
        this.materiel = materiel;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }
}