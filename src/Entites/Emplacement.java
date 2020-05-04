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
public class Emplacement {
    private int id_emp;
    private String nom_emp;
   

    public Emplacement() {
    }

    public Emplacement(int id_emp, String nom_emp) {
        this.id_emp = id_emp;
        this.nom_emp = nom_emp;
        
    }

    public int getId_emp() {
        return id_emp;
    }

    public void setId_emp(int id_emp) {
        this.id_emp = id_emp;
    }

    public String getNom_emp() {
        return nom_emp;
    }

    public void setNom_emp(String nom_emp) {
        this.nom_emp = nom_emp;
    }

}
