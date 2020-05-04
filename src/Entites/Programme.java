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
public class Programme {
    private int id_prog;
    private String date_prog;
    private String heure_debut;
    private String heure_fin;
    private String rediffusion;
    private String date_redif;
    private String departement;
    private Activite activite;
    

    public Programme() {
    }

    public Programme(int id_prog, String date_prog, String heure_debut, String heure_fin, String rediffusion, String date_redif, String departement, Activite activite) {
        this.id_prog = id_prog;
        this.date_prog = date_prog;
        this.heure_debut = heure_debut;
        this.heure_fin = heure_fin;
        this.rediffusion = rediffusion;
        this.date_redif = date_redif;
        this.activite = activite;
        this.departement = departement;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public int getId_prog() {
        return id_prog;
    }

    public void setId_prog(int id_prog) {
        this.id_prog = id_prog;
    }

    public String getDate_prog() {
        return date_prog;
    }

    public void setDate_prog(String date_prog) {
        this.date_prog = date_prog;
    }

    public String getHeure_debut() {
        return heure_debut;
    }

    public void setHeure_debut(String heure_debut) {
        this.heure_debut = heure_debut;
    }

    public String getHeure_fin() {
        return heure_fin;
    }

    public void setHeure_fin(String heure_fin) {
        this.heure_fin = heure_fin;
    }

    public String getRediffusion() {
        return rediffusion;
    }

    public void setRediffusion(String rediffusion) {
        this.rediffusion = rediffusion;
    }

    public String getDate_redif() {
        return date_redif;
    }

    public void setDate_redif(String date_redif) {
        this.date_redif = date_redif;
    }

    public Activite getActivite() {
        return activite;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }
}
