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
public class Parrain {
    private int id_parrain;
    private String nom_parrain;
    private String prenom_parrain;
    private String num_tel_parrain;
   
    public Parrain() {
    }

    public Parrain(int id_parrain, String nom_parrain, String prenom_parrain, String num_tel_parrain) {
        this.id_parrain = id_parrain;
        this.nom_parrain = nom_parrain;
        this.prenom_parrain = prenom_parrain;
        this.num_tel_parrain = num_tel_parrain;
       
    }

    public int getId_parrain() {
        return id_parrain;
    }

    public void setId_parrain(int id_parrain) {
        this.id_parrain = id_parrain;
    }

    public String getNom_parrain() {
        return nom_parrain;
    }

    public void setNom_parrain(String nom_parrain) {
        this.nom_parrain = nom_parrain;
    }

    public String getPrenom_parrain() {
        return prenom_parrain;
    }

    public void setPrenom_parrain(String prenom_parrain) {
        this.prenom_parrain = prenom_parrain;
    }

    public String getNum_tel_parrain() {
        return num_tel_parrain;
    }

    public void setNum_tel_parrain(String num_tel_parrain) {
        this.num_tel_parrain = num_tel_parrain;
    }
}
