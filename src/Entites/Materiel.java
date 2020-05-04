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
public class Materiel {
    private int id_mat;
    private String type_mat;
    private String caracteristique;
    private String disponibilite;
    private Emplacement emplacement = new Emplacement();
    
    public Materiel() {
    }

    public Materiel(int id_mat, String type_mat, String caracteristique, String disponibilite, Emplacement emplacement) {
        this.id_mat = id_mat;
        this.type_mat = type_mat;
        this.caracteristique = caracteristique;
        this.disponibilite = disponibilite;
        this.emplacement = emplacement;
    }

    public String getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(String disponibilite) {
        this.disponibilite = disponibilite;
    }

    public int getId_mat() {
        return id_mat;
    }

    public void setId_mat(int id_mat) {
        this.id_mat = id_mat;
    }

    public String getType_mat() {
        return type_mat;
    }

    public void setType_mat(String type_mat) {
        this.type_mat = type_mat;
    }

    public String getCaracteristique() {
        return caracteristique;
    }

    public void setCaracteristique(String caracteristique) {
        this.caracteristique = caracteristique;
    }

    public Emplacement getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(Emplacement emplacement) {
        this.emplacement = emplacement;
    }

    
}
