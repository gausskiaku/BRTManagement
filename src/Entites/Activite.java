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
public class Activite {
    private int id_act;
    private String nom_act;
    private String type_act;
    private String description_act;
    private String departement;
   
    public Activite() {
    }

    public Activite(int id_act, String nom_act, String type_act, String description_act, String departement) {
        this.id_act = id_act;
        this.nom_act = nom_act;
        this.type_act = type_act;
        this.description_act = description_act;
        this.departement = departement;
           }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public int getId_act() {
        return id_act;
    }

    public void setId_act(int id_act) {
        this.id_act = id_act;
    }

    public String getNom_act() {
        return nom_act;
    }

    public void setNom_act(String nom_act) {
        this.nom_act = nom_act;
    }

    public String getType_act() {
        return type_act;
    }

    public void setType_act(String type_act) {
        this.type_act = type_act;
    }

    public String getDescription_act() {
        return description_act;
    }

    public void setDescription_act(String description_act) {
        this.description_act = description_act;
    }

   
}
